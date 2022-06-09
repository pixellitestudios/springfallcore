package studio.pixellite.network.bootstrap;

import me.lucko.helper.plugin.ExtendedJavaPlugin;
import studio.pixellite.network.config.Configuration;

public abstract class NetworkPluginBootstrap extends ExtendedJavaPlugin {
  private final String pluginName = "PixelliteNetwork";
  private final String pluginVersion = "1.1.0";
  private final String pluginAuthor = "Pixellite Studios";

  // load configuration in Bootstrap to load before
  // anything else
  private Configuration configuration;

  @Override
  protected void enable() {
    saveDefaultConfig();
    configuration = new Configuration(this);

    init();
  }

  /** The abstract function to init the rest of the plugin. */
  protected abstract void init();

  public String getPluginName() {
    return pluginName;
  }

  public String getPluginVersion() {
    return pluginVersion;
  }

  public String getPluginAuthor() {
    return pluginAuthor;
  }

  public Configuration getConfiguration() {
    return configuration;
  }
}
