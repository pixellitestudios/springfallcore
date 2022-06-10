package studio.pixellite.network.command.privatemessage;

import me.lucko.helper.Commands;
import me.lucko.helper.Events;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.util.Strings;

public class ReplyCommand extends Command {
  public ReplyCommand(NetworkPlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPlayer()
            .assertUsage("<message>")
            .handler(this::run)
            .registerAndBind(consumer, "reply", "r");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Player sender = (Player) c.sender();
    String recentlyMessagedPlayer = getPlugin().getPrivateMessageService()
            .getRecentlyMessagedPlayerFor(sender);
    String message = Strings.joinList(c.args());
    String server = getPlugin().getInstanceData().getId();

    if(recentlyMessagedPlayer == null) {
      Players.msg(sender, "&cYou have not messaged anyone recently!");
      return;
    }

    // we have all the information, send private message
    getPlugin().getPrivateMessageService().sendPrivateMessage(sender,
            server,
            recentlyMessagedPlayer,
            message);
  }
}
