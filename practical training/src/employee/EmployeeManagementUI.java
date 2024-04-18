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

        setTitle("员工管理");
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        tableModel.addColumn("姓名");
        tableModel.addColumn("职位");
        tableModel.addColumn("手机号");
        tableModel.addColumn("证件号");
        tableModel.addColumn("邮箱");

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel nameLabel = new JLabel("姓名:");
        nameField = new JTextField(10);

        JLabel jobLabel = new JLabel("职位:");
        jobField = new JTextField(10);

        JLabel phoneLabel = new JLabel("手机号:");
        phoneField = new JTextField(10);

        JLabel idNumberLabel = new JLabel("证件号:");
        idNumberField = new JTextField(10);

        JLabel emailLabel = new JLabel("邮箱:");
        emailField = new JTextField(10);

        JButton addButton = new JButton("添加");
        JButton updateButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");

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

        JButton backButton = new JButton("返回");
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
            JOptionPane.showMessageDialog(this, "请填写所有字段");
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
                JOptionPane.showMessageDialog(this, "请填写所有字段");
                return;
            }

            Employee employee = new Employee(name, job, phone, idNumber, email);
            employeeDAO.updateEmployee(employee);

            clearFields();
            loadEmployees();
        } else {
            JOptionPane.showMessageDialog(this, "请选择要修改的员工");
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
            JOptionPane.showMessageDialog(this, "请选择要删除的员工");
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
