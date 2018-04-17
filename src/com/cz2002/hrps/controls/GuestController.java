package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class GuestController extends EntityController implements AppController {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Guest Menu",
        new MenuOption[] {
          new MenuOption("update_guest", "Update"),
          new MenuOption("search_guest", "Search"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true, false).getValue();
      switch (menuSelection) {
        case 1:
          update(new Guest());
          break;
        case 2:
          searchGuests();
          break;
        default:
          break;
      }
    } while (menuSelection != 3);
  }

  private void searchGuests() {
    Guest[] guests = findList(new Guest());
    if (guests.length == 0) {
      new Boundary().alertEmpty();
    } else {
      printEntities("Search Results for Guest", guests);
    }
  }

  public Guest createGuestIfNeeded() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Is the guest existed?",
        new MenuOption[] {
          new MenuOption("make_reservation_new_guest", "New Guest"),
          new MenuOption("make_reservation_old_guest", "Existing Guest"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true, false).getValue();
      switch (menuSelection) {
        case 1:
          Guest newGuest = create(new Guest());
          if (newGuest != null) {
            printEntity("New Guest", newGuest);
          }
          return newGuest;
        case 2:
          Guest oldGuest = find(new Guest());
          if (oldGuest != null) {
            printEntity("Seleted Guest", oldGuest);
          }
          return oldGuest;
        default:
          break;
      }
    } while (menuSelection != 3);
    return null;
  }

}
