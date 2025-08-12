package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.util.Random;

public class AddStudent extends JInternalFrame {

    JTextField textName, textFather, textAddress, textPhone, textEmail, textMarks10, textMarks12, textAadhar;
    JLabel labelRollNo;
    JDateChooser cdob;
    JComboBox<String> courseBox, branchBox;
    JButton submit, cancel;

    Random random = new Random();
    long first4 = Math.abs((random.nextLong() % 9000L) + 1000L);

    AddStudent() {
        //This 'super' call IS REQUIRED for JInternalFrame
        super("New Student Details", true, true, true, true);

        setSize(900, 600);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        // --- Title ---
        JLabel heading = new JLabel("New Student Details");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10); add(heading, gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.LINE_END; gbc.insets = new Insets(5, 10, 5, 10);

        // --- Row 1: Name and Father's Name ---
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Name:"), gbc);
        textName = new JTextField(15); gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textName, gbc);
        gbc.gridx = 2; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Father's Name:"), gbc);
        textFather = new JTextField(15); gbc.gridx = 3; gbc.gridy = 1; gbc.anchor = GridBagConstraints.LINE_START; add(textFather, gbc);

        // --- Row 2: Roll Number and DOB ---
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Roll Number:"), gbc);
        labelRollNo = new JLabel("2510" + first4); gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.LINE_START; add(labelRollNo, gbc);
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
        gbc.gridx = 2; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Aadhar Number:"), gbc);
        textAadhar = new JTextField(15); gbc.gridx = 3; gbc.gridy = 5; gbc.anchor = GridBagConstraints.LINE_START; add(textAadhar, gbc);

        // --- Row 6: Course and Branch ---
        gbc.gridx = 0; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Course:"), gbc);
        String[] courses = {"B.Tech", "BBA", "BCA", "BSc", "MSc", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(courses); gbc.gridx = 1; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_START; add(courseBox, gbc);
        gbc.gridx = 2; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_END; add(new JLabel("Branch:"), gbc);
        String[] branches = {"Computer Science", "Electronics", "Mechanical", "Civil", "IT"};
        branchBox = new JComboBox<>(branches); gbc.gridx = 3; gbc.gridy = 6; gbc.anchor = GridBagConstraints.LINE_START; add(branchBox, gbc);

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
        cancel.addActionListener(e -> dispose()); // dispose() is better for internal frames
    }

    private void performSubmit() {
        String name = textName.getText();
        String fatherName = textFather.getText();
        String rollNo = labelRollNo.getText();
        String dob = ((JTextField) cdob.getDateEditor().getUiComponent()).getText();
        String address = textAddress.getText();
        String phone = textPhone.getText();
        String email = textEmail.getText();
        String classX = textMarks10.getText();
        String classXII = textMarks12.getText();
        String aadhar = textAadhar.getText();
        String course = (String) courseBox.getSelectedItem();
        String branch = (String) branchBox.getSelectedItem();

        String query = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, fatherName);
            pstmt.setString(3, rollNo);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.setString(6, phone);
            pstmt.setString(7, email);
            pstmt.setString(8, classX);
            pstmt.setString(9, classXII);
            pstmt.setString(10, aadhar);
            pstmt.setString(11, course);
            pstmt.setString(12, branch);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Details Inserted Successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to insert details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddStudent();
    }
}