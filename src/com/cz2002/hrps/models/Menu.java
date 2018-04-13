package com.cz2002.hrps.models;

/**
 * The menu to be displayed in a MenuBoundary
 */
public class Menu {
  private String title;
  private String[] menuOptions;

  /**
   * Constructor
   * @param title is the title of the menu
   * @param menuOptions is all the options to be displayed
   */
  public Menu(String title, String[] menuOptions) {
    this.title = title;
    this.menuOptions = menuOptions;
  }

  /**
   * @return title of the menu
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return all options
   */
  public String[] getMenuOptions() {
    return menuOptions;
  }
}
