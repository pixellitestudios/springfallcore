package studio.pixellite.network.command.redirect;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;

public class RedirectCommand extends Command {
  private final String boundServer;

  public RedirectCommand(NetworkPlugin plugin, String boundServer) {
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
