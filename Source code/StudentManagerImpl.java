import java.sql.*;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {

    // Constructor to initialize the database table
    public StudentManagerImpl() {
        initializeDatabase();  // Ensure the table exists when the object is created
    }

    // Method to initialize the database table
    private void initializeDatabase() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS students ("
                + "name VARCHAR(100), "
                + "age INT, "
                + "grade REAL, "
                + "studentID VARCHAR(50) PRIMARY KEY"
                + ")";
        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableQuery);
            System.out.println("Table 'students' initialized successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a student exists by studentID
    public boolean studentExists(String studentID) {
        String query = "SELECT studentID FROM students WHERE studentID = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if the student exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Returns false if the student does not exist or an error occurs
    }

    // Method to add a student
    @Override
    public void addStudent(Student student) {
        // Check if the student already exists
        if (studentExists(student.getStudentID())) {
            System.out.println("Student with ID " + student.getStudentID() + " already exists.");
            return;
        }


        String query = "INSERT INTO students (name, age, grade, studentID) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, student.getName());
            stmt.setInt(2, student.getAge());
            stmt.setDouble(3, student.getGrade());
            stmt.setString(4, student.getStudentID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a student
    @Override
    public void removeStudent(String studentID) {
        String query = "DELETE FROM students WHERE studentID = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, studentID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student removed successfully.");
            } else {
                System.out.println("Student not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update student details
    @Override
    public void updateStudent(String studentID, String newName, int newAge, double newGrade) {
        // Check if the student exists before updating
        if (!studentExists(studentID)) {
            System.out.println("Student with ID " + studentID + " not found.");
            return;
        }

        String query = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?";
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setInt(2, newAge);
            stmt.setDouble(3, newGrade);
            stmt.setString(4, studentID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student updated successfully.");
            } else {
                System.out.println("Update failed. No changes made.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to display all students
    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double grade = rs.getDouble("grade");
                String studentID = rs.getString("studentID");
                Student student = new Student(name, age, grade, studentID);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Method to calculate average grade
    @Override
    public double calculateAverageGrade() {
        String query = "SELECT AVG(grade) FROM students";
        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                double avgGrade = rs.getDouble(1);
                if (rs.wasNull()) {
                    System.out.println("No students found.");
                    return 0.0;
                }
                return avgGrade;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}