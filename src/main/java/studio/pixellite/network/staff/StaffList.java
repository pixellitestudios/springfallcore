package studio.pixellite.network.staff;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a list of online staff members at a specific time.
 */
public class StaffList {
  private final Set<StaffMember> members;

  protected StaffList(Set<StaffMember> members) {
    this.members = members;
  }

  /**
   * Gets all the available online members at this time
   *
   * @return a copy of the initial members set to be interacted with
   */
  public Set<StaffMember> getMembers() {
    return ImmutableSet.copyOf(members);
  }
}
