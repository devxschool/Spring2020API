package models;

import db.DBUtils;
import org.apache.commons.dbutils.BeanProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// POJO, Beans
//Lombok
public class Employee{
    int employeeNumber;
    String lastName;
    String firstName;
    String extension;
    String email;
    String officeCode;
    int reportsTo;
    String jobTitle;

    public Employee(){};

    public Employee(ResultSet rs) throws SQLException {
        BeanProcessor processor = new BeanProcessor();
        processor.populateBean(rs, this); // populates bean from result set
    }

    public Employee(int employeeNumber, String lastName, String firstName, String extension, String email, String officeCode, int reportsTo, String jobTitle) {
        this.employeeNumber = employeeNumber;// rs.getInt(employeeNumber)
        this.lastName = lastName;
        this.firstName = firstName;
        this.extension = extension;
        this.email = email;
        this.officeCode = officeCode;
        this.reportsTo = reportsTo;
        this.jobTitle = jobTitle;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public int getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(int reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public static List<Employee> getAll() throws SQLException {
        String query = "SELECT * FROM employees";
        ResultSet rs = DBUtils.query(query);
        List<Employee> employees = new ArrayList<>();

        while (rs.next()) {
            employees.add(new Employee(rs));
        }
        return employees;
    }

        // getBy("employeeId", 101)
    public static Employee getBy(String field, Object value) throws SQLException {
        // Select * FROM employees WHERE employeeId = 101;
        String query = "SELECT * FROM employees WHERE " + field + " = ?";
        ResultSet rs = DBUtils.query(query, value);
        if(rs.next()) return new Employee(rs);
        return null;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeNumber=" + employeeNumber +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", extension='" + extension + '\'' +
                ", email='" + email + '\'' +
                ", officeCode='" + officeCode + '\'' +
                ", reportsTo=" + reportsTo +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }

    /**
     *
     *
     *
     */
}
