package vehiclerental;

import java.util.Scanner;

//User interface

public class RentalApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RentalSystem system = new RentalSystem();

        // Mode selection loop
        while (true) {
            System.out.println("\n===== SELECT MODE =====");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit Program");
            System.out.print("Enter choice: ");

            int mode = readInt(sc);

            switch (mode) {
                case 1:
                    // Admin login required. 3 failures, program ends
                    if (authenticateAdmin(sc)) {
                        adminMenu(sc, system);
                    }
                    break;

                case 2:
                    userMenu(sc, system);
                    break;

                case 3:
                    System.out.println("Program terminated.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid selection. Please choose 1, 2, or 3.");
            }
        }
    }

    // Admin menu
    private static void adminMenu(Scanner sc, RentalSystem system) {
        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. View All Vehicles");
            System.out.println("4. Search Vehicle by ID");
            System.out.println("5. Rent a Vehicle");
            System.out.println("6. Return a Vehicle");
            System.out.println("7. View Total Income");
            System.out.println("8. Back to Mode Selection");
            System.out.print("Select an option: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    addVehicle(sc, system);
                    break;

                case 2: {
                    String id = readNonEmptyString(sc, "Enter Vehicle ID to remove: ");
                    system.removeVehicleById(id);
                    break;
                }

                case 3:
                    system.viewAllVehicles();
                    break;

                case 4:
                    searchMenu(sc, system);
                    break;

                case 5:
                    rentVehicleMenu(sc, system);
                    break;

                case 6:
                    returnVehicleMenu(sc, system);
                    break;

                case 7:
                    System.out.printf("Total Rental Income: %.2f%n", system.getTotalIncome());
                    break;

                case 8:
                    return; // back to mode selection

                default:
                    System.out.println("Invalid selection. Please choose between 1 and 8.");
            }
        }
    }

    // User menu
    private static void userMenu(Scanner sc, RentalSystem system) {
        while (true) {
            System.out.println("\n========== USER MENU ==========");
            System.out.println("1. View All Vehicles");
            System.out.println("2. Search Vehicle by ID");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Return a Vehicle");
            System.out.println("5. Back to Mode Selection");
            System.out.print("Select an option: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1:
                    system.viewAllVehicles();
                    break;

                case 2:
                    searchMenu(sc, system);
                    break;

                case 3:
                    rentVehicleMenu(sc, system);
                    break;

                case 4:
                    returnVehicleMenu(sc, system);
                    break;

                case 5:
                    return; // back to mode selection

                default:
                    System.out.println("Invalid selection. Please choose between 1 and 5.");
            }
        }
    }

    // Menu actions

    //Add vehicle (Admin).
    private static void addVehicle(Scanner sc, RentalSystem system) {

        System.out.println("\n--- Add a Vehicle (Admin) ---");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.println("3. Van");
        System.out.print("Select type: ");

        int type = readInt(sc);

        // Validate ID first (empty + duplicate)
        String id = readUniqueVehicleId(sc, system);

        String brand = readNonEmptyString(sc, "Brand: ");
        String model = readNonEmptyString(sc, "Model: ");

        System.out.print("Base Rate Per Day: ");
        double baseRate = readDouble(sc);

        boolean available = true; // newly added vehicles are available
        Vehicle v;

        switch (type) {
            case 1: {
                System.out.print("Number of Seats: ");
                int seats = readInt(sc);
                if (seats <= 0) {
                    System.out.println("Error: Seats must be greater than zero.");
                    return;
                }
                v = new Car(id, brand, model, baseRate, available, seats);
                break;
            }

            case 2: {
                System.out.print("Engine Capacity (CC): ");
                int cc = readInt(sc);
                if (cc <= 0) {
                    System.out.println("Error: Engine capacity must be greater than zero.");
                    return;
                }
                v = new Bike(id, brand, model, baseRate, available, cc);
                break;
            }

            case 3: {
                System.out.print("Cargo Capacity (Kg): ");
                double kg = readDouble(sc);
                if (kg <= 0) {
                    System.out.println("Error: Cargo capacity must be greater than zero.");
                    return;
                }
                v = new Van(id, brand, model, baseRate, available, kg);
                break;
            }

            default:
                System.out.println("Invalid vehicle type selection.");
                return;
        }
        system.addVehicle(v);
    }

    //Rent vehicle
    private static void rentVehicleMenu(Scanner sc, RentalSystem system) {
        System.out.println("\n--- Rent a Vehicle ---");

        String id = readNonEmptyString(sc, "Enter Vehicle ID: ");
        Vehicle v = system.searchById(id);

        if (v == null) {
            System.out.println("Error: Vehicle not found.");
            return;
        }

        if (!v.isAvailable()) {
            System.out.println("Error: Vehicle is already rented.");
            return;
        }

        System.out.print("Enter rental days: ");
        int days = readInt(sc);

        // Call RentalSystem to rent + calculate payment + update total income
        system.rentVehicleById(id, days);
    }



    //Return vehicle
    private static void returnVehicleMenu(Scanner sc, RentalSystem system) {
        System.out.println("\n--- Return a Vehicle ---");
        String id = readNonEmptyString(sc, "Enter Vehicle ID: ");
        system.returnVehicleById(id);
    }

    //Search vehicle
    private static void searchMenu(Scanner sc, RentalSystem system) {
        System.out.println("\n--- Search Vehicle by ID ---");
        String id = readNonEmptyString(sc, "Enter Vehicle ID: ");

        Vehicle v = system.searchById(id);
        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("Vehicle found:");
        System.out.println("-----------------------------------");
        v.displayDetails();
        System.out.println("-----------------------------------");
    }

    // Admin authentication (3 failures, terminate program)

    /*
     * Simple demonstration login:
     * Hardcoded password
     * 3 attempts allowed
     * After 3 failures: program terminates
     */
    private static boolean authenticateAdmin(Scanner sc) {
        final String ADMIN_PASSWORD = "admin";
        int attempts = 3;

        while (attempts > 0) {
            System.out.print("Enter Admin Password: ");
            String input = sc.nextLine().trim();

            if (input.equals(ADMIN_PASSWORD)) {
                System.out.println("Access granted.");
                return true;
            }

            attempts--;
            System.out.println("Incorrect password. Attempts left: " + attempts);
        }

        System.out.println("Maximum attempts exceeded.");
        System.out.println("System locked. Program terminated.");
        sc.close();
        System.exit(0);
        return false;
    }

    // Input helpers

    //Reads a non-empty string input (prevents empty answers).
    private static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String text = sc.nextLine().trim();
            if (!text.isEmpty()) {
                return text;
            }
            System.out.println("Error: This field cannot be empty. Please try again.");
        }
    }

    /*
     * Reads a unique vehicle ID.
     * Shows error immediately if empty or duplicate.
     */
    private static String readUniqueVehicleId(Scanner sc, RentalSystem system) {
        while (true) {
            String id = readNonEmptyString(sc, "ID: ");

            if (system.searchById(id) != null) {
                System.out.println("Error: Vehicle ID already exists. Please enter a different ID.");
                continue;
            }
            return id;
        }
    }

    //Safe integer input using try-catch.
    private static int readInt(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    //Safe double input using try-catch.
    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                String line = sc.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}

