import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


class SignupFrame1 extends JFrame implements ActionListener {

    Container container = getContentPane();

    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    JButton signupButton = new JButton("SIGNUP");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JLabel SignupLabel = new JLabel("New User?");
    ImageIcon backgroundIcon = new ImageIcon("Login.png");
    JLabel backgroundLabel = new JLabel(backgroundIcon);


    SignupFrame1() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

        public void setLocationAndSize() {
            userLabel.setBounds(50, 157, 120, 40);
            passwordLabel.setBounds(50, 227, 120, 40);
            userTextField.setBounds(180, 150, 200, 40);
            passwordField.setBounds(180, 220, 200, 40);
            showPassword.setBounds(180, 280, 150, 30);
            showPassword.setBounds(180, 280, 150, 30);
            loginButton.setBounds(50, 340, 120, 40);
            resetButton.setBounds(260, 340, 120, 40);
            SignupLabel.setBounds(120, 400, 200, 20);
            signupButton.setBounds(230, 400, 100, 30);
            backgroundLabel .setBounds(0, 0, 500, 600);
        }




    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton); // login button added before signup button
        container.add(SignupLabel);
        container.add(signupButton); // signup button added after login button
        container.add(resetButton);
        container.add(backgroundLabel, BorderLayout.CENTER);
        container.add(backgroundLabel, BorderLayout.PAGE_START);
    }


    public void addActionEvent() {
        signupButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String pwdText = passwordField.getText();
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException(ex);
            }
            byte[] hash = messageDigest.digest(pwdText.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);

            for (byte element : hash) {
                String hex = Integer.toHexString(0xff & element);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            String url = "jdbc:mysql://localhost:3306/UserLogin";
            String username = "root";
            String password = "sriirazz";
            String query = "SELECT UserPassword FROM UserLogin WHERE UserId = ?";
            boolean isAuthenticated = false;

            try (Connection con = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, userText);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String storedPassword = rs.getString("UserPassword");
                    if (storedPassword.equals("")){
                        isAuthenticated=false;
                }

                    else if (storedPassword.equals(hexString.toString())) {
                        isAuthenticated = true;
                    }
                }


            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if ( isAuthenticated) {
                JOptionPane.showMessageDialog(this, "Successful Login Redirecting to Home Page");
                this.setVisible(false);
                Myframe c=new Myframe();
                c.setVisible(true);
            } else {

                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }

        }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        else if (e.getSource() == signupButton) {
            // this.setVisible(false);
            SignUpPage1 var =new SignUpPage1();
            var.setVisible(true);


        }
    }

}


    public class Login1 {
    public static void main(String[] args) {
        SignupFrame1 frame = new SignupFrame1();
        frame.setTitle("Aikhyaam Login");
        ImageIcon image = new ImageIcon("C:/Users/sriir/study material/sem-4/cse 310 java/Chat_App/img.jpg");
        frame.setIconImage(image.getImage());
        frame.setVisible(true);
        frame.setBounds(0, 0, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}