package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.*;

import java.util.HashMap;

public class RoomController extends EntityController {

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
      menuSelection = inputBoundary.processMenu(true, false).getValue();
      switch (menuSelection) {
        case 1:
          create(new Room());
          break;
        case 2:
          update(new Room());
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

  private boolean checkAvailability() {
    Room room = find(new Room());
    if (room == null) {
      return false;
    }
    new OutputBoundary().printHashMap("Room Status", new HashMap<>() {{
      put("status", room.getStatus().toString());
    }});
    return room.isAvailable();
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
      menuSelection = inputBoundary.processMenu(true, false).getValue();
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
    InputModel<String> input = inputBoundary.getInput(true, true);
    if (!input.isSucceed()) {
      return;
    }
    String selectedStatus = input.getValue();
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
        roomIds[j] = statusRoomTypeRooms[j].getId();
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
        roomIds[j] = statusRooms[j].getId();
      }
      models[i] = new RoomStatusReportModel(
        roomStatus.toString(),
        roomIds
      );
    }
    new OutputBoundary().printRoomStatusReport(models);
  }

  private void reportFaultyRoom() {
    Room room = find(new Room());
    if (room == null) {
      return;
    }
    printEntity("Target Room", room);
    HashMap<String, String> hashMap = room.toHashMap();
    if(new Boundary().inputBoolean(
      "Are you sure you want to report that this room has problem?",
      true,
      true).getValue()
      ) {
      hashMap.replace("status", Room.RoomStatus.UNDER_MAINTENANCE.toString());
      if (room.update(hashMap)) {
        new Boundary().alertSuccessful();
      } else {
        new Boundary().alertFailed();
      }
    }
  }

  public Room findVacantRoom() {
    HashMap<String, String> queries = new HashMap<>() {{
      put("status", Room.RoomStatus.VACANT.toString());
    }};
    return findWith(queries, "Vacant Rooms", new Room());
  }

}
