package studio.pixellite.core.group.impl;

import org.bukkit.entity.Player;
import studio.pixellite.core.group.PrimaryGroupTracker;

public class SimplePrimaryGroupTracker implements PrimaryGroupTracker {
  @Override
  public String getPrimaryGroup(Player player) {
    return "Player";
  }
}
