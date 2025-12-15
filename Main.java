import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* ===================== CAR ===================== */
class Car {
    private int carId;
    private String carModel;
    private boolean isAvailable;
    private double basePrice;
    private String brand;

    public Car(int carId, String brand, String carModel, double basePrice) {
        this.carId = carId;
        this.brand = brand;
        this.carModel = carModel;
        this.basePrice = basePrice;
        this.isAvailable = true;
    }

    public int getId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return carModel;
    }

    public boolean getAvailability() {
        return isAvailable;
    }

    public double getCost(int days) {
        return basePrice * days;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

/* ===================== CUSTOMER ===================== */
class Customer {
    private int govIdProof;
    private String customerName;
    private String phoneNo;
    private String address;

    public Customer(int govIdProof, String customerName, String phoneNo, String address) {
        this.govIdProof = govIdProof;
        this.customerName = customerName;
        this.phoneNo = phoneNo;
        this.address = address;
    }

    public int getGovIdProof() {
        return govIdProof;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getAddress() {
        return address;
    }
}

/* ===================== RENTAL ===================== */
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

/* ===================== CAR RENTAL SYSTEM ===================== */
class CarRentalSystem {
    private List<Car> cars = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.getAvailability()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;

        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n<=========== Car Rental System ==========>");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                /* -------- RENT CAR -------- */
                case 1:
                    System.out.println("\n== Rent a Car ==\n");

                    System.out.print("Enter your name: ");
                    String customerName = sc.nextLine();

                    System.out.print("Enter Govt ID Proof number: ");
                    int govId = sc.nextInt();
                    sc.nextLine();

                    // Phone number validation (10-digit Indian)
                    String phoneNo;
                    while (true) {
                        System.out.print("Enter Phone Number (10 digits only): ");
                        phoneNo = sc.nextLine();

                        if (phoneNo.length() != 10) {
                            System.out.println("Phone number must be exactly 10 digits.");
                        } else if (!phoneNo.matches("[6-9][0-9]{9}")) {
                            System.out.println("Invalid Indian phone number.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("Enter Address: ");
                    String address = sc.nextLine();

                    System.out.println("\nAvailable Cars:");
                    for (Car car : cars) {
                        if (car.getAvailability()) {
                            System.out.println(car.getId() + " - " + car.getBrand() + " " + car.getModel());
                        }
                    }

                    System.out.print("\nEnter Car ID to rent: ");
                    int carId = sc.nextInt();

                    System.out.print("Enter number of rental days: ");
                    int days = sc.nextInt();
                    sc.nextLine();

                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getId() == carId && car.getAvailability()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        Customer customer = new Customer(govId, customerName, phoneNo, address);
                        addCustomer(customer);
                        rentCar(selectedCar, customer, days);
                        System.out.println("Car rented successfully!");
                        System.out.println("Total Cost: " + selectedCar.getCost(days));
                    } else {
                        System.out.println("Invalid Car ID or Car not available.");
                    }
                    break;

                /* -------- RETURN CAR -------- */
                case 2:
                    System.out.println("\n== Return a Car ==\n");

                    System.out.print("Enter Car ID to return: ");
                    int returnCarId = sc.nextInt();
                    sc.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getId() == returnCarId) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully!");
                    } else {
                        System.out.println("Invalid Car ID.");
                    }
                    break;

                /* -------- EXIT -------- */
                case 3:
                    System.out.println("Thank you for using Car Rental System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

/* ===================== MAIN ===================== */
public class Main {
    public static void main(String[] args) {

        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Car(1, "Toyota", "Camry", 60.0));
        rentalSystem.addCar(new Car(2, "Honda", "Accord", 70.0));
        rentalSystem.addCar(new Car(3, "Mahindra", "Thar", 150.0));

        rentalSystem.menu();
    }
}
