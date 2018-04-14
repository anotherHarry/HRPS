package com.cz2002.hrps.models;

/**
 * GuestIdentity contains information about guest's indentity
 */
public class GuestIdentity {

  public enum IdentityType {
    PASSPORT, DRIVING_LICENSE
  }

  private IdentityType identityType;
  private String id;

  /**
   * Constructor
   */
  public GuestIdentity() {}

  /**
   * Constructor
   * @param identityType is the type
   * @param id is the unique identity value
   */
  public GuestIdentity(IdentityType identityType,  String id) {
    this.identityType = identityType;
    this.id = id;
  }

  public IdentityType getIdentityType() {
    return identityType;
  }

  public void setIdentityType(IdentityType identityType) {
    this.identityType = identityType;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
