package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.PromptModelContainer;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MenuItem extends Entity {

  private String id;
  private String name;
  private String description;
  private double price;

  public MenuItem() {
    super("menuitem.txt");
  }

  public MenuItem(HashMap<String, String> data) {
    super("menuitem.txt");
    this.fromHashMap(data);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public Entity newInstance() {
    return new MenuItem();
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new MenuItem(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> results = new LinkedHashMap<>();
    results.put("id", getId());
    results.put("name", getName());
    results.put("description", getDescription());
    results.put("price", Double.toString(getPrice()));

    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    setId(hashMap.get("id"));
    setName(hashMap.get("name"));
    setDescription(hashMap.get("description"));
    setPrice(Double.parseDouble(hashMap.get("price")));
  }

  @Override
  public PromptModelContainer promptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    return null;
  }

  @Override
  public String itemsListKey() {
    return getId();
  }

  public MenuItem[] findMenuItems(HashMap<String, String> queries) {
    return (MenuItem[]) findEntities(queries);
  }

  public MenuItem findMenuItem(HashMap<String, String> queries) {
    return (MenuItem) findEntity(queries);
  }

}
