package studio.pixellite.network.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.pixellite.network.NetworkPlugin;

public class PixelliteExpansion extends PlaceholderExpansion {
  private final NetworkPlugin plugin;

  public PixelliteExpansion(NetworkPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public @NotNull String getAuthor() {
    return plugin.getPluginAuthor();
  }

  @Override
  public @NotNull String getIdentifier() {
    return "pixellitenetwork";
  }

  @Override
  public @Nullable String getRequiredPlugin() {
    return "helper";
  }

  @Override
  public @NotNull String getVersion() {
    return plugin.getPluginVersion();
  }

  @Override
  public @Nullable String onPlaceholderRequest(Player player,
                                               @NotNull String params) {
    return super.onPlaceholderRequest(player, params);
  }
}
