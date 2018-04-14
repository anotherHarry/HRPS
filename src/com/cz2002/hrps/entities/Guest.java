package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.GuestIdentity;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Guest extends Entity {

  // Variable Declaration
  private GuestIdentity identity;
  private String name;
  private String address;
  private String country;
  private String gender;
  private String nationality;
  private String contact;
  private String creditCard;

  public Guest() {
    super("guest.txt");
    identity = new GuestIdentity();
  }

  public Guest(HashMap<String, String> data) {
    super("guest.txt");
    identity = new GuestIdentity();
    this.fromHashMap(data);
  }

  /**
   * Get the value of id
   * @return id Guest id
   */
  public String getId() {
    return identity.getId();
  }

  /**
   * Set the value of id
   * @param id Guest id
   */
  public void setId(String id) {
    identity.setId(id);
  }

  /**
   * Get the value of idType enum
   * @return value of idType
   */
  public GuestIdentity.IdentityType getIdentityType() {
    return identity.getIdentityType();
  }


  /**
   * Set the value of idType
   * @param identityType set enum value
   */
  public void setIdentityType(GuestIdentity.IdentityType identityType) {
    identity.setIdentityType(identityType);
  }


  /**
   * Get the value of contact
   *
   * @return the value of contact
   */
  public String getContact() {
    return contact;
  }

  /**
   * Set the value of contact
   *
   * @param contact new value of contact
   */
  public void setContact(String contact) {
    this.contact = contact;
  }


  /**
   * Get the value of nationality
   *
   * @return the value of nationality
   */
  public String getNationality() {
    return nationality;
  }

  /**
   * Set the value of nationality
   *
   * @param nationality new value of nationality
   */
  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  /**
   * Get the value of gender
   *
   * @return the value of gender
   */
  public String getGender() {
    return gender;
  }

  /**
   * Set the value of gender
   *
   * @param gender new value of gender
   */
  public void setGender(String gender) {
    this.gender = gender;
  }


  /**
   * Get the value of counGtry
   *
   * @return the value of country
   */
  public String getCountry() {
    return country;
  }

  /**
   * Set the value of country
   *
   * @param country new value of country
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * Get the value of address
   *
   * @return the value of address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Set the value of address
   *
   * @param address new value of address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Get the value of name
   *
   * @return the value of name
   */
  public String getName() {
    return name;
  }

  /**
   * Set the value of name
   *
   * @param name new value of name
   */
  public void setName(String name) {
    this.name = name;
  }

  public String getCreditCard() {
    return creditCard;
  }

  public void setCreditCard(String creditCard) {
    this.creditCard = creditCard;
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new Guest(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> results = new LinkedHashMap<>();
    results.put("id", getId());
    results.put("idType", getIdentityType().toString());
    results.put("name", getName());
    results.put("address", getAddress());
    results.put("country", getCountry());
    results.put("gender", getGender());
    results.put("nationality", getNationality());
    results.put("contact", getContact());
    results.put("creditCard", getCreditCard());

    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    setId(hashMap.get("id"));
    setIdentityType(GuestIdentity.IdentityType.valueOf(hashMap.get("idType")));
    setName(hashMap.get("name"));
    setAddress(hashMap.get("address"));
    setCountry(hashMap.get("country"));
    setGender(hashMap.get("gender"));
    setNationality(hashMap.get("nationality"));
    setContact(hashMap.get("contact"));
    setCreditCard(hashMap.get("creditCard"));
  }

  public Guest[] findGuests(HashMap<String, String> queries) {
    return (Guest[]) findEntities(queries);
  }

  public Guest findGuest(HashMap<String, String> queries) {
    return (Guest) findEntity(queries);
  }

}
