package employee;

public class Employee {
    private String employeeName;
    private String employeeJob;
    private String employeePhone;
    private String employeeIdNumber;
    private String employeeEmail;

    public Employee(String employeeName, String employeeJob, String employeePhone, String employeeIdNumber, String employeeEmail) {
        this.employeeName = employeeName;
        this.employeeJob = employeeJob;
        this.employeePhone = employeePhone;
        this.employeeIdNumber = employeeIdNumber;
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeJob() {
        return employeeJob;
    }

    public void setEmployeeJob(String employeeJob) {
        this.employeeJob = employeeJob;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeIdNumber() {
        return employeeIdNumber;
    }

    public void setEmployeeIdNumber(String employeeIdNumber) {
        this.employeeIdNumber = employeeIdNumber;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeName='" + employeeName + '\'' +
                ", employeeJob='" + employeeJob + '\'' +
                ", employeePhone='" + employeePhone + '\'' +
                ", employeeIdNumber='" + employeeIdNumber + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                '}';
    }
}
