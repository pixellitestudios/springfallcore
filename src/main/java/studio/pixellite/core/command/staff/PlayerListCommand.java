package studio.pixellite.core.command.staff;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;

import java.util.List;

public class PlayerListCommand extends Command {
  public PlayerListCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.playerlist")
            .handler(this::run)
            .registerAndBind(consumer, "playerlist", "list", "glist");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    Players.msg(c.sender(), "&3Online Players: ");

    getPlugin().getNetwork().getServers().forEach((s, server) -> {
      List<String> playerNames = server.getOnlinePlayers()
              .values()
              .stream()
              .map(profile -> profile.getName().orElse(""))
              .toList();

      Players.msg(c.sender(), "&b&l" +
              s +
              " &3- &b" +
              String.join(", ", playerNames));
    });
  }
}
