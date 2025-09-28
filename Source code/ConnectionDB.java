import java.sql.*;

public class ConnectionDB {

    private static final String URL = "jdbc:sqlserver://DESKTOP-35EOBF9\\MSSQLSERVER01;databaseName=StudentManagementDB;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "Ar27-9c110rRr";

    // Establish connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Close connection
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check the connection
    public static boolean testConnection() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Try to establish the connection
            conn = getConnection();
            stmt = conn.createStatement();
            // Execute a simple query to check the connection
            rs = stmt.executeQuery("SELECT 1");
            if (rs.next()) {
                System.out.println("Connection successful");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, rs);  // Close the resources
        }
        System.out.println("Connection failed.");
        return false;
    }
}
