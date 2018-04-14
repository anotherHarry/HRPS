package com.cz2002.hrps.models;

public class PromptModelContainer {

  private String title;
  private PromptModel[] promptModels;

  public PromptModelContainer(String title, PromptModel[] promptModels) {
    this.title = title;
    this.promptModels = promptModels;
  }

  public String getTitle() {
    return title;
  }

  public PromptModel[] getPromptModels() {
    return promptModels;
  }

}
