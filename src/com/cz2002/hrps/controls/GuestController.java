package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class GuestController extends EntityController {

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
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          update(new Guest());
          break;
        case 2:
          Guest[] guests = findList(new Guest());
          printEntities("Search Results for Guest", guests);
          break;
        default:
          break;
      }
    } while (menuSelection != 3);
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
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          Guest newGuest = create(new Guest());
          printEntity("New Guest", newGuest);
          return newGuest;
        case 2:
          Guest oldGuest = find(new Guest());
          printEntity("Target Guest", oldGuest);
          return oldGuest;
        default:
          break;
      }
    } while (menuSelection != 3);
    return null;
  }

}
