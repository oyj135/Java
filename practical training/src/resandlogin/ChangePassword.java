package resandlogin;

import mysql.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ChangePassword extends JFrame {
    private JTextField usernameField;
    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    public ChangePassword() {
        setTitle("修改密码");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        // 用户名输入框
        JLabel usernameLabel = new JLabel("用户名:");
        usernameField = new JTextField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // 原密码输入框
        JLabel oldPasswordLabel = new JLabel("原密码:");
        oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordLabel);
        formPanel.add(oldPasswordField);

        // 新密码输入框
        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);

        // 确认密码输入框
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        // 提交按钮和返回登录按钮面板
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets = new Insets(10, 10, 0, 0);

        // 提交按钮
        JButton submitButton = new JButton("提交");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.equals(confirmPassword)) {
                    if (changePassword(username, oldPassword, newPassword)) {
                        JOptionPane.showMessageDialog(ChangePassword.this, "密码修改成功！");
                    } else {
                        JOptionPane.showMessageDialog(ChangePassword.this, "用户名或原密码错误！");
                    }
                } else {
                    JOptionPane.showMessageDialog(ChangePassword.this, "新密码与确认密码不一致！");
                }
            }
        });
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        buttonPanel.add(submitButton, buttonConstraints);

        // 返回登录按钮
        JButton backButton = new JButton("返回登录");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login login = new Login();
                login.setVisible(true);
            }
        });
        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        buttonPanel.add(backButton, buttonConstraints);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    //更改密码
    private boolean changePassword(String username, String oldPassword, String newPassword) {
        // 连接数据库
        try {
            //获取数据库链接
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // 查询数据库中的用户信息
            String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, oldPassword);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // 更新密码
                String updateQuery = "UPDATE users SET user_password = ? WHERE user_name = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, username);
                int rowsAffected = updateStatement.executeUpdate();

                updateStatement.close();

                if (rowsAffected > 0) {
                    result.close();
                    statement.close();
                    dbConnection.closeConnection();
                    return true;
                }
            }

            result.close();
            statement.close();
            dbConnection.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChangePassword changePassword = new ChangePassword();
                changePassword.setVisible(true);
            }
        });
    }
}