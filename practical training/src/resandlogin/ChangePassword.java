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
        setTitle("�޸�����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        // �û��������
        JLabel usernameLabel = new JLabel("�û���:");
        usernameField = new JTextField();
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // ԭ���������
        JLabel oldPasswordLabel = new JLabel("ԭ����:");
        oldPasswordField = new JPasswordField();
        formPanel.add(oldPasswordLabel);
        formPanel.add(oldPasswordField);

        // �����������
        JLabel newPasswordLabel = new JLabel("������:");
        newPasswordField = new JPasswordField();
        formPanel.add(newPasswordLabel);
        formPanel.add(newPasswordField);

        // ȷ�����������
        JLabel confirmPasswordLabel = new JLabel("ȷ������:");
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        // �ύ��ť�ͷ��ص�¼��ť���
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.insets = new Insets(10, 10, 0, 0);

        // �ύ��ť
        JButton submitButton = new JButton("�ύ");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newPassword.equals(confirmPassword)) {
                    if (changePassword(username, oldPassword, newPassword)) {
                        JOptionPane.showMessageDialog(ChangePassword.this, "�����޸ĳɹ���");
                    } else {
                        JOptionPane.showMessageDialog(ChangePassword.this, "�û�����ԭ�������");
                    }
                } else {
                    JOptionPane.showMessageDialog(ChangePassword.this, "��������ȷ�����벻һ�£�");
                }
            }
        });
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        buttonPanel.add(submitButton, buttonConstraints);

        // ���ص�¼��ť
        JButton backButton = new JButton("���ص�¼");
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

    //��������
    private boolean changePassword(String username, String oldPassword, String newPassword) {
        // �������ݿ�
        try {
            //��ȡ���ݿ�����
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ��ѯ���ݿ��е��û���Ϣ
            String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, oldPassword);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                // ��������
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