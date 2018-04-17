package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.entities.MenuItem;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class MenuItemController extends EntityController implements AppController {

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
      menuSelection = inputBoundary.processMenu(true, false).getValue();
      switch (menuSelection) {
        case 1:
          create(new MenuItem());
          break;
        case 2:
          update(new MenuItem());
          break;
        case 3:
          delete(new MenuItem());
          break;
        case 4:
          printAll("Menu Items", new MenuItem());
          break;
        default:
          break;
      }
    } while (menuSelection != 5);
  }

}
