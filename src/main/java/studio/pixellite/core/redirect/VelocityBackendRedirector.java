package studio.pixellite.core.redirect;

import me.lucko.helper.Services;
import me.lucko.helper.messaging.bungee.BungeeCord;
import me.lucko.helper.network.redirect.PlayerRedirector;
import me.lucko.helper.profiles.Profile;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.util.Logging;

public class VelocityBackendRedirector implements PlayerRedirector {
  private final BungeeCord bungeeMessenger;

  public VelocityBackendRedirector() {
    this.bungeeMessenger = Services.load(BungeeCord.class);
  }

  @Override
  public void redirectPlayer(@NotNull String serverId, @NotNull Player player) {
    bungeeMessenger.connect(player, serverId);
    Logging.info("called redirect player");
  }

  @Override
  public void redirectPlayer(@NotNull String serverId, @NotNull Profile profile) {
    // because we know for a fact that the name will not be null, we can ignore
    // the warning
    bungeeMessenger.connectOther(profile.getName().orElse(null), serverId);
    Logging.info("called redirect profile");
  }
}
