package com.cz2002.hrps.models;

public class MenuOption {

  private String key;
  private String title;

  public MenuOption(String key, String title) {
    this.key = key;
    this.title = title;
  }

  public String getKey() {
    return key;
  }

  public String getTitle() {
    return title;
  }

}
