package com.cz2002.hrps.models;

public class ItemsList {

  private String title;
  private String[] items;

  public ItemsList(String title, String[] items) {
    this.title = title;
    this.items = items;
  }

  public String getTitle() {
    return title;
  }

  public String[] getItems() {
    return items;
  }
  
}
