package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherDetails extends JInternalFrame {

    private JComboBox<String> teacherIdComboBox;
    private JTable table;
    private JButton searchButton, printButton, updateButton, addButton, cancelButton;

    public TeacherDetails() {
        // 'super' call is required for JInternalFrame
        super("View Teacher Details", true, true, true, true);

        setSize(1000, 600);
        setLayout(new BorderLayout());

        // --- Top Panel for Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(new JLabel("Search by Employee ID:"));
        teacherIdComboBox = new JComboBox<>();
        teacherIdComboBox.setPreferredSize(new Dimension(150, 25));
        topPanel.add(teacherIdComboBox);

        try (ConnectionForSystem c = new ConnectionForSystem();
             ResultSet rs = c.statement.executeQuery("SELECT empId FROM teacher")) {
            while (rs.next()) {
                teacherIdComboBox.addItem(rs.getString("empId"));
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
        searchButton.addActionListener(e -> loadTableData((String) teacherIdComboBox.getSelectedItem()));

        printButton.addActionListener(e -> {
            try {
                table.print();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        updateButton.addActionListener(e -> openNewFrame(new UpdateTeacher()));
        addButton.addActionListener(e -> openNewFrame(new AddFaculty()));

        cancelButton.addActionListener(e -> dispose()); // dispose() closes the internal frame
    }

    private void openNewFrame(JInternalFrame frame) {
        JDesktopPane desktop = getDesktopPane();
        if (desktop != null) {
            desktop.add(frame);
            frame.setVisible(true);
        }
    }

    private void loadTableData(String employeeId) {
        String query = (employeeId == null) ? "SELECT * FROM teacher" : "SELECT * FROM teacher WHERE empId = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {

            if (employeeId != null) {
                pstmt.setString(1, employeeId);
            }
            ResultSet rs = pstmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load teacher data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new TeacherDetails();
    }
}