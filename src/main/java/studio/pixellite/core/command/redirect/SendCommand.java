package studio.pixellite.core.command.redirect;

import me.lucko.helper.Commands;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;

public class SendCommand extends Command {
  public SendCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.admin")
            .assertUsage("<player> <server>")
            .handler(this::run)
            .registerAndBind(consumer, "send");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) throws CommandInterruptException {
    Player player = Bukkit.getPlayer(c.arg(0).parseOrFail(String.class));
    String targetServer = c.arg(1).parseOrFail(String.class).toLowerCase();

    if(player == null) {
      Players.msg(c.sender(), "&cThat player is not online!");
      return;
    }

    getPlugin().getPlayerRedirector().attemptRedirect(player, targetServer);
  }
}
