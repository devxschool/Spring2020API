package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import config.Config;
import org.apache.commons.dbutils.QueryRunner;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {
    private static Connection connection;
    private static Statement statement;
    private static QueryRunner queryRunner;

    private DBUtils(){};

    public static void open(String dataBase) throws SQLException {
        MysqlDataSource dataSource = getBaseDataSource(dataBase);
        if(connection == null){
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            queryRunner = new QueryRunner(dataSource);
        }else{
            throw new SQLException("Connection is already open");
        }
    }

    public static void open() throws SQLException {
        open("classicmodels");
    }

    // String q = SELECT * FORM employee WHERE id = ? AND first_name = ?;
    // query(q, 101, "James")
    public static ResultSet query(String query, Object... params) throws SQLException {
        if(params.length == 0) return statement.executeQuery(query);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++){
            preparedStatement.setObject(i + 1, params[0]);
        }
        return preparedStatement.executeQuery();
    }

    public static List<Map<String, Object>> queryToList(String query) throws SQLException {
        return DBHandler.rsToList(query(query));
    }

    public static void close(){
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static MysqlDataSource getBaseDataSource(String database){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(Config.getProperty("server"));
        dataSource.setPortNumber(Integer.parseInt(Config.getProperty("port")));
        dataSource.setUser(Config.getProperty("user"));
        dataSource.setPassword(Config.getProperty("password"));
        dataSource.setDatabaseName(database);
        return dataSource;
    }
}
