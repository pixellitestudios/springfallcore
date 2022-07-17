package studio.pixellite.core.command.staff;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;
import studio.pixellite.core.util.Logging;
import studio.pixellite.core.util.Strings;

/**
 * Command for sending staff chat messages.
 */
public class StaffChatCommand extends Command {
  public StaffChatCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.staff")
            .assertPlayer()
            .assertUsage("<message>")
            .handler(this::run)
            .registerAndBind(consumer, "staffchat", "sc");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    String message = Strings.joinList(c.args());

    getPlugin().getStaffMessenger().dispatchMessage(Strings.concat(
            "&3[&f",
            getPlugin().getInstanceData().getId(),
            "&3] ", c.sender().getName(),
            ": &b",
            message),
            false);

    Logging.info("[SC] " + c.sender().getName() + ": " + message);
  }
}
