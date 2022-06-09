package studio.pixellite.network.group;

import org.bukkit.entity.Player;

/**
 * A utility for finding the name of a player's primary group.
 *
 * <p>Implemented based on the server's permissions plugin.
 * (I.E. LuckPerms, PermissionsEx, GroupManager, etc.)</p>
 */
public interface PrimaryGroupTracker {
  /**
   * Gets the ane of the player's primary group.
   *
   * @param player the player to query with
   * @return the name of the player's primary group.
   */
  String getPrimaryGroup(Player player);
}
