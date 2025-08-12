package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDetails extends JInternalFrame {

    private JComboBox<String> studentIdComboBox;
    private JTable table;
    private JButton searchButton, printButton, updateButton, addButton, cancelButton;

    public StudentDetails() {
        //'super' call is required for JInternalFrame
        super("View Student Details", true, true, true, true);

        setSize(1000, 600);
        setLayout(new BorderLayout());

        // --- Top Panel for Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search by Roll Number:"));
        studentIdComboBox = new JComboBox<>();
        studentIdComboBox.setPreferredSize(new Dimension(150, 25));
        topPanel.add(studentIdComboBox);

        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT rollNo FROM student")) {
            while (rs.next()) {
                studentIdComboBox.addItem(rs.getString("rollNo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchButton = new JButton("Search");
        printButton = new JButton("Print");
        updateButton = new JButton("Update");
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        topPanel.add(searchButton);
        topPanel.add(printButton);
        topPanel.add(updateButton);
        topPanel.add(addButton);
        topPanel.add(cancelButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Table ---
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadTableData(null); // Load all data initially

        // --- Action Listeners ---
        searchButton.addActionListener(e -> loadTableData((String) studentIdComboBox.getSelectedItem()));

        printButton.addActionListener(e -> {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Updated logic to open other internal frames
        updateButton.addActionListener(e -> openNewFrame(new UpdateStudent()));
        addButton.addActionListener(e -> openNewFrame(new AddStudent()));

        cancelButton.addActionListener(e -> dispose()); // dispose() closes the internal frame
    }

    /**
     * Helper method to open a new JInternalFrame on the parent desktop.
     * @param frame The JInternalFrame to open.
     */
    private void openNewFrame(JInternalFrame frame) {
        JDesktopPane desktop = getDesktopPane();
        if (desktop != null) {
            desktop.add(frame);
            frame.setVisible(true);
        }
    }

    /**
     * Loads data into the JTable.
     * @param rollNo The Roll Number to search for. If null, all students are loaded.
     */
    private void loadTableData(String rollNo) {
        String query = (rollNo == null) ? "SELECT * FROM student" : "SELECT * FROM student WHERE rollno = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            if (rollNo != null) {
                pstmt.setString(1, rollNo);
            }
            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load student data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new StudentDetails();
    }
}