package studio.pixellite.network;

import org.bukkit.entity.Player;
import studio.pixellite.network.gui.ServerSelectorGui;

/**
 * An implementation of {@link NetworkApi}.
 */
public class NetworkApiProvider implements NetworkApi {
  private final NetworkPlugin plugin;

  public NetworkApiProvider(NetworkPlugin plugin) {
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
