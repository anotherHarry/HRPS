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

  public InputModel<String> getInput(boolean inputRequired) {
    if (promptModel.getInputType() == PromptModel.InputType.STRING) {
      return inputString(promptModel.getTitle(), inputRequired);
    } else if (promptModel.getInputType() == PromptModel.InputType.INT) {
      InputModel input = inputInteger(promptModel.getTitle(), inputRequired);
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.POSITIVE_INT) {
      InputModel<Integer> input = inputInteger(promptModel.getTitle(), inputRequired);
      while (input.getValue() < 0 && inputRequired) {
        alertInvalidInput();
        input = inputInteger(promptModel.getTitle(), inputRequired);
      }
      if (input.getValue() < 0) {
        return new InputModel(false, input.getValue().toString());
      }
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.DOUBLE) {
      InputModel input = inputDouble(promptModel.getTitle(), inputRequired);
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.POSITIVE_DOUBLE) {
      InputModel<Double> input = inputDouble(promptModel.getTitle(), inputRequired);
      while (input.getValue() < 0 && inputRequired) {
        alertInvalidInput();
        input = inputDouble(promptModel.getTitle(), inputRequired);
      }
      if (input.getValue() < 0) {
        return new InputModel(false, input.getValue().toString());
      }
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.BOOLEAN) {
      InputModel input = inputBoolean(promptModel.getTitle(), inputRequired);
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.DATE) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
      InputModel<Date> input = inputDate(promptModel.getTitle(), inputRequired);
      return new InputModel(input.getIsSuccess(), sdf.format(input.getValue()));
    } else if (promptModel.getInputType() == PromptModel.InputType.GENDER) {
      return inputGender(promptModel.getTitle(), inputRequired);
    } else if (promptModel.getInputType() == PromptModel.InputType.CONTACT_NUMBER) {
      InputModel<Integer> input = inputInteger(promptModel.getTitle(), inputRequired);
      while ((input.getValue() < 80000000 || input.getValue() > 99999999) && inputRequired) {
        alertInvalidInput();
        input = inputInteger(promptModel.getTitle(), inputRequired);
      }
      if (input.getValue() < 80000000 || input.getValue() > 99999999) {
        return new InputModel(false, input.getValue().toString());
      }
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.CREDITCARD_NUMBER) {
      InputModel<Long> input = inputLong(promptModel.getTitle(), inputRequired);
      while ((input.getValue() < 1000000000000000l || input.getValue() > 9999999999999999l) &&
        inputRequired) {
        alertInvalidInput();
        input = inputLong(promptModel.getTitle(), inputRequired);
      }
      if (input.getValue() < 1000000000000000l || input.getValue() > 9999999999999999l) {
        return new InputModel(false, input.getValue().toString());
      }
      return new InputModel(input.getIsSuccess(), input.getValue().toString());
    } else if (promptModel.getInputType() == PromptModel.InputType.MENU_SELECTION) {
      Menu menu = promptModel.getMenu();
      InputModel<Integer> input = processMenu(inputRequired);
      if (!input.getIsSuccess()) {
        return new InputModel(false, "");
      }
      Integer selectedIndex = input.getValue();
      MenuOption selectedOption = menu.getMenuOptions()[selectedIndex-1];
      return new InputModel(true, selectedOption.getKey());
    } else if (promptModel.getInputType() == PromptModel.InputType.LIST_SELECTION) {
      return processList(inputRequired);
    }
    return new InputModel(false, "");
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  public InputModel<Integer> processMenu(boolean inputRequired) {
    Integer input = -1;
    Menu menu = promptModel.getMenu();
    printMenu(menu);

    do {
      input = inputInteger(inputRequired).getValue();
      if (input > 0 && input <= menu.getMenuOptions().length) {
        return new InputModel(true, input);
      }
      if (inputRequired) {
        alertInvalidInput();
      }
    } while (inputRequired);
    return new InputModel(false, -1);
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  private void printMenu(Menu menu) {
    System.out.println("\n==  " + menu.getTitle() + "  ==");
    MenuOption[] menuOptions = menu.getMenuOptions();
    for (int i = 0; i < menuOptions.length; i++)
      System.out.printf("%s. %s\n", i+1, menuOptions[i].getTitle());
  }

  /**
   * Print menu and get user selection
   * @return selected menu index
   */
  public InputModel<String> processList(boolean inputRequired) {
    String input;
    ItemsList itemsList = promptModel.getItemsList();
    printList(itemsList);
    do {
      input = inputString(inputRequired).getValue();
      if (Arrays.asList(itemsList.getItems()).contains(input)) {
        return new InputModel(true, input);
      }
      if (inputRequired) {
        alertInvalidInput();
      }
    } while (inputRequired);
    return new InputModel(false, "");
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
