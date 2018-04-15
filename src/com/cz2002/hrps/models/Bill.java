package com.cz2002.hrps.models;

public class Bill {

  private double roomChargeWeekday;
  private double roomChargeWeekend;
  private double roomServiceFee;
  private double taxRate;
  private double discountRate;

  public Bill(double roomChargeWeekday,
              double roomChargeWeekend,
              double roomSericeFee) {
    this.roomChargeWeekday = roomChargeWeekday;
    this.roomChargeWeekend = roomChargeWeekend;
    this.roomServiceFee = roomSericeFee;
  }

  public double getRoomChargeWeekday() {
    return roomChargeWeekday;
  }

  public double getRoomChargeWeekend() {
    return roomChargeWeekend;
  }

  public double getRoomServiceFee() {
    return roomServiceFee;
  }

  public double getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(double taxRate) {
    this.taxRate = taxRate;
  }

  public double getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(double discountRate) {
    this.discountRate = discountRate;
  }

  public double getTotalFee() {
    return getRoomChargeWeekday() + getRoomChargeWeekend() + getRoomServiceFee();
  }

  public double getTotalFeeDiscount() {
    return getTotalFee()*(1-getDiscountRate()/100);
  }

  public double getTotalFeeDiscountPlusTax() {
    return getTotalFeeDiscount()*(1+getTaxRate()/100);
  }

}
