package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.RoomStatusReportModel;
import com.cz2002.hrps.models.RoomTypeOccupancyRateReportModel;

import java.util.HashMap;
import java.util.Map;

public class OutputBoundary extends Boundary {

  public void printHashMap(String title, HashMap<String, String> hashMap) {
    System.out.println(title);
    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      System.out.println(key + ": \t" + value);
    }
    System.out.println();
  }

  public void printHashMaps(String title, HashMap<String, String>[] hashMaps) {
    System.out.println("* " + title);
    for (int i = 0; i < hashMaps.length; i++) {
      printHashMap("", hashMaps[i]);
    }
    System.out.println();
  }

  public void printRoomTypeOccupancyRateReport(RoomTypeOccupancyRateReportModel[] models) {
    System.out.println("*");
    for(RoomTypeOccupancyRateReportModel model: models) {
      System.out.println(model.getRoomType() + " :\tNumber : " + model.getNumber() + " out of " + model.getTotal());
      System.out.print("\t\t\tRooms : ");
      for (String roomId: model.getRoomIds()) {
        System.out.print(roomId + ", ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void printRoomStatusReport(RoomStatusReportModel[] models) {
    System.out.println("*");
    for(RoomStatusReportModel model: models) {
      System.out.println(model.getStatus() + "\t:");
      System.out.print("\t\tRooms : ");
      for (String roomId: model.getRoomIds()) {
        System.out.print(roomId + ", ");
      }
      System.out.println();
    }
    System.out.println();
  }

}
