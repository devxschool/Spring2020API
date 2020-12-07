package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import config.Config;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;
import java.util.List;
import java.util.Map;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;
    private static QueryRunner queryRunner; // APACHE COMONS DB UTILS

    private DBUtils() { }


    public static void open(String dataBase) throws SQLException {
        MysqlDataSource dataSource = getBaseDataSource(dataBase);
        if (connection == null) {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            queryRunner = new QueryRunner(dataSource);
        } else {
            throw new SQLException("Connection is already open");
        }
    }

    public static void open() throws SQLException {
        open("classicmodels");
    }

    // String q = SELECT * FORM employee WHERE id = ? AND first_name = ?;
    // query(q, 101, "James")

    /**         String query                                  Object... params Object[101]
     * query("SELECT * FROM employees WHERE employeeNumber = ?;", 101)
     * WHERE firstName = 'John'
     * WHERE employeeNumer = 101
     * query.replace(?, param)
     *
     * query("SELECT * FROM employees WHERE employeeNumber = ? AND firstName = ? AND lastName = ?", 101, "James", "Bond");
     * Object{101, "James", "Bond"};
     *"SELECT * FROM employees WHERE employeeNumber = 101 AND firstName = 'James' AND lastName = 'Bond'"
     */

    public static void truncate(String table) throws SQLException {
        statement.execute("TRUNCATE TABLE " + table + ";");
    }

    public static ResultSet query(String query, Object... params) throws SQLException {
        if (params.length == 0) return statement.executeQuery(query);

        // Prepared statement is used to execute a number of same queries with different params
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }

        return preparedStatement.executeQuery();
    }

    public static List<Map<String, Object>> queryToList(String query) throws SQLException {
        return DBHandler.rsToList(query(query));
    }

    public static boolean insertBean(String query, Object bean, String[] properties) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query); // instantiate new prep statement
        queryRunner.fillStatementWithBean(preparedStatement, bean, properties);
        // fill the statement(replace ? with values from our Bean(Object) from variable names provide as properties )
        return preparedStatement.execute();
    }

    /**
     * Columns(id, name, age)
     *
     * INSERT INTO employees
     * VALUES(?,?,?); -> (007, 'James Bond', 20)
     *
     * Employee{empNumber:007, fullName:James Bond, age:20}
     *
     * ["empNumber" "fullName", "age"];
     *
     */

    public static void close() {
        try {
            if (statement != null){
                statement.close();
                statement = null;
            }
            if (connection != null){
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    ////jdbc:mysql://18.218.51.74:3306/classicmodels?user=student&password=P@ssw0rd");
    private static MysqlDataSource getBaseDataSource(String database) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(Config.getProperty("server"));
        dataSource.setPortNumber(Integer.parseInt(Config.getProperty("port")));
        dataSource.setUser(Config.getProperty("user"));
        dataSource.setPassword(Config.getProperty("password"));
        dataSource.setDatabaseName(database);
        return dataSource;
    }
}
