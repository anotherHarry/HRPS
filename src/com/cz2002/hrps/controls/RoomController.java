package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.InputContainerBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Entity;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomController implements Control {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Room Menu",
        new MenuOption[] {
          new MenuOption("create_room", "Create Room"),
          new MenuOption("edit_room_details", "Edit Room Details"),
          new MenuOption("check_availability", "Check Availability"),
          new MenuOption("print_rooms_status_statistics", "Print Rooms' Status Statistics"),
          new MenuOption("report_faulty_room", "Report Faulty Room"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          create();
          break;
        case 2:
          update();
          break;
        case 3:
          checkAvailability();
          break;
        case 4:
          printRoomsStatusStatistics();
          break;
        case 5:
          reportFaultyRoom();
          break;
        default:
          break;
      }
    } while (menuSelection != 6);
  }

  @Override
  public Entity create() {
    Room room = new Room();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      room.creationPromptModelContainer()
    );
    HashMap<String, String> hashMap = inputContainerBoundary.getInputContainer(true);
    room.fromHashMap(hashMap);
    if (room.create()) {
      new Boundary().alertSuccessful();
      return room;
    }
    new Boundary().alertFailed();
    return null;
  }

  @Override
  public Entity find() {
    Room[] rooms = (Room[]) findList();
    if (rooms.length == 0) {
      new Boundary().alertNotFound();
      return null;
    } else if (rooms.length == 1) {
      return rooms[0];
    }
    printEntities("Search Results", rooms);
    String[] items = new String[rooms.length];
    for (int i = 0; i < rooms.length; i++) {
      items[i] = rooms[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (Room room: rooms) {
      if (room.itemsListKey().equals(key)) {
        return room;
      }
    }
    return null;
  }

  @Override
  public Entity[] findList() {
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      new Room().findingPromptModelContainer()
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    Room[] rooms = new Room().findRooms(queries);
    return rooms;
  }

  @Override
  public Entity update() {
    Room room = (Room) find();
    printEntity("Target Room", room);
    HashMap<String, String> hashMap = room.toHashMap();
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      room.editingPromptModelContainer()
    );
    HashMap<String, String> updatedHashMap = inputContainerBoundary.getInputContainer(false);
    for (Map.Entry<String, String> entry : updatedHashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      hashMap.replace(key, value);
    }
    if (room.update(hashMap)) {
      new Boundary().alertSuccessful();
      return room;
    }
    new Boundary().alertFailed();
    return null;
  }

  private boolean checkAvailability() {
    Room room = (Room) find();
    new OutputBoundary().printHashMap("Room Status", new HashMap<>() {{
      put("status", room.getStatus().toString());
    }});
    return room.getStatus() == Room.RoomStatus.VACANT;
  }

  private void printRoomsStatusStatistics() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Report Type",
        new MenuOption[] {
          new MenuOption("room_type_occupancy_rate", "Room Type Occupancy Rate"),
          new MenuOption("room_status", "Room Status"),
          new MenuOption("back", "Back"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          printRoomTypeOccupancyRate();
          break;
        case 2:
          printRoomsStatus();
          break;
        default:
          break;
      }
    } while (menuSelection != 3);
  }

  private void printRoomTypeOccupancyRate() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "status",
      new Menu(
        "Choose Room Status",
        new MenuOption[] {
          new MenuOption("VACANT", "Vacant"),
          new MenuOption("OCCUPIED", "Occupied"),
          new MenuOption("RESERVED", "Reserved"),
          new MenuOption("UNDER_MAINTENANCE", "Under Maintainance"),
        }
      )
    ));
    String selectedStatus = inputBoundary.getInput(true).getValue();
    HashMap<String, String> statusQuery = new HashMap<String, String>() {{
      put("status", selectedStatus);
    }};
    RoomTypeOccupancyRateReportModel[] models = new RoomTypeOccupancyRateReportModel[
      Room.RoomType.values().length
      ];
    for (int i = 0; i < Room.RoomType.values().length; i++) {
      Room.RoomType roomType = Room.RoomType.values()[i];
      Room[] roomTypeRooms = new Room().findRooms(new HashMap<>() {{
        put("roomType", roomType.toString());
      }});
      Room[] statusRoomTypeRooms = new Room().findRooms(new HashMap<>() {{
        put("roomType", roomType.toString());
        put("status", statusQuery.get("status"));
      }});
      String[] roomIds = new String[statusRoomTypeRooms.length];
      for (int j = 0; j < statusRoomTypeRooms.length; j++) {
        roomIds[j] = statusRoomTypeRooms[j].getRoomId();
      }
      models[i] = new RoomTypeOccupancyRateReportModel(
        roomType.toString(),
        statusRoomTypeRooms.length,
        roomTypeRooms.length,
        roomIds
      );
    }
    new OutputBoundary().printRoomTypeOccupancyRateReport(models);
  }

  private void printRoomsStatus() {
    RoomStatusReportModel[] models = new RoomStatusReportModel[Room.RoomStatus.values().length];
    for (int i = 0; i < Room.RoomStatus.values().length; i++) {
      Room.RoomStatus roomStatus = Room.RoomStatus.values()[i];
      Room[] statusRooms = new Room().findRooms(new HashMap<>() {{
        put("status", roomStatus.toString());
      }});
      String[] roomIds = new String[statusRooms.length];
      for (int j = 0; j < statusRooms.length; j++) {
        roomIds[j] = statusRooms[j].getRoomId();
      }
      models[i] = new RoomStatusReportModel(
        roomStatus.toString(),
        roomIds
      );
    }
    new OutputBoundary().printRoomStatusReport(models);
  }

  private void reportFaultyRoom() {
    Room room = (Room) find();
    printEntity("Target Room", room);
    HashMap<String, String> hashMap = room.toHashMap();
    if(new Boundary().inputBoolean(
      "Are you sure you want to report that this room has problem?",
      true).getValue()
      ) {
      hashMap.replace("status", Room.RoomStatus.UNDER_MAINTENANCE.toString());
      if (room.update(hashMap)) {
        new Boundary().alertSuccessful();
      }
    }
  }

  public Room findVacantRoom() {
    Room[] rooms = findVacantRooms();
    if (rooms.length == 0) {
      new Boundary().alertNotFound();
      return null;
    } else if (rooms.length == 1) {
      return rooms[0];
    }
    printEntities("Vacant Rooms", rooms);
    String[] items = new String[rooms.length];
    for (int i = 0; i < rooms.length; i++) {
      items[i] = rooms[i].itemsListKey();
    }
    InputBoundary inputBoundary = new InputBoundary(
      new PromptModel("find", new ItemsList(
        "Choose the item",
        items
      ))
    );
    String key = inputBoundary.getInput(true).getValue();
    for (Room room: rooms) {
      if (room.itemsListKey().equals(key)) {
        printEntity("Target Room", room);
        return room;
      }
    }
    return null;
  }

  private Room[] findVacantRooms() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: new Room().findingPromptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("status")) {
        continue;
      }
      promptModels.add(promptModel);
    }
    PromptModelContainer promptModelContainer = new PromptModelContainer(
      "Find Vacant Rooms",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
    InputContainerBoundary inputContainerBoundary = new InputContainerBoundary(
      promptModelContainer
    );
    HashMap<String, String> queries = inputContainerBoundary.getInputContainer(false);
    queries.put("status", Room.RoomStatus.VACANT.toString());
    Room[] rooms = new Room().findRooms(queries);
    return rooms;
  }

}
