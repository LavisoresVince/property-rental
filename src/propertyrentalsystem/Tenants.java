package propertyrentalsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Tenants {
    private final Scanner scan = new Scanner(System.in);
    private final Config conf = new Config();

    public void manageTenants() {
        int option;
        do {
            System.out.println("\n\t=== Tenant Management ===");
            System.out.println("1. View All Tenants");
            System.out.println("2. Add a Tenant");
            System.out.println("3. Remove a Tenant");
            System.out.println("4. Edit a Tenant");
            System.out.println("5. Go back");
            System.out.print("\nSelect an option: ");

            try {
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("\n\t\t\t=== Tenant List ===");
                        viewTenants("SELECT * FROM tenants");
                        break;
                    case 2:
                        addTenant();
                        break;
                    case 3:
                        deleteTenant();
                        break;
                    case 4:
                        editTenant();
                        break;
                    case 5:
                        System.out.println("Exiting Tenant Management...");
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

    private void viewTenants(String query) {
        
        String[] headers = {"ID", "First Name", "Last Name", "Contact Info"};
        String[] columns = {"id", "fname", "lname", "contact_info"};

        conf.viewRecords(query, 25, headers, columns);
    }

    private void addTenant() {
        System.out.println("\n=== Add a New Tenant ===");
        System.out.print("First Name: ");
        String fname = scan.nextLine();
        System.out.print("Last Name: ");
        String lname = scan.nextLine();
        System.out.print("Contact Info (Email/Phone): ");
        String contactInfo = scan.nextLine();

        String sql = "INSERT INTO tenants (fname, lname, contact_info) VALUES (?, ?, ?)";
        conf.addRecord(sql, fname, lname, contactInfo);
    }

    private void deleteTenant() {
        System.out.println("\n=== Remove a Tenant ===");
        System.out.print("Enter Tenant ID to delete: ");
        int id = scan.nextInt();
        scan.nextLine();

        String sql = "DELETE FROM tenants WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    private void editTenant() {
        System.out.println("\n=== Edit a Tenant ===");
        int id;
        do {
            System.out.print("Enter Tenant ID to edit: ");
            id = scan.nextInt();
            scan.nextLine();

            if (!conf.doesIDExist("tenants", id)) {
                System.out.println("Tenant ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("tenants", id));

        System.out.println("Selected Record:");
        viewTenants("SELECT * FROM tenants WHERE id = " + id);

        System.out.print("New First Name: ");
        String fname = scan.nextLine();
        System.out.print("New Last Name: ");
        String lname = scan.nextLine();
        System.out.print("New Contact Info (Email/Phone): ");
        String contactInfo = scan.nextLine();

        String sql = "UPDATE tenants SET fname = ?, lname = ?, contact_info = ? WHERE id = ?";
        conf.updateRecord(sql, fname, lname, contactInfo, id);
    }
}

