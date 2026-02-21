package vehiclerental;

/*
 * extends Vehicle.
 */
public class Bike extends Vehicle {

    // Specific attribute only for Bike
    private int engineCapacityCC;

    /*
     * Constructor to initialize Bike object.
     * Calls the parent (Vehicle) constructor using super.
     */
    public Bike(String vehicleId, String brand, String model,
                double baseRatePerDay, boolean isAvailable, int engineCapacityCC) {

        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.engineCapacityCC = engineCapacityCC;
    }

    // Getter method for engine capacity
    public int getEngineCapacityCC() {
        return engineCapacityCC;
    }

    // Setter method for engine capacity
    public void setEngineCapacityCC(int engineCapacityCC) {
        this.engineCapacityCC = engineCapacityCC;
    }

    /*
     * Overrides displayDetails() method.
     * First displays common vehicle details using super,
     * then displays Bike-specific information.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Engine Capacity (CC): " + engineCapacityCC);
    }

    //Overrides abstract method from Vehicle.
    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (engineCapacityCC * 0.5 * days);
    }
}

