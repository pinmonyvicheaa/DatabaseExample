import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseExample {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/"; // Base URL for the MySQL server
        String dbName = "Employee"; // Database name
        String fullUrl = url + dbName; // Full URL to the database
        String user = "root"; // Database user
        String password = "Your Password"; // Database password

        try {
            // First, attempt to create the database if it does not exist
            Connection rootCon = DriverManager.getConnection(url, user, password);
            Statement stat = rootCon.createStatement();
            stat.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Database check/create successful!");
            rootCon.close();

            // Now, establish a connection to the database
            Connection con = DriverManager.getConnection(fullUrl, user, password);
            System.out.println("Connection to database successful!");

            // Create a table if not exists
            stat = con.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Employee (" +
                    "emp_id INT, " +
                    "emp_last_name VARCHAR(50), " +
                    "emp_first_name VARCHAR(50), " +
                    "emp_email VARCHAR(100), " +
                    "emp_phone VARCHAR(20));";
            stat.execute(createTableQuery);
            System.out.println("Table check/create successful!");

            // Inserting data into the table
            String insertQuery = "INSERT INTO Employee (emp_id, emp_last_name, emp_first_name, emp_email, emp_phone) VALUES (?, ?, ?, ?, ?);";
            Object[][] employeeData = {
                    {1, "Chenda", "Sovisal", "chendasovisal@gmail.com", "092834989"},
                    {2, "Khorn", "Line", "khornLine@gmail.com", "092888555"},
                    {3, "Ly", "Mey", "lymey@gmail.com", "092834999"}
            };

            // Preparing statement and batch inserting employees
            PreparedStatement ps = con.prepareStatement(insertQuery);
            for (Object[] emp : employeeData) {
                ps.setInt(1, (Integer) emp[0]);
                ps.setString(2, (String) emp[1]);
                ps.setString(3, (String) emp[2]);
                ps.setString(4, (String) emp[3]);
                ps.setString(5, (String) emp[4]);
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Data insert successful!");
            con.close(); // Close connection

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
