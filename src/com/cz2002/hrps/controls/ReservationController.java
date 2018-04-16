package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.*;
import com.cz2002.hrps.models.*;

import java.util.*;

public class ReservationController extends EntityController {

  private static Date timeKeeper;

  public ReservationController() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.HOUR_OF_DAY, -2);
    timeKeeper = calendar.getTime();
    checkExpiredReservationsIfNeeded();
  }

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Reservation Menu",
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
      menuSelection = inputBoundary.processMenu(true, false).getValue();
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
    checkExpiredReservationsIfNeeded();
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
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true, true);
    if (hashMap == null) {
      return null;
    }
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

  @Override
  public <T extends Entity> T[] findList(T t) {
    checkExpiredReservationsIfNeeded();
    return super.findList(t);
  }

  public Reservation findConfirmedReservaton() {
    return findReservationOfStatus(Reservation.ReservationStatus.CONFIRMED);
  }

  public Reservation findCheckedInReservaton() {
    return findReservationOfStatus(Reservation.ReservationStatus.CHECKED_IN);
  }

  private Reservation findReservationOfStatus(Reservation.ReservationStatus reservationStatus) {
    HashMap<String, String> queries = new HashMap<>() {{
      put("reservationStatus", reservationStatus.toString());
    }};
    return findWith(queries, "Checked-in Reservations", new Reservation());
  }

  private void checkExpiredReservationsIfNeeded() {
    if (!isOlderThanOneHour(timeKeeper)) {
      return;
    }
    timeKeeper = new Date();
    checkExpiredReservations();
  }

  private boolean isOlderThanOneHour(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, 1);
    return !calendar.getTime().after(new Date());
  }

  private void checkExpiredReservations() {
    checkExpiredReservationsForStatus(Reservation.ReservationStatus.WAITLIST);
    checkExpiredReservationsForStatus(Reservation.ReservationStatus.CONFIRMED);
  }

  private void checkExpiredReservationsForStatus(Reservation.ReservationStatus reservationStatus) {
    Reservation[] reservations = new Reservation().findReservations(new HashMap<>() {{
      put("reservationStatus", reservationStatus.toString());
    }});

    for (Reservation reservation: reservations) {
      if (reservation.getCheckInDate() != null &&
        isOlderThanOneHour(reservation.getCheckInDate())) {
        reservation.setExpire();
      }
    }
  }

}
