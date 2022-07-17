package studio.pixellite.core.config.cache;

import studio.pixellite.core.config.Configuration;
import studio.pixellite.core.config.key.ConfigKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple cache backed by a {@link ConcurrentHashMap} for storing configuration values.
 */
@SuppressWarnings("unchecked")
public class ValuesCache {
  private final Map<ConfigKey<?>, Object> valuesMap = new ConcurrentHashMap<>();

  /** The parent of this cache. */
  private final Configuration parent;

  public ValuesCache(Configuration parent) {
    this.parent = parent;
  }

  public void clearCache() {
    valuesMap.clear();
  }

  /**
   * Gets a value from the values cache.
   *
   * @param key the key to query with
   * @return the value, may be null
   * @param <T> the type of value being returned
   */
  public <T> T get(ConfigKey<T> key) {
    // unchecked cast as we know for certain that
    // the value is of the same type
    return (T) valuesMap.get(key);
  }

  /**
   * Calculates the value from a config key and adds it to the cache.
   *
   * @param key the key & value to add
   */
  public void add(ConfigKey<?> key) {
    try {
      valuesMap.putIfAbsent(key, key.getValue(parent.getNode()));
    } catch (Exception e) {
      // something went wrong, throw exception
      // into console
      throw new RuntimeException(e);
    }
  }
}
