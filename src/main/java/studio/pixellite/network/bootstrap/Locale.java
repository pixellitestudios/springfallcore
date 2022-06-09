package studio.pixellite.network.bootstrap;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

/**
 * Class containing all the plugin's locale.
 */
public final class Locale {
  @FunctionalInterface
  public interface NoArg {
    void send(Player player);
  }

  @FunctionalInterface
  public interface Arg<T> {
    void send(Player player, T arg1);
  }

  @FunctionalInterface
  public interface Args4<T, S, U, V> {
    void send(Player player, T arg1, S arg2, U arg3, V arg4);
  }

  public static final Arg<String> GLOBAL_ALERT = (player, msg) -> {
    Component component = Component.text("ALERT: ")
            .color(colorOf(0xde0000))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text(msg)
                    .color(colorOf(0xff7373))
                    .decoration(TextDecoration.BOLD, false));

    player.sendMessage(component);
  };

  public static TextColor colorOf(int color) {
    return TextColor.color(color);
  }
}
