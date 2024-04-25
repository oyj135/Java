package resandlogin;

import mysql.DatabaseConnection;
import user.AdminFrame;
import user.UserFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JTextField captchaField;


    public Login() {
        setTitle("登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // 标题面板
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("欢迎登录超市管理系统");
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

        // 角色选择框
        JLabel roleLabel = new JLabel("角色:");
        String[] roles = {"管理员", "用户"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(roleLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(roleComboBox, constraints);

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
        JTextField userInputCaptchaField = new JTextField();
        userInputCaptchaField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(userInputCaptchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(userInputCaptchaField, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, constraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取用户输入的信息
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();
                String userInputCaptcha = userInputCaptchaField.getText().toLowerCase(); // 统一转换为小写
                String captcha = captchaField.getText();

                // 验证验证码是否正确
                if (validateCaptcha(userInputCaptcha)) {
                    if (role.equals("管理员")) {
                        // 执行管理员登录的逻辑
                        if (validateAdminLogin(username, password)) {
                            JOptionPane.showMessageDialog(Login.this, "管理员登录成功！");
                            showAdminFrame(); // 显示管理员界面
                        } else {
                            JOptionPane.showMessageDialog(Login.this, "管理员用户名或密码错误！");
                        }
                    } else {
                        // 执行用户登录的逻辑
                        if (validateUserLogin(username, password)) {
                            JOptionPane.showMessageDialog(Login.this, "用户登录成功！");
                            showUserFrame(); // 显示用户界面
                        } else {
                            JOptionPane.showMessageDialog(Login.this, "用户用户名或密码错误！");
                        }
                    }
                }

                if (username.isEmpty() && password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "请填写所有字段！");
                } else if (username.isEmpty() ) {
                    JOptionPane.showMessageDialog(Login.this, "请填写用户名！");
                }else if (password.isEmpty() ) {
                    JOptionPane.showMessageDialog(Login.this, "请填写密码！");
                }else if (userInputCaptcha.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "请输入验证码！");
                }else if (!userInputCaptcha.equals(captcha)) {
                    JOptionPane.showMessageDialog(Login.this, "验证码错误！");
                }




            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 注册按钮的逻辑
                dispose(); // 关闭当前登录界面
                RegistrationFrame registrationFrame = new RegistrationFrame();
                registrationFrame.setVisible(true);
            }
        });
    }



    private boolean validateAdminLogin(String username, String password) {
        // 连接数据库
        try {
            //获取数据库链接
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // 查询数据库中的管理员用户名和密码
            String query = "SELECT user_name, user_password FROM users WHERE user_name = ? AND role = '管理员'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String dbUsername = result.getString("user_name");
                String dbPassword = result.getString("user_password");

                if (password.equals(dbPassword)) {
                    return true;
                }
            }

            result.close();
            statement.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean validateUserLogin(String username, String password) {
        // 连接数据库
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // 查询数据库中的用户用户名和密码
            String query = "SELECT user_name, user_password FROM users WHERE user_name = ? AND role = '用户'";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String dbUsername = result.getString("user_name");
                String dbPassword = result.getString("user_password");

                if (password.equals(dbPassword)) {
                    return true;
                }
            }

            result.close();
            statement.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void showAdminFrame() {
        // 执行管理员登录成功后的逻辑
        // 显示管理员界面的代码
        dispose(); // 关闭当前登录界面
        AdminFrame adminFrame = new AdminFrame();
        adminFrame.setVisible(true);
    }

    private void showUserFrame() {
        // 执行用户登录成功后的逻辑
        // 显示用户界面的代码
        dispose(); // 关闭当前登录界面
        UserFrame userFrame = new UserFrame();
        userFrame.setVisible(true);
    }

    private void refreshCaptcha() {
        // 生成随机四位数字和字母组合的验证码
        String captcha = generateRandomCaptcha(4);
        captchaField.setText(captcha.toLowerCase()); // 统一转换为小写
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
        String captcha = captchaField.getText().toLowerCase(); // 统一转换为小写
        return userInputCaptcha.equals(captcha);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }
}
