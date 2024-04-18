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
        setTitle("���й���ϵͳ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // �������
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("��ӭע�ᳬ�й���ϵͳ");
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        mainPanel.add(formPanel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        // �û��������
        JLabel usernameLabel = new JLabel("�û���:");
        usernameField = new JTextField();
        usernameField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(usernameLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(usernameField, constraints);

        // ���������
        JLabel passwordLabel = new JLabel("����:");
        passwordField = new JPasswordField();
        passwordField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 1;
        formPanel.add(passwordLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(passwordField, constraints);

        // ȷ�����������
        JLabel confirmPasswordLabel = new JLabel("ȷ������:");
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(confirmPasswordLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(confirmPasswordField, constraints);

        // ��֤��
        JLabel captchaLabel = new JLabel("��֤��:");
        JPanel captchaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        captchaField = new JTextField(10);
        captchaField.setColumns(5);
        JButton refreshButton = new JButton("ˢ��");
        captchaPanel.add(captchaField);
        captchaPanel.add(refreshButton);

        constraints.gridx = 0;
        constraints.gridy = 3;
        formPanel.add(captchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(captchaPanel, constraints);

        refreshCaptcha(); // ���ɳ�ʼ��֤��

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshCaptcha(); // ˢ����֤��
            }
        });

        // �û��������֤��
        JLabel userInputCaptchaLabel = new JLabel("������֤��:");
        userInputCaptchaField = new JTextField();
        userInputCaptchaField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(userInputCaptchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(userInputCaptchaField, constraints);

        // ע�ᰴť�ͷ��ذ�ť���
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("ע��");
        JButton backButton = new JButton("����");
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
                // ��ȡ�û��������Ϣ
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String userInputCaptcha = userInputCaptchaField.getText();

                // ��֤�û������������֤���Ƿ����Ҫ��
                if (validateInputs(username, password, confirmPassword) && validateCaptcha(userInputCaptcha)) {
                    // ִ��ע���߼�
                    if (registerUser(username, password)) {
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "ע��ɹ���");
                        dispose(); // �رյ�ǰע�����
                        Login login = new Login();
                        login.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(RegistrationFrame.this, "ע��ʧ�ܣ������ԣ�");
                    }
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // �رյ�ǰע�����
                Login login = new Login();
                login.setVisible(true);
            }
        });
    }

    private boolean validateInputs(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "����д�����ֶΣ�");
            return false;
        } else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "����д�û�����");
            return false;
        }else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "����д���룡");
            return false;
        }else if (password.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "����дȷ�����룡");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "������������벻һ�£�");
            return false;
        }

        return true;
    }

    private boolean registerUser(String username, String password) {
        try {
            //��ȡ���ݿ�����
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();

            // ����û����Ƿ��Ѵ���
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM users WHERE user_name = ?");
            stmt.setString(1, username);
            if (stmt.executeQuery().next()) {
                JOptionPane.showMessageDialog(this, "�û����Ѵ��ڣ�");
                return false;
            }

            // ִ�в������
            stmt = connection.prepareStatement("INSERT INTO users (user_name, user_password ,role) VALUES (?, ?,?)");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, "�û�");
            stmt.executeUpdate();

            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void refreshCaptcha() {
        // ���������λ���ֺ���ĸ��ϵ���֤��
        String captcha = generateRandomCaptcha(4);
        captchaField.setText(captcha);
    }

    private String generateRandomCaptcha(int length) {
        // ���������λ���ֺ���ĸ��ϵ���֤��
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
            JOptionPane.showMessageDialog(this, "��������֤�룡");
            return false;
        }

        if (!userInputCaptcha.equals(captcha)) {
            JOptionPane.showMessageDialog(this, "��֤�����");
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
