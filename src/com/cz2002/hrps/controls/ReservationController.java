package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.*;

import java.util.HashMap;

public class ReservationController extends EntityController {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Room Menu",
        new MenuOption[] {
          new MenuOption("make_reservation", "Make Reservation"),
          new MenuOption("update_reservation", "Update Reservation"),
          new MenuOption("delete_reservation", "Delete Reservation"),
          new MenuOption("print_all_reservations", "Print All Reservation"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          create(new Reservation());
          break;
        case 2:
          update(new Reservation());
          break;
        case 3:
          delete(new Reservation());
          break;
        case 4:
          printAll("Reservations", new Reservation());
          break;
        default:
          break;
      }
    } while (menuSelection != 5);
  }

  @Override
  public <T extends Entity> T create(T t) {
    if (!(t instanceof Reservation)) {
      return null;
    }
    Guest guest = new GuestController().createGuestIfNeeded();
    if (guest == null) {
      return null;
    }
    Room room = find(new Room());
    if (room == null) {
      return null;
    }

    Reservation reservation = new Reservation();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      reservation.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    reservation.fromHashMap(hashMap);
    reservation.setGuest(guest);
    reservation.setRoom(room);
    if (reservation.create()) {
      new Boundary().alertSuccessful();
      return (T) reservation;
    }
    new Boundary().alertFailed();
    return null;
  }

  public Reservation findConfirmedReservaton() {
    HashMap<String, String> queries = new HashMap<>() {{
      put("reservationStatus", Reservation.ReservationStatus.CONFIRMED.toString());
    }};
    return findWith(queries, "Confirmed Reservation", new Reservation());
  }

  public Reservation findCheckedInReservaton() {
    HashMap<String, String> queries = new HashMap<>() {{
      put("reservationStatus", Reservation.ReservationStatus.CHECKED_IN.toString());
    }};
    return findWith(queries, "Checked-in Reservation", new Reservation());
  }

}
