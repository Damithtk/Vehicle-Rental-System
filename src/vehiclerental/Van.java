package vehiclerental;

/*
 * Van class extends Vehicle.
 * Demonstrates:
 * - Inheritance (Van inherits common attributes and methods from Vehicle)
 * - Method Overriding (custom rental cost calculation for vans)
 */
public class Van extends Vehicle {

    // Specific attribute only for Van
    private double cargoCapacityKg;

    /*
     * Constructor to initialize Van object.
     * Calls the parent (Vehicle) constructor using super
     * to initialize common vehicle properties.
     */
    public Van(String vehicleId, String brand, String model,
               double baseRatePerDay, boolean isAvailable, double cargoCapacityKg) {

        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.cargoCapacityKg = cargoCapacityKg;
    }

    // Getter method for cargo capacity
    public double getCargoCapacityKg() {
        return cargoCapacityKg;
    }

    // Setter method for cargo capacity
    public void setCargoCapacityKg(double cargoCapacityKg) {
        this.cargoCapacityKg = cargoCapacityKg;
    }

    /*
     * Overrides displayDetails() method.
     * First displays common vehicle details using super,
     * then displays Van-specific information.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Cargo Capacity (Kg): " + cargoCapacityKg);
    }

    //Overrides abstract method from Vehicle.
    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (cargoCapacityKg * 0.2 * days);
    }
}

