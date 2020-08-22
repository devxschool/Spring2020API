package models;

import db.DBUtils;
import org.apache.commons.dbutils.BeanProcessor;

import javax.print.DocFlavor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//POJO, Beans
//Lombok
public class Employee extends BaseModel{
    private int employeeNumber;
    private String lastName;
    private String firstName;
    private String extension;
    private String email;
    private String officeCode;
    private int reportsTo;
    private String jobTitle;


    /**
     * INSERT INTO employees VALUES(?, ?)
     * Employee{101, "Bond", "James", null, null, 100, 105, "Developer"}
     * {"firstName", "extension"};
     */

    // List of properties names in the sequence as it is in the DB table;
    private static final String[] properties = {"employeeNumber", "lastName", "firstName", "extension", "email", "officeCode", "reportsTo", "jobTitle"};
    public Employee(){};

    //Constructor that will turn a row from a ResultSet into the object of this class
    public Employee(ResultSet rs) throws SQLException {
        // BeanProcessor comes form Apache DB utils
        getProcessor().populateBean(rs, this); // populates bean from result set
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

    public boolean insertIntoDB() throws SQLException {
        String query = "INSERT INTO employees VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
        return DBUtils.insertBean(query, this, properties);
    }

    public static List<Employee> getAll() throws SQLException {
        String query = "SELECT * FROM employees";
        ResultSet rs = DBUtils.query(query);
        // toBeanList() converts a result set into a list of objects of a specified class by
        // populating class variables with the data from Result set
        return getProcessor().toBeanList(rs, Employee.class);
//        List<Employee> employees = new ArrayList<>();
//
//        while (rs.next()) {
//            employees.add(new Employee(rs));
//        }
//        return employees;
    }

    // getBy("employeeId", 101)
    // getBy("firstName", "James")
    // Emloyee.getBy("firstName", "Joan")
    public static Employee getBy(String field, Object value) throws SQLException {
        // Select * FROM employees WHERE employeeId = 101;
        // firstName = "Joan"
        String query = "SELECT * FROM employees WHERE " + field + " = ?";// add our value from field param into our query
        // (SELECT * FROM employees WHERE firstName = ?)
        ResultSet rs = DBUtils.query(query, value); // (SELECT * FROM employees WHERE firstName = 'Joan')
        if(rs.next()) return new Employee(rs);
        return null;
    }

    public static Employee getBy(List<String> fieldsToUseInWhere, Object... values) throws SQLException {
        if(fieldsToUseInWhere.size() != values.length) throw new IllegalArgumentException("Number of arguments don't match");
        String query = "SELECT * FROM employees WHERE ";

        for (int i = 0; i < fieldsToUseInWhere.size(); i++){
            if(i == 0){
                query += fieldsToUseInWhere.get(i) + " =  ? \n";
            }else{
                query += "AND " + fieldsToUseInWhere.get(i) + " = ? \n";
            }
        }
        ResultSet rs = DBUtils.query(query, values);// Get result from query build into result set
        if(!rs.next()) return null;// if Result Set is empty return null
        return getProcessor().toBean(rs, Employee.class);// else Map a row from result set into new object of employee
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
