package studio.pixellite.core.command;

import me.lucko.helper.Commands;
import me.lucko.helper.command.CommandInterruptException;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;

/**
 * An organized, more clean way of running executions for helper generated commands.
 *
 * <p>Commands are built in {@link #setup(TerminableConsumer)} and their
 * execution method is ran via {@link #run(CommandContext)}.</p>
 */
public abstract class Command implements TerminableModule {
  private final CorePlugin plugin;

  public Command(CorePlugin plugin) {
    this.plugin = plugin;
  }

  public CorePlugin getPlugin() {
    return plugin;
  }

  /**
   * Sets up this command. Typically, used with helper's {@link Commands} library to register
   * and bind to the given consumer
   *
   * @param consumer the terminable consumer to bind with
   */
  @Override
  public abstract void setup(@NotNull TerminableConsumer consumer);

  /**
   * Runs the execution logic for this command.
   *
   * @param c the command handler
   * @param <T> the type of sender this handler is providing
   * @throws CommandInterruptException if the command is interrupted
   */
  public abstract <T extends CommandSender> void run(CommandContext<T> c)
          throws CommandInterruptException;
}
