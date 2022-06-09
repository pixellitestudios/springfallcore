package studio.pixellite.network.command.staff;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.messaging.Channel;
import me.lucko.helper.messaging.ChannelAgent;
import me.lucko.helper.terminable.TerminableConsumer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.bootstrap.Locale;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.util.Logging;
import studio.pixellite.network.util.Strings;

public class AlertCommand extends Command {
  private static final class AlertMessage {
    String serverId;
    String sender;
    String message;
  }

  private final Channel<AlertMessage> channel;

  public AlertCommand(NetworkPlugin plugin) {
    super(plugin);
    this.channel = plugin.getMessenger().getChannel("pixellite-alerts",
            AlertMessage.class);

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
  }

  private void handleAlert(AlertMessage message) {
    for(Player player : Bukkit.getOnlinePlayers()) {
      Locale.GLOBAL_ALERT.send(player, message.message);
    }

    Logging.info("[Alert] (Dispatched by " + message.sender + " on " +
            message.serverId + ") " + message.message);

    getPlugin().getStaffMessenger().dispatchMessage("&7[&anetwork&7] " +
            "Alert message dispatched by &2" + message.sender +
            " &7from &2" + message.serverId, true);
  }
}
