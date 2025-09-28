import java.util.ArrayList;

public interface StudentManager {
    void addStudent(Student student);
    void removeStudent(String studentID);
    void updateStudent(String studentID, String newName, int newAge, double newGrade);
    ArrayList<Student> displayAllStudents();
    double calculateAverageGrade();
}
