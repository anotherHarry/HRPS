package com.cz2002.hrps.controls;

import com.cz2002.hrps.boundaries.Boundary;
import com.cz2002.hrps.boundaries.InputBoundary;
import com.cz2002.hrps.boundaries.OutputBoundary;
import com.cz2002.hrps.entities.Reservation;
import com.cz2002.hrps.models.*;

public class CheckOutController implements AppController {

  @Override
  public void index() {
    Reservation reservation = new ReservationController().findCheckedInReservaton();
    if (reservation == null) {
      return;
    }

    if(new Boundary().inputBoolean(
      "Are you sure you want to check-out?",
      true,
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
      menuSelection = inputBoundary.processMenu(true, false).getValue();
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
    Double taxRate = getRate("Tax Rate");
    Double discountRate = getRate("Discount Rate");
    bill.setTaxRate(taxRate);
    bill.setDiscountRate(discountRate);
    return bill;
  }

  private Double getRate(String object) {
    InputModel<String> input = new InputBoundary(new PromptModel(
      "",
      object,
      PromptModel.InputType.POSITIVE_DOUBLE
    )).getInput(true, false);
    return input.isSucceed() ? Double.parseDouble(input.getValue()) : 0.0;
  }

  private boolean confirmPayWith(String paymentMethod) {
    if(new Boundary().inputBoolean(
      "Are you sure you want to pay by " + paymentMethod + "?",
      true,
      false).getValue()
      ) {
      new Boundary().alertSuccessful();
      return true;
    }
    return false;
  }

}
