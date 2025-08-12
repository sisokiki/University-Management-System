package university.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherLeave extends JInternalFrame {

    private JComboBox<String> empIdComboBox;
    private JDateChooser dateChooser;
    private JComboBox<String> timeComboBox;
    private JButton submitButton, cancelButton;

    public TeacherLeave() {
        // 'super' call is required for JInternalFrame
        super("Apply Faculty Leave", true, true, true, true);

        setSize(500, 450);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Heading ---
        JLabel heading = new JLabel("Apply Leave (Faculty)");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(heading, gbc);
        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // --- Employee ID Selection ---
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Select Employee ID:"), gbc);
        empIdComboBox = new JComboBox<>();
        gbc.gridx = 1; gbc.gridy = 1; add(empIdComboBox, gbc);

        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT empId FROM teacher")) {
            while (rs.next()) {
                empIdComboBox.addItem(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- Date Selection ---
        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Select Date:"), gbc);
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1; gbc.gridy = 2; add(dateChooser, gbc);

        // --- Time Duration Selection ---
        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("Time Duration:"), gbc);
        String[] timeOptions = {"Full Day", "Half Day"};
        timeComboBox = new JComboBox<>(timeOptions);
        gbc.gridx = 1; gbc.gridy = 3; add(timeComboBox, gbc);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(buttonPanel, gbc);

        // --- Action Listeners ---
        submitButton.addActionListener(e -> performSubmit());
        cancelButton.addActionListener(e -> dispose()); // dispose() closes the internal frame
    }

    private void performSubmit() {
        String empId = (String) empIdComboBox.getSelectedItem();
        String date = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String time = (String) timeComboBox.getSelectedItem();

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a date.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO teacherLeave VALUES(?, ?, ?)";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, empId);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Leave Confirmed Successfully!");
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to submit leave application.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new TeacherLeave();
    }
}