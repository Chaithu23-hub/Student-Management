import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class FXDatabaseInsert extends Application {

    @Override
    public void start(Stage stage) {
        // Input Fields
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("Enter Student ID");

        TextField studentNameField = new TextField();
        studentNameField.setPromptText("Enter Student Name");

        TextField studentEmailField = new TextField();
        studentEmailField.setPromptText("Enter Student Email");

        TextField courseIdField = new TextField();
        courseIdField.setPromptText("Enter Course ID");

        TextField courseNameField = new TextField();
        courseNameField.setPromptText("Enter Course Name");

        // Buttons and Output
        Button submitButton = new Button("Submit All");
        Button fetchButton = new Button("Fetch Data");
        Label statusLabel = new Label();
        TextArea dataArea = new TextArea();
        dataArea.setEditable(false);
        dataArea.setPrefHeight(200);

        // Submit logic
        submitButton.setOnAction(e -> {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String studentName = studentNameField.getText();
                String studentEmail = studentEmailField.getText();

                int courseId = Integer.parseInt(courseIdField.getText());
                String courseName = courseNameField.getText();

                if (studentName.isEmpty() || studentEmail.isEmpty() || courseName.isEmpty()) {
                    statusLabel.setText("Please fill all fields.");
                    return;
                }

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/stdinfo", "root", "Chaithu@112223");

                PreparedStatement pst1 = con.prepareStatement(
                        "INSERT INTO students VALUES (?, ?, ?)");
                pst1.setInt(1, studentId);
                pst1.setString(2, studentName);
                pst1.setString(3, studentEmail);
                pst1.executeUpdate();

                PreparedStatement pst2 = con.prepareStatement(
                        "INSERT INTO courses  VALUES (?, ?)");
                pst2.setInt(1, courseId);
                pst2.setString(2, courseName);
                pst2.executeUpdate();

                PreparedStatement pst3 = con.prepareStatement(
                        "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)");
                pst3.setInt(1, studentId);
                pst3.setInt(2, courseId);
                pst3.executeUpdate();

                statusLabel.setText("Data inserted into all 3 tables!");

                studentIdField.clear();
                studentNameField.clear();
                studentEmailField.clear();
                courseIdField.clear();
                courseNameField.clear();

                pst1.close();
                pst2.close();
                pst3.close();
                con.close();

            } catch (SQLIntegrityConstraintViolationException ex) {
                statusLabel.setText("Duplicate or FK violation: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                statusLabel.setText("ID fields must be numbers.");
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        // Fetch logic
        fetchButton.setOnAction(e -> {
            dataArea.clear();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/stdinfo", "root", "Chaithu@112223");

                Statement stmt = con.createStatement();

                dataArea.appendText("Students:\n");
                ResultSet rs1 = stmt.executeQuery("SELECT * FROM students");
                while (rs1.next()) {
                    dataArea.appendText("ID: " + rs1.getInt("student_id") +
                            ", Name: " + rs1.getString("name") +
                            ", Email: " + rs1.getString("email") + "\n");
                }

                dataArea.appendText("\nCourses:\n");
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM courses");
                while (rs2.next()) {
                    dataArea.appendText("Course ID: " + rs2.getInt("course_id") +
                            ", Course Name: " + rs2.getString("name") + "\n");
                }

                dataArea.appendText("\nEnrollments:\n");
                ResultSet rs3 = stmt.executeQuery("SELECT * FROM enrollments");
                while (rs3.next()) {
                    dataArea.appendText("Student ID: " + rs3.getInt("student_id") +
                            ", Course ID: " + rs3.getInt("course_id") + "\n");
                }

                rs1.close();
                rs2.close();
                rs3.close();
                stmt.close();
                con.close();

                statusLabel.setText("Data fetched successfully!");

            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10,
                new Label("Student Details"), studentIdField, studentNameField, studentEmailField,
                new Label("Course Details"), courseIdField, courseNameField,
                submitButton, fetchButton, statusLabel, dataArea);

        Scene scene = new Scene(layout, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Insert & Fetch MySQL Records");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}