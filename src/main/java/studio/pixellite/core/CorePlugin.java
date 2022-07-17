package studio.pixellite.core;

import me.lucko.helper.Services;
import me.lucko.helper.messaging.InstanceData;
import me.lucko.helper.messaging.Messenger;
import me.lucko.helper.network.Network;
import me.lucko.helper.network.modules.FindCommandModule;
import me.lucko.helper.network.modules.NetworkStatusModule;
import me.lucko.helper.network.modules.NetworkSummaryModule;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.bootstrap.NetworkPluginBootstrap;
import studio.pixellite.core.command.player.ServersCommand;
import studio.pixellite.core.command.player.StaffListCommand;
import studio.pixellite.core.command.redirect.HubCommand;
import studio.pixellite.core.command.redirect.RedirectCommand;
import studio.pixellite.core.command.redirect.SendCommand;
import studio.pixellite.core.command.staff.AlertCommand;
import studio.pixellite.core.command.staff.PlayerListCommand;
import studio.pixellite.core.command.staff.StaffChatCommand;
import studio.pixellite.core.config.key.ConfigKeys;
import studio.pixellite.core.group.PrimaryGroupTracker;
import studio.pixellite.core.group.impl.LPPrimaryGroupTracker;
import studio.pixellite.core.group.impl.SimplePrimaryGroupTracker;
import studio.pixellite.core.placeholder.PixellitePapiExpansion;
import studio.pixellite.core.redirect.PlayerRedirector;
import studio.pixellite.core.staff.StaffListMetadataProvider;
import studio.pixellite.core.staff.messenger.StaffMessenger;
import studio.pixellite.core.util.Logging;

import java.util.HashSet;
import java.util.Set;

public class CorePlugin extends NetworkPluginBootstrap {
  private PlayerRedirector playerRedirector;
  private PrimaryGroupTracker primaryGroupTracker;
  private StaffMessenger staffMessenger;
  private Network network;
  private InstanceData instanceData;
  private Messenger messenger;

  @Override
  protected void init() {
    // Load helper network and messenger platforms
    Logging.info("Loading network...");

    messenger = Services.load(Messenger.class);
    instanceData = new InstanceData() {
      @NotNull
      @Override
      public String getId() {
        return getConfiguration().get(ConfigKeys.SERVER_ID);
      }

      @NotNull
      @Override
      public Set<String> getGroups() {
        return new HashSet<>();
      }
    };
    Services.provide(InstanceData.class, instanceData);

    network = Network.create(messenger, instanceData);
    network.bindWith(this);
    network.registerMetadataProvider(new StaffListMetadataProvider(this));
    Services.provide(Network.class, network, this, ServicePriority.Highest);

    Logging.info("Network successfully hooked in and set up!");

    // load the player redirector
    playerRedirector = new PlayerRedirector(this);

    // bind commands
    bindCommands();

    // init group tracker
    initGroupTracker();

    // init staff messenger
    staffMessenger = new StaffMessenger(this);

    // provide API
    Services.provide(CoreApi.class, new CoreApiProvider(this));

    // register PlaceholderAPI expansion
    if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      new PixellitePapiExpansion(this).register();
    }
  }

  protected void bindCommands() {
    Logging.info("Loading commands...");

    // pixellitenetwork commands
    bindModule(new ServersCommand(this));
    bindModule(new StaffListCommand(this));
    bindModule(new AlertCommand(this));
    bindModule(new StaffChatCommand(this));
    bindModule(new HubCommand(this));
    bindModule(new SendCommand(this));
    bindModule(new PlayerListCommand(this));

    // Bind redirect commands
    for(String server : getConfiguration().get(ConfigKeys.REDIRECT_COMMANDS)) {
      bindModule(new RedirectCommand(this, server));
    }

    // helper modules
    bindModule(new FindCommandModule(network));
    bindModule(new NetworkStatusModule(network));
    bindModule(new NetworkSummaryModule(network, instanceData));
  }

  protected void initGroupTracker() {
    if(getServer().getPluginManager().isPluginEnabled("LuckPerms")) {
      primaryGroupTracker = new LPPrimaryGroupTracker();
    } else {
      primaryGroupTracker = new SimplePrimaryGroupTracker();
    }
  }

  public PlayerRedirector getPlayerRedirector() {
    return playerRedirector;
  }

  public PrimaryGroupTracker getPrimaryGroupTracker() {
    return primaryGroupTracker;
  }

  public StaffMessenger getStaffMessenger() {
    return staffMessenger;
  }

  public Network getNetwork() {
    return network;
  }

  public InstanceData getInstanceData() {
    return instanceData;
  }

  public Messenger getMessenger() {
    return messenger;
  }
}
