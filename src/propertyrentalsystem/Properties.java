package propertyrentalsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Properties {
    private final Scanner scan = new Scanner(System.in);
    private final Config conf = new Config();

    public void manageProperties() {
        int option;
        do {
            System.out.println("\n\t=== Property Management ===");
            System.out.println("1. View All Properties");
            System.out.println("2. Add a Property");
            System.out.println("3. Remove a Property");
            System.out.println("4. Edit a Property");
            System.out.println("5. Go back");
            System.out.print("\nSelect an option: ");

            try {
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("\n\t\t\t=== Property List ===");
                        viewProperties("SELECT * FROM properties");
                        break;
                    case 2:
                        addProperty();
                        break;
                    case 3:
                        deleteProperty();
                        break;
                    case 4:
                        editProperty();
                        break;
                    case 5:
                        System.out.println("Exiting Property Management...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void viewProperties(String query) {
        
        String[] headers = {"Property ID", "Address", "Monthly Rent Price", "Status"};
        String[] columns = {"id", "address", "price", "status"};

        conf.viewRecords(query, 27, headers, columns);
    }

    private void addProperty() {
        System.out.println("\n=== Add a New Property ===");
        System.out.print("Address: ");
        String address = scan.nextLine();
        System.out.print("Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        System.out.print("Status: ");
        String status = scan.nextLine();

        String sql = "INSERT INTO properties (address, price, status) VALUES (?, ?, ?)";
        conf.addRecord(sql, address, price, status);
    }

    private void deleteProperty() {
        System.out.println("\n=== Remove a Property ===");
        System.out.print("Enter Property ID to delete: ");
        int id = scan.nextInt();
        scan.nextLine();

        String sql = "DELETE FROM properties WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    private void editProperty() {
        System.out.println("\n=== Edit a Property ===");
        int id;
        do {
            System.out.print("Enter Property ID to edit: ");
            id = scan.nextInt();
            scan.nextLine();

            if (!conf.doesIDExist("properties", id)) {
                System.out.println("Property ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("properties", id));

        System.out.println("Selected Record:");
        viewProperties("SELECT * FROM properties WHERE id = " + id);

        System.out.print("New Address: ");
        String address = scan.nextLine();
        System.out.print("New Price: ");
        double price = scan.nextDouble();
        scan.nextLine();
        System.out.print("New Status: ");
        String status = scan.nextLine();

        String sql = "UPDATE properties SET address = ?, price = ?, status = ? WHERE id = ?";
        conf.updateRecord(sql, address, price, status, id);
    }
}
