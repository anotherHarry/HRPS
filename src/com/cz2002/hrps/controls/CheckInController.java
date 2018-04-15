package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class CheckInController extends EntityController {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Check-in",
        new MenuOption[] {
          new MenuOption("has_reservation", "Has Reservation"),
          new MenuOption("walk-in", "Walk In"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          checkInReservation();
          break;
        case 2:
          walkIn();
          break;
        default:
          break;
      }
    } while (menuSelection != 3);
  }

  private void checkInReservation() {

  }

  private void walkIn() {}
}
