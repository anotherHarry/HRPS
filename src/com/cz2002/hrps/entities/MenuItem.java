package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;
import com.cz2002.hrps.models.PromptModelContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

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
    if (id == null) {
      id = UUID.randomUUID().toString();
    }
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
    return new PromptModelContainer(
      "",
      new PromptModel[] {
        new PromptModel("id", "Item Id", PromptModel.InputType.STRING),
        new PromptModel("name", "Name", PromptModel.InputType.STRING),
        new PromptModel("description", "Description", PromptModel.InputType.STRING),
        new PromptModel("price", "price", PromptModel.InputType.POSITIVE_DOUBLE),
      }
    );
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("id")) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Add New Menu Item",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("description")) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Search for Menu Items",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("id")) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Edit Menu Item Details",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
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
