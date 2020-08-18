package utils;


import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBConnection {
    private static Connection connection;
    private static Statement statement;

    private DBConnection() {
    }

    public static void open(String db) throws SQLException {
        if (connection == null) {
            connection = getBaseDataSource(db).getConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
    }

    public static ResultSet query(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public static List<Map<String, Object>> queryToList(String query) throws SQLException {
        try (ResultSet rs = query(query)) {
            List<Map<String, Object>> result = new ArrayList<>();
            List<String> columns = getColumns(rs.getMetaData());

            while (rs.next()){
                result.add(rowToMap(rs, columns));
            }

            return result;
        }

    }


    public static void close() {
        try {
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }

        } catch (SQLException e) {
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

    private static Map<String, Object> rowToMap(ResultSet rs, List<String> columns) throws SQLException {
        Map<String, Object> row = new HashMap<>();

        for(String column : columns){
            row.put(column, rs.getObject(column));
        }

        return row;
    }

    private static List<String> getColumns(ResultSetMetaData metaData) throws SQLException {
        int num_of_columns = metaData.getColumnCount();
        List<String> headers = new ArrayList<>();

        for(int i = 1; i <= num_of_columns; i++){
            headers.add(metaData.getColumnName(i));
        }

        return headers;
    }
}
