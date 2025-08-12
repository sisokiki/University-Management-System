package university.management.system;

import javax.swing.*;
import java.awt.*;

public class About extends JInternalFrame {

    public About() {
        super("About", true, true, true, true);
        setSize(700, 500);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/about.png"));
        Image i2 = i1.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(i2));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the image
        mainPanel.add(imageLabel);

        JLabel heading = new JLabel("University Management System");
        heading.setFont(new Font("Tahoma", Font.BOLD, 28));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(heading);

        JLabel versionLabel = new JLabel("Version 1.0");
        versionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(versionLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JLabel developedBy = new JLabel("Developed By");
        developedBy.setFont(new Font("Tahoma", Font.BOLD, 20));
        developedBy.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(developedBy);

        JLabel name = new JLabel("Souvik Mishra");
        name.setFont(new Font("Tahoma", Font.PLAIN, 18));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(name);

        JLabel contact = new JLabel("Contact: bitmishra00054@gmail.com");
        contact.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contact.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(contact);
    }
}