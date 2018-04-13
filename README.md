# Hotel Reservation and Payment System (HRPS)

**HRPS** is an application to computerize the processes of making hotel reservation, recording of orders and displaying of records. It is solely used by the hotel staff.

## The following are information about the application:

**About Guest**
- Guests can be added with details like name, credit card details, address, country, gender, identity, nationality, contact when check in.
- Identity could be driving license or passport.

**About Room**
- Rooms can be categorized according to its type: Single, Double, Deluxe, VIP Suite, etc. (you may refer to hotel website for the types). Each type is at different rate.
- Rooms have details like room number, bed type (single/double/master, WIFI enabled, facing (with view), smoking/non-smoking, status, etc.
- Roomavailability/detailscanbecheckedbyenteringtheroomnumber(id)/guestname. d) Room availability status can be Vacant, Occupied, Reserved, Under Maintenance.

**About Reservation**
- Whenaroomisreserved,itwillhaveacorrespondingreservationdetails.
- Reservations can be added with a reservation code/number, the associated guest details, room and billing information (eg credit card), date of check in/out, number of adults/children, reservation status.
- An acknowledgement receipts with the essential reservation details should be provided (displayed) when a reservation is made.
- Reservations can be in different status: confirmed, in waitlist, checked-in, expired, etc. Note that payment is not necessary at the time of reservation.
- A reservation will be expired if no one checks in by a specific time point (example 1 hour after scheduled check-in time), and the room should be made available again.
- When guests check-in, the reservation and room/s should be updated and reflect the correct status.

**About Room Service**
- Hotel staff can order room service meals on guest’s behalf upon his/her request.
- List of menu items selection will be displayed for selection.
- Each menu item will have a name, a description of how it is prepared and price.
- When ordered, there will be a room service order created with a date/time, remarks (eg, less oil, less salt) and the status (confirmed, preparing, delivered). e) The order status will be updated accordingly.

**About Payment**
- When a guest check-out, the total bill will be presented for payment. After payment, the room/s will become available.
- Total bill include room charges, tax, room services. Room charges can be different for weekends and weekdays. In addition, discount could be provided for promotion.
- Payment can be made in cash, credit card with details such as billing address, etc.
- Room occupancy report can be generated based on the percentage of occupied rooms in a particular day.

**Functional Requirements**
- Create/Update/Search guests detail (Search by name using keyword/s)
- Create/Update/Remove/Printreservation
- Create/Update rooms details (include setting status like ‘Under Maintenance”,
- Entering room service orders - list menu items for selection
- Create/Update/Remove room service menu items.
- Check room availability
- RoomCheck-in(forwalk-inorreservation)
- RoomCheck-outandprintbillinvoice(withbreakdownsondaysofstay,roomserviceorderitems and its total, tax and total amount) i. Print Room Status statistic report by
   - room type occupancy rate (room status = vacant) - (room type, number and list the room number).
   ```
   Eg, [for illustration only]
        Single :    Number : 10 out of 20
                    Rooms : 02-03, 03-04, 03-05,.......
        Double :    Number : 5 out of 10
                    Rooms : 02-04, 05-04, 05-05,....... .......
     `````
    - room status (status type and list the room number)
    ```
    Eg, [for illustration only]
        Vacant :
            Rooms : 02-03, 03-04, 03-05,.......
        Occupied :
            .......... Roo
   `````

**Assumptions**
- The currency will be in Singapore Dollar (SGD).
- There is no need to interface with external system, eg Payment, printer, etc.
- Payment is always successful.
- No need to log in - features
- No graphical interface for selection of rooms.
- The room service bill is entered as an amount and time stamp. There is no need to specify the items consumed.
- The hotel has 48 rooms (of various type) from 02 – 07 level and the room number format is <2 digit floor level><running number from 01>, eg 0702.

## UML Class Diagram

## UML Sequence Diagram

## Design Consideration

## Demonstration

## Contributors
