package models;

import cucumber.api.java.cs.A;
import db.DBUtils;
import file_utils.FileReader;
import file_utils.FileWriter;
import gherkin.lexer.Ar;
import org.junit.Assert;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EmployeeInfo extends BaseModel{
    String lastName;
    String firstName;
    String email;
    String officeCode;
    String addressLine1;
    String addressLine2;
    String city;
    String state;
    String country;
    String postalCode;

    private static final String getAllQuery = "" +
                                                "SELECT lastName, firstName, email, officeCode, addressLine1," +
                                                " addressLine2, city, state, country, postalCode\n" +
                                                "FROM employees\n" +
                                                "JOIN offices\n" +
                                                "USING (officeCode)";


    public EmployeeInfo(ResultSet rs) throws SQLException {
        getProcessor().populateBean(rs, this);
    }

    public EmployeeInfo(){}


    public EmployeeInfo(String line){
        String[] info = line.split(",");
        this.lastName = info[0];
        this.firstName = info[1];
        this.email = info[2];
        this.officeCode = info[3];
        this.addressLine1 = info[4];
        this.addressLine2 = info[5];
        this.city = info[6];
        this.state = info[7];
        this.country = info[8];
        this.postalCode = info[9];
    }

    public static List<EmployeeInfo> getAll() throws SQLException {
       return getProcessor().toBeanList(DBUtils.query(getAllQuery), EmployeeInfo.class);
    }

    public static Set<EmployeeInfo> parseAll(String filePath) throws IOException {
        FileReader reader = new FileReader(filePath);
        List<String> lines = reader.readLine();
        Set<EmployeeInfo> beans = new HashSet<>();

        for(String line : lines){
            beans.add(new EmployeeInfo(line));
        }

        return beans;
    }

    public static void main(String[] args) throws IOException{
        try{
            DBUtils.open();
            Set<EmployeeInfo> target = EmployeeInfo.parseAll("emp.txt");// Source 1 (Target)
            List<EmployeeInfo> source = EmployeeInfo.getAll();// From DataBase directly using queries (Source)
            Assert.assertEquals(target.size(), source.size());
            for (EmployeeInfo employeeInfo : source) { // Iterate thru list of objs received from DB
                if (!target.contains(employeeInfo)) //Verify that list from other source contains the object from database
                    System.out.println(employeeInfo);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.close();
        }


    }

//    public static void main(String[] args) throws SQLException, IOException {
//        DBUtils.open();
//        List<EmployeeInfo> source = EmployeeInfo.getAll();
//        FileWriter writer = new FileWriter("emp.txt");
//        writer.writeLines(source);
//        DBUtils.close();
//    }

//    public static void main(String[] args) throws SQLException, IOException {
//        DBUtils.open();
//        List<EmployeeInfo> source = EmployeeInfo.getAll();
//        Set<EmployeeInfo> target = EmployeeInfo.parseAll("emp.txt");
//
////        source.forEach(System.out::println);
////        System.out.println("TARGET");
////        target.forEach(System.out::println);
////        for(EmployeeInfo record : source){
////            if(!target.contains(record)) System.out.println(record);//Assert.fail("No record present");
////        }
//        System.out.println(source.size());
//        System.out.println(target.size());
//        DBUtils.close();
//    }

//    @Override
//    public String toString() {
//        return  lastName + "," +
//                firstName + ',' +
//                email + ',' +
//                officeCode + ',' +
//                addressLine1 + ',' +
//                addressLine2 + ',' +
//                city + ',' +
//                state + ',' +
//                country + ',' +
//                postalCode;
//    }


    @Override
    public String toString() {
        return "EmployeeInfo{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", officeCode='" + officeCode + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeInfo that = (EmployeeInfo) o;
        return Objects.equals(lastName, that.lastName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(officeCode, that.officeCode) &&
                Objects.equals(addressLine1, that.addressLine1) &&
                Objects.equals(addressLine2, that.addressLine2) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(country, that.country) &&
                Objects.equals(postalCode, that.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, firstName, email, officeCode, addressLine1, addressLine2, city, state, country, postalCode);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.replace("'", "").replace("-", "");;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.replace("'", "").replace("-", "");;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace("'", "").replace("-", "");;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
