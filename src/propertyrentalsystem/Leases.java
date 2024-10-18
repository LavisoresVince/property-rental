package propertyrentalsystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Leases {
    private final Scanner scan = new Scanner(System.in);
    private final Config conf = new Config();

    public void manageLeases() {
        int option;
        do {
            System.out.println("\n\t=== Lease Management ===");
            System.out.println("1. View All Leases");
            System.out.println("2. Add a Lease");
            System.out.println("3. Remove a Lease");
            System.out.println("4. Edit a Lease");
            System.out.println("5. Go back");
            System.out.print("\nSelect an option: ");

            try {
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("\n\t\t\t=== Lease List ==="); 
                        viewLeases("SELECT * FROM leases");
                        break;
                    case 2:
                        addLease();
                        break;
                    case 3:
                        deleteLease();
                        break;
                    case 4:
                        editLease();
                        break;
                    case 5:
                        System.out.println("Exiting Lease Management...");
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

    private void viewLeases(String query) {
        String[] headers = {"ID", "Property ID", "Tenant ID", "Start Date", "End Date"};
        String[] columns = {"id", "property_id", "tenant_id", "start_date", "end_date"};

        conf.viewRecords(query, 16, headers, columns);
    }

    private void addLease() {
        System.out.println("\n=== Add a New Lease ===");
        
        int propertyId, tenantId;
        do {
            System.out.print("Enter Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("properties", propertyId)) {
                System.out.println("Property ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("properties", propertyId));

        do {
            System.out.print("Enter Tenant ID: ");
            tenantId = scan.nextInt();
            if (!conf.doesIDExist("tenants", tenantId)) {
                System.out.println("Tenant ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("tenants", tenantId));

        System.out.print("Start Date (MM-DD-YYYY): ");
        String startDate = scan.next();
        System.out.print("End Date (MM-DD-YYYY): ");
        String endDate = scan.next();

        String sql = "INSERT INTO leases (property_id, tenant_id, start_date, end_date) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, propertyId, tenantId, startDate, endDate);
    }

    private void deleteLease() {
        System.out.println("\n=== Remove a Lease ===");
        System.out.print("Enter Lease ID to delete: ");
        int id = scan.nextInt();
        scan.nextLine();

        String sql = "DELETE FROM leases WHERE id = ?";
        conf.deleteRecord(sql, id);
    }

    private void editLease() {
        System.out.println("\n=== Edit a Lease ===");
        int id;
        
        do {
            System.out.print("Enter Lease ID to edit: ");
            id = scan.nextInt();
            scan.nextLine();

            if (!conf.doesIDExist("leases", id)) {
                System.out.println("Lease ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("leases", id));

        System.out.println("Selected Record:");
        viewLeases("SELECT * FROM leases WHERE id = " + id);

        int propertyId, tenantId;
        do {
            System.out.print("New Property ID: ");
            propertyId = scan.nextInt();
            if (!conf.doesIDExist("properties", propertyId)) {
                System.out.println("Property ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("properties", propertyId));

        do {
            System.out.print("New Tenant ID: ");
            tenantId = scan.nextInt();
            if (!conf.doesIDExist("tenants", tenantId)) {
                System.out.println("Tenant ID doesn't exist. Please try again.");
            }
        } while (!conf.doesIDExist("tenants", tenantId));

        System.out.print("New Start Date (MM-DD-YYYY): ");
        String startDate = scan.next();
        System.out.print("New End Date (MM-DD-YYYY): ");
        String endDate = scan.next();

        String sql = "UPDATE leases SET property_id = ?, tenant_id = ?, start_date = ?, end_date = ? WHERE id = ?";
        conf.updateRecord(sql, propertyId, tenantId, startDate, endDate, id);
    }
}

