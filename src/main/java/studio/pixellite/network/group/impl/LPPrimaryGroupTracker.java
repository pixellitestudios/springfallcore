package studio.pixellite.network.group.impl;

import me.lucko.helper.Services;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.entity.Player;
import studio.pixellite.network.group.PrimaryGroupTracker;
import studio.pixellite.network.util.Strings;

/**
 * An implementation of {@link PrimaryGroupTracker} for LuckPerms.
 */
public class LPPrimaryGroupTracker implements PrimaryGroupTracker {
  @Override
  public String getPrimaryGroup(Player player) {
    LuckPerms luckPerms = Services.get(LuckPerms.class).orElseThrow(() ->
            new UnsupportedOperationException("LuckPerms is not installed!"));

    User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);

    return Strings.capitalizeFirst(user.getPrimaryGroup());
  }
}
