package studio.pixellite.core.command.player;

import me.lucko.helper.Commands;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.utils.Players;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.CorePlugin;
import studio.pixellite.core.command.Command;
import studio.pixellite.core.staff.StaffList;
import studio.pixellite.core.staff.StaffMember;
import studio.pixellite.core.util.Strings;

import java.util.Set;

public class StaffListCommand extends Command {
  public StaffListCommand(CorePlugin plugin) {
    super(plugin);
  }

  @Override
  public void setup(@NotNull TerminableConsumer consumer) {
    Commands.create()
            .handler(this::run)
            .registerAndBind(consumer, "stafflist", "staff");
  }

  @Override
  public <T extends CommandSender> void run(CommandContext<T> c) {
    CommandSender sender = c.sender();

    // using legacy codes as I'm too lazy to implement a solution using
    // adventure
    Players.msg(sender, "&3&m                                                       ");
    Players.msg(sender, " ");
    Players.msg(sender, " &3&lONLINE STAFF");
    Players.msg(sender, " ");

    getPlugin().getNetwork().getServers().forEach((id, server) -> {
      if(!server.isOnline()) {
        return; // server isn't online, skip!
      }

      Set<StaffMember> members =
              server.getMetadata("stafflist", StaffList.class).getMembers();

      if(members.isEmpty()) {
        return; // skip, no staff members are on this server, so we don't need to show
      }

      boolean allHidden = members.stream().allMatch(StaffMember::isHidden);
      boolean isStaff = sender.hasPermission("pixellite.staff");

      if(!isStaff && allHidden) {
        return; // skip, all staff members are hidden and the player isn't a staff member
      }

      Players.msg(sender, " &3&l➥ &b" + Strings.capitalizeFirst(id));

      members.forEach(member -> {
        if(member.isHidden()) { // member is hidden
          if(isStaff) { // player is staff, so send
            Players.msg(sender, Strings.concat("   &b&l-&f&o ",
                    member.getName(),
                    " &7&o(", member.getPrimaryGroup(),
                    ")"));
          }
        } else { // member is not hidden and player is not staff
          Players.msg(sender, Strings.concat("   &b&l-&f ",
                  member.getName(),
                  " &7(", member.getPrimaryGroup(),
                  ")"));
        }
      });

      Players.msg(sender, " ");
    });

    Players.msg(sender, " ");
    Players.msg(sender, "&3&m                                                       ");
  }
}
