import java.sql.*;
import java.util.Scanner;

public class EmployeeApp {
    private static final String URL = "jdbc:mysql://localhost:3306/companydb";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // 👈 Change this

    private static Connection conn;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL Database!");

            int choice;
            do {
                System.out.println("\n====== Employee Management System ======");
                System.out.println("1. Add Employee");
                System.out.println("2. View Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> viewEmployees();
                    case 3 -> updateEmployee();
                    case 4 -> deleteEmployee();
                    case 5 -> System.out.println("👋 Exiting...");
                    default -> System.out.println("❌ Invalid choice! Try again.");
                }
            } while (choice != 5);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addEmployee() throws SQLException {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        String query = "INSERT INTO employees (name, age, department) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, department);
        ps.executeUpdate();

        System.out.println("✅ Employee added successfully!");
    }

    private static void viewEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.printf("\n%-5s %-20s %-5s %-20s\n", "ID", "Name", "Age", "Department");
        System.out.println("-----------------------------------------------");

        while (rs.next()) {
            System.out.printf("%-5d %-20s %-5d %-20s\n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("department"));
        }
    }

    private static void updateEmployee() throws SQLException {
        System.out.print("Enter Employee ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Department: ");
        String department = scanner.nextLine();

        String query = "UPDATE employees SET name=?, age=?, department=? WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, department);
        ps.setInt(4, id);
        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("✅ Employee updated successfully!");
        } else {
            System.out.println("⚠️ Employee not found.");
        }
    }

    private static void deleteEmployee() throws SQLException {
        System.out.print("Enter Employee ID to delete: ");
        int id = scanner.nextInt();

        String query = "DELETE FROM employees WHERE id=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, id);
        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("🗑️ Employee deleted successfully!");
        } else {
            System.out.println("⚠️ Employee not found.");
        }
    }
}
