package studio.pixellite.network.config.key;

import com.google.common.collect.ImmutableList;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ConfigKeys {
  public static final ConfigKey<String> SERVER_ID = node ->
          node.node("server-id").getString();

  public static final ConfigKey<Boolean> STAFF_CHAT_ENABLED = node ->
          node.node("staff-chat-enabled").getBoolean();

  public static final ConfigKey<String> SELECTOR_TITLE = node ->
          node.node("server-selector", "title").getString();

  public static final ConfigKey<Integer> SELECTOR_ROWS = node ->
          node.node("server-selector", "rows").getInt();

  public static final ConfigKey<List<String>> SELECTOR_LAYOUT = node -> {
    try {
      return node.node("server-selector", "pane-scheme", "layout")
              .getList(String.class);
    } catch (SerializationException e) {
      throw new RuntimeException(e);
    }
  };

  public static final ConfigKey<List<int[]>> SELECTOR_SCHEME = node -> {
    try {
      List<String> scheme = node.node("server-selector",
                      "pane-scheme",
                      "scheme")
              .getList(String.class);

      Objects.requireNonNull(scheme);

      return scheme.stream()
              .map(str -> Arrays.stream(str.split(" "))
                      .mapToInt(Integer::parseInt)
                      .toArray())
              .collect(Collectors.toList());
    } catch (SerializationException e) {
      throw new RuntimeException(e);
    }
  };

  public static List<ConfigKey<?>> getAllKeys() {
    return ImmutableList.<ConfigKey<?>>builder()
            .add(SERVER_ID,
                    STAFF_CHAT_ENABLED,
                    SELECTOR_TITLE,
                    SELECTOR_ROWS,
                    SELECTOR_LAYOUT,
                    SELECTOR_SCHEME)
            .build();
  }
}
