package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Marks extends JInternalFrame {

    public Marks(String rollNo) {
        //'super' call sets the window's title and enables the buttons
        super("Student Mark Sheet for " + rollNo, true, true, true, true);

        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));

        // --- Main Panel with Padding ---
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Header ---
        JLabel heading = new JLabel("X.Y. Institute of Information Technology");
        heading.setFont(new Font("Tahoma", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(heading, gbc);

        JLabel subHeading = new JLabel("Result of Examination");
        subHeading.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridy = 1; gbc.insets = new Insets(5, 5, 20, 5);
        mainPanel.add(subHeading, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // --- Student Info ---
        JLabel rollNoLabel = new JLabel("Roll Number: " + rollNo);
        rollNoLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridy = 2; gbc.gridwidth = 1;
        mainPanel.add(rollNoLabel, gbc);

        JLabel semesterLabel = new JLabel();
        semesterLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        gbc.gridy = 3;
        mainPanel.add(semesterLabel, gbc);

        gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(new JSeparator(), gbc);
        gbc.fill = GridBagConstraints.NONE;

        // --- Marks Table Headers ---
        JLabel subjectHeader = new JLabel("Subject");
        subjectHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1;
        mainPanel.add(subjectHeader, gbc);

        JLabel marksHeader = new JLabel("Marks");
        marksHeader.setFont(new Font("Tahoma", Font.BOLD, 16));
        gbc.gridx = 1; gbc.gridy = 5;
        mainPanel.add(marksHeader, gbc);

        // --- Load Data and Populate UI ---
        // Securely fetch data using a JOIN query
        String query = "SELECT * FROM subject s JOIN marks m ON s.rollNo = m.rollNo AND s.semester = m.semester WHERE s.rollNo = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                semesterLabel.setText("Semester: " + rs.getString("semester"));
                for (int i = 1; i <= 5; i++) {
                    gbc.gridx = 0; gbc.gridy = 6 + i;
                    mainPanel.add(new JLabel(rs.getString("sub" + i)), gbc);

                    gbc.gridx = 1; gbc.gridy = 6 + i;
                    mainPanel.add(new JLabel(rs.getString("mark" + i)), gbc);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No marks found for roll number: " + rollNo, "Not Found", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not fetch marks.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // --- Close Button ---
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        gbc.gridx = 0; gbc.gridy = 12; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER; gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(closeButton, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }
}