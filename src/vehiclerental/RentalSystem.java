package vehiclerental;

import java.util.ArrayList;

//RentalSystem contains the core logic.

public class RentalSystem {

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();
    private double totalIncome = 0.0;


    public double getTotalIncome() {
        return totalIncome;
    }

    /*
     * Adds a vehicle (Admin).
     * Checks unique ID.
     */
    public void addVehicle(Vehicle v) {
        if (v == null) return;

        if (searchById(v.getVehicleId()) != null) {
            System.out.println("Error: Vehicle ID already exists. Must be unique.");
            return;
        }

        vehicles.add(v);
        System.out.println("Vehicle added successfully.");
    }

    /*
     * Removes a vehicle (Admin).
     * Cannot remove a rented vehicle.
     */
    public void removeVehicleById(String id) {
        Vehicle v = searchById(id);

        if (v == null) {
            System.out.println("Error: Vehicle not found.");
            return;
        }

        if (!v.isAvailable()) {
            System.out.println("Error: Cannot remove a rented vehicle.");
            return;
        }

        vehicles.remove(v);
        System.out.println("Vehicle removed successfully.");
    }

    //Displays all vehicles using polymorphism.
    public void viewAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        System.out.println("\n===== VEHICLE LIST (" + vehicles.size() + ") =====");
        for (Vehicle v : vehicles) {
            System.out.println("-----------------------------------");
            v.displayDetails();
        }
        System.out.println("-----------------------------------");
    }

    //Searches by ID (case-insensitive)
    public Vehicle searchById(String id) {
        if (id == null) return null;
        String key = id.trim();

        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(key)) {
                return v;
            }
        }
        return null;
    }

    /*
     * Rents a vehicle (User).
     * Validations: days > 0, vehicle exists, vehicle available.
     * Updates total income.
     */
    public void rentVehicleById(String id, int days) {

        if (days <= 0) {
            System.out.println("Error: Rental days must be greater than zero.");
            return;
        }

        Vehicle v = searchById(id);

        if (v == null) {
            System.out.println("Error: Vehicle not found.");
            return;
        }

        if (!v.isAvailable()) {
            System.out.println("Error: Vehicle is already rented.");
            return;
        }

        // Mark as rented
        v.rentVehicle();

        // Calculate cost and update total income
        double cost = v.calculateRentalCost(days);
        totalIncome += cost;

        System.out.printf("Rental Cost for %d day(s): %.2f%n", days, cost);
    }


    /*
     * Returns a vehicle (User).
     * Validations: vehicle exists, vehicle must be rented.
     */
    public void returnVehicleById(String id) {
        Vehicle v = searchById(id);

        if (v == null) {
            System.out.println("Error: Vehicle not found.");
            return;
        }

        if (v.isAvailable()) {
            System.out.println("Error: Vehicle is not currently rented.");
            return;
        }

        v.returnVehicle();
    }
}

