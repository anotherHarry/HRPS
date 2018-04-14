package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class InputContainerBoundary extends InputBoundary {

  private PromptModelContainer promptModelContainer;
  private InputBoundary[] inputBoundaries;

  public InputContainerBoundary(PromptModelContainer promptModelContainer) {
    super();
    this.promptModelContainer = promptModelContainer;
    PromptModel[] promptModels = promptModelContainer.getPromptModels();
    this.inputBoundaries = new InputBoundary[promptModels.length];
    for (int i = 0; i < promptModels.length; i++) {
      PromptModel promptModel = promptModels[i];
      this.inputBoundaries[i] = new InputBoundary(promptModel);
    }
  }

  public HashMap<String, String> getInputContainer(boolean inputRequired) {
    System.out.println("\n==  " + promptModelContainer.getTitle() + "  ==");
    LinkedHashMap<String, String> result = new LinkedHashMap<>();
    for (InputBoundary inputBoundary: inputBoundaries) {
      InputModel<String> input = inputBoundary.getInput(inputRequired);
      if (input.getIsSuccess()) {
        result.put(inputBoundary.getPromptModel().getKey(), input.getValue());
      }
    }
    return result;
  }

}
