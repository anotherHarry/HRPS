package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.HashMap;

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

  private Reservation checkInReservation() {
    Reservation reservation = new ReservationController().findConfirmedReservaton();
    printEntity("Selected Reservation", reservation);
    if(new Boundary().inputBoolean(
      "Are you sure you want to check-in with this reservation?",
      true).getValue()
      ) {
      if (reservation.checkIn()) {
        new Boundary().alertSuccessful();
        return reservation;
      } else {
        new Boundary().alertFailed();
      }
    }
    return null;
  }

  private Reservation walkIn() {
    Guest guest = new GuestController().createGuestIfNeeded();
    if (guest == null) {
      return null;
    }
    Room room = new RoomController().findVacantRoom();
    if (room == null) {
      return null;
    }

    Reservation reservation = new Reservation();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      reservation.walkInPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    reservation.fromHashMap(hashMap);
    reservation.setGuest(guest);
    reservation.setRoom(room);
    if (reservation.walkIn()) {
      new Boundary().alertSuccessful();
      return reservation;
    }
    new Boundary().alertFailed();
    return null;
  }
}
