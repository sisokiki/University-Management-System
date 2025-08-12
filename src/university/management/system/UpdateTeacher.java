package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UpdateTeacher extends JInternalFrame {

    private JTextField textAddress, textPhone, textEmail, textCourse, textBranch;
    private JLabel labelName, labelFatherName, labelEmpId, labelDob, labelMarks10, labelMarks12, labelAadhar;
    private JButton updateButton, cancelButton;
    private JComboBox<String> empIdComboBox;

    public UpdateTeacher() {
        // 'super' call is required for JInternalFrame
        super("Update Faculty Details", true, true, true, true);

        setSize(900, 600);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // --- Title and Search ---
        JLabel heading = new JLabel("Update Teacher Details");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(heading, gbc);

        gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Select Employee ID:"), gbc);
        empIdComboBox = new JComboBox<>();
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(empIdComboBox, gbc);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Populate ComboBox
        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT empId FROM teacher")) {
            while (rs.next()) {
                empIdComboBox.addItem(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Form Fields ---
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Name:"), gbc);
        labelName = new JLabel(); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(labelName, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Father's Name:"), gbc);
        labelFatherName = new JLabel(); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(labelFatherName, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Employee ID:"), gbc);
        labelEmpId = new JLabel(); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(labelEmpId, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Date of Birth:"), gbc);
        labelDob = new JLabel(); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(labelDob, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Address:"), gbc);
        textAddress = new JTextField(15); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textAddress, gbc);

        gbc.gridx = 2; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Phone:"), gbc);
        textPhone = new JTextField(15); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(textPhone, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Email ID:"), gbc);
        textEmail = new JTextField(15); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textEmail, gbc);

        gbc.gridx = 2; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Class X (%):"), gbc);
        labelMarks10 = new JLabel(); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(labelMarks10, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Class XII (%):"), gbc);
        labelMarks12 = new JLabel(); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(labelMarks12, gbc);

        gbc.gridx = 2; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Aadhar Number:"), gbc);
        labelAadhar = new JLabel(); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(labelAadhar, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Qualification:"), gbc);
        textCourse = new JTextField(15); gbc.gridx = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textCourse, gbc);

        gbc.gridx = 2; gbc.gridy = 7; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Department:"), gbc);
        textBranch = new JTextField(15); gbc.gridx = 3; gbc.anchor = GridBagConstraints.LINE_START; add(textBranch, gbc);

        // --- Buttons ---
        updateButton = new JButton("Update");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(buttonPanel, gbc);

        // --- Action Listeners ---
        empIdComboBox.addActionListener(e -> loadTeacherData((String) empIdComboBox.getSelectedItem()));
        updateButton.addActionListener(e -> performUpdate());
        cancelButton.addActionListener(e -> dispose()); // dispose() is used to close JInternalFrame

        // Load data for the initially selected teacher
        loadTeacherData((String) empIdComboBox.getSelectedItem());
    }

    private void loadTeacherData(String empId) {
        if (empId == null) return;
        String query = "SELECT * FROM teacher WHERE empId = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, empId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                labelName.setText(rs.getString("name"));
                labelFatherName.setText(rs.getString("fatherName"));
                labelEmpId.setText(rs.getString("empId"));
                labelDob.setText(rs.getString("dob"));
                textAddress.setText(rs.getString("address"));
                textPhone.setText(rs.getString("phone"));
                textEmail.setText(rs.getString("email"));
                labelMarks10.setText(rs.getString("class_X"));
                labelMarks12.setText(rs.getString("class_XII"));
                labelAadhar.setText(rs.getString("aadhar"));
                textCourse.setText(rs.getString("education"));
                textBranch.setText(rs.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load teacher data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void performUpdate() {
        String query = "UPDATE teacher SET address = ?, phone = ?, email = ?, education = ?, department = ? WHERE empId = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, textAddress.getText());
            pstmt.setString(2, textPhone.getText());
            pstmt.setString(3, textEmail.getText());
            pstmt.setString(4, textCourse.getText());
            pstmt.setString(5, textBranch.getText());
            pstmt.setString(6, labelEmpId.getText());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Faculty Details Updated Successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. Teacher not found.", "Update Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update details.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UpdateTeacher();
    }
}