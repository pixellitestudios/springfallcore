package studio.pixellite.network.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * A simple utility using adventure that deserializes Gui lore into
 * a component-based format.
 */
public final class GuiTextDeserializer {
  /**
   * Deserializes a string into a single component.
   *
   * @param string the string to deserialize
   * @return the new component
   */
  public static Component deserialize(String string) {
    // by default, minecraft items have italicized lore and names,
    // so we need to disable that
    return LegacyComponentSerializer.legacyAmpersand().deserialize(string)
            .decoration(TextDecoration.ITALIC, false);
  }

  // ensure that this class is not constructed
  private GuiTextDeserializer() {}
}
