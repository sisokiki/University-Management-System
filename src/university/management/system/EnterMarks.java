package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterMarks extends JInternalFrame {
    private JComboBox<String> rollNoComboBox;
    private JComboBox<String> semesterComboBox;
    private JTextField[] subjectFields = new JTextField[5];
    private JTextField[] marksFields = new JTextField[5];
    private JButton submitButton, cancelButton;

    public EnterMarks() {
        //'super' call is required for JInternalFrame
        super("Enter Student Marks", true, true, true, true);

        setSize(1000, 500);
        setLayout(new GridLayout(1, 2, 20, 10));

        // --- Left Panel for the Form ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(new JLabel("Enter Student Marks"), gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Select Roll Number:"), gbc);
        rollNoComboBox = new JComboBox<>(); gbc.gridx = 1; gbc.gridy = 1; formPanel.add(rollNoComboBox, gbc);
        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT rollNo FROM student")) {
            while (rs.next()) {
                rollNoComboBox.addItem(rs.getString("rollNo"));
            }
        } catch (Exception e) { e.printStackTrace(); }

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Select Semester:"), gbc);
        String[] semesters = {"1st Semester", "2nd Semester", "3rd Semester", "4th Semester", "5th Semester", "6th Semester", "7th Semester", "8th Semester"};
        semesterComboBox = new JComboBox<>(semesters); gbc.gridx = 1; gbc.gridy = 2; formPanel.add(semesterComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER; formPanel.add(new JLabel("Enter Subjects"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER; formPanel.add(new JLabel("Enter Marks"), gbc);
        gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < 5; i++) {
            subjectFields[i] = new JTextField(20);
            marksFields[i] = new JTextField(10);
            gbc.gridx = 0; gbc.gridy = 4 + i; formPanel.add(subjectFields[i], gbc);
            gbc.gridx = 1; gbc.gridy = 4 + i; formPanel.add(marksFields[i], gbc);
        }

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);
        add(formPanel);

        // --- Right Panel for the Image ---
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/exam.png"));
        Image i2 = i1.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT);
        JLabel imageLabel = new JLabel(new ImageIcon(i2));
        add(imageLabel);

        // --- Action Listeners ---
        submitButton.addActionListener(e -> performSubmit());
        cancelButton.addActionListener(e -> dispose()); // dispose() closes the internal frame
    }

    private void performSubmit() {
        String rollNo = (String) rollNoComboBox.getSelectedItem();
        String semester = (String) semesterComboBox.getSelectedItem();

        String subjectQuery = "INSERT INTO subject VALUES(?, ?, ?, ?, ?, ?, ?)";
        String marksQuery = "INSERT INTO marks VALUES(?, ?, ?, ?, ?, ?, ?)";

        ConnectionForSystem connWrapper = null;
        try {
            connWrapper = new ConnectionForSystem();
            connWrapper.connection.setAutoCommit(false);

            try (PreparedStatement subjectPstmt = connWrapper.connection.prepareStatement(subjectQuery)) {
                subjectPstmt.setString(1, rollNo);
                subjectPstmt.setString(2, semester);
                for (int i = 0; i < 5; i++) {
                    subjectPstmt.setString(i + 3, subjectFields[i].getText());
                }
                subjectPstmt.executeUpdate();
            }

            try (PreparedStatement marksPstmt = connWrapper.connection.prepareStatement(marksQuery)) {
                marksPstmt.setString(1, rollNo);
                marksPstmt.setString(2, semester);
                for (int i = 0; i < 5; i++) {
                    marksPstmt.setString(i + 3, marksFields[i].getText());
                }
                marksPstmt.executeUpdate();
            }

            connWrapper.connection.commit();
            JOptionPane.showMessageDialog(this, "Marks Inserted Successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (connWrapper != null) {
                    connWrapper.connection.rollback();
                }
            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Failed to insert marks. Transaction rolled back.", "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (connWrapper != null) {
                    connWrapper.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}