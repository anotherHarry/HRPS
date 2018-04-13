package com.cz2002.hrps;

import com.cz2002.hrps.boundaries.MainMenu;

/**
 * Main Class
 */
public class Main {

  /**
   * main is the entrance to start the app
   * @param args
   */
  public static void main(String[] args) {
    MainMenu mainMenu = new MainMenu();
    mainMenu.processMenu();
  }

}
