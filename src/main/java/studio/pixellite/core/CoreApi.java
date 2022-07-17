package studio.pixellite.core;

import org.bukkit.entity.Player;

/**
 * A simple API for interacting with Pixellite's baseline network features.
 */
public interface CoreApi {
  /**
   * Redirects a player to the given server.
   *
   * @param player the player to redirect
   * @param server the player to redirect to
   */
  void redirectPlayer(Player player, String server);

  /**
   * Opens the server selector GUI for a player.
   *
   * @param player the player to open for
   */
  void openServerSelector(Player player);

  /**
   * Gets a player's primary group name.
   *
   * @param player the player to query with
   * @return String the name of the player's primary gorup
   */
  String getPlayerPrimaryGroup(Player player);
}
