package studio.pixellite.network.staff.messenger;

import me.lucko.helper.messaging.Channel;
import me.lucko.helper.messaging.ChannelAgent;
import me.lucko.helper.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import studio.pixellite.network.NetworkPlugin;

/**
 * Messenger for sending cross-network staff alerts.
 *
 * <p>Usage examples include sending staff alerts, dispatching
 * notifications to admins, etc.</p>
 */
public class StaffMessenger {
  private static final class StaffMessage {
    String message;
    boolean adminOnly;
  }

  /** The messaging channel for sending staff alerts. */
  private final Channel<StaffMessage> channel;

  public StaffMessenger(NetworkPlugin plugin) {
    this.channel = plugin.getMessenger()
            .getChannel("pixellite-staff-alerts", StaffMessage.class);

    ChannelAgent<StaffMessage> agent = this.channel.newAgent();
    agent.bindWith(plugin);
    agent.addListener((a, msg) -> handleStaffMessage(msg));
  }

  /**
   * Dispatches a message to all online available staff members
   * on the network in legacy color code formatting.
   *
   * @param message the message that is being sent
   * @param adminOnly if this message should only be sent to admins
   */
  public void dispatchMessage(String message, boolean adminOnly) {
    StaffMessage msg = new StaffMessage();
    msg.message = message;
    msg.adminOnly = adminOnly;
    channel.sendMessage(msg);
  }

  private void handleStaffMessage(StaffMessage message) {
    if(message.adminOnly) {
      // only send this message to admins
      for(Player player : Bukkit.getOnlinePlayers()) {
        if(player.hasPermission("pixellite.admin")) {
          Players.msg(player, message.message);
        }
      }

      return;
    }

    for(Player player : Bukkit.getOnlinePlayers()) {
      Players.msg(player, message.message);
    }
  }
}
