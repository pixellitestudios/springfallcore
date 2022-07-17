package studio.pixellite.core.command.player;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;
import studio.pixellite.core.gui.ServerSelectorGui;

/**
 * Command for opening the server selector GUI.
 */
public class ServersCommand extends Command {
  public ServersCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPlayer()
            .handler(this::run)
            .registerAndBind(consumer, "server", "servers", "serverlist");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Player sender = (Player) c.sender();
    new ServerSelectorGui(sender, getPlugin()).open();
  }
}
