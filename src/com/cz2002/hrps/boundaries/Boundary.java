package com.cz2002.hrps.boundaries;

import java.util.Scanner;

/**
 * Super Class of all Boundaries
 * Handle I/O
 */
public abstract class Boundary {

  private static Scanner scanner;

  /**
   * Constructor
   */
  public Boundary() {
    scanner = new Scanner(System.in);
  }

  /**
   * Get a string from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public String inputString(String promptMessage) {
    System.out.println(promptMessage);
    return inputString();
  }

  /**
   * Get a string from console
   * @return input from console
   */
  public String inputString() {
    System.out.print(" >> ");
    String input = scanner.nextLine();
    System.out.println();
    return input;
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public int inputInteger() {
    int input = -1;
    try {
      input =  Integer.parseInt(inputString());
    } catch (NumberFormatException e) {}
    return input;
  }
}
