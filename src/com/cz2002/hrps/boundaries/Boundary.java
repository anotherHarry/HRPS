package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.InputModel;

import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Super Class of all Boundaries
 * Handle I/O
 */
public class Boundary {

  protected static final String ANSI_RESET = "\u001B[0m";
  protected static final String ANSI_BLUE = "\u001B[34m";
  protected static final String ANSI_CYAN = "\u001B[36m";
  protected static final String ANSI_YELLOW = "\u001B[33m";
  protected static final String ANSI_BOLD = "\u001B[1m";
  protected static final String ANSI_ITALIC = "\033[3m";

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
  public InputModel<String> inputString(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage);
    return inputString(inputRequired, isCancelable);
  }

  /**
   * Get a string from console
   * @return input from console
   */
  public InputModel<String> inputString(boolean inputRequired, boolean isCancelable) {
    System.out.print(
      "\n" +
        ANSI_ITALIC + ANSI_YELLOW +
        (!inputRequired ? " Press enter to skip |" : "") +
        (isCancelable ? " Type cancel to cancel |" : "") +
        " >> " +
        ANSI_RESET
    );
    String input = scanner.nextLine();
    System.out.println();
    if (input.equals("cancel") && isCancelable) {
      return new InputModel(InputModel.InputStatus.CANCELED, input);
    } else if (!input.equals("") || !inputRequired) {
      return new InputModel(InputModel.statusFor(!input.equals("")), input);
    }
    alertInvalidInput();
    return inputString(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Integer> inputInteger(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage);
    return inputInteger(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Integer> inputInteger(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, -1);
    }
    try {
      Integer input =  Integer.parseInt(stringInput.getValue());
      return new InputModel(InputModel.InputStatus.SUCCEED, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, -1);
    }
    alertInvalidInput();
    return inputInteger(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Long> inputLong(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage);
    return inputLong(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Long> inputLong(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, -1l);
    }
    try {
      Long input =  Long.parseLong(stringInput.getValue());
      return new InputModel(InputModel.InputStatus.SUCCEED, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, -1l);
    }
    alertInvalidInput();
    return inputLong(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Double> inputDouble(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage);
    return inputDouble(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Double> inputDouble(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, -1.0);
    }
    try {
      Double input =  Double.parseDouble(stringInput.getValue());
      return new InputModel(InputModel.InputStatus.SUCCEED, input);
    } catch (NumberFormatException e) {}
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, -1.0);
    }
    alertInvalidInput();
    return inputDouble(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Boolean> inputBoolean(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage + " (Y/N)");
    return inputBoolean(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Boolean> inputBoolean(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, false);
    }
    String input = stringInput.getValue();
    if (input.equals("Y")) {
      return new InputModel(InputModel.InputStatus.SUCCEED, true);
    } else if (input.equals("N")) {
      return new InputModel(InputModel.InputStatus.SUCCEED, false);
    }
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, false);
    }
    alertInvalidInput();
    return inputBoolean(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<Date> inputDate(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage + " (Ex: 2018-06-25-17-05)");
    return inputDate(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<Date> inputDate(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, new Date());
    }
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
      Date input = sdf.parse(inputString(inputRequired, isCancelable).getValue());
      return new InputModel(InputModel.InputStatus.SUCCEED, input);
    } catch (ParseException e) {}
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, new Date());
    }
    alertInvalidInput();
    return inputDate(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @param promptMessage the message to print before input
   * @return input from console
   */
  public InputModel<String> inputGender(String promptMessage, boolean inputRequired, boolean isCancelable) {
    printPromptMessage(promptMessage+ " (M/F)");
    return inputGender(inputRequired, isCancelable);
  }

  /**
   * Get an integer from console
   * @return input from console
   */
  public InputModel<String> inputGender(boolean inputRequired, boolean isCancelable) {
    InputModel<String> stringInput = inputString(inputRequired, isCancelable);
    if (stringInput.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, "NULL");
    }
    String input = inputString(inputRequired, isCancelable).getValue();
    if (input.equals("M")) {
      return new InputModel(InputModel.InputStatus.SUCCEED, "M");
    } else if (input.equals("F")) {
      return new InputModel(InputModel.InputStatus.SUCCEED, "F");
    }
    if (!inputRequired) {
      return new InputModel(InputModel.InputStatus.FAILED, "NULL");
    }
    alertInvalidInput();
    return inputGender(inputRequired, isCancelable);
  }
  
  private void printPromptMessage(String promptMessage) {
    System.out.print("○ " + promptMessage);
  }

  public void alertSuccessful() {
    System.out.println(ANSI_BLUE + "Successful!\n" + ANSI_RESET);
  }

  public void alertFailed() {
    System.err.println("Failed!\n");
  }

  public void alertNotFound() {
    System.err.println("ERROR: Item Not Found!\n");
  }

  public void alertInvalidInput() {
    System.err.println("ERROR: Invalid Input!\n");
  }
}
