package org.example;

import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class ConnectionDb {
    private SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static String url = "jdbc:sqlite:/home/mpuss/kodingan/kumpulan-database/kuncen.db";

    public ConnectionDb() {
        createTables();
    }

    public boolean createTables() {
        try {
            connection = connectToDatabase();
            if (!tableExists(connection, "users") && !tableExists(connection, "data")) {
                System.out.println("Tabel berhasil dibuat");
                createTable("users", connection, "CREATE TABLE IF NOT EXISTS users (" + "id_user INTEGER PRIMARY KEY AUTOINCREMENT," + "username TEXT NOT NULL UNIQUE," + "password TEXT NOT NULL" +")");
                createTable("data", connection, "CREATE TABLE IF NOT EXISTS data (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "id_user INTEGER," + "name_app TEXT NOT NULL," + "password TEXT NOT NULL," + "FOREIGN KEY(id_user) REFERENCES users(id_user)" + ")");
                return true;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertDataUser(String username, String password) {
        try {
            connection = connectToDatabase();
            String url = "insert into users (username,password) values (?,?)";
            preparedStatement = connection.prepareStatement(url);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            System.out.println("berhasil");
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("use different username / password");
        }
    }

    public void updateDataUser(int id_user, String username, String password) {
        try {
            connection = connectToDatabase();
            String url = "update users set username = ?, password = ? where id_user ="+id_user;
            preparedStatement = connection.prepareStatement(url);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connectToDatabase() throws SQLException {
        sqLiteDataSource.setUrl(url);
        return sqLiteDataSource.getConnection();
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        boolean tableExists = resultSet.next();
        resultSet.close();
        return tableExists;
    }

    private void createTable(String nameTable, Connection connection, String createTableSql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute();
        }
    }
}
