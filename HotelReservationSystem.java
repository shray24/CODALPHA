import java.io.*;
import java.util.Scanner;
public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel choice = new Hotel();
        choice.createRoom();
        choice.loadData();
        while (true) {
            System.out.println("\n=========== HOTEL RESERVATION SYSTEM ===========");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Search Room");
            System.out.println("3. Book Room");
            System.out.println("4. Check Out");
            System.out.println("5. View Booking Details");
            System.out.println("6. Exit");
            int option = choice.getIntInput("Select an option: ");
            switch (option) {
                case 1:
                    System.out.println("\n1. View All Rooms");
                    System.out.println("2. Select Rooms by Category");
                    System.out.println("3. Select Rooms by Floor");
                    int n = choice.getIntInput("Enter your choice: ");
                    choice.viewAvailableRoom(n);
                    break;
                case 2:
                    choice.searchRooms();
                    break;
                case 3:
                    choice.bookRooms();
                    break;
                case 4:
                    choice.checkOut();
                    break;
                case 5:
                    choice.viewBookingDetails();
                    break;
                case 6:
                    System.out.println("Thanks for using the Hotel Reservation System!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please select between 1 and 6.");
            }
        }
    }
}
class Hotel extends Room {
    Scanner r = new Scanner(System.in);
    public void viewAvailableRoom(int n) {
        switch (n) {
            case 1:
                displayRooms();
                break;
            case 2:
                System.out.println("Categories of Rooms are: Standard, Deluxe, Suite");
                System.out.println("Press 1 for Standard Rooms");
                System.out.println("Press 2 for Deluxe Rooms");
                System.out.println("Press 3 for Suite Rooms");
                int choiceCategory = getIntInput("Enter your choice: ");
                displayRoomByCategory(choiceCategory);
                break;
            case 3:
                int floorNumber = getIntInput("Enter Floor Number: ");
                displayRoomByFloor(floorNumber);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public void searchRooms() {
        int roomNumber = getIntInput("Enter Room Number: ");
        searchRoom(roomNumber);
    }
    public void bookRooms() {
        int roomNumber = getIntInput("Enter Room Number: ");
        bookRoom(roomNumber);
    }
    public void viewBookingDetails() {
        System.out.print("Enter Booking ID: ");
        String id = r.next();
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                if (bookingID[i][j].equalsIgnoreCase(id) && booked[i][j]) {
                    System.out.println("\n======= BOOKING DETAILS =======");
                    System.out.println("Booking ID    : " + bookingID[i][j]);
                    System.out.println("Customer Name : " + customerName[i][j]);
                    System.out.println("Room Number   : " + RoomNumber[i][j]);
                    System.out.println("Category      : " + Category[i][j]);
                    System.out.println("Price/Day     : ₹" + price[i][j]);
                    System.out.println("Days          : " + days[i][j]);
                    System.out.println("Total Price   : ₹" + totalPrice[i][j]);
                    System.out.println("Phone         : " + phone[i][j]);
                    System.out.println("Email         : " + email[i][j]);
                    System.out.println("================================");
                    return;
                }
            }
        }
        System.out.println("Booking ID not found.");
    }
    public void checkOut() {
        System.out.print("Enter Booking ID: ");
        String id = r.next();
        checkoutRoom(id);
    }
}
class Room {
    Scanner sc = new Scanner(System.in);
    int room = 10;
    int floor = 5;
    int RoomNumber[][] = new int[floor][room];
    String Category[][] = new String[floor][room];
    boolean booked[][] = new boolean[floor][room];
    int price[][] = new int[floor][room];
    String customerName[][] = new String[floor][room];
    String bookingID[][] = new String[floor][room];
    int days[][] = new int[floor][room];
    String phone[][] = new String[floor][room];
    String email[][] = new String[floor][room];
    int totalPrice[][] = new int[floor][room];
    public void createRoom() {
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                RoomNumber[i][j] = i * 100 + j + 1;
                if (j < 4) {
                    Category[i][j] = "Standard";
                    price[i][j] = 2000;
                } else if (j < 7) {
                    Category[i][j] = "Deluxe";
                    price[i][j] = 3500;
                } else {
                    Category[i][j] = "Suite";
                    price[i][j] = 5000;
                }
                booked[i][j] = false;
                customerName[i][j] = "";
                bookingID[i][j] = "";
                days[i][j] = 0;
                phone[i][j] = "";
                email[i][j] = "";
                totalPrice[i][j] = 0;
            }
        }
    }
    public int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    public void displayRooms() {
        System.out.println("\n===== VIEW ALL ROOMS =====");
        System.out.println("Room    Category    Price    Status");
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                System.out.printf("%03d    %-10s  ₹%-6d %s%n", RoomNumber[i][j], Category[i][j], price[i][j], booked[i][j] ? "Booked" : "Available");
            }
            System.out.println();
        }
    }
    public void displayRoomByFloor(int floorNumber) {
        if (floorNumber < 0 || floorNumber >= floor) {
            System.out.println("Invalid floor number. Floor must be between 0 and 4.");
            return;
        }
        System.out.println("\n===== ROOMS ON FLOOR " + floorNumber + " =====");
        System.out.println("Room    Category    Price    Status");
        for (int j = 0; j < room; j++) {
            System.out.printf("%03d    %-10s  ₹%-6d %s%n", RoomNumber[floorNumber][j], Category[floorNumber][j], price[floorNumber][j], booked[floorNumber][j] ? "Booked" : "Available");
        }
    }
    public void displayRoomByCategory(int choiceCategory) {
        String selectedCategory;
        switch (choiceCategory) {
            case 1:
                selectedCategory = "Standard";
                break;
            case 2:
                selectedCategory = "Deluxe";
                break;
            case 3:
                selectedCategory = "Suite";
                break;
            default:
                System.out.println("Invalid category choice.");
                return;
        }
        System.out.println("\n===== AVAILABLE " + selectedCategory.toUpperCase() + " ROOMS =====");
        System.out.println("Room    Category    Price    Status");

        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                if (Category[i][j].equals(selectedCategory) && !booked[i][j]) {
                    System.out.printf("%03d    %-10s  ₹%-6d Available%n", RoomNumber[i][j], Category[i][j], price[i][j]);
                }
            }
        }
    }
    public void searchRoom(int roomNumber) {
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                if (RoomNumber[i][j] == roomNumber) {
                    System.out.println("\n===== ROOM DETAILS =====");
                    System.out.println("Room Number : " + RoomNumber[i][j]);
                    System.out.println("Category    : " + Category[i][j]);
                    System.out.println("Price       : ₹" + price[i][j]);
                    if (booked[i][j]) {
                        System.out.println("Status      : Booked");
                        System.out.println("Booking ID  : " + bookingID[i][j]);
                        System.out.println("Name        : " + customerName[i][j]);
                        System.out.println("Phone       : " + phone[i][j]);
                        System.out.println("Days        : " + days[i][j]);
                    } else {
                        System.out.println("Status      : Available");
                    }
                    return;
                }
            }
        }
        System.out.println("Room not found.");
    }
    public void bookRoom(int roomNumber) {
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                if (RoomNumber[i][j] == roomNumber) {
                    if (booked[i][j]) {
                        System.out.println("Room is already booked.");
                        return;
                    }
                    System.out.print("Enter Customer Name: ");
                    customerName[i][j] = sc.nextLine();
                    System.out.print("Enter Phone Number: ");
                    phone[i][j] = sc.nextLine();
                    System.out.print("Enter Email ID: ");
                    email[i][j] = sc.nextLine();
                    days[i][j] = getIntInput("Enter Number of Days: ");
                    if (days[i][j] <= 0) {
                        System.out.println("Number of days must be greater than 0.");
                        return;
                    }
                    totalPrice[i][j] = price[i][j] * days[i][j];
                    System.out.println("Total Price = ₹" + totalPrice[i][j]);
                    System.out.println("\nSelect Payment Method:");
                    System.out.println("1. Cash");
                    System.out.println("2. UPI");
                    System.out.println("3. Card");
                    int paymentChoice = getIntInput("Enter your choice: ");
                    Payment payment;
                    switch (paymentChoice) {
                        case 1:
                            payment = new CashPayment();
                            break;
                        case 2:
                            payment = new UPIPayment();
                            break;
                        case 3:
                            payment = new CardPayment();
                            break;
                        default:
                            System.out.println("Invalid payment method.");
                            return;
                    }
                    payment.makePayment(totalPrice[i][j]);
                    booked[i][j] = true;
                    bookingID[i][j] = "BOOK" + RoomNumber[i][j] + days[i][j];
                    saveData();
                    System.out.println("\nPayment Successful!");
                    System.out.println("Booking ID: " + bookingID[i][j]);
                    System.out.println("Room " + RoomNumber[i][j] + " booked successfully!");
                    return;
                }
            }
        }
        System.out.println("Room not found.");
    }
    public void checkoutRoom(String id) {
        for (int i = 0; i < floor; i++) {
            for (int j = 0; j < room; j++) {
                if (bookingID[i][j].equalsIgnoreCase(id) && booked[i][j]) {
                    System.out.println("\n===== CHECKOUT DETAILS =====");
                    System.out.println("Customer Name : " + customerName[i][j]);
                    System.out.println("Room Number   : " + RoomNumber[i][j]);
                    System.out.println("Total Price   : ₹" + totalPrice[i][j]);
                    System.out.println("Checking out...");
                    booked[i][j] = false;
                    customerName[i][j] = "";
                    bookingID[i][j] = "";
                    days[i][j] = 0;
                    phone[i][j] = "";
                    email[i][j] = "";
                    totalPrice[i][j] = 0;
                    saveData();
                    System.out.println("Checkout successful!");
                    System.out.println("Room " + RoomNumber[i][j] + " is now available.");
                    return;
                }
            }
        }
        System.out.println("Booking ID not found.");
    }
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hotel_data.txt"))) {
            for (int i = 0; i < floor; i++) {
                for (int j = 0; j < room; j++) {
                    if (booked[i][j]) {
                        writer.write(RoomNumber[i][j] + "|" + booked[i][j] + "|" + customerName[i][j] + "|" + bookingID[i][j] + "|" + days[i][j] + "|" + phone[i][j] + "|" + email[i][j] + "|" + totalPrice[i][j]);
                        writer.newLine();
                    }
                }
            }
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }
    public void loadData() {
        File file = new File("hotel_data.txt");
        if (!file.exists()) {
            System.out.println("No previous booking data found. Starting fresh.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data.length != 8) {
                    continue;
                }
                int roomNumber = Integer.parseInt(data[0]);
                boolean isBooked = Boolean.parseBoolean(data[1]);
                for (int i = 0; i < floor; i++) {
                    for (int j = 0; j < room; j++) {
                        if (RoomNumber[i][j] == roomNumber) {
                            booked[i][j] = isBooked;
                            customerName[i][j] = data[2];
                            bookingID[i][j] = data[3];
                            days[i][j] = Integer.parseInt(data[4]);
                            phone[i][j] = data[5];
                            email[i][j] = data[6];
                            totalPrice[i][j] = Integer.parseInt(data[7]);
                        }
                    }
                }
            }
            System.out.println("Previous booking data loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error while loading data: " + e.getMessage());
        }
    }
}
abstract class Payment {
    abstract void makePayment(int amount);
}
class CashPayment extends Payment {
    @Override
    void makePayment(int amount) {
        System.out.println("Cash payment of ₹" + amount + " received.");
    }
}
class UPIPayment extends Payment {
    @Override
    void makePayment(int amount) {
        System.out.println("UPI payment of ₹" + amount + " received.");
    }
}
class CardPayment extends Payment {
    @Override
    void makePayment(int amount) {
        System.out.println("Card payment of ₹" + amount + " received.");
    }
}