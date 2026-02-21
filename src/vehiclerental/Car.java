package vehiclerental;

//Car class extends Vehicle.


public class Car extends Vehicle {

    // Specific attribute only for Car
    private int numberOfSeats;

    /*
     * Constructor to initialize Car object.
     * Calls the parent (Vehicle) constructor using super.
     */
    public Car(String vehicleId, String brand, String model,
               double baseRatePerDay, boolean isAvailable, int numberOfSeats) {

        super(vehicleId, brand, model, baseRatePerDay, isAvailable);
        this.numberOfSeats = numberOfSeats;
    }

    // Getter method for numberOfSeats
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    // Setter method for numberOfSeats
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /*
     * Overrides the displayDetails() method.
     * First prints common vehicle details using super,
     * then prints Car-specific information.
     */
    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Seats: " + numberOfSeats);
    }


    //Overrides abstract method from Vehicle.
    @Override
    public double calculateRentalCost(int days) {
        return getBaseRatePerDay() * days + (numberOfSeats * 200.0 * days);
    }
}

