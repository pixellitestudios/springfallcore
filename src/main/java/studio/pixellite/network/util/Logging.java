package studio.pixellite.network.util;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * A utility class for statically logging messages to
 * the plugin's logger.
 */
public final class Logging {
  private static final Logger PLUGIN_LOGGER =
          JavaPlugin.getProvidingPlugin(Logging.class).getLogger();

  public static void info(String message) {
    PLUGIN_LOGGER.info(message);
  }

  public static void warning(String message) {
    PLUGIN_LOGGER.warning(message);
  }

  public static void severe(String message) {
    PLUGIN_LOGGER.severe(message);
  }

  // Ensure that this class cannot be constructed
  private Logging() {}
}
