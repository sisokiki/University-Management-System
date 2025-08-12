package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentFeeForm extends JInternalFrame {
    private JComboBox<String> rollNoComboBox;
    private JComboBox<String> courseComboBox, branchComboBox, semesterComboBox;
    private JLabel labelName, labelFatherName, totalAmountLabel;
    private JButton payButton, updateButton, backButton;

    public StudentFeeForm() {
        //'super' call is required for JInternalFrame
        super("Student Fee Form", true, true, true, true);

        setSize(900, 500);
        setLayout(new GridLayout(1, 2, 10, 10)); // Form on left, image on right

        // --- Left Panel for the Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Roll Number
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Select Roll Number:"), gbc);
        rollNoComboBox = new JComboBox<>(); gbc.gridx = 1; formPanel.add(rollNoComboBox, gbc);

        // Name
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Name:"), gbc);
        labelName = new JLabel(); gbc.gridx = 1; formPanel.add(labelName, gbc);

        // Father's Name
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Father's Name:"), gbc);
        labelFatherName = new JLabel(); gbc.gridx = 1; formPanel.add(labelFatherName, gbc);

        // Course
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Course:"), gbc);
        String[] courses = {"BTech", "Bsc", "BCA", "MTech", "MSc", "MCA", "Bcom", "Mcom"};
        courseComboBox = new JComboBox<>(courses); gbc.gridx = 1; formPanel.add(courseComboBox, gbc);

        // Branch
        gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Branch:"), gbc);
        String[] branches = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        branchComboBox = new JComboBox<>(branches); gbc.gridx = 1; formPanel.add(branchComboBox, gbc);

        // Semester
        gbc.gridx = 0; gbc.gridy = 5; formPanel.add(new JLabel("Semester:"), gbc);
        String[] semesters = {"Semester 1", "Semester 2", "Semester 3", "Semester 4", "Semester 5", "Semester 6", "Semester 7", "Semester 8"};
        semesterComboBox = new JComboBox<>(semesters); gbc.gridx = 1; formPanel.add(semesterComboBox, gbc);

        // Total Payable
        gbc.gridx = 0; gbc.gridy = 6; formPanel.add(new JLabel("Total Payable:"), gbc);
        totalAmountLabel = new JLabel();
        totalAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 1; formPanel.add(totalAmountLabel, gbc);

        // Buttons
        updateButton = new JButton("Update Amount");
        payButton = new JButton("Pay Fee");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(updateButton);
        buttonPanel.add(payButton);
        buttonPanel.add(backButton);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(buttonPanel, gbc);

        add(formPanel);

        // --- Right Panel for Image ---
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fee.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(i2));
        add(imageLabel);

        // --- Populate and Load Data ---
        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT rollNo FROM student")) {
            while (rs.next()) {
                rollNoComboBox.addItem(rs.getString("rollNo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadStudentData((String) rollNoComboBox.getSelectedItem());

        // --- Action Listeners ---
        rollNoComboBox.addActionListener(e -> loadStudentData((String) rollNoComboBox.getSelectedItem()));
        updateButton.addActionListener(e -> updateTotalAmount());
        payButton.addActionListener(e -> performPayment());
        backButton.addActionListener(e -> dispose());
    }

    private void loadStudentData(String rollNo) {
        if (rollNo == null) return;
        String query = "SELECT * FROM student WHERE rollNo = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                labelName.setText(rs.getString("name"));
                labelFatherName.setText(rs.getString("fatherName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTotalAmount() {
        String course = (String) courseComboBox.getSelectedItem();
        String semester = (String) semesterComboBox.getSelectedItem();
        String query = "SELECT * FROM fee WHERE course = ?";

        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, course);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                totalAmountLabel.setText(rs.getString(semester));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void performPayment() {
        String query = "INSERT INTO feecollege VALUES(?, ?, ?, ?, ?)";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, (String) rollNoComboBox.getSelectedItem());
            pstmt.setString(2, (String) courseComboBox.getSelectedItem());
            pstmt.setString(3, (String) branchComboBox.getSelectedItem());
            pstmt.setString(4, (String) semesterComboBox.getSelectedItem());
            pstmt.setString(5, totalAmountLabel.getText());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Fee Submitted Successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Fee submission failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}