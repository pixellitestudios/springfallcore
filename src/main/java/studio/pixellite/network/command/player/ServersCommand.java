package studio.pixellite.network.command.player;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.gui.ServerSelectorGui;

/**
 * Command for opening the server selector GUI.
 */
public class ServersCommand extends Command {
  public ServersCommand(NetworkPlugin plugin) {
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
