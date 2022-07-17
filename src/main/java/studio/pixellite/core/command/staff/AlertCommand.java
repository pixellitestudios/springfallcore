package studio.pixellite.core.command.staff;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.messaging.Channel;
import me.lucko.helper.messaging.ChannelAgent;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.bootstrap.AdventureLocale;
import studio.pixellite.core.command.Command;
import studio.pixellite.core.util.Logging;
import studio.pixellite.core.util.Strings;

/**
 * Command for sending global alerts across the entire network.
 */
public class AlertCommand extends Command {
  private static final class AlertMessage {
    String serverId;
    String sender;
    String message;
  }

  private final Channel<AlertMessage> channel;

  public AlertCommand(CorePlugin plugin) {
    super(plugin);
    this.channel = plugin.getMessenger().getChannel("pixellite-alerts", AlertMessage.class);

    ChannelAgent<AlertMessage> agent = this.channel.newAgent();
    agent.bindWith(plugin);
    agent.addListener((a, msg) -> handleAlert(msg));
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .assertPermission("pixellite.alert")
            .assertUsage("<message>")
            .handler(this::run)
            .registerAndBind(consumer, "alert");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    AlertMessage message = new AlertMessage();
    message.message = Strings.joinList(c.args());
    message.sender = c.sender().getName();
    message.serverId = getPlugin().getInstanceData().getId();
    channel.sendMessage(message);

    // send dispatch notification to all other online servers
    getPlugin().getStaffMessenger().dispatchMessage(Strings.concat(
                    "&7[&anetwork&7] ",
                    "Alert message dispatched by &2",
                    message.sender,
                    " &7from &2",
                    message.serverId),
            true);
  }

  private void handleAlert(AlertMessage message) {
    for(Player player : Bukkit.getOnlinePlayers()) {
      AdventureLocale.GLOBAL_ALERT.send(player, message.message);
    }

    Logging.info("[Alert] (Dispatched by " + message.sender + " on " +
            message.serverId + ") " + message.message);
  }
}
