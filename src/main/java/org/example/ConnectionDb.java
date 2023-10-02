package org.example;

import org.sqlite.SQLiteDataSource;

import java.security.Key;
import java.sql.*;

public class ConnectionDb {
    private SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static String url = "jdbc:sqlite:/home/mpuss/kodingan/inteelij/kuncen/src/main/resources/sql/kuncen.db";

    private static String encryptedTextOne, encryptedPassword, decryptedUsername, decryptedPassword, query;

    private static HashPassword hashPassword = new HashPassword();
    private static PasswordManager passwordManager = new PasswordManager();

    public ConnectionDb() {
        CreateTables();
    }

    public boolean CreateTables() {
        try {
            connection = ConnectToDatabase();
            if (!TableExists(connection, "users") && !TableExists(connection, "data")) {
                System.out.println("Tabel success for create");
                CreateTable("users", connection, "CREATE TABLE IF NOT EXISTS users (" + "id_user INTEGER PRIMARY KEY AUTOINCREMENT," + "username TEXT NOT NULL UNIQUE," + "password TEXT NOT NULL" + ")");
                CreateTable("data", connection, "CREATE TABLE IF NOT EXISTS data (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "id_user INTEGER," + "name_app TEXT NOT NULL," + "password TEXT NOT NULL" + ")");
                return true;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int CheckDataUser(String username, String password) throws Exception {
        try {
            connection = ConnectToDatabase();
            //konek to database
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            encryptedTextOne = hashPassword.encrypt(username, key);
            encryptedPassword = hashPassword.encrypt(password, key);
            //encrypted username,password for validating existing data in the database
            query = "select * from users where username = ? and password = ?";
            //query sql
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, encryptedTextOne);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("data ditemukan");
                int id_user = resultSet.getInt("id_user");
                connection.close();
                passwordManager.menuPasswordManager(id_user);
            } else {
                System.out.println("data tidak ditemukan");
            }
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("use different username / password");
        }
        return 0;
    }

    public void InsertDataUser(String username, String password) throws Exception {
        try {
            connection = ConnectToDatabase();
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            encryptedTextOne = hashPassword.encrypt(username, key);
            encryptedPassword = hashPassword.encrypt(password, key);
            String query = "insert into users (username,password) values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, encryptedTextOne);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.executeUpdate();
            System.out.println("success");
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("use different username / password " + e);
        }
    }

    public void UpdateDataUser(int id_user, String username, String password) {
        try {
            connection = ConnectToDatabase();
            String query = "update users set username = ?, password = ? where id_user =" + id_user;
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void InsertDataPasswordManager(int id_user, String name_app, String password) throws Exception {
        try {
            connection = ConnectToDatabase();
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            encryptedTextOne = hashPassword.encrypt(name_app, key);
            encryptedPassword = hashPassword.encrypt(password, key);
            query = "insert into data (id_user,name_app,password) values (?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setInt(1, id_user);
            preparedStatement.setString(2, encryptedTextOne);
            preparedStatement.setString(3, encryptedPassword);
            preparedStatement.executeUpdate();
            System.out.println("success add pass-manager");
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("salah " + e);
        }
    }

    private Connection ConnectToDatabase() throws SQLException {
        sqLiteDataSource.setUrl(url);
        return sqLiteDataSource.getConnection();
    }

    private boolean TableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(null, null, tableName, null);
        boolean tableExists = resultSet.next();
        resultSet.close();
        return tableExists;
    }

    private void CreateTable(String nameTable, Connection connection, String createTableSql) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute();
        }
    }
}
