package models;

import db.DBUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Payment extends BaseModel {
    private int customerNumber;
    private String checkNumber;
    private LocalDate paymentDate;
    private double amount;
    private final static String getAllQuery = "SELECT * FROM payments;";

    public Payment() {}

    public static List<Payment> getAll() throws SQLException {
        ResultSet rs = DBUtils.query(getAllQuery); // query all, returns ResultSet
        return getProcessor().toBeanList(rs, Payment.class); // converts res set to list of objects of provided class;
    }

    public static Payment getTop1() throws SQLException {
        String q = "SELECT * FROM payments LIMIT 1;";
        ResultSet rs = DBUtils.query(q);
        return getProcessor().populateBean(rs, new Payment());
    }

    public static void main(String[] args) {
        try {
            DBUtils.open();
            List<Payment> payments = getAll();
            payments.forEach(System.out::println);

            for (Payment p : payments){

                System.out.println(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close();
        }
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date date) {
        System.out.println(date);
        this.paymentDate = date.toLocalDate();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "customerNumber=" + customerNumber +
                ", checkNumber='" + checkNumber + '\'' +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}
