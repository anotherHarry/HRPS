package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.PromptModelContainer;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class OrderItem extends Entity {

  private String id;
  private int quantity;
  private String roomServiceId;
  private String menuItemId;

  private RoomService roomService;
  private MenuItem menuItem;

  public OrderItem() {
    super("orderitem.txt");
  }

  public OrderItem(HashMap<String, String> data) {
    super("orderitem.txt");
    this.fromHashMap(data);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getRoomServiceId() {
    return roomServiceId;
  }

  public void setRoomServiceId(String roomServiceId) {
    this.roomServiceId = roomServiceId;
  }

  public String getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(String menuItemId) {
    this.menuItemId = menuItemId;
  }

  public RoomService getRoomService() {
    if (roomService == null)
      roomService = new RoomService().findRoomService(new HashMap<String, String>() {{
        put("id", roomServiceId);
      }});

    return roomService;
  }

  public void setRoomService(RoomService roomService) {
    this.roomService = roomService;
  }

  public MenuItem getMenuItem() {
    if (menuItem == null)
      menuItem = new MenuItem().findMenuItem(new HashMap<String, String>() {{
        put("id", menuItemId);
      }});

    return menuItem;
  }

  public void setMenuItem(MenuItem menuItem) {
    this.menuItem = menuItem;
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new OrderItem(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> results = new LinkedHashMap<>();
    results.put("id", getId());
    results.put("quantity", Integer.toString(getQuantity()));
    results.put("roomServiceId", getRoomServiceId());
    results.put("menuItemId", getMenuItemId());
    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    setId(hashMap.get("id"));
    setQuantity(Integer.parseInt(hashMap.get("quantity")));
    setRoomServiceId(hashMap.get("roomServiceId"));
    setMenuItemId(hashMap.get("menuItemId"));
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

  public OrderItem[] findOrderItems(HashMap<String, String> queries) {
    return (OrderItem[]) findEntities(queries);
  }

  public OrderItem findOrderItem(HashMap<String, String> queries) {
    return (OrderItem) findEntity(queries);
  }
}
