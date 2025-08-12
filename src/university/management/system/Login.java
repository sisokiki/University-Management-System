package university.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    private JTextField textFieldName;
    private JPasswordField passwordField;

    Login() {
        super("Login Page");
        setIconImage(new ImageIcon(ClassLoader.getSystemResource("icons/AppLogo.png")).getImage());
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- 1. Layered Pane for Stacking Components ---
        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane);

        // --- 2. Background Image (Bottom Layer) ---
        ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/loginback.png"));
        Image bgImg = bgIcon.getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(bgImg));
        backgroundLabel.setBounds(0, 0, 1000, 600);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        // --- 3. Character Illustration (Middle Layer) ---
        ImageIcon personIcon = new ImageIcon(ClassLoader.getSystemResource("icons/second.png"));
        JLabel personLabel = new JLabel(personIcon);
        // Adjust the size and position of the character image as needed
        personLabel.setBounds(300, 50, personIcon.getIconWidth(), personIcon.getIconHeight());
        layeredPane.add(personLabel, JLayeredPane.PALETTE_LAYER);

        // --- 4. Login Form (Top Layer) ---
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setOpaque(true);
        loginPanel.setBackground(new Color(0, 0, 0, 120)); // Semi-transparent black
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        textFieldName = new JTextField(15);
        loginPanel.add(textFieldName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        loginPanel.add(buttonPanel, gbc);

        loginPanel.setBounds(325, 380, 350, 180);
        layeredPane.add(loginPanel, JLayeredPane.MODAL_LAYER);

        // --- Action Listeners ---
        loginButton.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());
        backButton.addActionListener(e -> System.exit(0));

        setResizable(false);
        setVisible(true);
    }

    private void performLogin() {
        String username = textFieldName.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (ConnectionForSystem c = new ConnectionForSystem();
             PreparedStatement pstmt = c.connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    setVisible(false);
                    new Main();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            java.util.Arrays.fill(passwordChars, ' ');
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}