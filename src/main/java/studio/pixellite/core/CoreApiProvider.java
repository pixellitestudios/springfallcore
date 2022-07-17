package studio.pixellite.core;

import org.bukkit.entity.Player;
import studio.pixellite.core.gui.ServerSelectorGui;

/**
 * An implementation of {@link CoreApi}.
 */
public class CoreApiProvider implements CoreApi {
  private final CorePlugin plugin;

  public CoreApiProvider(CorePlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public void redirectPlayer(Player player, String server) {
    plugin.getPlayerRedirector().attemptRedirect(player, server);
  }

  @Override
  public void openServerSelector(Player player) {
    new ServerSelectorGui(player, plugin).open();
  }

  @Override
  public String getPlayerPrimaryGroup(Player player) {
    return plugin.getPrimaryGroupTracker().getPrimaryGroup(player);
  }
}
