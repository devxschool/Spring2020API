package test;

import config.Config;
import db.DBUtils;
import models.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class TestConnection {
    @Test
    public void testConf() {
        Assert.assertTrue(Config.getProperty("user").equals("student"));
    }


    @Test
    public void testConnection() {
        ResultSet rs = null;
        try {
            DBUtils.open("classicmodels"); // open new connection
            rs = DBUtils.query("SELECT * FROM employees"); // query the database
            Assert.assertTrue(rs.next()); // Verify if the result set contains at leas 1 row
            System.out.println(rs.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close(); // close result st
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBUtils.close();// close the connection
        }

    }

    @Test
    public void testParams() {
        try {
            DBUtils.open();
            ResultSet rs = DBUtils.query("SELECT * FROM employees WHERE firstName = ?;", "Diane");
            if (rs.next()) {
                Assert.assertTrue(rs.getString("firstName").equals("Diane"));
            }
        } catch (SQLException e) {

        } finally {
            DBUtils.close();
        }
    }

    @Test
    public void testBean() {
        try {
            DBUtils.open();
            ResultSet rs = DBUtils.query("SELECT * FROM employees;");
            while (rs.next()) {
                Employee employee = new Employee(rs);
                Assert.assertTrue(employee != null);
            }
        } catch (SQLException e) {

        } finally {
            DBUtils.close();
        }
    }

    @Test
    public void testEmployees() {
        try {
            DBUtils.open();
            List<Employee> employees = Employee.getAll();
            Assert.assertTrue(employees.size() > 0);
        } catch (SQLException e) {

        } finally {
            DBUtils.close();
        }

    }

    @Test
    public void getByTest() {
        try {
            DBUtils.open();
            Employee employee = Employee.getBy("firstName", "Diane");
            Assert.assertTrue(employee != null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close();
        }
    }

    @Test
    public void testMultFieldsFilters() {
        try {
            DBUtils.open();
            List<String> columnNames = Arrays.asList(new String[]{"firstName", "lastName"});
            Employee employee = Employee.getBy(columnNames, "Mary", "Patterson");
            Assert.assertTrue(employee != null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close();
        }
    }

    @Test
    public void testInsert() {
        try {
            DBUtils.open("test");
            Employee employee = new Employee();
            employee.setEmployeeNumber(1);
            employee.setFirstName("James");
            employee.setLastName("Bond");
            employee.setJobTitle("Programmer");
            employee.insertIntoDB();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            DBUtils.close();
        }
    }


}
