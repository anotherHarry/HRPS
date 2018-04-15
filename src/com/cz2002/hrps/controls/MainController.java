package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class MainController extends EntityController {

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
          new CheckInController().index();
          break;
        case 2:
          System.out.println(menuSelection);
          break;
        case 3:
          new ReservationController().index();
          break;
        case 4:
          new GuestController().index();
          break;
        case 5:
          new RoomController().index();
          break;
        case 6:
          new RoomServiceController().index();
          break;
        default:
          break;
      }
    } while (menuSelection != 7);
  }

}
