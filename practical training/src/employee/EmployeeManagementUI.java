package employee;

import user.AdminFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private EmployeeDAO employeeDAO;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField nameField;
    private JTextField jobField;
    private JTextField phoneField;
    private JTextField idNumberField;
    private JTextField emailField;

    public EmployeeManagementUI() throws SQLException {
        employeeDAO = new EmployeeDAO();

        setTitle("Ա������");
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("����");
        tableModel.addColumn("ְλ");
        tableModel.addColumn("�ֻ���");
        tableModel.addColumn("֤����");
        tableModel.addColumn("����");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("����:");
        nameField = new JTextField(10);

        JLabel jobLabel = new JLabel("ְλ:");
        jobField = new JTextField(10);

        JLabel phoneLabel = new JLabel("�ֻ���:");
        phoneField = new JTextField(10);

        JLabel idNumberLabel = new JLabel("֤����:");
        idNumberField = new JTextField(10);

        JLabel emailLabel = new JLabel("����:");
        emailField = new JTextField(10);

        JButton addButton = new JButton("���");
        JButton updateButton = new JButton("�޸�");
        JButton deleteButton = new JButton("ɾ��");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(jobLabel);
        panel.add(jobField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(idNumberLabel);
        panel.add(idNumberField);
        panel.add(emailLabel);
        panel.add(emailField);

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.NORTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        JButton backButton = new JButton("����");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new AdminFrame().setVisible(true);
            }
        });
        panel.add(backButton);

        add(panel, BorderLayout.NORTH);

        loadEmployees();

        setSize(1500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadEmployees() {
        tableModel.setRowCount(0);
        List<Employee> employees = employeeDAO.getAllEmployees();
        for (Employee employee : employees) {
            Object[] rowData = {employee.getEmployeeName(), employee.getEmployeeJob(), employee.getEmployeePhone(), employee.getEmployeeIdNumber(), employee.getEmployeeEmail()};
            tableModel.addRow(rowData);
        }
    }

    private void addEmployee() {
        String name = nameField.getText();
        String job = jobField.getText();
        String phone = phoneField.getText();
        String idNumber = idNumberField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || job.isEmpty() || phone.isEmpty() || idNumber.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "����д�����ֶ�");
            return;
        }

        Employee employee = new Employee(name, job, phone, idNumber, email);
        employeeDAO.addEmployee(employee);

        clearFields();
        loadEmployees();
    }

    private void updateEmployee() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            String name = nameField.getText();
            String job = jobField.getText();
            String phone = phoneField.getText();
            String idNumber = idNumberField.getText();
            String email = emailField.getText();

            if (name.isEmpty() || job.isEmpty() || phone.isEmpty() || idNumber.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "����д�����ֶ�");
                return;
            }

            Employee employee = new Employee(name, job, phone, idNumber, email);
            employeeDAO.updateEmployee(employee);

            clearFields();
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫ�޸ĵ�Ա��");
        }
    }

    private void deleteEmployee() {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            int employeeId = (int) table.getValueAt(selectedRow, 0);
            employeeDAO.deleteEmployee(employeeId);

            clearFields();
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "��ѡ��Ҫɾ����Ա��");
        }
    }

    private void clearFields() {
        nameField.setText("");
        jobField.setText("");
        phoneField.setText("");
        idNumberField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new EmployeeManagementUI();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
