package resandlogin;

import mysql.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class RegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField captchaField;
    private JTextField userInputCaptchaField;

    public RegistrationFrame() {
        setTitle("超市管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // 标题面板
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("欢迎注册超市管理系统");
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // 用户名输入框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField();
        usernameField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(usernameField, constraints);

        // 密码输入框
        JLabel passwordLabel = new JLabel("密码:");
        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(passwordField, constraints);

        // 确认密码输入框
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(confirmPasswordLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(confirmPasswordField, constraints);

        // 验证码
        JLabel captchaLabel = new JLabel("验证码:");
        JPanel captchaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        captchaField = new JTextField(10);
        captchaField.setColumns(5);
        JButton refreshButton = new JButton("刷新");
        captchaPanel.add(captchaField);
        captchaPanel.add(refreshButton);

        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(captchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(captchaPanel, constraints);

        refreshCaptcha(); // 生成初始验证码

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCaptcha(); // 刷新验证码
            }
        });

        // 用户输入的验证码
        JLabel userInputCaptchaLabel = new JLabel("输入验证码:");
        userInputCaptchaField = new JTextField();
        userInputCaptchaField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(userInputCaptchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(userInputCaptchaField, constraints);

        // 注册按钮和返回按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("注册");
        JButton backButton = new JButton("返回");
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, constraints);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的信息
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String userInputCaptcha = userInputCaptchaField.getText();

                // 验证用户名、密码和验证码是否符合要求
                if (validateInputs(username, password, confirmPassword) && validateCaptcha(userInputCaptcha)) {
                    // 执行注册逻辑
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "注册成功！");
                        dispose(); // 关闭当前注册界面
                        Login login = new Login();
                        login.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "注册失败，请重试！");
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前注册界面
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }

    private boolean validateInputs(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写所有字段！");
            return false;
        } else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "请填写用户名！");
            return false;
        }else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "请填写密码！");
            return false;
        }else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "请填写确认密码！");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致！");
            return false;
        }

        return true;
    }

    private boolean registerUser(String username, String password) {
        try {
            //获取数据库链接
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // 检查用户名是否已存在
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE user_name = ?");
            stmt.setString(1, username);
            if (stmt.executeQuery().next()) {
                JOptionPane.showMessageDialog(this, "用户名已存在！");
                return false;
            }

            // 执行插入操作
            stmt = connection.prepareStatement("INSERT INTO users (user_name, user_password ,role) VALUES (?, ?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "用户");
            stmt.executeUpdate();

            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refreshCaptcha() {
        // 生成随机四位数字和字母组合的验证码
        String captcha = generateRandomCaptcha(4);
        captchaField.setText(captcha);
    }

    private String generateRandomCaptcha(int length) {
        // 生成随机四位数字和字母组合的验证码
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            captcha.append(characters.charAt(index));
        }
        return captcha.toString();
    }

    private boolean validateCaptcha(String userInputCaptcha) {
        String captcha = captchaField.getText();
        if (userInputCaptcha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入验证码！");
            return false;
        }

        if (!userInputCaptcha.equals(captcha)) {
            JOptionPane.showMessageDialog(this, "验证码错误！");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RegistrationFrame registrationFrame = new RegistrationFrame();
                registrationFrame.setVisible(true);
            }
        });
    }
}
