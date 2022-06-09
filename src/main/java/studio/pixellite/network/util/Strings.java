package studio.pixellite.network.util;

import java.util.List;

/**
 * A simple utility class for working with strings in a
 * cleaner way.
 */
public final class Strings {
  public static String joinList(List<String> list) {
    return String.join(" ", list);
  }

  public static String joinList(List<String> list, int startingIndex) {
    return String.join(" ", list.subList(startingIndex, list.size()));
  }

  public static String capitalizeFirst(String string) {
    return string.substring(0, 1).toUpperCase() + string.substring(1);
  }
}
