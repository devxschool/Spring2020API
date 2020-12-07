package models;

import db.DBUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
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

    public static List<Employee> getAllWithoutId() throws SQLException {
        String query = "SELECT firstName, lastName... FROM employees";
        ResultSet rs = DBUtils.query(query);
        // toBeanList() converts a result set into a list of objects of a specified class by
        // populating class variables with the data from Result set
        return getProcessor().toBeanList(rs, Employee.class);
    }

    // getBy("employeeId", 101)
    // getBy("firstName", "James")
    // Emloyee.getBy("firstName", "Joan")
    public static Employee getBy(String field, Object value) {
        String query = "SELECT * FROM employees WHERE " + field + " = ?";// add our value from field param into our query
        try(ResultSet rs = DBUtils.query(query, value)){ // (SELECT * FROM employees WHERE firstName = 'Joan')){
            // Select * FROM employees WHERE employeeId = 101;
            // firstName = "Joan"
            // (SELECT * FROM employees WHERE firstName = ?)
            if(rs.next()) return new Employee(rs);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

   // {"firstName", "lastName"}
   // SELECT * FROM employees WHERE firstName = ? AND lastName = ?;
    public static Employee getBy(List<String> fieldsToUseInWhere, Object... values) {
        if(fieldsToUseInWhere.size() != values.length) throw new IllegalArgumentException("Number of arguments don't match");
        String query = "SELECT * FROM employees WHERE ";

        for (int i = 0; i < fieldsToUseInWhere.size(); i++){
            if(i == 0){
                query += fieldsToUseInWhere.get(i) + " =  ? \n";
            }else{
                query += "AND " + fieldsToUseInWhere.get(i) + " = ? \n";
            }
        }
        try(ResultSet rs = DBUtils.query(query, values)){// Get result from query build into result set)
            if(!rs.next()) return null;// if Result Set is empty return null
            return getProcessor().toBean(rs, Employee.class);// else Map a row from result set into new object of employee
        }catch (SQLException e){
            e.printStackTrace();
        }
       return null;
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
        if(extension == null) this.extension = "";
        else this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode == null ? "" : officeCode;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return reportsTo == employee.reportsTo &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(extension, employee.extension) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(officeCode, employee.officeCode) &&
                Objects.equals(jobTitle, employee.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, extension, email, officeCode, reportsTo, jobTitle);
    }
}
