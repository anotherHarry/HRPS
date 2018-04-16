package com.cz2002.hrps.entities;

import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;
import com.cz2002.hrps.models.PromptModelContainer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RoomService extends Entity {

  public enum OrderStatus {
    CONFIRMED, PREPARING, DELIVERED
  }

  private String id;
  private String remarks;
  private OrderStatus status;
  private Date createdAt;
  private String reservationId;

  private Reservation reservation;
  private OrderItem[] orderItems;

  public RoomService() {
    super("roomservice.txt");
  }

  public RoomService(HashMap<String, String> data) {
    super("roomservice.txt");
    this.fromHashMap(data);
  }

  public String getId() {
    if (id == null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
      return String.format("%s-%s", sdf.format(getCreatedAt()), getReservation().getId());
    }
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  private void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public String getReservationId() {
    if (reservationId == null) {
      return getReservation().getId();
    }
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

  public OrderItem[] getOrderItems() {
    if (orderItems == null || orderItems.length == 0) {
      orderItems = new OrderItem().findOrderItems(new HashMap<>() {{
        put("roomServiceId", getId());
      }});
    }
    return orderItems;
  }

  public void setOrderItems(OrderItem[] orderItems) {
    this.orderItems = orderItems;
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
  public boolean create() {
    status = OrderStatus.CONFIRMED;
    createdAt = new Date();
    getReservation().addRoomService(this);
    return super.create();
  }

  @Override
  public HashMap<String, String> toHashMap() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
    LinkedHashMap<String, String> results = new LinkedHashMap<>();

    results.put("id", getId());
    results.put("remarks", getRemarks());
    results.put("status", getStatus().toString());
    results.put("createdAt", sdf.format(getCreatedAt()));
    results.put("reservationId", getReservationId());
    return results;
  }

  @Override
  public void fromHashMap(HashMap<String, String> hashMap) {
    if (hashMap.get("status") != null) {
      setStatus(OrderStatus.valueOf(hashMap.get("status")));
    }
    setReservationId(hashMap.get("reservationId"));
    setRemarks(hashMap.get("remarks"));
    setId(hashMap.get("id"));
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-H-m");
      if (hashMap.get("createdAt") != null) {
        setCreatedAt(sdf.parse(hashMap.get("createdAt")));
      }
    } catch (ParseException e) { }
  }

  @Override
  public PromptModelContainer promptModelContainer() {
    return new PromptModelContainer(
      "",
      new PromptModel[] {
        new PromptModel("id", "Id", PromptModel.InputType.STRING),
        new PromptModel("remarks", "Remarks", PromptModel.InputType.STRING),
        new PromptModel("status", new Menu(
          "Order Status",
          new MenuOption[] {
            new MenuOption("CONFIRMED", "Confirmed"),
            new MenuOption("PREPARING", "Preparing"),
            new MenuOption("DELIVERED", "Delivered"),
          }
        )),
        new PromptModel("createdAt", "Created At", PromptModel.InputType.DATE),
        new PromptModel("reservationId", "Reservation Id", PromptModel.InputType.STRING),
      }
    );
  }

  @Override
  public PromptModelContainer creationPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (Arrays.asList("id", "status", "createdAt", "reservationId").contains(promptModel.getKey())) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Create Room Service",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer findingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (promptModel.getKey().equals("remarks")) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Search for Room Services",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
  }

  @Override
  public PromptModelContainer editingPromptModelContainer() {
    ArrayList<PromptModel> promptModels = new ArrayList<>();
    for (PromptModel promptModel: promptModelContainer().getPromptModels()) {
      if (Arrays.asList("id", "createdAt", "reservationId").contains(promptModel.getKey())) {
        continue;
      }
      promptModels.add(promptModel);
    }
    return new PromptModelContainer(
      "Edit Room Service Details",
      promptModels.toArray(new PromptModel[promptModels.size()])
    );
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

  public double getFee() {
    double fee = 0.0;
    for (OrderItem orderItem: getOrderItems()) {
      fee += orderItem.getMenuItem().getPrice() * orderItem.getQuantity();
    }
    return fee;
  }

}
