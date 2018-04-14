package com.cz2002.hrps.models;

public class InputModel<T> {

  private boolean isSuccess;
  private T value;

  public InputModel(boolean isSuccess, T value) {
    this.isSuccess = isSuccess;
    this.value = value;
  }

  public boolean getIsSuccess() {
    return isSuccess;
  }

  public T getValue() {
    return value;
  }

}
