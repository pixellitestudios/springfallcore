package studio.pixellite.core.staff;

import me.lucko.helper.network.metadata.ServerMetadata;
import me.lucko.helper.network.metadata.ServerMetadataProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import studio.pixellite.core.CorePlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A network metadata provider for providing staff list metadata to each server.
 */
public class StaffListMetadataProvider implements ServerMetadataProvider {
  private final CorePlugin plugin;

  public StaffListMetadataProvider(CorePlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public Iterable<ServerMetadata> provide() {
    Set<StaffMember> members = new HashSet<>();

    for(Player player : Bukkit.getOnlinePlayers()) {
      if(!player.hasPermission("pixellite.staff")) {
        continue;
      }

      String name = player.getName();
      String primaryGroup = plugin.getPrimaryGroupTracker().getPrimaryGroup(player);
      boolean isHidden = player.hasPermission("pixellite.hidden");

      members.add(new StaffMember(name, primaryGroup, isHidden));
    }

    return Collections.singleton(ServerMetadata.of("stafflist",
            new StaffList(members),
            StaffList.class));
  }
}
