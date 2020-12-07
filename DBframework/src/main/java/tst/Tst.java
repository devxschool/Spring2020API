package tst;

import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tst {
    public static void main(String[] args) throws SQLException {
        DBConnection.open("test");
//        DBConnection.insertBean("INSERT INTO tab VALUES(?, ?, ? ,?, ?)", new Bean(6, "John", "Doe", null, 10));
        ResultSet rs = DBConnection.query("SELECT * FROM tab WHERE first_name = ?;", "Jasmin");
        rs.next();
        System.out.println(new Bean(rs));
        Bean.getAll().forEach(System.out::println);
        System.out.println(Bean.getBy("first_name", "lion"));
        DBConnection.close();

    }
}
