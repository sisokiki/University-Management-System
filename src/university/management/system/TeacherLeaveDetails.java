package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// CHANGE 1: It now extends JInternalFrame
public class TeacherLeaveDetails extends JInternalFrame {

    private JComboBox<String> empIdComboBox;
    private JTable table;
    private JButton searchButton, printButton, cancelButton;

    public TeacherLeaveDetails() {
        // 'super' call is required for JInternalFrame
        super("View Faculty Leave Details", true, true, true, true);

        setSize(900, 600);
        setLayout(new BorderLayout());

        // --- Top Panel for Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search by Employee ID:"));

        empIdComboBox = new JComboBox<>();
        empIdComboBox.setPreferredSize(new Dimension(150, 25));
        topPanel.add(empIdComboBox);

        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT empId FROM teacher")) {
            while (rs.next()) {
                empIdComboBox.addItem(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchButton = new JButton("Search");
        printButton = new JButton("Print");
        cancelButton = new JButton("Cancel");
        topPanel.add(searchButton);
        topPanel.add(printButton);
        topPanel.add(cancelButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Table ---
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadLeaveData(null); // Load all data initially

        // --- Action Listeners ---
        searchButton.addActionListener(e -> loadLeaveData((String) empIdComboBox.getSelectedItem()));

        printButton.addActionListener(e -> {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cancelButton.addActionListener(e -> dispose()); // dispose() closes the internal frame
    }

    /**
     * Loads leave data into the JTable.
     * @param employeeId The ID to search for. If null, all leave records are loaded.
     */
    private void loadLeaveData(String employeeId) {
        // Using PreparedStatement for security
        String query = (employeeId == null) ? "SELECT * FROM teacherLeave" : "SELECT * FROM teacherLeave WHERE empId = ?";

        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            if (employeeId != null) {
                pstmt.setString(1, employeeId);
            }
            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load leave data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}