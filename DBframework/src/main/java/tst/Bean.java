package tst;

import org.apache.commons.dbutils.BeanProcessor;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Bean {
    int id;
    String first_name;
    String last_name;
    String email;
    int age;

    public Bean(){};

    public Bean(int id, String first_name, String last_name, String email, int age) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.age = age;
    }

    public static List<Bean> getAll() throws SQLException {
        String query = "SELECT * FROM alott";
        try(ResultSet resultSet = DBConnection.query(query)){
            return new BeanProcessor().toBeanList(resultSet, Bean.class);
        }
    }

    public static Bean getBy(String column, String value) throws SQLException {
        String query = "SELECT * FROM alott WHERE " + column + " = ?;";
        ResultSet rs = DBConnection.query(query, value);
        if(!rs.next()) return null;
        return new BeanProcessor().toBean(rs, Bean.class);
    }

    public Bean(ResultSet rs) throws SQLException {
        new BeanProcessor().populateBean(rs, this);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
