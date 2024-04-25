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
        setTitle("��¼");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        // �������
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("��ӭ��¼���й���ϵͳ");
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

        // ��ɫѡ���
        JLabel roleLabel = new JLabel("��ɫ:");
        String[] roles = {"����Ա", "�û�"};
        roleComboBox = new JComboBox<>(roles);
        constraints.gridx = 0;
        constraints.gridy = 2;
        formPanel.add(roleLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(roleComboBox, constraints);

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
        JTextField userInputCaptchaField = new JTextField();
        userInputCaptchaField.setColumns(20);
        constraints.gridx = 0;
        constraints.gridy = 4;
        formPanel.add(userInputCaptchaLabel, constraints);
        constraints.gridx = 1;
        formPanel.add(userInputCaptchaField, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("��¼");
        JButton registerButton = new JButton("ע��");
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
                // ��ȡ�û��������Ϣ
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) roleComboBox.getSelectedItem();
                String userInputCaptcha = userInputCaptchaField.getText().toLowerCase(); // ͳһת��ΪСд
                String captcha = captchaField.getText();

                // ��֤��֤���Ƿ���ȷ
                if (validateCaptcha(userInputCaptcha)) {
                    if (role.equals("����Ա")) {
                        // ִ�й���Ա��¼���߼�
                        if (validateAdminLogin(username, password)) {
                            JOptionPane.showMessageDialog(Login.this, "����Ա��¼�ɹ���");
                            showAdminFrame(); // ��ʾ����Ա����
                        } else {
                            JOptionPane.showMessageDialog(Login.this, "����Ա�û������������");
                        }
                    } else {
                        // ִ���û���¼���߼�
                        if (validateUserLogin(username, password)) {
                            JOptionPane.showMessageDialog(Login.this, "�û���¼�ɹ���");
                            showUserFrame(); // ��ʾ�û�����
                        } else {
                            JOptionPane.showMessageDialog(Login.this, "�û��û������������");
                        }
                    }
                }

                if (username.isEmpty() && password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "����д�����ֶΣ�");
                } else if (username.isEmpty() ) {
                    JOptionPane.showMessageDialog(Login.this, "����д�û�����");
                }else if (password.isEmpty() ) {
                    JOptionPane.showMessageDialog(Login.this, "����д���룡");
                }else if (userInputCaptcha.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this, "��������֤�룡");
                }else if (!userInputCaptcha.equals(captcha)) {
                    JOptionPane.showMessageDialog(Login.this, "��֤�����");
                }




            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ע�ᰴť���߼�
                dispose(); // �رյ�ǰ��¼����
                RegistrationFrame registrationFrame = new RegistrationFrame();
                registrationFrame.setVisible(true);
            }
        });
    }



    private boolean validateAdminLogin(String username, String password) {
        // �������ݿ�
        try {
            //��ȡ���ݿ�����
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // ��ѯ���ݿ��еĹ���Ա�û���������
            String query = "SELECT user_name, user_password FROM users WHERE user_name = ? AND role = '����Ա'";
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
        // �������ݿ�
        try {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Connection connection = dbConnection.getConnection();
            // ��ѯ���ݿ��е��û��û���������
            String query = "SELECT user_name, user_password FROM users WHERE user_name = ? AND role = '�û�'";
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
        // ִ�й���Ա��¼�ɹ�����߼�
        // ��ʾ����Ա����Ĵ���
        dispose(); // �رյ�ǰ��¼����
        AdminFrame adminFrame = new AdminFrame();
        adminFrame.setVisible(true);
    }

    private void showUserFrame() {
        // ִ���û���¼�ɹ�����߼�
        // ��ʾ�û�����Ĵ���
        dispose(); // �رյ�ǰ��¼����
        UserFrame userFrame = new UserFrame();
        userFrame.setVisible(true);
    }

    private void refreshCaptcha() {
        // ���������λ���ֺ���ĸ��ϵ���֤��
        String captcha = generateRandomCaptcha(4);
        captchaField.setText(captcha.toLowerCase()); // ͳһת��ΪСд
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
        String captcha = captchaField.getText().toLowerCase(); // ͳһת��ΪСд
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
