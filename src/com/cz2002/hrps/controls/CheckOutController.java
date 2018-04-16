package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.entities.RoomService;
import com.cz2002.hrps.models.Bill;
import com.cz2002.hrps.models.Menu;
import com.cz2002.hrps.models.MenuOption;
import com.cz2002.hrps.models.PromptModel;

public class CheckOutController implements Control {

  @Override
  public void index() {
    Reservation reservation = new ReservationController().findCheckedInReservaton();
    if (reservation == null) {
      return;
    }
    new EntityController().printEntity("Target Reservation", reservation);
    if(new Boundary().inputBoolean(
      "Are you sure you want to check-out?",
      true).getValue()
      ) {
      reservation.checkOut();
      makePayment(reservation);
    }
  }

  private void makePayment(Reservation reservation) {
    Bill bill = getBill(reservation);
    new OutputBoundary().printBill(bill);
    InputBoundary inputBoundary = new InputBoundary(new PromptModel(
      "",
      new Menu(
        "Pay With",
        new MenuOption[] {
          new MenuOption("cash", "Cash"),
          new MenuOption("credit_card", "Credit Card"),
        }
      )
    ));

    int menuSelection = 0;
    do {
      menuSelection = inputBoundary.processMenu(true).getValue();
      switch (menuSelection) {
        case 1:
          if (confirmPayWith("Cash")) {
            menuSelection = Integer.MAX_VALUE;
          }
          break;
        case 2:
          if (confirmPayWith("Credit Card")) {
            menuSelection = Integer.MAX_VALUE;
          }
          break;
        default:
          break;
      }
    } while (menuSelection != Integer.MAX_VALUE);
  }

  private Bill getBill(Reservation reservation) {
    Bill bill = reservation.getBill();
    Double taxRate = Double.parseDouble(new InputBoundary(new PromptModel(
      "",
      "Tax Rate",
      PromptModel.InputType.POSITIVE_DOUBLE
    )).getInput(true).getValue());
    bill.setTaxRate(taxRate);
    Double discountRate = Double.parseDouble(new InputBoundary(new PromptModel(
      "",
      "Discount Rate",
      PromptModel.InputType.POSITIVE_DOUBLE
    )).getInput(true).getValue());
    bill.setDiscountRate(discountRate);
    return bill;
  }

  private boolean confirmPayWith(String paymentMethod) {
    if(new Boundary().inputBoolean(
      "Are you sure you want to pay by " + paymentMethod + "?",
      true).getValue()
      ) {
      new Boundary().alertSuccessful();
      return true;
    }
    return false;
  }

}
