package studio.pixellite.network.command.privatemessage;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.metadata.Metadata;
import me.lucko.helper.metadata.MetadataMap;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.privatemessage.SocialSpyCommunicator;

import java.util.Optional;

public class ToggleSocialSpyCommand extends Command {
  public ToggleSocialSpyCommand(NetworkPlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.socialspy")
            .assertPlayer()
            .handler(this::run)
            .registerAndBind(consumer, "socialspy", "ss");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Player player = (Player) c.sender();
    MetadataMap playerMetadata = Metadata.provideForPlayer(player);
    Optional<Boolean> isEnabled = playerMetadata.get(SocialSpyCommunicator.SOCIAL_SPY_ENABLED);

    if(isEnabled.isPresent()) {
      Players.msg(player, "&7[&anetwork&7] Social spy &2disabled");
      playerMetadata.remove(SocialSpyCommunicator.SOCIAL_SPY_ENABLED);
    } else {
      Players.msg(player, "&7[&anetwork&7] Social spy &2enabled");
      playerMetadata.put(SocialSpyCommunicator.SOCIAL_SPY_ENABLED, true);
    }
  }
}
