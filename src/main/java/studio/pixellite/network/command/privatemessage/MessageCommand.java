package studio.pixellite.network.command.privatemessage;

import me.lucko.helper.Commands;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.util.Strings;

public class MessageCommand extends Command {
  public MessageCommand(NetworkPlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPlayer()
            .assertUsage("<to> <message>")
            .handler(this::run)
            .registerAndBind(consumer, "msg", "pm", "whisper", "w", "message");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) throws CommandInterruptException {
    Player sender = (Player) c.sender();
    String to = c.arg(0).parseOrFail(String.class);
    String message = Strings.joinList(c.args(), 1);
    String serverId = getPlugin().getInstanceData().getId();

    // send private message
    getPlugin().getPrivateMessageService().sendPrivateMessage(sender, serverId, to, message);
  }
}
