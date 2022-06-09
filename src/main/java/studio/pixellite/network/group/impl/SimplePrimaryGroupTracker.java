package studio.pixellite.network.group.impl;

import org.bukkit.entity.Player;
import studio.pixellite.network.group.PrimaryGroupTracker;

public class SimplePrimaryGroupTracker implements PrimaryGroupTracker {
  @Override
  public String getPrimaryGroup(Player player) {
    return "Player";
  }
}
