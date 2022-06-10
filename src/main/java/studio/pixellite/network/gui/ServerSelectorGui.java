package studio.pixellite.network.gui;

import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.Item;
import me.lucko.helper.menu.scheme.MenuScheme;
import me.lucko.helper.menu.scheme.StandardSchemeMappings;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.config.info.SelectorItemInfo;
import studio.pixellite.network.config.key.ConfigKeys;

import java.util.List;

/**
 * An extension of {@link Gui} that opens the server's realm selector GUI.
 */
public class ServerSelectorGui extends Gui {
  private final NetworkPlugin plugin;

  public ServerSelectorGui(Player player, NetworkPlugin plugin) {
    super(player,
            plugin.getConfiguration().get(ConfigKeys.SELECTOR_ROWS),
            plugin.getConfiguration().get(ConfigKeys.SELECTOR_TITLE));
    this.plugin = plugin;
  }

  @Override
  public void redraw() {
    if(isFirstDraw()) {
      drawMenuScheme();
    }

    drawSelectorItems();
  }

  private void drawMenuScheme() {
    MenuScheme menuScheme = new MenuScheme(StandardSchemeMappings.STAINED_GLASS);
    List<String> layout = plugin.getConfiguration().get(ConfigKeys.SELECTOR_LAYOUT);
    List<int[]> scheme = plugin.getConfiguration().get(ConfigKeys.SELECTOR_SCHEME);

    for(String row : layout) {
      menuScheme.mask(row);
    }

    for(int[] values : scheme) {
      menuScheme.scheme(values);
    }

    menuScheme.apply(this);
  }

  private void drawSelectorItems() {
    List<SelectorItemInfo> infos = plugin.getConfiguration().get(ConfigKeys.SELECTOR_ITEMS);

    for(SelectorItemInfo info : infos) {
      ItemStack itemStack = new ItemStack(info.getMaterial(), 1);
      ItemMeta meta = itemStack.getItemMeta();

      meta.displayName(info.getName());
      meta.lore(info.getLore());
      meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
      itemStack.setItemMeta(meta);

      setItem(info.getSlot(), Item.builder(itemStack).build());
    }
  }
}
