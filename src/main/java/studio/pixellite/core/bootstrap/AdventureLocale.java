package studio.pixellite.core.bootstrap;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

/**
 * Class for interacting with Adventure locale. Legacy components may
 * be used under certain circumstances for more efficiency.
 */
public final class AdventureLocale {
  @FunctionalInterface
  public interface NoArg {
    void send(Player player);
  }

  @FunctionalInterface
  public interface Arg<T> {
    void send(Player player, T arg1);
  }

  @FunctionalInterface
  public interface Args3<T, S, U> {
    void send(Player player, T arg1, S arg2, U arg3);
  }

  @FunctionalInterface
  public interface Args4<T, S, U, V> {
    void send(Player player, T arg1, S arg2, U arg3, V arg4);
  }

  /** Message with RGB coloring for sending alerts accross the entire network. */
  public static final Arg<String> GLOBAL_ALERT = (player, msg) -> {
    Component component = Component.text("ALERT: ")
            .color(colorOf(0xde0000))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text(msg)
                    .color(colorOf(0xff7373))
                    .decoration(TextDecoration.BOLD, false));

    player.sendMessage(component);
  };

  /** Message with RGB color for sending private messages. */
  public static final Args3<String, String, String> PRIVATE_MESSAGE_TO = (player,
                                                                          to,
                                                                          server,
                                                                          message) -> {
    Component component = Component.text("PM ")
            .color(colorOf(0x45a2ff))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text("(" + server + ") ")
                    .decoration(TextDecoration.BOLD, false))
            .append(Component.text("You ➥ " + to + ": ")
                    .color(colorOf(0xc4c4c4))
                    .decoration(TextDecoration.BOLD, false))
            .append(Component.text(message)
                    .color(colorOf(0x45a2ff))
                    .decoration(TextDecoration.BOLD, false));

    player.sendMessage(component);
  };

  /** Message with RGB color for sending private messages. */
  public static final Args3<String, String, String> PRIVATE_MESSAGE_FROM = (player,
                                                                          from,
                                                                          server,
                                                                          message) -> {
    Component component = Component.text("PM ")
            .color(colorOf(0x45a2ff))
            .decoration(TextDecoration.BOLD, true)
            .append(Component.text("(" + server + ") ")
                    .decoration(TextDecoration.BOLD, false))
            .append(Component.text(from + " ➥ You"  + ": ")
                    .color(colorOf(0xc4c4c4))
                    .decoration(TextDecoration.BOLD, false))
            .append(Component.text(message)
                    .color(colorOf(0x45a2ff))
                    .decoration(TextDecoration.BOLD, false));

    player.sendMessage(component);
  };

  /**
   * Simple utility function for getting the color of an integer value in a more clean manner.
   *
   * @param color the color value to get
   * @return the text color
   */
  private static TextColor colorOf(int color) {
    return TextColor.color(color);
  }
}
