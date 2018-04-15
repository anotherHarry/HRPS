package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.HashMap;
import java.util.Map;

public class ReservationController implements Control {

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
          create();
          break;
        case 2:
          update();
          break;
        case 3:
          delete();
          break;
        case 4:
          printAll();
          break;
        default:
          break;
      }
    } while (menuSelection != 5);
  }

  @Override
  public Entity create() {
    String guestId = creationGuestId();
    if (guestId == null) {
      return null;
    }
    String roomId = new RoomController().findVacantRoom().getRoomId();

    Reservation reservation = new Reservation();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      reservation.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    hashMap.put("guestId", guestId);
    hashMap.put("roomId", roomId);
    reservation.fromHashMap(hashMap);
    if (reservation.create()) {
      // room update status
      new Boundary().alertSuccessful();
      return reservation;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity find() {
    Reservation[] reservations = (Reservation[]) findList();
    if (reservations.length == 0) {
      new Boundary().alertNoItemExisted();
      return null;
    } else if (reservations.length == 1) {
      return reservations[0];
    }
    printEntities("Search Results", reservations);
    String[] items = new String[reservations.length];
    for (int i = 0; i < reservations.length; i++) {
      items[i] = reservations[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (Reservation reservation: reservations) {
      if (reservation.itemsListKey().equals(key)) {
        return reservation;
      }
    }
    return null;
  }

  @Override
  public Entity[] findList() {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      new Reservation().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    Reservation[] reservations = new Reservation().findReservations(queries);
    HashMap<String, String>[] hashMaps = (HashMap<String, String>[]) new HashMap<?,?>[reservations.length];
    for (int i = 0; i < reservations.length; i++) {
      hashMaps[i] = reservations[i].toHashMap();
    }
    return reservations;
  }

  @Override
  public Entity update() {
    Reservation reservation = (Reservation) find();
    printEntity("Target Reservation", reservation);
    HashMap<String, String> hashMap = reservation.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      reservation.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      hashMap.replace(key, value);
    }
    if (reservation.update(hashMap)) {
      new Boundary().alertSuccessful();
      return reservation;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity delete() {
    Reservation reservation = (Reservation) find();
    printEntity("Target Reservation", reservation);
    if(new Boundary().inputBoolean(
      "Are you sure you want to delete this reservation?",
      true).getValue()
      ) {
      if (reservation.delete()) {
        new Boundary().alertSuccessful();
        return reservation;
      }
    }
    return null;
  }

  private void printAll() {
    Reservation[] reservations = new Reservation().findReservations(new HashMap<String, String>() {{
    }});
    printEntities("All Reservations", reservations);
  }

  private String creationGuestId() {
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
          Guest newGuest = (Guest) new GuestController().create();
          printEntity("New Guest", newGuest);
          return newGuest.getId();
        case 2:
          Guest oldGuest = (Guest) new GuestController().find();
          printEntity("Target Guest", oldGuest);
          return oldGuest.getId();
        default:
          break;
      }
    } while (menuSelection != 3);
    return null;
  }

}