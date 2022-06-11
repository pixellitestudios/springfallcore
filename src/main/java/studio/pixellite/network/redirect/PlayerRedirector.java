package studio.pixellite.network.redirect;

import me.lucko.helper.network.redirect.RedirectSystem;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.config.key.ConfigKeys;

import java.util.HashMap;

/**
 * A system for handling requests to redirect a player to another server. Backed
 * by {@link RedirectSystem}.
 */
public class PlayerRedirector {
  /** The primary redirect system backing this redirector. */
  private final RedirectSystem redirectSystem;

  public PlayerRedirector(NetworkPlugin plugin) {
    this.redirectSystem = RedirectSystem.create(plugin.getMessenger(),
            plugin.getInstanceData(),
            new VelocityBackendRedirector());

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
    String serverName = server.toLowerCase();
    Players.msg(player, "&cAttempting to send you to " + server + "...");

    this.redirectSystem.redirectPlayer(serverName, player, new HashMap<>())
            .thenAcceptAsync(response -> {
              // attempted server is offline?
              if(response.getStatus().equals(RedirectSystem.ReceivedResponse.Status.NO_REPLY)) {
                Players.msg(player, "&cConnection to " + serverName + " timed out.");
              }
            });
  }
}
