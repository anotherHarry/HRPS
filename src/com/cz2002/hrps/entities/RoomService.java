package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.PromptModelContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RoomService extends Entity {

  public enum OrderStatus {
    CONFIRMED, PREPARING, DELIVERED
  }

  private String remarks;
  private OrderStatus status;
  private Date created;
  private String reservationId;

  private Reservation reservation;

  public RoomService() {
    super("roomservice.txt");
  }

  public RoomService(HashMap<String, String> data) {
    super("roomservice.txt");
    this.fromHashMap(data);
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public Date getCreated() {
    return created;
  }

  private void setCreated(Date created) {
    this.created = created;
  }

  public String getReservationId() {
    return reservationId;
  }

  public void setReservationId(String reservationId) {
    this.reservationId = reservationId;
  }

  public Reservation getReservation() {
    if (reservation == null)
      reservation = new Reservation().findReservation(new HashMap<String, String>() {{
        put("reservationId", reservationId);
      }});
    return reservation;
  }

  public void setReservation(Reservation reservation) {
    this.reservation = reservation;
  }

  public String getId() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
    return String.format("%s-%s", sdf.format(getCreated()), getReservation().getRoom().getRoomId());
  }

  @Override
  public Entity newInstance() {
    return new RoomService();
  }

  @Override
  public Entity newInstance(HashMap<String, String> data) {
    return new RoomService(data);
  }

  @Override
  public HashMap<String, String> toHashMap() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
    LinkedHashMap<String, String> results = new LinkedHashMap<>();

    results.put("id", getId());
    results.put("remarks", getRemarks());
    results.put("status", getStatus().toString());
    results.put("created", sdf.format(getCreated()));
    results.put("reservationId", getReservationId());
    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");

      setRemarks(hashMap.get("remarks"));
      setStatus(OrderStatus.valueOf(hashMap.get("status")));
      setCreated(sdf.parse(hashMap.get("created")));
      setReservationId(hashMap.get("reservationId"));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Override
  public PromptModelContainer promptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    return null;
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    return null;
  }

  @Override
  public String itemsListKey() {
    return getId();
  }

  public RoomService[] findRoomServices(HashMap<String, String> queries) {
    return (RoomService[]) findEntities(queries);
  }

  public RoomService findRoomService(HashMap<String, String> queries) {
    return (RoomService) findEntity(queries);
  }

}
