package vehiclerental;

//Abstract parent class for all vehicles.

public abstract class Vehicle {

    // Common attributes shared by all vehicle types
    private String vehicleId;
    private String brand;
    private String model;
    private double baseRatePerDay;
    private boolean isAvailable;

    //Constructor to initialize common vehicle properties.
    public Vehicle(String vehicleId, String brand, String model,
                   double baseRatePerDay, boolean isAvailable) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.baseRatePerDay = baseRatePerDay;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBaseRatePerDay() {
        return baseRatePerDay;
    }

    public void setBaseRatePerDay(double baseRatePerDay) {
        this.baseRatePerDay = baseRatePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /*
     * Prints common details for any vehicle.
     * Subclasses call super.displayDetails() and add their own info.
     */
    public void displayDetails() {
        System.out.println("ID: " + vehicleId);
        System.out.println("Type: " + getClass().getSimpleName());
        System.out.println("Brand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Base Rate/Day: " + baseRatePerDay);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
    }

    //Marks vehicle as rented. Validation handled by RentalSystem.
    public void rentVehicle() {
        isAvailable = false;
        System.out.println("Vehicle rented successfully.");
    }

    //Marks vehicle as returned. Validation handled by RentalSystem.
    public void returnVehicle() {
        isAvailable = true;
        System.out.println("Vehicle returned successfully.");
    }

    /*
     * Each vehicle has a different rental cost formula.
     * This abstract method makes subclasses to define their own formula.
     */
    public abstract double calculateRentalCost(int days);
}

