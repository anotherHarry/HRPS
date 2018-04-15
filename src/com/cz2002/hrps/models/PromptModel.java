package com.cz2002.hrps.models;

public class PromptModel {

  public enum InputType {
    STRING,
    INT,
    POSITIVE_INT,
    DOUBLE,
    POSITIVE_DOUBLE,
    BOOLEAN,
    DATE,
    GENDER,
    CONTACT_NUMBER,
    CREDITCARD_NUMBER,
    MENU_SELECTION,
    LIST_SELECTION
  }

  private String key;
  private String title;
  private Menu menu;
  private ItemsList itemsList;
  private InputType inputType;

  public PromptModel(String key, String title, InputType inputType) {
    this.key = key;
    this.title = title;
    this.inputType = inputType;
  }

  public PromptModel(String key, Menu menu) {
    this.key = key;
    this.menu = menu;
    this.inputType = InputType.MENU_SELECTION;
  }

  public PromptModel(String key, ItemsList itemsList) {
    this.key = key;
    this.itemsList = itemsList;
    this.inputType = InputType.LIST_SELECTION;
  }

  public String getKey() {
    return key;
  }

  public String getTitle() {
    return title;
  }

  public Menu getMenu() {
    return menu;
  }

  public ItemsList getItemsList() {
    return itemsList;
  }

  public InputType getInputType() {
    return inputType;
  }
}
