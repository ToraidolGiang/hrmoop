package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.border.Border;

public class HRLoginSystem extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel leftPanel, rightPanel;
    
    public HRLoginSystem() {
        setTitle("HR System Login");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        
        // Left Panel (Welcome message with blue background)
        leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Blue background
                g2d.setColor(new Color(0, 174, 239)); // Bright blue
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Text
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial", Font.BOLD, 36));
                g2d.drawString("WELCOME", 50, 180);
                
                g2d.setFont(new Font("Arial", Font.PLAIN, 24));
                g2d.drawString("To the best HR", 50, 240);
            }
        };
        
        // Right Panel (Login form)
        rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBackground(Color.WHITE);

        // Username
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setBounds(70, 170, 100, 20);
        usernameLabel.setForeground(Color.GRAY);
        rightPanel.add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setBounds(70, 195, 260, 35);
        usernameField.setBackground(new Color(245, 245, 245));
        rightPanel.add(usernameField);
        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(70, 240, 100, 20);
        passwordLabel.setForeground(Color.GRAY);
        rightPanel.add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(70, 265, 260, 30);
        rightPanel.add(passwordField);
        
        // Login Button
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(70, 330, 260, 40);
        loginButton.setBackground(new Color(77, 208, 255)); // Light blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                // Here you would typically authenticate the user
                JOptionPane.showMessageDialog(null, "Login attempt with username: " + username);
            }
        });
        rightPanel.add(loginButton);
        
        // Close button (X)
        JButton closeButton = new JButton("X");
        closeButton.setBounds(350, 10, 30, 30);
        closeButton.setBackground(Color.WHITE);
        closeButton.setForeground(Color.BLACK);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        rightPanel.add(closeButton);
        
        // Add panels to frame
        add(leftPanel);
        add(rightPanel);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HRLoginSystem();
            }
        });
    }
}
