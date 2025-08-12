package university.management.system;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class FeeStructure extends JInternalFrame {

    public FeeStructure() {
        //'super' call is required for JInternalFrame
        super("Fee Structure", true, true, true, true);

        setSize(1000, 600);
        setLayout(new BorderLayout());

        // --- Heading ---
        JLabel heading = new JLabel("Fee Structure");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        // --- Table ---
        JTable table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- Load Data ---
        try (ConnectionForSystem c = new ConnectionForSystem()) {
            ResultSet rs = c.statement.executeQuery("SELECT * FROM fee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not load fee data.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}