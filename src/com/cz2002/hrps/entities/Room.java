package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;
import com.cz2002.hrps.models.PromptModelContainer;
import com.cz2002.hrps.models.Menu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

  @Override
  public PromptModelContainer promptModelContainer() {
    return new PromptModelContainer(
      "",
      new PromptModel[] {
        new PromptModel("roomId", "Room Id", PromptModel.InputType.STRING),
        new PromptModel("roomType", new Menu(
          "Room Type",
          new MenuOption[] {
            new MenuOption("SINGLE", "Single"),
            new MenuOption("STANDARD", "Standard"),
            new MenuOption("DELUXE", "Deluxe"),
            new MenuOption("VIP", "Vip"),
          }
        )),
        new PromptModel("roomRate", "Room Rate", PromptModel.InputType.POSITIVE_DOUBLE),
        new PromptModel("roomWeekendRate", "Room Weekend Rate", PromptModel.InputType.POSITIVE_DOUBLE),
        new PromptModel("roomFloor", "Room Floor", PromptModel.InputType.POSITIVE_INT),
        new PromptModel("roomNumber", "Room Number", PromptModel.InputType.POSITIVE_INT),
        new PromptModel("wifiEnabled", "Wifi Enabled", PromptModel.InputType.BOOLEAN),
        new PromptModel("smokingAllowed", "Smoking Allowed", PromptModel.InputType.BOOLEAN),
        new PromptModel("bedType", new Menu(
          "Bed Type",
          new MenuOption[] {
            new MenuOption("SINGLE_BED", "Single Bed"),
            new MenuOption("DOUBLE_BED", "Double Bed"),
            new MenuOption("MASTER_BED", "Master Bed"),
          }
        )),
        new PromptModel("facing", new Menu(
          "Facing View",
          new MenuOption[] {
            new MenuOption("SEA_VIEW", "Sea View"),
            new MenuOption("CITY_VIEW", "City View"),
            new MenuOption("MOUNTAIN_VIEW", "Mountain View"),
            new MenuOption("NO_VIEW", "No View"),
          }
        )),
        new PromptModel("status", new Menu(
          "Room Status",
          new MenuOption[] {
            new MenuOption("VACANT", "Vacant"),
            new MenuOption("OCCUPIED", "Occupied"),
            new MenuOption("RESERVED", "Reserved"),
            new MenuOption("UNDER_MAINTENANCE", "Under Maintainance"),
          }
        )),
      }
    );
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey() == "roomId") {
        continue;
      } else if (promptModel.getKey() == "status") {
        promptModels.add(new PromptModel(
          "status",
          new Menu(
            "Room Status",
            new MenuOption[] {
              new MenuOption("VACANT", "Vacant"),
              new MenuOption("UNDER_MAINTENANCE", "Under Maintainance"),
            }
          )
        ));
      } else {
        promptModels.add(promptModel);
      }
    }
    return new PromptModelContainer(
      "Create New Room",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    return new PromptModelContainer(
      "Search Rooms",
      promptModelContainer().getPromptModels()
    );
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (Arrays.asList("roomId", "roomFloor", "roomNumber").contains(promptModel.getKey())) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Create New Room",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  public Room[] findRooms(HashMap<String, String> queries) {
    return (Room[]) findEntities(queries);
  }

  public Room findRoom(HashMap<String, String> queries) {
    return (Room) findEntity(queries);
  }

}
