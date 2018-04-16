package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.entities.Guest;
import com.cz2002.hrps.entities.MenuItem;
import com.cz2002.hrps.entities.Room;
import com.cz2002.hrps.models.GuestIdentity;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainController extends EntityController {

  @Override
  public void index() {
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Hotel Reservation and Payment System",
        new MenuOption[] {
          new MenuOption("check_in", "Check in"),
          new MenuOption("check_out", "Check out"),
          new MenuOption("reservation", "Reservation"),
          new MenuOption("guest", "Guest"),
          new MenuOption("room", "Room"),
          new MenuOption("room_service", "RoomService"),
          new MenuOption("generate", "Generate"),
          new MenuOption("exit", "Exit"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true, false).getValue();
      switch(menuSelection) {
        case 1:
          new CheckInController().index();
          break;
        case 2:
          new CheckOutController().index();
          break;
        case 3:
          new ReservationController().index();
          break;
        case 4:
          new GuestController().index();
          break;
        case 5:
          new RoomController().index();
          break;
        case 6:
          new RoomServiceController().index();
          break;
        case 7:
          generateRooms();
        default:
          break;
      }
    } while (menuSelection != 8);
  }

  private void generateGuests() {
    ArrayList<String> names = new ArrayList<>() {{
      add("high");
      add("scrogham");
      add("loftus");
      add( "craik");
      add( "lucy");
      add( "biglow");
      add("westbrook");
      add("handsacre");
      add("smythe");
      add("crowe");
      add("caswall");
      add("fitzcharles");
      add("mewis");
      add("harrow");
      add("auten");
      add("hutchison");
      add("rawson");
      add("dunlop");
      add("newland");
      add("bosenberry");
      add("fish");
      add("hazelgreave");
      add("wyatville");
      add("shellhouse");
      add("hoyland");
      add("shacklock");
      add("chaney");
      add("newarch");
      add("hardman");
      add("sanderson");
      add("pott");
      add("snutch");
    }};

    Random rng = new Random();
    for (int i = 0; i < 30; i++) {
      String id = "";
      GuestIdentity.IdentityType idType;
      String name;
      String address;
      String country = "Singapore";
      String gender;
      String nationality = "Singaporean";
      String contact;
      String creditCard = "";

      for (int j = 0; j < 8; j++) {
        id += Integer.toString(rng.nextInt(10));
      }

      idType = rng.nextInt(2) < 1 ? GuestIdentity.IdentityType.DRIVING_LICENSE : GuestIdentity.IdentityType.PASSPORT;

      int nameRan = rng.nextInt(names.size());
      String bufferName = names.remove(nameRan);
      name = bufferName.substring(0, 1).toUpperCase() + bufferName.substring(1);

      address = "Adress " + Integer.toString(i);

      gender = rng.nextInt(2) < 1 ? "M" : "F";

      contact = Integer.toString(rng.nextInt(17900000) + 80100000);
      for (int j = 0; j < 16; j++) {
        creditCard += Integer.toString(rng.nextInt(10));
      }

      String id1 = id;
      String creditCard1 = creditCard;

      Guest guest = new Guest(new HashMap<>() {{
        put("id", id1);
        put("idType", idType.toString());
        put("name", name);
        put("address", address);
        put("country", country);
        put("gender", gender);
        put("nationality", nationality);
        put("contact", contact);
        put("creditCard", creditCard1);
      }});
      guest.create();
    }
  }

  private void generateRooms() {
    Random rng = new Random();
    for (int i = 2; i < 8; i++) {
      for (int j = 1; j < 9; j++) {
        Room.RoomType roomType;
        double roomRate;
        double roomWeekendRate;
        int roomFloor;
        int roomNumber;
        boolean wifiEnabled;
        boolean smokingAllowed;
        Room.BedType bedType;
        Room.FacingType facing;
        Room.RoomStatus status;

        int roomTypeRan = rng.nextInt(6);
        if (roomTypeRan < 2) {
          roomType = Room.RoomType.SINGLE;
          roomRate = 150.0 + (rng.nextInt(6) - 3)*10.0;
        } else if (roomTypeRan < 4) {
          roomType = Room.RoomType.STANDARD;
          roomRate = 300.0 + (rng.nextInt(6) - 3)*10.0;
        } else if (roomTypeRan < 5) {
          roomType = Room.RoomType.DELUXE;
          roomRate = 450.0 + (rng.nextInt(6) - 3)*10.0;
        } else {
          roomType = Room.RoomType.VIP;
          roomRate = 600.0 + (rng.nextInt(6) - 3)*10.0;
        }
        roomWeekendRate = roomRate + rng.nextInt(3)*10.0;

        roomFloor = i;
        roomNumber = j;

        wifiEnabled = rng.nextInt(2) < 1;
        smokingAllowed = rng.nextInt(2) < 1;

        int bedTypeRan = rng.nextInt(3);
        bedType = bedTypeRan < 1
          ? Room.BedType.SINGLE_BED
          : bedTypeRan < 2
          ? Room.BedType.DOUBLE_BED
          : Room.BedType.MASTER_BED;

        int viewRan = rng.nextInt(4);
        facing = viewRan < 1
          ? Room.FacingType.SEA_VIEW
          : viewRan < 2
          ? Room.FacingType.CITY_VIEW
          : viewRan < 3
          ? Room.FacingType.MOUNTAIN_VIEW
          : Room.FacingType.NO_VIEW;

        int statusRan = rng.nextInt(9);
        status = statusRan < 6
          ? Room.RoomStatus.VACANT
          : Room.RoomStatus.UNDER_MAINTENANCE;

        Room room = new Room(new HashMap<>(){{
          put("roomType", roomType.toString());
          put("roomRate", Double.toString(roomRate));
          put("roomWeekendRate", Double.toString(roomWeekendRate));
          put("roomFloor", Integer.toString(roomFloor));
          put("roomNumber", Integer.toString(roomNumber));
          put("wifiEnabled", Boolean.toString(wifiEnabled));
          put("smokingAllowed", Boolean.toString(smokingAllowed));
          put("bedType", bedType.toString());
          put("facing", facing.toString());
          put("status", status.toString());
        }});
        room.create();
      }
    }
  }

  private void generateMenuItems() {

    ArrayList<String> foodNames = new ArrayList<>() {{
      add("Basted Soy Chicken");
      add("Poached Blueberry Tuna");
      add("Stewed Soy Clams");
      add("Brined Fennel Buns");
      add("Coconut Toffee");
      add("Guava Crispies");
      add("Infused Mint Risotto");
      add("Vanilla Waffles");
      add("Lemon and Rum Surprise");
      add("Infused Yogurt Lobster");
    }};

    ArrayList<String> foodDes = new ArrayList<>() {{
      add("Roasted bison with ajowan seeds, star anise and bell peppers. ");
      add("Broiled ox brisket with bush mango nuts, shallots and black pepper. Served with brined gouda, bison pie, tortillas, horseradish soup and scrambled eggs.");
      add("Salted werecat with chili powder, caraway seeds and snowberries. Served with cantaloupe soup, pumpernickel with jam and gouda.");
      add("Poached asp with almonds and lemons. Served with lizard soup and goat pie.");
      add("Pan-fried brown rice with kola nut, ricebean and horse gram with a salad of cooked scorzonera, ginger and avocados. Served with whisky, white bread, curry powder soup, scrambled eggs and cheddar.");
      add("Stir-fried thick acorn noodles with albatross, green bean and acorn. Served with jarlsberg, rum and challah with jam.");
      add("Boiled raven with huckleberries, snowberries and tallow nuts with a salad of sauteed elephant garlic and strawberries.");
      add("Pan-fried flat rice noodles with garbanzo, mozuku, ahipa, pigeon pea and plum. Served with hard boiled eggs, tigernut pie, seed cakes, beer and beaver soup. ");
      add("Stir-fried brown rice with canna and carob.");
      add("Tortoise with cardamom, olives and nutmeg with a salad of sliced swede, broccoli and figs. Served with beer, dog soup, napa cabbage pie and potato bread with honey. ");
    }};

    Random rng = new Random();
    for (int i = 0; i < 10; i++) {
      MenuItem menuItem = new MenuItem(new HashMap<>(){{
        put("name", foodNames.remove(0));
        put("description", foodDes.remove(0));
        put("price", Double.toString((rng.nextInt(5) + 3)*3.5));
      }});
      menuItem.create();
    }

  }

}
