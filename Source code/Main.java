public class Main {
    public static void main(String[] args) {
        // Test the connection
        boolean isConnected = ConnectionDB.testConnection();

        if (isConnected) {
            System.out.println("Connection is working");

            // Create an instance of StudentManagerImpl
            StudentManagerImpl manager = new StudentManagerImpl();


            /**
            // Block for adding students (commented out for now)
            manager.addStudent(new Student("Summer Smith", 17, 85.5, "001"));
            manager.addStudent(new Student("Johnny Cage", 22, 80.0, "002"));
            manager.addStudent(new Student("Nathan Explosion", 28, 25.0, "003"));
            manager.addStudent(new Student("Marty McFly", 28, 60.0, "004"));
            manager.addStudent(new Student("Joseph Joestar", 18, 65.5, "005"));
             manager.addStudent(new Student("Patrick Bateman", 27, 90.5, "006"));
            */

            /**
            // Block for removing students (commented out for now)
            manager.removeStudent("001");
            manager.removeStudent("002");
            manager.removeStudent("003");
            */

            /**
            // Block for updating a student (commented out for now)
            manager.updateStudent("004", "Marty McFly", 17, 60.5);
             manager.updateStudent("006", "Patrick Bateman", 27, 95);
            */


            // Display all students (to check current state of the database)
            System.out.println("\nCurrent students in the database:");
            manager.displayAllStudents();

            // Calculate and display the average grade (to check current state of the database)
            double avgGrade = manager.calculateAverageGrade();
            System.out.println("\nCurrent average grade of all students: " + avgGrade);
        } else {
            System.out.println("Connection failed.");
        }
    }
}