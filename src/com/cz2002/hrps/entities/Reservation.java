package com.cz2002.hrps.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Reservation extends Entity {

  public enum ReservationStatus {
    CONFIRMED, WAITLIST, CHECKED_IN, CHECKED_OUT, EXPIRED
  }

  private ReservationStatus reservationStatus;
  private String reservationId = null;
  private int numberOfChildren;
  private int numberOfAdults;
  private Date checkInDate;
  private Date checkOutDate;
  private String guestId;
  private String roomId;

  private Guest guest = null;
  private Room room = null;
  private RoomService roomService;


  public Reservation() {
    super("reservation.txt");
  }

  public Reservation(HashMap<String, String> data) {
    super("reservation.txt");
    this.fromHashMap(data);
  }

  public ReservationStatus getReservationStatus() {
    return reservationStatus;
  }


  public void setReservationStatus(ReservationStatus reservationStatus) {
    this.reservationStatus = reservationStatus;
  }

  /**
   * Get the value of reservationId
   *
   * @return the value of reservationId
   */
  public String getReservationId() {
    return reservationId;
  }

  /**
   * Set Reservation Identifier
   * @param reservationId
   */
  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  /**
   * Get the value of numberOfAdults
   *
   * @return the value of numberOfAdults
   */
  public int getNumberOfAdults() {
    return numberOfAdults;
  }

  /**
   * Set the value of numberOfAdults
   *
   * @param numberOfAdults new value of numberOfAdults
   */
  public void setNumberOfAdults(int numberOfAdults) {
    this.numberOfAdults = numberOfAdults;
  }

  /**
   * Get the value of numberOfChildren
   *
   * @return the value of numberOfChildren
   */
  public int getNumberOfChildren() {
    return numberOfChildren;
  }

  /**
   * Set the value of numberOfChildren
   *
   * @param numberOfChildren new value of numberOfChildren
   */
  public void setNumberOfChildren(int numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }

  /**
   * Get the value of checkInDate
   *
   * @return the value of checkInDate
   */
  public Date getCheckInDate() {
    return checkInDate;
  }

  /**
   * Set the value of checkInDate
   *
   * @param checkInDate new value of checkInDate
   */
  public void setCheckInDate(Date checkInDate) {
    this.checkInDate = checkInDate;
  }


  /**
   * Get the value of checkOutDate
   *
   * @return the value of checkOutDate
   */
  public Date getCheckOutDate() {
    return checkOutDate;
  }

  /**
   * Set the value of checkOutDate
   *
   * @param checkOutDate new value of checkOutDate
   */
  public void setCheckOutDate(Date checkOutDate) {
    this.checkOutDate = checkOutDate;
  }

  public String getGuestId() {
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public Guest getGuest() {
    if (guest == null)
      guest = new Guest().findGuest(new HashMap<String, String>() {{
        put("id", guestId);
      }});

    return guest;
  }

  public void setGuest(Guest guest) {
    this.guest = guest;
  }

  public Room getRoom() {
    if (room == null)
      room = new Room().findRoom(new HashMap<String, String>() {{
        put("roomId", roomId);
      }});

    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public RoomService getRoomService() {
    if (roomService == null)
      roomService = new RoomService().findRoomService(new HashMap<String, String>() {{
        put("reservationId", getReservationId());
      }});

    return roomService;
  }

  public void setRoomService(RoomService roomService) {
    this.roomService = roomService;
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new Reservation(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> results = new LinkedHashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmm");

    results.put("reservationId", getReservationId());
    results.put("reservationStatus", getReservationStatus().toString());
    results.put("numberOfChildren", Integer.toString(getNumberOfChildren()));
    results.put("numberOfAdult", Integer.toString(getNumberOfAdults()));
    results.put("checkInDate", sdf.format(getCheckInDate()));
    results.put("checkOutDate", sdf.format(getCheckOutDate()));
    results.put("guestId", getGuestId());
    results.put("roomId", getRoomId());

    return results;
  }


  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmm");

    try {
      setNumberOfChildren(Integer.parseInt(hashMap.get("numberOfChildren")));
      setNumberOfAdults(Integer.parseInt(hashMap.get("numberOfAdult")));
      setCheckInDate(sdf.parse(hashMap.get("checkInDate")));
      setCheckOutDate(sdf.parse(hashMap.get("checkOutDate")));

      // Caught Exception as this are not user entered data
      try {
        setReservationId(hashMap.get("reservationId"));
        setReservationStatus(ReservationStatus.valueOf(hashMap.get("reservationStatus")));
        setGuestId(hashMap.get("guestId"));
        setRoomId(hashMap.get("roomId"));
      } catch (Exception e) {}
    } catch (ParseException e) {
      System.out.println("Error loading Check in or Check out dates");
    }
  }

  public Reservation[] findReservations(HashMap<String, String> queries) {
    return (Reservation[]) findEntities(queries);
  }

  public Reservation findReservation(HashMap<String, String> queries) {
    return (Reservation) findEntity(queries);
  }

}
