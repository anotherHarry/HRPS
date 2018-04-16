package com.cz2002.hrps.boundaries;

import com.cz2002.hrps.models.Bill;
import com.cz2002.hrps.models.RoomStatusReportModel;
import com.cz2002.hrps.models.RoomTypeOccupancyRateReportModel;

import java.util.HashMap;
import java.util.Map;

public class OutputBoundary extends Boundary {

  public void printHashMap(String title, HashMap<String, String> hashMap) {
    printHeader(title);
    for (Map.Entry<String, String> entry : hashMap.entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue();
      System.out.println(key + ": \t" + value);
    }
    System.out.println();
  }

  public void printHashMaps(String title, HashMap<String, String>[] hashMaps) {
    printHeader(title);
    for (int i = 0; i < hashMaps.length; i++) {
      printHashMap("", hashMaps[i]);
    }
    System.out.println();
  }

  public void printRoomTypeOccupancyRateReport(RoomTypeOccupancyRateReportModel[] models) {
    printHeader("Room Type Occupancy Rate Report");
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
    printHeader("Room Status Report");
    for(RoomStatusReportModel model: models) {
      System.out.println(model.getStatus() + " :");
      System.out.print("\t\tRooms : ");
      for (String roomId: model.getRoomIds()) {
        System.out.print(roomId + ", ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public void printBill(Bill bill) {
    printBillLine("Weekday Charge\t\t", bill.getRoomChargeWeekday(), null);
    printBillLine("Weekend Charge\t\t", bill.getRoomChargeWeekend(), null);
    printBillLine("Room Service Fee\t", bill.getRoomServiceFee(), null);
    printBillLine("\t\t\t\t__________________", null, null);
    printBillLine("\t\t\t\t\t", bill.getTotalFee(), null);
    printBillLine("Discount\t\t\t-", bill.getDiscountRate()/100, "%");
    printBillLine("\t\t\t\t__________________", null, null);
    printBillLine("\t\t\t\t\t", bill.getTotalFeeDiscount(), null);
    printBillLine("Tax\t\t\t\t\t+", bill.getTaxRate()/100,"%");
    printBillLine("\t\t\t\t__________________", null, null);
    printBillLine("Total\t\t\t\t", bill.getTotalFeeDiscountPlusTax(), null);
  }

  private void printBillLine(String leading, Double value, String trailing) {
    if (leading != null) {
      System.out.print(leading);
    }
    if (value != null) {
      System.out.print(String.format("%.2f", value));
    }
    if (trailing != null) {
      System.out.print(trailing);
    }
    System.out.println();
  }

  private void printHeader(String header) {
    if (header.equals("")) {
      return;
    }
    System.out.println(ANSI_BLUE + "► " + header + ANSI_RESET);
  }

}
