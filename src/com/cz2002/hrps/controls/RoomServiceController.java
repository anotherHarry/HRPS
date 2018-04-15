package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.*;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomServiceController extends EntityController {

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
          create(new RoomService());
          break;
        case 2:
          update(new RoomService());
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
  public <T extends Entity> T create(T t) {
    if (!(t instanceof RoomService)) {
      return null;
    }

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
      return (T) roomService;
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
    MenuItem menuItem = new MenuItemController().find(new MenuItem());
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
