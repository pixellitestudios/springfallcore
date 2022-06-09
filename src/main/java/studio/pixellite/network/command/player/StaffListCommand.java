package studio.pixellite.network.command.player;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.network.NetworkPlugin;
import studio.pixellite.network.command.Command;
import studio.pixellite.network.config.key.ConfigKeys;
import studio.pixellite.network.staff.StaffList;
import studio.pixellite.network.staff.StaffMember;

import java.util.Set;

public class StaffListCommand extends Command {
  public StaffListCommand(NetworkPlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .handler(this::run)
            .registerAndBind(consumer, "stafflist", "staff", "list");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    CommandSender sender = c.sender();

    // using legacy codes as I'm too lazy to implement a solution using
    // adventure
    Players.msg(sender, "&3&m                                                       "); // ignore column limit
    Players.msg(sender, " ");
    Players.msg(sender, " &3&lONLINE STAFF");
    Players.msg(sender, " ");

    getPlugin().getNetwork().getServers().forEach((id, server) -> {
      Set<StaffMember> members =
              server.getMetadata("stafflist", StaffList.class).getMembers();

      if(members.isEmpty()) {
        return; // skip!
      }

      boolean allHidden = members.stream().allMatch(StaffMember::isHidden);
      boolean isStaff = sender.hasPermission("pixellite.staff");

      if(!isStaff && allHidden) {
        return; // skip!
      }

      Players.msg(sender, " &3&l(*) &b" + getPlugin()
              .getConfiguration()
              .get(ConfigKeys.SERVER_DISPLAY_NAME));

      members.forEach(member -> {
        if(member.isHidden() && isStaff) {
          Players.msg(sender, "   &b&l- &f&o" + member.getName() +
                  " &7&o(" + member.getPrimaryGroup() + ")");
        } else {
          Players.msg(sender, "   &b&l- &f" + member.getName() +
                  " &7(" + member.getPrimaryGroup() + ")");
        }
      });
    });

    Players.msg(sender, " ");
    Players.msg(sender, "&3&m                                                       "); // ignore column limit
  }
}
