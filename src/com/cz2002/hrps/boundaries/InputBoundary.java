package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class InputBoundary extends Boundary {

  private PromptModel promptModel;

  protected InputBoundary() {}

  public InputBoundary(PromptModel promptModel) {
    this.promptModel = promptModel;
  }

  public PromptModel getPromptModel() {
    return promptModel;
  }

  public InputModel<String> getInput(boolean inputRequired, boolean isCancelable) {
    if (promptModel.getInputType() == PromptModel.InputType.STRING) {
      return processString(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.INT) {
      return processInteger(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.POSITIVE_INT) {
      return processPositiveInteger(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.DOUBLE) {
      return processDouble(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.POSITIVE_DOUBLE) {
      return processPositiveDouble(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.BOOLEAN) {
      return processBoolean(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.DATE) {
      return processDate(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.GENDER) {
      return processGender(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.CONTACT_NUMBER) {
      return processContactNumber(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.CREDITCARD_NUMBER) {
      return processCreditCardNumber(inputRequired, isCancelable);
    } else if (promptModel.getInputType() == PromptModel.InputType.MENU_SELECTION) {
      Menu menu = promptModel.getMenu();
      InputModel<Integer> input = processMenu(inputRequired, isCancelable);
      if (!input.isSucceed()) {
        return new InputModel(input.getInputStatus(), "");
      }
      Integer selectedIndex = input.getValue();
      MenuOption selectedOption = menu.getMenuOptions()[selectedIndex-1];
      return new InputModel(InputModel.InputStatus.SUCCEED, selectedOption.getKey());
    } else if (promptModel.getInputType() == PromptModel.InputType.LIST_SELECTION) {
      return processList(inputRequired, isCancelable);
    }
    return new InputModel(InputModel.InputStatus.FAILED, "");
  }

  private InputModel<String> processString(boolean inputRequired, boolean isCancelable) {
    return inputString(promptModel.getTitle(), inputRequired, isCancelable);
  }

  private InputModel<String> processInteger(boolean inputRequired, boolean isCancelable) {
    InputModel input = inputInteger(promptModel.getTitle(), inputRequired, isCancelable);
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processPositiveInteger(boolean inputRequired, boolean isCancelable) {
    InputModel<Integer> input = inputInteger(promptModel.getTitle(), inputRequired, isCancelable);
    if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, "");
    }
    while (input.getValue() < 0 && inputRequired) {
      alertInvalidInput();
      input = inputInteger(promptModel.getTitle(), inputRequired, isCancelable);
    }
    if (input.getValue() < 0) {
      return new InputModel(InputModel.InputStatus.FAILED, input.getValue().toString());
    }
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processDouble(boolean inputRequired, boolean isCancelable) {
    InputModel input = inputDouble(promptModel.getTitle(), inputRequired, isCancelable);
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processPositiveDouble(boolean inputRequired, boolean isCancelable) {
    InputModel<Double> input = inputDouble(promptModel.getTitle(), inputRequired, isCancelable);
    if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, "");
    }
    while (input.getValue() < 0 && inputRequired) {
      alertInvalidInput();
      input = inputDouble(promptModel.getTitle(), inputRequired, isCancelable);
    }
    if (input.getValue() < 0) {
      return new InputModel(InputModel.InputStatus.FAILED, input.getValue().toString());
    }
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processBoolean(boolean inputRequired, boolean isCancelable) {
    InputModel input = inputBoolean(promptModel.getTitle(), inputRequired, isCancelable);
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processDate(boolean inputRequired, boolean isCancelable) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
    InputModel<Date> input = inputDate(promptModel.getTitle(), inputRequired, isCancelable);
    return new InputModel(input.getInputStatus(), sdf.format(input.getValue()));
  }

  private InputModel<String> processGender(boolean inputRequired, boolean isCancelable) {
    return inputGender(promptModel.getTitle(), inputRequired, isCancelable);
  }

  private InputModel<String> processContactNumber(boolean inputRequired, boolean isCancelable) {
    InputModel<Integer> input = inputInteger(promptModel.getTitle(), inputRequired, isCancelable);
    if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, "");
    }
    while ((input.getValue() < 80000000 || input.getValue() > 99999999) && inputRequired) {
      alertInvalidContactInput();
      input = inputInteger(promptModel.getTitle(), inputRequired, isCancelable);
    }
    if (input.getValue() < 80000000 || input.getValue() > 99999999) {
      return new InputModel(InputModel.InputStatus.FAILED, input.getValue().toString());
    }
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  private InputModel<String> processCreditCardNumber(boolean inputRequired, boolean isCancelable) {
    InputModel<Long> input = inputLong(promptModel.getTitle(), inputRequired, isCancelable);
    if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
      return new InputModel(InputModel.InputStatus.CANCELED, "");
    }
    while ((input.getValue() < 1000000000000000l || input.getValue() > 9999999999999999l) &&
      inputRequired) {
      alertInvalidCreditCardInput();
      input = inputLong(promptModel.getTitle(), inputRequired, isCancelable);
    }
    if (input.getValue() < 1000000000000000l || input.getValue() > 9999999999999999l) {
      return new InputModel(InputModel.InputStatus.FAILED, input.getValue().toString());
    }
    return new InputModel(input.getInputStatus(), input.getValue().toString());
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  public InputModel<Integer> processMenu(boolean inputRequired, boolean isCancelable) {
    Menu menu = promptModel.getMenu();
    printMenu(menu);

    do {
      InputModel<Integer> input = inputInteger(inputRequired, isCancelable);
      if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
        return new InputModel(InputModel.InputStatus.CANCELED, Integer.MAX_VALUE);
      }
      if (input.getValue() > 0 && input.getValue() <= menu.getMenuOptions().length) {
        return new InputModel(InputModel.InputStatus.SUCCEED, input.getValue());
      }
      if (inputRequired) {
        alertInvalidInput();
      }
    } while (inputRequired);
    return new InputModel(InputModel.InputStatus.FAILED, -1);
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  private void printMenu(Menu menu) {
    System.out.println(ANSI_CYAN + ANSI_BOLD + "\n==  " + menu.getTitle() + "  ==" + ANSI_RESET);
    MenuOption[] menuOptions = menu.getMenuOptions();
    for (int i = 0; i < menuOptions.length; i++)
      System.out.printf("%s. %s\n", i+1, menuOptions[i].getTitle());
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  public InputModel<String> processList(boolean inputRequired, boolean isCancelable) {
    ItemsList itemsList = promptModel.getItemsList();
    printList(itemsList);
    do {
      InputModel<String> input = inputString(inputRequired, isCancelable);
      if (input.getInputStatus() == InputModel.InputStatus.CANCELED) {
        return new InputModel(InputModel.InputStatus.CANCELED, -1);
      }
      if (Arrays.asList(itemsList.getItems()).contains(input.getValue())) {
        return new InputModel(InputModel.InputStatus.SUCCEED, input.getValue());
      }
      if (inputRequired) {
        alertInvalidInput();
      }
    } while (inputRequired);
    return new InputModel(InputModel.InputStatus.FAILED, "");
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  private void printList(ItemsList itemsList) {
    System.out.println("\n**  " + itemsList.getTitle() + ": ");
    String[] items = itemsList.getItems();
    for (String item: items) {
      System.out.printf(item + ", ");
    }
    System.out.println();
  }

}
