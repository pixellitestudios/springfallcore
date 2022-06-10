package studio.pixellite.network.config.info;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;

/**
 * Represents the information for a GUI selector.
 */
public class SelectorItemInfo {
  private final String serverId;
  private final int slot;
  private final Material material;
  private final Component name;
  private final List<Component> lore;

  public SelectorItemInfo(String serverId,
                          int slot,
                          Material material,
                          Component name,
                          List<Component> lore) {
    this.serverId = serverId;
    this.slot = slot;
    this.material = material;
    this.name = name;
    this.lore = lore;
  }

  public String getServerId() {
    return serverId;
  }

  public int getSlot() {
    return slot;
  }

  public Material getMaterial() {
    return material;
  }

  public Component getName() {
    return name;
  }

  public List<Component> getLore() {
    return lore;
  }
}
