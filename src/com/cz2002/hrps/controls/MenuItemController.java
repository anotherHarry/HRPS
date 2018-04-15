package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.MenuItem;
import com.cz2002.hrps.models.ItemsList;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.HashMap;
import java.util.Map;

public class MenuItemController implements Control {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "MenuItem Menu",
        new MenuOption[] {
          new MenuOption("add_new_menu_item", "Add New Menu Item"),
          new MenuOption("edit_menu_item", "Edit Menu Item"),
          new MenuOption("delete_menu_item", "Delete Menu Item"),
          new MenuOption("print_menu", "Print Menu"),
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
    MenuItem menuItem = new MenuItem();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      menuItem.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    menuItem.fromHashMap(hashMap);
    if (menuItem.create()) {
      new Boundary().alertSuccessful();
      return menuItem;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity find() {
    MenuItem[] menuItems = (MenuItem[]) findList();
    if (menuItems.length == 0) {
      new Boundary().alertNotFound();
      return null;
    } else if (menuItems.length == 1) {
      return menuItems[0];
    }
    printEntities("Search Results", menuItems);
    String[] items = new String[menuItems.length];
    for (int i = 0; i < menuItems.length; i++) {
      items[i] = menuItems[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (MenuItem menuItem: menuItems) {
      if (menuItem.itemsListKey().equals(key)) {
        return menuItem;
      }
    }
    return null;
  }

  @Override
  public Entity[] findList() {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      new MenuItem().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    MenuItem[] menuItems = new MenuItem().findMenuItems(queries);
    return menuItems;
  }

  @Override
  public Entity update() {
    MenuItem menuItem = (MenuItem) find();
    printEntity("Target Menu Item", menuItem);
    HashMap<String, String> hashMap = menuItem.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      menuItem.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      hashMap.replace(key, value);
    }
    if (menuItem.update(hashMap)) {
      new Boundary().alertSuccessful();
      return menuItem;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity delete() {
    MenuItem menuItem = (MenuItem) find();
    printEntity("Target Menu Item", menuItem);
    if(new Boundary().inputBoolean(
      "Are you sure you want to delete this Menu Item?",
      true).getValue()
      ) {
      if (menuItem.delete()) {
        new Boundary().alertSuccessful();
        return menuItem;
      } else {
        new Boundary().alertFailed();
      }
    }
    return null;
  }

  private void printAll() {
    MenuItem[] menuItems = new MenuItem().findMenuItems(new HashMap<String, String>() {{
    }});
    printEntities("Menu", menuItems);
  }
}
