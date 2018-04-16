package com.cz2002.hrps.models;

public class InputModel<T> {

  public enum InputStatus {
    SUCCEED, FAILED, CANCELED
  }

  private InputStatus inputStatus;
  private T value;

  public InputModel(InputStatus inputStatus, T value) {
    this.inputStatus = inputStatus;
    this.value = value;
  }

  public InputStatus getInputStatus() {
    return inputStatus;
  }

  public T getValue() {
    return value;
  }

  public boolean isSucceed() {
    return inputStatus == InputStatus.SUCCEED;
  }

  public static InputStatus statusFor(boolean state) {
    return state ? InputStatus.SUCCEED : InputStatus.FAILED;
  }

}
