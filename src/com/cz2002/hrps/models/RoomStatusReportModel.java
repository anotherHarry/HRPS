package com.cz2002.hrps.models;

public class RoomStatusReportModel {

  private String status;
  private String[] roomIds;

  public RoomStatusReportModel(String status, String[] roomIds) {
    this.status = status;
    this.roomIds = roomIds;
  }

  public String getStatus() {
    return status;
  }

  public String[] getRoomIds() {
    return roomIds;
  }

}
