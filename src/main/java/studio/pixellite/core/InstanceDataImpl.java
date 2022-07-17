package studio.pixellite.core;

import me.lucko.helper.messaging.InstanceData;
import org.jetbrains.annotations.NotNull;
import studio.pixellite.core.config.key.ConfigKeys;

import java.util.HashSet;
import java.util.Set;

/**
 * A simple implementation of {@link InstanceData}.
 */
public class InstanceDataImpl implements InstanceData {
  private final CorePlugin plugin;

  public InstanceDataImpl(CorePlugin plugin) {
    this.plugin = plugin;
  }

  @NotNull
  @Override
  public String getId() {
    return plugin.getConfiguration().get(ConfigKeys.SERVER_ID);
  }

  @NotNull
  @Override
  public Set<String> getGroups() {
    return new HashSet<>();
  }
}
