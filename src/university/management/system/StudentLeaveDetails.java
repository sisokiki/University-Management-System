package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentLeaveDetails extends JInternalFrame {

    private JComboBox<String> rollNoComboBox;
    private JTable table;
    private JButton searchButton, printButton, cancelButton;

    public StudentLeaveDetails() {
        //'super' call is required for JInternalFrame
        super("View Student Leave Details", true, true, true, true);

        setSize(900, 600);
        setLayout(new BorderLayout());

        // --- Top Panel for Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search by Roll Number:"));

        // Using JComboBox instead of Choice
        rollNoComboBox = new JComboBox<>();
        rollNoComboBox.setPreferredSize(new Dimension(150, 25));
        topPanel.add(rollNoComboBox);

        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT rollNo FROM student")) {
            while (rs.next()) {
                rollNoComboBox.addItem(rs.getString("rollNo"));
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
        searchButton.addActionListener(e -> loadLeaveData((String) rollNoComboBox.getSelectedItem()));

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
     * @param rollNo The ID to search for. If null, all leave records are loaded.
     */
    private void loadLeaveData(String rollNo) {
        String query = (rollNo == null) ? "SELECT * FROM studentLeave" : "SELECT * FROM studentLeave WHERE rollNo = ?";

        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            if (rollNo != null) {
                pstmt.setString(1, rollNo);
            }
            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load leave data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}