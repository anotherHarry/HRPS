package com.cz2002.hrps.boundaries;

import java.util.HashMap; // TODO: Delete

import com.cz2002.hrps.entities.Room; // TODO: Delete
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
        System.out.print(menuSelection);
        break;
      case 4:
        System.out.print(menuSelection);
        break;
      case 5:
        Room room = new Room();
        Room[] allRooms =  (Room[]) room.findList(new HashMap<String, String>());
        for (int i = 0; i < allRooms.length; i++) {
          System.out.print(allRooms[i].getRoomId() + "\n");
        }
        break;
      case 6:
        System.out.print(menuSelection);
        break;
      default:
        break;
    }
  }
}
