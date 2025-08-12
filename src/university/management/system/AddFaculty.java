package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.util.Random;


public class AddFaculty extends JInternalFrame {

    JTextField textName, textFather, textAddress, textPhone, textEmail, textMarks10, textMarks12, textAadhar;
    JLabel labelEmpId;
    JDateChooser cdob;
    JComboBox<String> courseBox, departmentBox;
    JButton submit, cancel;

    Random random = new Random();
    long first4 = Math.abs((random.nextLong() % 9000L) + 1000L);

    AddFaculty() {
        // This 'super' call IS REQUIRED for JInternalFrame
        super("New Faculty Details", true, true, true, true);

        setSize(900, 600);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // --- Title ---
        JLabel heading = new JLabel("New Faculty Details");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(heading, gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(5, 10, 5, 10);

        // --- Row 1: Name and Father's Name ---
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Name:"), gbc);
        textName = new JTextField(15); gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textName, gbc);

        gbc.gridx = 2; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Father's Name:"), gbc);
        textFather = new JTextField(15); gbc.gridx = 3; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textFather, gbc);

        // --- Row 2: Employee ID and DOB ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Employee ID:"), gbc);
        labelEmpId = new JLabel("1501" + first4); gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START; add(labelEmpId, gbc);

        gbc.gridx = 2; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Date of Birth:"), gbc);
        cdob = new JDateChooser(); cdob.setPreferredSize(new Dimension(180, 25)); gbc.gridx = 3; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START; add(cdob, gbc);

        // --- Row 3: Address and Phone ---
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Address:"), gbc);
        textAddress = new JTextField(15); gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_START; add(textAddress, gbc);

        gbc.gridx = 2; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Phone:"), gbc);
        textPhone = new JTextField(15); gbc.gridx = 3; gbc.gridy = 3; gbc.anchor = GridBagConstraints.LINE_START; add(textPhone, gbc);

        // --- Row 4: Email and Class X ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Email ID:"), gbc);
        textEmail = new JTextField(15); gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_START; add(textEmail, gbc);

        gbc.gridx = 2; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Class X (%):"), gbc);
        textMarks10 = new JTextField(15); gbc.gridx = 3; gbc.gridy = 4; gbc.anchor = GridBagConstraints.LINE_START; add(textMarks10, gbc);

        // --- Row 5: Class XII and Aadhar ---
        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Class XII (%):"), gbc);
        textMarks12 = new JTextField(15); gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_START; add(textMarks12, gbc);

        gbc.gridx = 2; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Aadhaar Number:"), gbc);
        textAadhar = new JTextField(15); gbc.gridx = 3; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_START; add(textAadhar, gbc);

        // --- Row 6: Education and Department ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Qualification:"), gbc);
        String[] courses = {"B.Tech", "BBA", "BCA", "BSc", "MSc", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(courses); gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_START; add(courseBox, gbc);

        gbc.gridx = 2; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Department:"), gbc);
        String[] departments = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        departmentBox = new JComboBox<>(departments); gbc.gridx = 3; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_START; add(departmentBox, gbc);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        buttonPanel.add(submit);
        buttonPanel.add(cancel);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(buttonPanel, gbc);

        // --- Action Listeners ---
        submit.addActionListener(e -> performSubmit());
        // For JInternalFrame, dispose() is preferred to setVisible(false) to free resources
        cancel.addActionListener(e -> dispose());
    }

    private void performSubmit() {
        String name = textName.getText();
        String fatherName = textFather.getText();
        String empId = labelEmpId.getText();
        String dob = ((JTextField) cdob.getDateEditor().getUiComponent()).getText();
        String address = textAddress.getText();
        String phone = textPhone.getText();
        String email = textEmail.getText();
        String classX = textMarks10.getText();
        String classXII = textMarks12.getText();
        String aadhar = textAadhar.getText();
        String education = (String) courseBox.getSelectedItem();
        String department = (String) departmentBox.getSelectedItem();

        String query = "INSERT INTO teacher VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, empId);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, classX);
            pstmt.setString(9, classXII);
            pstmt.setString(10, aadhar);
            pstmt.setString(11, education);
            pstmt.setString(12, department);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Faculty Details Inserted Successfully!");
            dispose(); // Close the internal frame on success

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to insert details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddFaculty();
    }
}