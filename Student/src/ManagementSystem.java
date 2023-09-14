import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagementSystem {
    private ArrayList<Student> studentList;
    private Scanner scanner;
    private static final String DATA_FILE = "student_data.ser";

    public ManagementSystem() {
        studentList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("==============================");
        System.out.println("Welcome Student MIS");
        System.out.println("==============================");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student Information");
        System.out.println("3. View Student Details");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
        System.out.println("------------------------------------------");
        System.out.print("Please enter your choice (1-5): ");
    }

    private int getUserChoice() {
        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                validChoice = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    public void performOperation(int choice) {
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                updateStudentInformation();
                break;
            case 3:
                viewStudentDetails();
                break;
            case 4:
                deleteStudent();
                break;
            case 5:
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public void updateStudentInformation() {
        System.out.print("Enter the student ID to update: ");
        String studentId = scanner.next();

        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                System.out.println("Enter updated information for the student:");

                System.out.print("Name: ");
                String name = scanner.next();
                student.setName(name);

                System.out.print("Age: ");
                int age = scanner.nextInt();
                student.setAge(age);

                System.out.print("Grade: ");
                String grade = scanner.next();
                student.setGrade(grade);

                System.out.println("Student information updated successfully!");
                return;
            }
        }

        System.out.println("Student with the provided ID not found.");
    }

    public void addStudent() {
        System.out.println("Enter student details:");

        System.out.print("Student ID: ");
        String studentId = scanner.next();

        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Age: ");
        int age = scanner.nextInt();

        System.out.print("Grade: ");
        String grade = scanner.next();

        Student newStudent = new Student(studentId, name, age, grade);

        studentList.add(newStudent);

        System.out.println("Student added successfully!");
    }

    public void viewStudentDetails() {
        System.out.print("Enter the student ID to view details: ");
        String studentId = scanner.next();

        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                System.out.println("Student Details:");
                System.out.println("Student ID: " + student.getStudentId());
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());
                System.out.println("Grade: " + student.getGrade());
                return;
            }
        }

        System.out.println("Student with the provided ID not found.");
    }

    public void deleteStudent() {
        System.out.print("Enter the student ID to delete: ");
        String studentId = scanner.next();

        for (Student student : studentList) {
            if (student.getStudentId().equals(studentId)) {
                studentList.remove(student);
                System.out.println("Student deleted successfully!");
                return;
            }
        }

        System.out.println("Student with the provided ID not found.");
    }

    private ArrayList<Student> loadStudentData() {
        try (FileInputStream fileIn = new FileInputStream(DATA_FILE);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (ArrayList<Student>) objectIn.readObject();
        } catch (FileNotFoundException e) {
            // No existing data file found, return an empty list
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading student data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void saveStudentData() {
        try (FileOutputStream fileOut = new FileOutputStream(DATA_FILE);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    @Override
    public void finalize() {
        saveStudentData();
    }
    public void run() {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            performOperation(choice);
        }
    }
}
