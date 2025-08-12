package university.management.system;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable {

    Thread t;

    Splash() {
        // --- UI Component Setup ---
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/first.png"));
        Image i2 = i1.getImage().getScaledInstance(1080, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        add(img);

        setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/AppLogo.png")).getImage());

        // --- Window Setup for Animation ---
        setUndecorated(true);
        setSize(1080, 700);
        setLocationRelativeTo(null);
        setOpacity(0.0f); // Start transparent
        setVisible(true);

        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            // Fade-in Animation
            for (float i = 0.0f; i <= 1.0f; i = i + 0.01f) {
                final float opacity = i;
                SwingUtilities.invokeLater(() -> setOpacity(opacity));
                Thread.sleep(10);
            }
            // Pause
            Thread.sleep(2000);
            // Transition to Login
            SwingUtilities.invokeLater(() -> {
                setVisible(false);
                new Login(); // Calls the Login class
                dispose();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            } catch (Exception e) {
                System.err.println("Failed to initialize LaF.");
            }
            new Splash();
        });
    }
}