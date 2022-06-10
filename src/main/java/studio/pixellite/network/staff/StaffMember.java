package studio.pixellite.network.staff;

/**
 * A snapshot of an online staff member at a specific time.
 *
 * <p>Should only be created within {@link StaffListMetadataProvider}.</p>
 */
public class StaffMember {
  private final String name;
  private final String primaryGroup;
  private final boolean hidden;

  StaffMember(String name, String primaryGroup, boolean hidden) {
    this.name = name;
    this.primaryGroup = primaryGroup;
    this.hidden = hidden;
  }

  public String getName() {
    return name;
  }

  public String getPrimaryGroup() {
    return primaryGroup;
  }

  public boolean isHidden() {
    return hidden;
  }
}
