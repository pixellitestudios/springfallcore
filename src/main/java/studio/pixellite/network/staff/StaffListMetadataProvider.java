package studio.pixellite.network.staff;

import me.lucko.helper.network.metadata.ServerMetadata;
import me.lucko.helper.network.metadata.ServerMetadataProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import studio.pixellite.network.NetworkPlugin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StaffListMetadataProvider implements ServerMetadataProvider {
  private final NetworkPlugin plugin;

  public StaffListMetadataProvider(NetworkPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public Iterable<ServerMetadata> provide() {
    Set<StaffMember> members = new HashSet<>();

    for(Player player : Bukkit.getOnlinePlayers()) {
      if(player.hasPermission("pixellite.staff")) {
        members.add(StaffMember.builder()
                .setName(player.getName())
                .setPrimaryGroup(plugin.getPrimaryGroupTracker()
                        .getPrimaryGroup(player))
                .setHidden(player.hasPermission("pixellite.hidden"))
                .build());
      }
    }

    return Collections.singleton(ServerMetadata.of("stafflist",
            new StaffList(members),
            StaffList.class));
  }
}
