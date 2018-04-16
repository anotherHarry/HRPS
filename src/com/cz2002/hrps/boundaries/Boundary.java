package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.InputModel;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;

import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Super Class of all Boundaries
 * Handle I/O
 */
public class Boundary {

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
  public InputModel<String> inputString(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage);
    return inputString(inputRequired);
  }

  /**
   * Get a string from console
   * @return input from console
   */
  public InputModel<String> inputString(boolean inputRequired) {
    System.out.print((!inputRequired ? "\n (Press enter to skip)" : "") + " >> ");
    String input = scanner.nextLine();
    System.out.println();
    if (!input.equals("") || !inputRequired) {
      return new InputModel(!input.equals(""), input);
    }
    alertInvalidInput();
    return inputString(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Integer> inputInteger(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage);
    return inputInteger(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Integer> inputInteger(boolean inputRequired) {
    try {
      Integer input =  Integer.parseInt(inputString(inputRequired).getValue());
      return new InputModel(true, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(false, -1);
    }
    alertInvalidInput();
    return inputInteger(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Long> inputLong(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage);
    return inputLong(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Long> inputLong(boolean inputRequired) {
    try {
      Long input =  Long.parseLong(inputString(inputRequired).getValue());
      return new InputModel(true, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(false, -1l);
    }
    alertInvalidInput();
    return inputLong(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Double> inputDouble(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage);
    return inputDouble(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Double> inputDouble(boolean inputRequired) {
    try {
      Double input =  Double.parseDouble(inputString(inputRequired).getValue());
      return new InputModel(true, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(false, -1.0);
    }
    alertInvalidInput();
    return inputDouble(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Boolean> inputBoolean(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage + " (Y/N)");
    return inputBoolean(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Boolean> inputBoolean(boolean inputRequired) {
    String input = inputString(inputRequired).getValue();
    if (input.equals("Y")) {
      return new InputModel(true, true);
    } else if (input.equals("N")) {
      return new InputModel(true, false);
    }
    if (!inputRequired) {
      return new InputModel(false, false);
    }
    alertInvalidInput();
    return inputBoolean(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Date> inputDate(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage + " (Ex: 2018-06-25-17-05)");
    return inputDate(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Date> inputDate(boolean inputRequired) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
      Date input = sdf.parse(inputString(inputRequired).getValue());
      return new InputModel(true, input);
    } catch (ParseException e) {}
    if (!inputRequired) {
      return new InputModel(false, new Date());
    }
    alertInvalidInput();
    return inputDate(inputRequired);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<String> inputGender(String promptMessage, boolean inputRequired) {
    printPromptMessage(promptMessage);
    return inputGender(inputRequired);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<String> inputGender(boolean inputRequired) {
    String input = inputString(inputRequired).getValue();
    if (input.equals("M")) {
      return new InputModel(true, "M");
    } else if (input.equals("F")) {
      return new InputModel(true, "F");
    }
    if (!inputRequired) {
      return new InputModel(false, "NULL");
    }
    alertInvalidInput();
    return inputGender(inputRequired);
  }
  
  private void printPromptMessage(String promptMessage) {
    System.out.print("○ " + promptMessage);
  }

  public void alertSuccessful() {
    System.out.println("Successful!\n");
  }

  public void alertFailed() {
    System.out.println("Failed!\n");
  }

  public void alertNotFound() {
    System.out.println("ERROR: Item Not Found!\n");
  }

  public void alertInvalidInput() {
    System.out.println("ERROR: Invalid Input!\n");
  }
}
