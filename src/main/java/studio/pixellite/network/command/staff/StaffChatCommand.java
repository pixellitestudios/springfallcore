package studio.pixellite.network.command.staff;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.util.Logging;
import studio.pixellite.network.util.Strings;

public class StaffChatCommand extends Command {
  public StaffChatCommand(NetworkPlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.alert")
            .assertPlayer()
            .assertUsage("<message>")
            .handler(this::run)
            .registerAndBind(consumer, "alert");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    String message = Strings.joinList(c.args());

    getPlugin().getStaffMessenger().dispatchMessage("&3[&f" +
            getPlugin().getInstanceData().getId() + "&3] " +
            c.sender().getName() + ": &b" + message, false);

    Logging.info("[SC] " + c.sender().getName() + ": " + message);
  }
}
