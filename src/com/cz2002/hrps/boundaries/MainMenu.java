package com.cz2002.hrps.boundaries;

import java.util.HashMap;

import com.cz2002.hrps.entities.*; // TODO: Delete
import com.cz2002.hrps.models.Menu;

/**
 * Main menu at the bottom of the hierarchy
 */
public class MainMenu extends MenuBoundary {

  /**
   * Constructor
   * Initialize MenuBoundary with main menu's options
   */
  public MainMenu() {
    super(new Menu(
      "Hotel Reservation and Payment System",
      new String[] {
        "Check in",
        "Check out",
        "Reservation",
        "Guest",
        "Room",
        "RoomService",
      }
    ));
  }

  @Override
  public void processMenu() {
    int menuSelection = getMenuSelection();
    switch(menuSelection) {
      case 1:
        System.out.print(menuSelection);
        break;
      case 2:
        System.out.print(menuSelection);
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
        Guest guest = new Guest();
        Guest guestResult = guest.findGuest(new HashMap<String, String>() {{
          put("contact", "92831176");
        }});
        System.out.println(guestResult.getName());
        Guest[] allGuests = guest.findGuests(new HashMap<String, String>());
        for (int i = 0; i < allGuests.length; i++) {
          System.out.println(allGuests[i].getContact());
        };
        break;
      case 5:
        Room room = new Room();
        Room roomResult = room.findRoom(new HashMap<String, String>() {{
            put("smokingAllowed", "true");
          }});
        System.out.println(roomResult.getFacing());
        Room[] allRooms = room.findRooms(new HashMap<String, String>());
        for (int i = 0; i < allRooms.length; i++) {
          System.out.println(allRooms[i].getRoomId());
        }
        break;
      case 6:
//        MenuItem menuItem = new MenuItem();
//        MenuItem menuItemResult = menuItem.findMenuItem(new HashMap<String, String>() {{
//          put("name", "Dinner Set");
//        }});
//        System.out.println(menuItemResult.getDescription());
//        MenuItem[] allMenuItems = menuItem.findMenuItems(new HashMap<String, String>());
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
  }
}
