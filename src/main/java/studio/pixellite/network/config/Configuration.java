package studio.pixellite.network.config;

import org.bukkit.Bukkit;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;
import studio.pixellite.network.bootstrap.NetworkPluginBootstrap;
import studio.pixellite.network.config.cache.ValuesCache;
import studio.pixellite.network.config.key.ConfigKey;
import studio.pixellite.network.config.key.ConfigKeys;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * The access point for the plugin's configuration file.
 *
 * <p>Values are accessed via {@link ConfigKeys} and are loaded
 * on plugin startup.</p>
 *
 * <p>Closing this object will clear all cached values.</p>
 */
public class Configuration {
  /** A cache for storing configuration key values. */
  private final ValuesCache cache = new ValuesCache(this);

  /** The plugin's bootstrap. */
  private final NetworkPluginBootstrap bootstrap;

  /** The backend configuration node being used to obtain values. */
  private final ConfigurationNode node;

  /** Boolean to ensure that keys are online loaded once at startup. */
  private boolean keysLoaded = false;

  public Configuration(NetworkPluginBootstrap bootstrap) {
    this.bootstrap = bootstrap;
    this.node = resolveNode();

    loadKeys();
  }

  public ConfigurationNode getNode() {
    return node;
  }

  /**
   * Gets a key from the cache.
   *
   * @param key the key to obtain
   * @return the value
   * @param <T> the type of value being retrieved
   */
  public <T> T get(ConfigKey<T> key) {
    T value = cache.get(key);
    Objects.requireNonNull(value);

    return value;
  }

  private void loadKeys() {
    if(keysLoaded) {
      return;
    }

    for(ConfigKey<?> key : ConfigKeys.getAllKeys()) {
      // load and calculate key values into
      // the cache.
      cache.add(key);
    }

    // while the cache does physically prevent duplicate
    // key entries, this is just a safe precaution to ensure
    // that under no circumstances are key calculations made
    // again
    keysLoaded = true;
  }

  /**
   * Generates a {@link ConfigurationNode} for the plugin's
   * config.yml file.
   *
   * <p>Should only be used when reloading or on the plugin's
   * startup.</p>
   *
   * @return the new configuration node
   */
  private ConfigurationNode resolveNode() {
    YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .path(Path.of(bootstrap.getDataFolder() + "/config.yml"))
            .build();

    ConfigurationNode root;

    try {
      root = loader.load();
    } catch (IOException e) {
      // Could not gather configuration, disable plugin
      Bukkit.getPluginManager().disablePlugin(bootstrap);
      throw new RuntimeException(e);
    }

    return root;
  }
}
