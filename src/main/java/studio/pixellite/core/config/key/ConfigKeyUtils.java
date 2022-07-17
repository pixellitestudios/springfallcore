package studio.pixellite.core.config.key;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import studio.pixellite.core.config.info.SelectorItemInfo;
import studio.pixellite.core.util.GuiTextDeserializer;

import java.util.List;
import java.util.Objects;

/**
 * A set of utility functions to make generating {@link ConfigKey} easier.
 */
public class ConfigKeyUtils {
  /**
   * Utility method for accessing the selector node in a cleaner way.
   *
   * @param node the configuration root
   * @return the selector node
   */
  protected static ConfigurationNode selectorNode(ConfigurationNode node, Object... searchParams) {
    return node.node("server-selector").node(searchParams);
  }

  /**
   * Utility method for error handling & null checking string lists.
   *
   * @param node the node to get the list from
   * @return the list, assuming there was no issue in serialization
   */
  protected static List<String> getStringList(ConfigurationNode node) {
    try {
      List<String> list = node.getList(String.class);
      Objects.requireNonNull(list);
      return list;
    } catch (SerializationException e) {
      // Check configuration -- most likely occurred due
      // to an error in how the values were entered
      throw new RuntimeException(e);
    }
  }

  /**
   * Generates a new {@link SelectorItemInfo} using the values given in the provided node.
   *
   * @param node the node to generate with
   * @return the new {@link SelectorItemInfo}
   */
  protected static SelectorItemInfo generateSelectorItem(ConfigurationNode node) {
    String serverId = node.node("server").getString("global");
    int slot = node.node("slot").getInt();
    Material material = Material.matchMaterial(node.node("material").getString("DIAMOND_PICKAXE"));
    Component name = GuiTextDeserializer.deserialize(node.node("name").getString(" "));

    List<Component> lore = getStringList(node.node("lore"))
            .stream()
            .map(GuiTextDeserializer::deserialize)
            .toList();

    return new SelectorItemInfo(serverId, slot, material, name, lore);
  }
}
