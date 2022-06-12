package studio.pixellite.network.redirect;

import me.lucko.helper.cooldown.Cooldown;
import me.lucko.helper.cooldown.CooldownMap;
import me.lucko.helper.network.redirect.RedirectSystem;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.config.key.ConfigKeys;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A system for handling requests to redirect a player to another server. Backed
 * by {@link RedirectSystem}.
 */
public class PlayerRedirector {
  /** The primary plugin instance. */
  private final NetworkPlugin plugin;

  /** The backend player redirector. */
  private final me.lucko.helper.network.redirect.PlayerRedirector redirector;

  /** The primary redirect system backing this redirector. */
  private final RedirectSystem redirectSystem;

  /** A cooldown map for server redirects. To prevent players from excessively switching servers. */
  private final CooldownMap<UUID> cooldowns =
          CooldownMap.create(Cooldown.of(6, TimeUnit.SECONDS));

  public PlayerRedirector(NetworkPlugin plugin) {
    this.plugin = plugin;
    this.redirector = new VelocityBackendRedirector();
    this.redirectSystem = RedirectSystem.create(plugin.getMessenger(),
            plugin.getInstanceData(),
            this.redirector);

    // Disable redirect queue ensure if the server the plugin
    // is installed on is a hub server
    if(plugin.getConfiguration().get(ConfigKeys.IS_HUB_SERVER)) {
      this.redirectSystem.setEnsure(false);
    }
  }

  /**
   * Attempts to redirect a player to the desired server.
   *
   * @param player the player to redirect
   * @param server the name of the server to redirect to
   */
  public void attemptRedirect(Player player, String server) {
    // test the player for a cooldown
    if(!cooldowns.test(player.getUniqueId())) {
      Players.msg(player, "&cPlease wait before switching servers again!");
      return;
    }

    String serverName = server.toLowerCase();
    Players.msg(player, "&cAttempting to send you to " + server + "...");

    // check if the server that is being requested is the same server
    if(plugin.getInstanceData().getId().equals(serverName)) {
      Players.msg(player, "&cYou're already connected to that server!");
      return;
    }

    this.redirectSystem.redirectPlayer(serverName, player, new HashMap<>())
            .thenAcceptAsync(response -> {
              RedirectSystem.ReceivedResponse.Status status = response.getStatus();

              if(status.equals(RedirectSystem.ReceivedResponse.Status.NO_REPLY)) {
                // attempted server is offline?
                Players.msg(player, "&cConnection to " + serverName + " timed out.");
              } else if(status.equals(RedirectSystem.ReceivedResponse.Status.ALLOWED)) {
                // redirect player
                redirector.redirectPlayer(serverName, player);
              }
            });
  }
}
