package com.cz2002.hrps.models;

public class RoomTypeOccupancyRateReportModel {

  private String roomType;
  private int number;
  private int total;
  private String[] roomIds;

  public RoomTypeOccupancyRateReportModel(String roomType, int number, int total, String[] roomIds) {
    this.roomType = roomType;
    this.number = number;
    this.total = total;
    this.roomIds = roomIds;
  }

  public String getRoomType() {
    return roomType;
  }

  public int getNumber() {
    return number;
  }

  public int getTotal() {
    return total;
  }

  public String[] getRoomIds() {
    return roomIds;
  }

}
