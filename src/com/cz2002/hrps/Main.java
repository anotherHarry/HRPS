package com.cz2002.hrps;

import com.cz2002.hrps.controls.MainController;

/**
 * Main Class
 */
public class Main {

  /**
   * main is the entrance to start the app
   * @param args
   */
  public static void main(String[] args) {
    MainController mainController = new MainController();
    mainController.index();
  }

}
