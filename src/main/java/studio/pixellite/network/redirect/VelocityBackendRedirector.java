package studio.pixellite.network.redirect;

import me.lucko.helper.Services;
import me.lucko.helper.messaging.bungee.BungeeCord;
import me.lucko.helper.network.redirect.PlayerRedirector;
import me.lucko.helper.profiles.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VelocityBackendRedirector implements PlayerRedirector {
  private final BungeeCord bungeeMessenger;

  public VelocityBackendRedirector() {
    this.bungeeMessenger = Services.load(BungeeCord.class);
  }

  @Override
  public void redirectPlayer(@NotNull String serverId, @NotNull Player player) {
    bungeeMessenger.connect(player, serverId);
  }

  @Override
  public void redirectPlayer(@NotNull String serverId, @NotNull Profile profile) {
    Player player = Bukkit.getPlayer(profile.getUniqueId());
    if(player == null) {
      return;
    }

    // player is online, redirect them
    redirectPlayer(serverId, player);
  }
}
