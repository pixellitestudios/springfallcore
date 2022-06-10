package studio.pixellite.network.config.key;

import com.google.common.collect.ImmutableList;
import org.spongepowered.configurate.ConfigurationNode;
import studio.pixellite.network.config.info.SelectorItemInfo;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static studio.pixellite.network.config.key.ConfigKeyUtils.selectorNode;
import static studio.pixellite.network.config.key.ConfigKeyUtils.getStringList;
import static studio.pixellite.network.config.key.ConfigKeyUtils.generateSelectorItem;

public class ConfigKeys {
  /**
   * The ID for this specific server instance. These should not be duplicates across the network.
   */
  public static final ConfigKey<String> SERVER_ID = node ->
          node.node("server-id").getString();

  /**
   * The server's display name. To be used in commands such as /staff.
   */
  public static final ConfigKey<String> SERVER_DISPLAY_NAME = node ->
          node.node("server-display-name").getString();

  /**
   * The title of the server selector GUI.
   */
  public static final ConfigKey<String> SELECTOR_TITLE = node ->
          selectorNode(node, "title").getString();

  /**
   * The amount of rows in the server selector GUI.
   */
  public static final ConfigKey<Integer> SELECTOR_ROWS = node ->
          selectorNode(node, "rows").getInt();

  /**
   * The pane layout of the server selector GUI.
   */
  public static final ConfigKey<List<String>> SELECTOR_LAYOUT = node ->
          getStringList(selectorNode(node, "pane-scheme", "layout"));

  /**
   * The pane scheme of the server selector GUI.
   */
  public static final ConfigKey<List<int[]>> SELECTOR_SCHEME = node ->
          getStringList(selectorNode(node, "pane-scheme", "scheme"))
                  .stream()
                  .map(str -> Arrays.stream(str.split(" "))
                          .mapToInt(Integer::parseInt)
                          .toArray())
                  .collect(Collectors.toList());

  /**
   * The selector items that should go inside the GUI.
   */
  public static final ConfigKey<List<SelectorItemInfo>> SELECTOR_ITEMS = node -> {
    ImmutableList.Builder<SelectorItemInfo> builder = ImmutableList.builder();

    for (ConfigurationNode child : selectorNode(node, "items").childrenMap().values()) {
      builder.add(generateSelectorItem(child));
    }

    return builder.build();
  };

  /**
   * Uses reflection to grab the static keys in this class. Due to performance concerns,
   * this method should only be called a limited number of times.
   *
   * @return a list of the config keys
   */
  public static List<ConfigKey<?>> getAllKeys() {
    return Arrays.stream(ConfigKeys.class.getDeclaredFields())
            .filter(f -> Modifier.isStatic(f.getModifiers()))
            .map(f -> {
              try {
                return (ConfigKey<?>) f.get(ConfigKey.class);
              } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
              }
            })
            .collect(Collectors.toList());
  }
}
