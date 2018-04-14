package com.cz2002.hrps.entities;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Room extends Entity {

  public enum RoomStatus {
    VACANT, OCCUPIED, RESERVED, UNDER_MAINTENANCE
  }

  public enum RoomType {
    SINGLE, STANDARD, DELUXE, VIP
  }

  public enum BedType {
    SINGLE_BED, DOUBLE_BED, MASTER_BED
  }

  public enum FacingType {
    SEA_VIEW, CITY_VIEW, MOUNTAIN_VIEW, NO_VIEW
  }

  // Variable Declaration
  private RoomType 	roomType;
  private double 		roomRate;
  private double      roomWeekendRate;
  private int         roomFloor;
  private int 		roomNumber;
  private boolean 	wifiEnabled;
  private boolean 	smokingAllowed;
  private BedType 	bedType;
  private FacingType 	facing;
  private RoomStatus 	status;

  public Room() {
    super("room.txt");
  }

  public Room(HashMap<String, String> data) {
    super("room.txt");
    this.fromHashMap(data);
  }

  public RoomType getRoomType() {return roomType;}
  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  public double getRoomRate() {return roomRate;}
  public void setRoomRate(double roomRate) {
    this.roomRate = roomRate;
  }

  public double getRoomWeekendRate() {return roomWeekendRate;}
  public void setRoomWeekendRate(double roomWeekendRate) {
    this.roomWeekendRate = roomWeekendRate;
  }


  public int getRoomNumber() {return roomNumber;}
  public void setRoomNumber(int roomNumber) {
    this.roomNumber = roomNumber;
  }

  public BedType getBedType() {return bedType;}
  public void setBedType(BedType bedType) {this.bedType = bedType;}

  public boolean isWifiEnabled() {return wifiEnabled;}
  public void setWifiEnabled(boolean wifiEnabled) {this.wifiEnabled = wifiEnabled;}

  public FacingType getFacing() {return facing;}
  public void setFacing(FacingType facing) {this.facing = facing;}

  public boolean isSmokingAllowed() {return smokingAllowed;}
  public void setSmokingAllowed(boolean smokingAllowed) {this.smokingAllowed = smokingAllowed;}

  public RoomStatus getStatus() {return status;}
  public void setStatus(RoomStatus status) {this.status = status;}

  public int getRoomFloor() {
    return roomFloor;
  }
  public void setRoomFloor(int roomFloor) {
    this.roomFloor = roomFloor;
  }

  public String getRoomId() {
    return String.format("%02d", getRoomFloor()) + String.format("%02d", getRoomNumber());
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new Room(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> result = new LinkedHashMap<>();
    result.put("roomId", getRoomId());
    result.put("roomType", getRoomType().toString());
    result.put("roomRate", Double.toString(getRoomRate()));
    result.put("roomWeekendRate", Double.toString(getRoomWeekendRate()));
    result.put("roomFloor", Integer.toString(getRoomFloor()));
    result.put("roomNumber", Integer.toString(getRoomNumber()));
    result.put("wifiEnabled", Boolean.toString(isWifiEnabled()));
    result.put("smokingAllowed", Boolean.toString(isSmokingAllowed()));
    result.put("bedType", getBedType().toString());
    result.put("facing", getFacing().toString());
    result.put("status", getStatus().toString());

    return result;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    setRoomType(RoomType.valueOf(hashMap.get("roomType")));
    setRoomRate(Double.parseDouble(hashMap.get("roomRate")));
    setRoomWeekendRate(Double.parseDouble(hashMap.get("roomWeekendRate")));
    setRoomFloor(Integer.parseInt(hashMap.get("roomFloor")));
    setRoomNumber(Integer.parseInt(hashMap.get("roomNumber")));
    setWifiEnabled(Boolean.parseBoolean(hashMap.get("wifiEnabled")));
    setSmokingAllowed(Boolean.parseBoolean(hashMap.get("smokingAllowed")));
    setBedType(BedType.valueOf(hashMap.get("bedType")));
    setFacing(FacingType.valueOf(hashMap.get("facing")));
    setStatus(RoomStatus.valueOf(hashMap.get("status")));
  }

  public Room[] findRooms(HashMap<String, String> queries) {
    return (Room[]) findEntities(queries);
  }

  public Room findRoom(HashMap<String, String> queries) {
    return (Room) findEntity(queries);
  }

}
