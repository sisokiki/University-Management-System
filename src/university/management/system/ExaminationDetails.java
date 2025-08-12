package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

public class ExaminationDetails extends JInternalFrame {
    private JTextField searchField;
    private JButton resultButton, backButton;
    private JTable table;

    public ExaminationDetails() {
        super("Check Examination Results", true, true, true, true);

        setSize(1000, 500);
        setLayout(new BorderLayout(10, 10));

        // --- Top Panel for Search Controls ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        topPanel.add(new JLabel("Enter Roll Number to Search:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        resultButton = new JButton("Show Result");
        topPanel.add(resultButton);
        backButton = new JButton("Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Table of Students ---
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- Load Data into Table ---
        try (ConnectionForSystem c = new ConnectionForSystem()) {
            ResultSet rs = c.statement.executeQuery("SELECT name, fatherName, rollNo, course, branch FROM student");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load student data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        // --- Action Listeners ---
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                searchField.setText(table.getModel().getValueAt(row, 2).toString());
            }
        });

        resultButton.addActionListener(e -> {
            String rollNo = searchField.getText();
            if (!rollNo.isEmpty()) {
                JDesktopPane desktop = getDesktopPane();
                if (desktop != null) {
                    Marks marksFrame = new Marks(rollNo);
                    desktop.add(marksFrame);
                    marksFrame.setVisible(true);

                    // --- CENTERING LOGIC ---
                    Dimension desktopSize = desktop.getSize();
                    Dimension frameSize = marksFrame.getSize();
                    marksFrame.setLocation((desktopSize.width - frameSize.width) / 2, (desktopSize.height - frameSize.height) / 2);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter or select a roll number.", "Input Required", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> dispose());
    }
}