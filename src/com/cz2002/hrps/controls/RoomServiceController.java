package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.*;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomServiceController implements Control {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Room Menu",
        new MenuOption[] {
          new MenuOption("make_order", "Make Order"),
          new MenuOption("update_order", "Update Order"),
          new MenuOption("edit_menu", "Edit Menu"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          create();
          break;
        case 2:
          update();
          break;
        case 3:
          new MenuItemController().index();
          break;
        default:
          break;
      }
    } while (menuSelection != 4);
  }

  @Override
  public Entity create() {
    Reservation reservation = new ReservationController().findCheckInReservaton();
    if (reservation == null) {
      return null;
    }

    RoomService roomService = new RoomService();
    OrderItem[] orderItems = createOrderItems(roomService);
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      roomService.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    roomService.fromHashMap(hashMap);
    roomService.setReservation(reservation);
    if (roomService.create()) {
      for (OrderItem orderItem: orderItems) {
        orderItem.create();
      }
      new Boundary().alertSuccessful();
      return reservation;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity find() {
    RoomService[] roomServices = (RoomService[]) findList();
    if (roomServices.length == 0) {
      new Boundary().alertNotFound();
      return null;
    } else if (roomServices.length == 1) {
      return roomServices[0];
    }
    printEntities("Search Results", roomServices);
    String[] items = new String[roomServices.length];
    for (int i = 0; i < roomServices.length; i++) {
      items[i] = roomServices[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (RoomService roomService: roomServices) {
      if (roomService.itemsListKey().equals(key)) {
        return roomService;
      }
    }
    return null;
  }

  @Override
  public Entity[] findList() {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      new RoomService().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    RoomService[] roomServices = new RoomService().findRoomServices(queries);
    HashMap<String, String>[] hashMaps = (HashMap<String, String>[]) new HashMap<?,?>[roomServices.length];
    for (int i = 0; i < roomServices.length; i++) {
      hashMaps[i] = roomServices[i].toHashMap();
    }
    return roomServices;
  }

  @Override
  public Entity update() {
    RoomService roomService = (RoomService) find();
    printEntity("Target Room Service", roomService);
    HashMap<String, String> hashMap = roomService.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      roomService.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      hashMap.replace(key, value);
    }
    if (roomService.update(hashMap)) {
      new Boundary().alertSuccessful();
      return roomService;
    }
    new Boundary().alertFailed();
    return null;
  }

  private OrderItem[] createOrderItems(RoomService roomService) {
    ArrayList<OrderItem> orderItems = new ArrayList<>();
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Make Order, Add Menu Item",
        new MenuOption[] {
          new MenuOption("add_menu_item", "Add Item to Order"),
          new MenuOption("done", "Done"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          OrderItem orderItem = createOrderItem(roomService);
          if (orderItem != null) {
            new Boundary().alertSuccessful();
            orderItems.add(orderItem);
          }
          break;
        default:
          break;
      }
    } while (menuSelection != 2);
    return orderItems.toArray(new OrderItem[orderItems.size()]);
  }

  private OrderItem createOrderItem(RoomService roomService) {
    MenuItem menuItem = (MenuItem) new MenuItemController().find();
    if (menuItem == null) {
      return null;
    }
    printEntity("Selected Menu Item", menuItem);
    OrderItem orderItem = new OrderItem();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      orderItem.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    orderItem.fromHashMap(hashMap);
    orderItem.setRoomService(roomService);
    orderItem.setMenuItem(menuItem);
    return orderItem;
  }
}
