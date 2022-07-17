package studio.pixellite.core.command.redirect;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;

public class RedirectCommand extends Command {
  private final String boundServer;

  public RedirectCommand(CorePlugin plugin, String boundServer) {
    super(plugin);
    this.boundServer = boundServer;
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPlayer()
            .handler(this::run)
            .registerAndBind(consumer, boundServer);
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Player player = (Player) c.sender();
    getPlugin().getPlayerRedirector().attemptRedirect(player, boundServer);
  }
}
