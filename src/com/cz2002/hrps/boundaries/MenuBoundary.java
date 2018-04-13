package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.Menu;

/**
 * Super class of all boundaries that display menu
 */
public abstract class MenuBoundary extends Boundary {

  private Menu menu;

  /**
   * Constructor
   * @param menu is the menu to be displayed
   */
  public MenuBoundary(Menu menu) {
    this.menu = menu;
  }

  /**
   * Subclass to implement how to process a menu and user's selection
   */
  public abstract void processMenu();

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  protected int getMenuSelection() {
    int input = -1;
    while (true) {
      printMenu();
      input = inputInteger();
      if (input > 0 && input <= menu.getMenuOptions().length) {
        break;
      }
    }
    return input;
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  private void printMenu() {
    System.out.println(menu.getTitle());
    String[] menuOptions = menu.getMenuOptions();
    for (int i = 0; i < menuOptions.length; i++)
      System.out.printf("%s. %s\n", i+1, menuOptions[i]);
  }

}
