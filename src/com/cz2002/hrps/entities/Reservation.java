package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.*;
import com.cz2002.hrps.utilities.DateProcessor;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reservation extends Entity {

  public enum ReservationStatus {
    WAITLIST, CONFIRMED, CHECKED_IN, CHECKED_OUT, EXPIRED
  }

  private ReservationStatus reservationStatus;
  private String id = null;
  private int numberOfChildren;
  private int numberOfAdults;
  private Date createdAt;
  private Date checkInDate;
  private Date checkOutDate;
  private String guestId;
  private String roomId;

  private Guest guest = null;
  private Room room = null;
  private ArrayList<RoomService> roomServices;


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
   * Get the value of id
   *
   * @return the value of id
   */
  public String getId() {
    if (id == null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-Hm");
      id = String.format("%s-%s-%s", sdf.format(getCreatedAt()), getGuestId(), getRoomId());
    }
    return id;
  }

  /**
   * Set Reservation Identifier
   * @param id
   */
  public void setId(String id) {
    this.id = id;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  private void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
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
    if (guestId == null) {
      guestId = getGuest().getId();
    }
    return guestId;
  }

  public void setGuestId(String guestId) {
    this.guestId = guestId;
  }

  public String getRoomId() {
    if (roomId == null) {
      roomId = getRoom().getId();
    }
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
        put("id", roomId);
      }});

    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public ArrayList<RoomService> getRoomServices() {
    if (roomServices == null || roomServices.size() == 0) {
      RoomService[] rss = new RoomService().findRoomServices(new HashMap<>() {{
        put("id", getId());
      }});
      roomServices = new ArrayList<>(Arrays.asList(rss));
    }

    return roomServices;
  }

  public void setRoomServices(ArrayList<RoomService> roomServices) {
    this.roomServices = roomServices;
  }

  @Override
  public Entity newInstance() {
    return new Reservation();
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new Reservation(data);
  }

  @Override
  public boolean create() {
    Room room = getRoom();
    Room.RoomStatus oldRoomStatus = room.getStatus();
    if (reservationStatus == ReservationStatus.CHECKED_IN) {
      room.setStatus(Room.RoomStatus.OCCUPIED);
    } else {
      if (room.getStatus() == Room.RoomStatus.VACANT) {
        setReservationStatus(ReservationStatus.CONFIRMED);
        room.setStatus(Room.RoomStatus.RESERVED);
      } else {
        setReservationStatus(ReservationStatus.WAITLIST);
      }
    }
    createdAt = new Date();
    if (super.create()) {
      room.update();
      return true;
    }
    room.setStatus(oldRoomStatus);
    return false;
  }

  @Override
  public boolean update() {
    if (!super.update()) {
      return false;
    }

    Room room = getRoom();
    Room.RoomStatus roomStatus = getReservationStatus() == ReservationStatus.CHECKED_IN
      ? Room.RoomStatus.OCCUPIED
      : getReservationStatus() == ReservationStatus.CONFIRMED ||
        getReservationStatus() == ReservationStatus.WAITLIST
        ? Room.RoomStatus.RESERVED
        : Room.RoomStatus.VACANT;
    room.setStatus(roomStatus);
    return room.update();
  }

  @Override
  public boolean delete() {
    if (!super.delete()) {
      return false;
    }
    Room room = getRoom();
    room.setStatus(Room.RoomStatus.VACANT);
    return room.update();
  }

  @Override
  public HashMap<String, String> toHashMap() {
    LinkedHashMap<String, String> results = new LinkedHashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");

    results.put("id", getId());
    results.put("reservationStatus", getReservationStatus().toString());
    results.put("numberOfChildren", Integer.toString(getNumberOfChildren()));
    results.put("numberOfAdult", Integer.toString(getNumberOfAdults()));
    results.put("createdAt", sdf.format(getCreatedAt()));
    if (getCheckInDate() != null) {
      results.put("checkInDate", sdf.format(getCheckInDate()));
    }
    if (getCheckOutDate() != null) {
      results.put("checkOutDate", sdf.format(getCheckOutDate()));
    }
    results.put("guestId", getGuestId());
    results.put("roomId", getRoomId());

    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");

    setId(hashMap.get("id"));
    if (hashMap.get("reservationStatus") != null) {
      setReservationStatus(ReservationStatus.valueOf(hashMap.get("reservationStatus")));
    }
    setNumberOfChildren(Integer.parseInt(hashMap.get("numberOfChildren")));
    setNumberOfAdults(Integer.parseInt(hashMap.get("numberOfAdult")));
    setGuestId(hashMap.get("guestId"));
    setRoomId(hashMap.get("roomId"));
    try {
      if (hashMap.get("createdAt") != null) {
        setCreatedAt(sdf.parse(hashMap.get("createdAt")));
      }
      if (hashMap.get("checkInDate") != null) {
        setCheckInDate(sdf.parse(hashMap.get("checkInDate")));
      }
      if (hashMap.get("checkOutDate") != null) {
        setCheckOutDate(sdf.parse(hashMap.get("checkOutDate")));
      }
    } catch (Exception e) {}
  }

  @Override
  public boolean isAleadyExisted() {
    Reservation[] reservations = findReservations(new HashMap<>() {{
    }});
    if (reservations == null) {
      return false;
    }
    for (Reservation reservation: reservations) {
      if (reservation.getGuestId().equals(getGuestId()) &&
        reservation.getRoomId().equals(getRoomId()) &&
        reservation.getReservationStatus() != ReservationStatus.CHECKED_OUT &&
        reservation.getReservationStatus() != ReservationStatus.EXPIRED) {
        return true;
      }
    }
    return false;
  }

  @Override
  public PromptModelContainer promptModelContainer() {
    return new PromptModelContainer(
      "",
      new PromptModel[] {
        new PromptModel("id", "Reservation Id", PromptModel.InputType.STRING),
        new PromptModel("reservationStatus", new Menu(
          "Reservation Status",
          new MenuOption[] {
            new MenuOption("WAITLIST", "Waitlist"),
            new MenuOption("CONFIRMED", "Confirmed"),
            new MenuOption("CHECKED_IN", "Check-in"),
            new MenuOption("CHECKED_OUT", "Check-out"),
            new MenuOption("EXPIRED", "Expired"),
          }
        )),
        new PromptModel("numberOfChildren", "Number of Children", PromptModel.InputType.POSITIVE_INT),
        new PromptModel("numberOfAdult", "Number of Adults", PromptModel.InputType.POSITIVE_INT),
        new PromptModel("createdAt", "Created At", PromptModel.InputType.DATE),
        new PromptModel("checkInDate", "Check-in Date", PromptModel.InputType.DATE),
        new PromptModel("checkOutDate", "Check-out Date", PromptModel.InputType.DATE),
        new PromptModel("guestId", "Guest Id", PromptModel.InputType.STRING),
        new PromptModel("roomId", "Room Id", PromptModel.InputType.STRING),
      }
    );
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (Arrays.asList("id", "reservationStatus", "createdAt", "checkOutDate", "guestId", "roomId").contains(
        promptModel.getKey())
        ) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Make Reservation",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    return new PromptModelContainer(
      "Search for Reservations",
      promptModelContainer().getPromptModels()
    );
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("reservationStatus") &&
        getReservationStatus().equals(ReservationStatus.WAITLIST)) {
        promptModels.add(new PromptModel(
          "reservationStatus",
          new Menu(
            "Reservation Status",
            new MenuOption[] {
              new MenuOption("WAITLIST", "Waitlist"),
              new MenuOption("CONFIRMED", "Confirmed"),
            }
          )
        ));
      } else if ((promptModel.getKey().equals("checkInDate") &&
        !(getReservationStatus().equals(ReservationStatus.WAITLIST) ||
          getReservationStatus().equals(ReservationStatus.CONFIRMED)))) {
      } else if (Arrays.asList("id", "reservationStatus", "createdAt", "guestId", "roomId").contains(
        promptModel.getKey())
        ) {
      } else {
        promptModels.add(promptModel);
      }
    }
    return new PromptModelContainer(
      "Edit Reservation Details",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  public PromptModelContainer walkInPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (Arrays.asList("id", "reservationStatus", "createdAt", "checkInDate", "checkOutDate", "guestId", "roomId").contains(
        promptModel.getKey())
        ) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Make Reservation",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public String itemsListKey() {
    return getId();
  }

  public Reservation[] findReservations(HashMap<String, String> queries) {
    return (Reservation[]) findEntities(queries);
  }

  public Reservation findReservation(HashMap<String, String> queries) {
    return (Reservation) findEntity(queries);
  }

  public void addRoomService(RoomService roomService) {
    getRoomServices().add(roomService);
  }

  public boolean setExpire() {
    setReservationStatus(ReservationStatus.EXPIRED);
    return update();
  }

  private void checkInConfig() {
    setReservationStatus(ReservationStatus.CHECKED_IN);
    setCheckInDate(new Date());
  }

  public boolean checkIn() {
    checkInConfig();
    return update();
  }

  public boolean walkIn() {
    checkInConfig();
    return create();
  }

  public boolean checkOut() {
    setReservationStatus(ReservationStatus.CHECKED_OUT);
    setCheckOutDate(new Date());
    return update();
  }

  public Bill getBill() {
    return new Bill(
      roomChargeWeekday(),
      roomChargeWeekend(),
      roomSericeFee()
    );
  }


  private double roomChargeWeekday() {
    int weekDays = DateProcessor.getTotalWeekdays(checkInDate, checkOutDate);
    return getRoom().getRoomRate() * weekDays;
  }

  private double roomChargeWeekend() {
    int weekEnds = DateProcessor.getTotalWeekends(checkInDate, checkOutDate);
    return getRoom().getRoomWeekendRate() * weekEnds;
  }

  private double roomSericeFee() {
    double roomServiceFee = 0.0;
    for (RoomService roomService: getRoomServices()) {
      roomServiceFee += roomService.getFee();
    }
    return roomServiceFee;
  }

}
