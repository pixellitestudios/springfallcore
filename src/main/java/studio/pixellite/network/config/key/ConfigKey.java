package studio.pixellite.network.config.key;

import org.spongepowered.configurate.ConfigurationNode;

/**
 * A functional interface for accessing specific values within the given configuration node.
 *
 * @param <T> the type of value that is being accessed
 */
@FunctionalInterface
public interface ConfigKey<T> {
  /**
   * Gets a value from the given configuration node.
   *
   * @param node the node to obtain from
   * @return the obtained value, can be null
   */
  T getValue(ConfigurationNode node);
}
