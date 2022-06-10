package studio.pixellite.network.privatemessage;

import me.lucko.helper.messaging.Channel;
import me.lucko.helper.messaging.ChannelAgent;
import me.lucko.helper.metadata.Metadata;
import me.lucko.helper.metadata.MetadataKey;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import studio.pixellite.network.util.Strings;

import java.util.Set;

/**
 * A communicator for sending social spy messages across the network.
 */
public class SocialSpyCommunicator {
  private static final class SocialSpyMessage {
    private String message;
    private String to;
    private String from;
  }

  /** A public metadata key that contains players who have social spy enabled. */
  public static final MetadataKey<Boolean> SOCIAL_SPY_ENABLED =
          MetadataKey.createBooleanKey("pixellite-social-spy");

  /** The channel being used to send social spy messages. */
  private final Channel<SocialSpyMessage> channel;

  public SocialSpyCommunicator(PrivateMessageService parent) {
    this.channel = parent.getPlugin()
            .getMessenger()
            .getChannel("pixellite-social-spy", SocialSpyMessage.class);

    ChannelAgent<SocialSpyMessage> agent = this.channel.newAgent();
    agent.bindWith(parent.getPlugin());
    agent.addListener((a, message) -> handleSocialSpy(message));
  }

  /**
   * Sends a social spy message to admins across the network.
   *
   * @param message the message that was sent
   * @param to who sent the message
   * @param from who the message was sent to
   */
  public void sendSocialSpy(String message, String to, String from) {
    SocialSpyMessage ss = new SocialSpyMessage();
    ss.message = message;
    ss.to = to;
    ss.from = from;
    channel.sendMessage(ss);
  }

  /**
   * Handles an incoming social spy message over the messaging channel.
   *
   * @param message the message to handle
   */
  private void handleSocialSpy(SocialSpyMessage message) {
    Set<Player> playersEnabled = Metadata.players().getAllWithKey(SOCIAL_SPY_ENABLED).keySet();
    String notification = Strings.concat("&7[&anetwork&7] &2(SS) &f",
            message.from,
            " &7-> &f",
            message.to,
            "&7: &b",
            message.message);

    for(Player player : playersEnabled) {
      Players.msg(player, notification);
    }
  }
}
