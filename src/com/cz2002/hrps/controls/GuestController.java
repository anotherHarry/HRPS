package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.HashMap;
import java.util.Map;

public class GuestController implements Control {

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
          update();
          break;
        case 2:
          findList();
          break;
        default:
          break;
      }
    } while (menuSelection != 3);
  }

  @Override
  public Entity create() {
    Guest guest = new Guest();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      guest.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    guest.fromHashMap(hashMap);
    if (guest.create()) {
      new Boundary().alertSuccessful();
      return guest;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity find() {
    Guest[] guests = (Guest[]) findList();
    if (guests.length == 0) {
      new Boundary().alertNoItemExisted();
      return null;
    } else if (guests.length == 1) {
      return guests[0];
    }
    printEntities("Search Results", guests);
    String[] items = new String[guests.length];
    for (int i = 0; i < guests.length; i++) {
      items[i] = guests[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (Guest guest: guests) {
      if (guest.itemsListKey().equals(key)) {
        return guest;
      }
    }
    return null;
  }

  @Override
  public Entity[] findList() {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      new Guest().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    Guest[] guests = new Guest().findGuests(queries);
    return guests;
  }

  @Override
  public Entity update() {
    Guest guest = (Guest) find();
    printEntity("Target Guest", guest);
    HashMap<String, String> hashMap = guest.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      guest.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      hashMap.replace(key, value);
    }
    if (guest.update(hashMap)) {
      new Boundary().alertSuccessful();
      return guest;
    }
    new Boundary().alertFailed();
    return null;
  }


}
