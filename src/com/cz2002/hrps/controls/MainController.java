package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.entities.OrderItem;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.HashMap;

public class MainController implements Control {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Hotel Reservation and Payment System",
        new MenuOption[] {
          new MenuOption("check_in", "Check in"),
          new MenuOption("check_out", "Check out"),
          new MenuOption("reservation", "Reservation"),
          new MenuOption("guest", "Guest"),
          new MenuOption("room", "Room"),
          new MenuOption("room_service", "RoomService"),
          new MenuOption("exit", "Exit"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch(menuSelection) {
        case 1:
          System.out.println(menuSelection);
          break;
        case 2:
          System.out.println(menuSelection);
          break;
        case 3:
          Reservation reservation = new Reservation();
          Reservation reservationResult = reservation.findReservation(new HashMap<String, String>() {{
            put("guestId", "A0831171");
          }});
          System.out.println(reservationResult.getReservationStatus());
          Reservation[] allReservations = reservation.findReservations(new HashMap<String, String>());
          for (int i = 0; i < allReservations.length; i++) {
            System.out.println(allReservations[i].getGuestId());
          };
          break;
        case 4:
          new GuestController().index();
          break;
        case 5:
          new RoomController().index();
          break;
        case 6:
//        MenuOption menuItem = new MenuOption();
//        MenuOption menuItemResult = menuItem.findMenuItem(new HashMap<String, String>() {{
//          put("name", "Dinner Set");
//        }});
//        System.out.println(menuItemResult.getDescription());
//        MenuOption[] allMenuItems = menuItem.findMenuItems(new HashMap<String, String>());
//        for (int i = 0; i < allMenuItems.length; i++) {
//          System.out.println(allMenuItems[i].getDescription());
//        }
//        RoomService roomService = new RoomService();
//        RoomService roomServiceResult = roomService.findRoomService(new HashMap<String, String>() {{
//          put("reservationId", "20160415-221-A0831180");
//        }});
//        System.out.println(roomServiceResult.getCreated() + " " + roomServiceResult.getStatus());
//        RoomService[] allRoomServices = roomService.findRoomServices(new HashMap<String, String>());
//        for (int i = 0; i < allRoomServices.length; i++) {
//          System.out.println(allRoomServices[i].getCreated() + " " + allRoomServices[i].getRemarks());
//        }
          OrderItem orderItem = new OrderItem();
//        OrderItem orderItemResult = orderItem.findOrderItem(new HashMap<String, String>() {{
//          put("quantity", "2");
//        }});
//        System.out.println(orderItemResult.getRoomService().getReservation().getGuest().getName() + " " + orderItemResult.getMenuItem().getName());
          OrderItem[] allOrderItems = orderItem.findOrderItems(new HashMap<String, String>());
          for (int i = 0; i < allOrderItems.length; i++) {
            System.out.println(allOrderItems[i].getRoomService().getReservation().getGuest().getName() + " " + allOrderItems[i].getMenuItem().getName());
          }
          break;
        default:
          break;
      }
    } while (menuSelection != 7);
  }

}
