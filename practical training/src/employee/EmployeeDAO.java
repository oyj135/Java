package employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import mysql.DatabaseConnection;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO() throws SQLException {
        DatabaseConnection dbConnection = new DatabaseConnection();
        connection = dbConnection.getConnection();
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employee");

            while (resultSet.next()) {
                String employeeName = resultSet.getString("employeeName");
                String employeeJob = resultSet.getString("employeeJob");
                String employeePhone = resultSet.getString("employeePhone");
                String employeeIdNumber = resultSet.getString("employeeIdNumber");
                String employeeEmail = resultSet.getString("employeeEmail");

                Employee employee = new Employee(employeeName, employeeJob, employeePhone, employeeIdNumber, employeeEmail);
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public void addEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee (employeeName, employeeJob, employeePhone, employeeIdNumber, employeeEmail) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setString(2, employee.getEmployeeJob());
            preparedStatement.setString(3, employee.getEmployeePhone());
            preparedStatement.setString(4, employee.getEmployeeIdNumber());
            preparedStatement.setString(5, employee.getEmployeeEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employee SET employeeName=?, employeeJob=?, employeePhone=?, employeeIdNumber=?, employeeEmail=? WHERE id=?");
            preparedStatement.setString(1, employee.getEmployeeName());
            preparedStatement.setString(2, employee.getEmployeeJob());
            preparedStatement.setString(3, employee.getEmployeePhone());
            preparedStatement.setString(4, employee.getEmployeeIdNumber());
            preparedStatement.setString(5, employee.getEmployeeEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployee(int employeeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employee WHERE id=?");
            preparedStatement.setInt(1, employeeId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
