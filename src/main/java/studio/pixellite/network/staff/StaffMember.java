package studio.pixellite.network.staff;

/**
 * A snapshot of an online staff member at a specific time.
 *
 * <p>Specifically designed to be delivered as apart of a server's
 * metadata, creation of this object should be limited to
 * {@link StaffListMetadataProvider} only.</p>
 */
public class StaffMember {
  protected static final class Builder {
    private String name;
    private String primaryGroup;
    private boolean hidden;
    private long timestamp;

    private Builder() {
      this.timestamp = System.currentTimeMillis();
    }

    Builder setName(String name) {
      this.name = name;
      return this;
    }

    Builder setPrimaryGroup(String primaryGroup) {
      this.primaryGroup = primaryGroup;
      return this;
    }

    Builder setHidden(boolean hidden) {
      this.hidden = hidden;
      return this;
    }

    StaffMember build() {
      return new StaffMember(name, primaryGroup, hidden, timestamp);
    }
  }

  protected static Builder builder() {
    return new Builder();
  }

  private final String name;
  private final String primaryGroup;
  private final boolean hidden;
  private final long timestamp;

  private StaffMember(String name,
              String primaryGroup,
              boolean hidden,
              long timestamp) {
    this.name = name;
    this.primaryGroup = primaryGroup;
    this.hidden = hidden;
    this.timestamp = timestamp;
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

  public long getTimestamp() {
    return timestamp;
  }
}
