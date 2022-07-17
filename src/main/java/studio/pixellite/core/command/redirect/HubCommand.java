package studio.pixellite.core.command.redirect;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;
import studio.pixellite.core.config.key.ConfigKeys;

public class HubCommand extends Command {
  public HubCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    // Server is not a hub server, no need to register
    if(getPlugin().getConfiguration().get(ConfigKeys.IS_HUB_SERVER)) {
      return;
    }

    Commands.create()
            .assertPlayer()
            .handler(this::run)
            .registerAndBind(consumer, "hub");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Player player = (Player) c.sender();
    getPlugin().getPlayerRedirector().attemptRedirect(player, "hub");
  }
}
