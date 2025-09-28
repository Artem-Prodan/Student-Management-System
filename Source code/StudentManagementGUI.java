import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentManagementGUI extends JFrame {
    private JTextField idField, nameField, ageField, gradeField;
    private JTextArea outputArea;
    private StudentManagerImpl manager;

    // Constructor
    public StudentManagementGUI() {
        manager = new StudentManagerImpl();
        initializeUI();
    }

    // Method to initialize the UI
    private void initializeUI() {
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with vertical layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the panel

        // Input fields panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));

        // Add Student ID field
        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        idField.setMaximumSize(new Dimension(50, 25));
        inputPanel.add(idField);

        // Add vertical spacing
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add Name field
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(200, 25));
        inputPanel.add(nameField);

        // Add vertical spacing
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add Age field
        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        ageField.setMaximumSize(new Dimension(50, 25));
        inputPanel.add(ageField);

        // Add vertical spacing
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add Grade field
        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        gradeField.setMaximumSize(new Dimension(80, 25));
        inputPanel.add(gradeField);

        // Add input panel to the main panel
        mainPanel.add(inputPanel);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        // Create buttons
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton updateButton = new JButton("Update Student");
        JButton displayButton = new JButton("Display All Students");
        JButton averageButton = new JButton("Calculate Average");

        // Add buttons to the button panel with vertical spacing
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between buttons

        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between buttons

        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between buttons

        buttonPanel.add(displayButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between buttons

        buttonPanel.add(averageButton);

        // Add button panel to the main panel
        mainPanel.add(buttonPanel);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add main panel and output area to the window
        add(mainPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event handlers for buttons

        // Add Student button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    String name = nameField.getText().trim(); // Trim whitespace
                    String ageText = ageField.getText();
                    String gradeText = gradeField.getText();

                    // Validate name field (must be a valid text, letters and spaces only)
                    if (name.isEmpty() || !name.matches("^[a-zA-Z\\s]+$")) {
                        outputArea.append("Invalid input! Name field must be a text.\n");
                        return; // Exit the method if the name is empty or not valid
                    }

                    // Validate input for age (must be a number)
                    int age;
                    try {
                        age = Integer.parseInt(ageText);
                    } catch (NumberFormatException ex) {
                        outputArea.append("Invalid input! Age field must be a number.\n");
                        return;
                    }

                    // Validate input for grade (must be a number)
                    double grade;
                    try {
                        grade = Double.parseDouble(gradeText);
                    } catch (NumberFormatException ex) {
                        outputArea.append("Invalid input! Grade field must be a number.\n");
                        return;
                    }

                    // Check if age and grade are valid
                    if (age <= 0) {
                        outputArea.append("Invalid input! Age must be a positive number.\n");
                        return;
                    }
                    if (grade < 0.0 || grade > 100.0) {
                        outputArea.append("Invalid input! Grade must be between 0.0 and 100.0.\n");
                        return;
                    }

                    // Check if the student already exists
                    if (manager.studentExists(id)) {
                        outputArea.append("Invalid input! Student with ID " + id + " already exists.\n");
                        return; // Exit the method if the student already exists
                    }

                    // Add student to the database
                    manager.addStudent(new Student(name, age, grade, id));
                    outputArea.append("Student added: " + name + " (ID: " + id + ")\n");

                    // Clear input fields after adding
                    idField.setText("");
                    nameField.setText("");
                    ageField.setText("");
                    gradeField.setText("");
                } catch (Exception ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        // Remove Student button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                if (id.isEmpty()) {
                    outputArea.append("Please enter a Student ID to remove.\n");
                    return;
                }

                // Check if the student exists before removing
                if (!manager.studentExists(id)) {
                    outputArea.append("Invalid input! No student found with ID: " + id + "\n");
                    return; // Exit the method if the student doesn't exist
                }

                // Remove student from the database
                manager.removeStudent(id);
                outputArea.append("Student removed: " + id + "\n");
            }
        });

        // Update Student button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idField.getText();
                    if (!manager.studentExists(id)) {
                        outputArea.append("Invalid input! No student found with ID: " + id + "\n");
                        return; // Exit the method if student doesn't exist
                    }
                    String name = nameField.getText().trim(); // Trim whitespace
                    String ageText = ageField.getText();
                    String gradeText = gradeField.getText();

                    // Validate name field (must be a valid text, letters and spaces only)
                    if (name.isEmpty() || !name.matches("^[a-zA-Z\\s]+$")) {
                        outputArea.append("Invalid input! Name field must be a text.\n");
                        return; // Exit the method if the name is empty or not valid
                    }

                    // Validate input for age (must be a number)
                    int age;
                    try {
                        age = Integer.parseInt(ageText);
                    } catch (NumberFormatException ex) {
                        outputArea.append("Invalid input! Age field must be a number.\n");
                        return;
                    }

                    // Validate input for grade (must be a number)
                    double grade;
                    try {
                        grade = Double.parseDouble(gradeText);
                    } catch (NumberFormatException ex) {
                        outputArea.append("Invalid input! Grade field must be a number.\n");
                        return;
                    }

                    // Check if age and grade are valid
                    if (age <= 0) {
                        outputArea.append("Invalid input! Age must be a positive number.\n");
                        return;
                    }
                    if (grade < 0.0 || grade > 100.0) {
                        outputArea.append("Invalid input! Grade must be between 0.0 and 100.0.\n");
                        return;
                    }

                    // Update student in the database
                    manager.updateStudent(id, name, age, grade);
                    outputArea.append("Student updated: " + name + " (ID: " + id + ")\n");
                } catch (Exception ex) {
                    outputArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        // Display All Students button
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.append("Fetching all students...\n");
                ArrayList<Student> students = manager.displayAllStudents();
                if (students.isEmpty()) {
                    outputArea.append("No students found.\n");
                } else {
                    for (Student student : students) {
                        outputArea.append(student.toString() + "\n"); // Use toString() to display student details
                    }
                }
            }
        });

        // Calculate Average button
        averageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double average = manager.calculateAverageGrade();
                outputArea.append("Average grade of all students: " + average + "\n");
            }
        });
    }

    // Main method to launch the GUI
    public static void main(String[] args) {
        // Launch the GUI in the Swing event thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementGUI().setVisible(true);
            }
        });
    }
}
