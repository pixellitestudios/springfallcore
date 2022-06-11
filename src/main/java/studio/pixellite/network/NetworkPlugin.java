package studio.pixellite.network;

import me.lucko.helper.Services;
import me.lucko.helper.messaging.InstanceData;
import me.lucko.helper.messaging.Messenger;
import me.lucko.helper.network.Network;
import me.lucko.helper.network.modules.FindCommandModule;
import me.lucko.helper.network.modules.NetworkStatusModule;
import me.lucko.helper.network.modules.NetworkSummaryModule;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.bootstrap.NetworkPluginBootstrap;
import studio.pixellite.network.command.player.ServersCommand;
import studio.pixellite.network.command.player.StaffListCommand;
import studio.pixellite.network.command.privatemessage.MessageCommand;
import studio.pixellite.network.command.privatemessage.ReplyCommand;
import studio.pixellite.network.command.privatemessage.ToggleSocialSpyCommand;
import studio.pixellite.network.command.redirect.HubCommand;
import studio.pixellite.network.command.redirect.RedirectCommand;
import studio.pixellite.network.command.staff.AlertCommand;
import studio.pixellite.network.command.staff.StaffChatCommand;
import studio.pixellite.network.config.key.ConfigKeys;
import studio.pixellite.network.group.PrimaryGroupTracker;
import studio.pixellite.network.group.impl.LPPrimaryGroupTracker;
import studio.pixellite.network.group.impl.SimplePrimaryGroupTracker;
import studio.pixellite.network.placeholder.PixelliteExpansion;
import studio.pixellite.network.privatemessage.PrivateMessageService;
import studio.pixellite.network.redirect.PlayerRedirector;
import studio.pixellite.network.staff.StaffListMetadataProvider;
import studio.pixellite.network.staff.messenger.StaffMessenger;
import studio.pixellite.network.util.Logging;

import java.util.HashSet;
import java.util.Set;

public class NetworkPlugin extends NetworkPluginBootstrap {
  private PlayerRedirector playerRedirector;
  private PrivateMessageService privateMessageService;
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
    Services.provide(Network.class, network);

    Logging.info("Network successfully hooked in and set up!");

    // load the player redirector
    playerRedirector = new PlayerRedirector(this);

    // load private message server
    privateMessageService = new PrivateMessageService(this);

    // bind commands
    bindCommands();

    // init group tracker
    initGroupTracker();

    // init staff messenger
    staffMessenger = new StaffMessenger(this);

    // provide API
    Services.provide(NetworkApi.class, new NetworkApiProvider());

    // register PlaceholderAPI expansion
    new PixelliteExpansion(this).register();
  }

  protected void bindCommands() {
    Logging.info("Loading commands...");

    // pixellitenetwork commands
    bindModule(new ServersCommand(this));
    bindModule(new StaffListCommand(this));
    bindModule(new AlertCommand(this));
    bindModule(new StaffChatCommand(this));
    bindModule(new MessageCommand(this));
    bindModule(new ReplyCommand(this));
    bindModule(new ToggleSocialSpyCommand(this));
    bindModule(new HubCommand(this));

    // Bind redirect commands
    for(String server : getConfiguration().get(ConfigKeys.REDIRECT_COMMANDS)) {
      bindModule(new RedirectCommand(this, server));
    }

    // helper modules
    bindModule(new NetworkStatusModule(network));
    bindModule(new FindCommandModule(network));
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

  public PrivateMessageService getPrivateMessageService() {
    return privateMessageService;
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
