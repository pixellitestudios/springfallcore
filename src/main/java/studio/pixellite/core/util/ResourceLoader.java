package studio.pixellite.core.util;

import com.google.common.collect.ImmutableList;
import studio.pixellite.core.CorePlugin;

import java.io.File;
import java.util.List;

/**
 * A utility class for loading resources onto actual files.
 *
 * @author Connor Ruesch
 */
public final class ResourceLoader {
  /**
   * Loads a resource into the plugin data folder.
   *
   * @param plugin the primary plugin instance
   * @param resourceName the name of the resource we're loading
   * @return the resource file, empty if no resource is found
   */
  public static File load(CorePlugin plugin, String resourceName) {
    File resourceFile = new File(plugin.getDataFolder(), resourceName);

    if (resourceFile.exists()) {
      return resourceFile;
    }

    resourceFile.getParentFile().mkdirs(); // result ignored
    plugin.saveResource(resourceName, false);

    return resourceFile;
  }

  // Ensure that this class is not constructed
  private ResourceLoader() {}
}
