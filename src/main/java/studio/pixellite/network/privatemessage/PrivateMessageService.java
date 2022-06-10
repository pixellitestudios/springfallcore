package studio.pixellite.network.privatemessage;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.lucko.helper.Schedulers;
import me.lucko.helper.messaging.Messenger;
import me.lucko.helper.messaging.conversation.*;
import me.lucko.helper.promise.Promise;
import me.lucko.helper.utils.Players;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.bootstrap.AdventureLocale;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A private messaging service for easy and effective use of cross-network private messaging backed
 * by helper's {@link Messenger} system.
 */
public class PrivateMessageService {
  /**
   * Inner-class for delivering private messages across the network.
   */
  private static final class PrivateMessage implements ConversationMessage {
    private final UUID conversationId = UUID.randomUUID();
    private final String from;
    private final String to;
    private final String server;
    private final String message;

    private PrivateMessage(String from, String to, String server, String message) {
      this.from = from;
      this.to = to;
      this.server = server;
      this.message = message;
    }

    @NotNull
    @Override
    public UUID getConversationId() {
      return conversationId;
    }
  }

  /**
   * Inner-class for replying to private messages that were delivered.
   */
  private static final class PrivateMessageReply implements ConversationMessage {
    private final UUID conversationId;
    private final String sent;

    private PrivateMessageReply(UUID conversationId, String sent) {
      this.conversationId = conversationId;
      this.sent = sent;
    }

    @NotNull
    @Override
    public UUID getConversationId() {
      return conversationId;
    }
  }

  /** A cache of users who are able to reply to messages. */
  Cache<UUID, String> recentlyMessagedPlayers = CacheBuilder.newBuilder()
          .expireAfterAccess(10, TimeUnit.MINUTES)
          .build();

  /** The primary plugin instance. */
  private final NetworkPlugin plugin;

  /** The primary conversation channel for sending private messages. */
  private final ConversationChannel<PrivateMessage, PrivateMessageReply> channel;

  /** The social spy communicator. Sends SS messages across the network. */
  private final SocialSpyCommunicator socialSpy;

  public PrivateMessageService(NetworkPlugin plugin) {
    this.plugin = plugin;
    this.channel = plugin.getMessenger().getConversationChannel("pixellite-pms",
            PrivateMessage.class,
            PrivateMessageReply.class);
    this.socialSpy = new SocialSpyCommunicator(this);

    ConversationChannelAgent<PrivateMessage, PrivateMessageReply> agent = this.channel.newAgent();
    agent.bindWith(plugin);
    agent.addListener((a, message) -> ConversationReply.ofPromise(handlePrivateMessage(message)));
  }

  /**
   * Sends a private message to the specified player on the network.
   *
   * @param sender the sender of this private message
   * @param server the server the message is being sent from
   * @param to the player we're sending this to
   * @param message the message that is being sent
   */
  public void sendPrivateMessage(Player sender, String server, String to, String message) {
    PrivateMessage pm = new PrivateMessage(sender.getName(),
            to,
            plugin.getInstanceData().getId(),
            message);

    channel.sendMessage(pm, new ConversationReplyListener<PrivateMessageReply>() {
      @NotNull
      @Override
      public RegistrationAction onReply(@NotNull PrivateMessageService.PrivateMessageReply reply) {
        AdventureLocale.PRIVATE_MESSAGE_TO.send(sender, to, server, message);
        return RegistrationAction.STOP_LISTENING;
      }

      @Override
      public void onTimeout(@NotNull List<PrivateMessageReply> replies) {
        Players.msg(sender, "&cCould not deliver message to '" +
                to +
                "'. Maybe they're offline?");
      }
    }, 3, TimeUnit.SECONDS);
  }

  /**
   * Gets the name of the player's recently messaged server
   *
   * @param player the unique Id of the player
   * @return the player's recently messaged player
   */
  public String getRecentlyMessagedPlayerFor(Player player) {
    return recentlyMessagedPlayers.getIfPresent(player.getUniqueId());
  }

  protected NetworkPlugin getPlugin() {
    return plugin;
  }

  /**
   * Handles an incoming private message.
   *
   * @param message the message being handled
   * @return a promise containing the message reply
   */
  private Promise<PrivateMessageReply> handlePrivateMessage(PrivateMessage message) {
    return Schedulers.sync().supply(() -> {
      Player player = Bukkit.getPlayer(message.to);
      if(player == null) {
        return null;
      }

      // Send message to player
      AdventureLocale.PRIVATE_MESSAGE_FROM.send(
              player,
              message.from,
              message.server,
              message.message);

      recentlyMessagedPlayers.put(player.getUniqueId(), message.from);

      // Send out social spy notification
      socialSpy.sendSocialSpy(message.message, message.to, message.from);

      return new PrivateMessageReply(message.getConversationId(), player.getName());
    });
  }
}
