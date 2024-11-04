package propertyrentalsystem;

import java.util.InputMismatchException;
import java.util.Scanner;
 
public class PropertyRentalSystem {
    static Scanner scan = new Scanner(System.in);
    static Config conf = new Config();
    
    public static void main(String[] args) {
        Properties prop = new Properties();
        Tenants ten = new Tenants();
        Leases ls = new Leases();
        
        int opt;
        do {    
            try {
                System.out.println("\n\t=== Job Application Tracker ===\n");
                System.out.println("1. Properties\n" +
                                    "2. Tenants\n" +
                                    "3. Leases\n" +
                                    "4. Generate Reports\n" +
                                    "5. Exit");
                
                System.out.print("\nEnter Option: ");
                opt = scan.nextInt();
                scan.nextLine();
                System.out.println("");
                
                switch (opt) {
                    case 1:
                        System.out.println("------------------------------------------------------------------");
                        prop.manageProperties();
                        break;
                        
                    case 2:
                        System.out.println("------------------------------------------------------------------");
                        ten.manageTenants();
                        break;

                    case 3:
                        System.out.println("------------------------------------------------------------------");
                        ls.manageLeases();
                        break;

                    case 4:
                        System.out.println("------------------------------------------------------------------");
                        generateReport();
                        break;
 
                    case 5:
                        System.out.println("Exiting...");
                        System.out.println("------------------------------------------------------------------");
                        break;

                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine(); 
                opt = -1; 
            }
        } while (opt != 5);    
    }
    
    public static void generateReport() {
        Tenants ten = new Tenants();
        
        System.out.println("\n\t\t\t--- LEASES REPORT ---");
           
        System.out.println("\n\t\t\t=== Tenant List ===");
        ten.viewTenants("SELECT * FROM tenants");
       
        int tenantId;
        do {
            System.out.print("\nEnter Tenant ID for the report: ");
            tenantId = scan.nextInt();
            if (!conf.doesIDExist("tenants", tenantId)) {
                System.out.println("Tenant ID not found. Please try again.");
            }
        } while (!conf.doesIDExist("tenants", tenantId));

        System.out.println("\n=================================");
        System.out.println("       TENANT DETAILS             ");
        System.out.println("=================================");
        System.out.printf("Tenant ID   : %d%n", tenantId);
        System.out.printf("First Name  : %s%n", conf.getDataFromID("tenants", tenantId, "fname"));
        System.out.printf("Last Name   : %s%n", conf.getDataFromID("tenants", tenantId, "lname"));
        System.out.println("---------------------------------");

        if (conf.isTableEmpty("leases WHERE tenant_id = " + tenantId)) {
            System.out.println("No lease records found for this tenant.");
        } else {
            System.out.println("Lease Details:");
            String sql = "SELECT properties.address, properties.price, leases.start_date, leases.end_date " +
                         "FROM leases " +
                         "JOIN properties ON leases.property_id = properties.id " +
                         "WHERE leases.tenant_id = " + tenantId;

            String[] headers = {"Address", "Monthly Rent Price", "Start Date", "End Date"};
            String[] columns = {"address", "price", "start_date", "end_date"};

            conf.viewRecords(sql, 20, headers, columns);
            System.out.println("\n=================================");
        }
    }


}
