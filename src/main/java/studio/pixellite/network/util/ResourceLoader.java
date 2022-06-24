package studio.pixellite.network.util;

import com.google.common.collect.ImmutableList;
import studio.pixellite.network.NetworkPlugin;

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
  public static File load(NetworkPlugin plugin, String resourceName) {
    File resourceFile = new File(plugin.getDataFolder(), resourceName);

    if (resourceFile.exists()) {
      return resourceFile;
    }

    resourceFile.getParentFile().mkdirs(); // result ignored
    plugin.saveResource(resourceName, false);

    return resourceFile;
  }

  /**
   * Loads multiple resources by recursively calling
   * {@link #load(NetworkPlugin, String)} until all resources are loaded.
   *
   * @param plugin the primary plugin instance
   * @param resourceNames the names of the individual resources
   * @return an immutable list of resource files
   */
  public static List<File> loadMultiple(NetworkPlugin plugin,
                                        String... resourceNames) {
    ImmutableList.Builder<File> builder = ImmutableList.builder();

    for(String name : resourceNames) {
      builder.add(load(plugin, name));
    }

    return builder.build();
  }

  // Ensure that this class is not constructed
  private ResourceLoader() {}
}
