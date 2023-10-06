package org.example;

import org.sqlite.SQLiteDataSource;

import java.io.Console;
import java.security.Key;
import java.sql.*;
import java.util.Scanner;

public class ConnectionDb {
    private SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
    private Connection connection;
    private Console console;
    private PreparedStatement preparedStatement;
    private static String url = "jdbc:sqlite:/home/mpuss/kodingan/inteelij/kuncen/src/main/resources/sql/kuncen.db";

    private static String encryptedTextOne, encryptedPassword, decryptedTextOne, decryptedPassword, query, password;
    private char[] passwordArray;

    private static HashPassword hashPassword = new HashPassword();
    private static PasswordManager passwordManager = new PasswordManager();
    private Scanner scanner = new Scanner(System.in);

    public ConnectionDb() {
        CreateTables();
    }

    public boolean CreateTables() {
        try {
            connection = ConnectToDatabase();
            if (!TableExists(connection, "users") && !TableExists(connection, "data")) {
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

    public int CheckDataUser(String username) throws Exception {
        try {
            connection = ConnectToDatabase();
            //konek to database
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            encryptedTextOne = hashPassword.encrypt(username, key);
            console = System.console();

            if (console == null) {
                System.out.print("pass : ");
                password = scanner.nextLine();
            } else {
                passwordArray = console.readPassword("Masukkan password: ");
                password = String.valueOf(passwordArray);
            }
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
                int id_user = resultSet.getInt("id_user");
                connection.close();
                passwordManager.menuPasswordManager(id_user);
            } else {
                System.out.println("error username / password");
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
            System.out.println("use different username / password ");
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
            connection.close();
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("error");
        }
    }

    public void UpdateDataPasswordManager(int id_user, String name_app, String password, int id_app) throws Exception {
        try {
            connection = ConnectToDatabase();
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            encryptedTextOne = hashPassword.encrypt(name_app, key);
            encryptedPassword = hashPassword.encrypt(password, key);
            query = "update data set name_app = ?, password = ? where id_user = ? and id = ?";
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setString(1, encryptedTextOne);
            preparedStatement.setString(2, encryptedPassword);
            preparedStatement.setInt(3, id_user);
            preparedStatement.setInt(4, id_app);
            preparedStatement.executeUpdate();
            System.out.println("success add pass-manager");
            connection.close();
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("error ");
        }
    }

    public void DeleteDataPasswordManager(int id_user, int id_app) throws Exception {
        try {
            connection = ConnectToDatabase();
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            query = "delete from data where id_user = ? and id = ?";
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setInt(1, id_user);
            preparedStatement.setInt(2, id_app);
            preparedStatement.executeUpdate();
            System.out.println("success add pass-manager");
            connection.close();
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println("error ");
        }
    }

//    public void ReadDataPasswordManager(int id_user, String app_name) throws Exception {
//        try {
//            connection = ConnectToDatabase();
//            Key key = hashPassword.getOrGenerateSecretKey();
//            //encrypt text it first after that find qith query and get encrypt data on database after that decrypt the data and print it
//            String encryptedTextOnee = hashPassword.encrypt(app_name, key);
//            //to take keyhash
//            query = "select * from data where id_user = ? and name_app like ?";
//            preparedStatement = connection.prepareStatement(query);
//            //object standart sql use for execute sql query
//            preparedStatement.setInt(1, id_user);
//            preparedStatement.setString(2, "%" + encryptedTextOnee + "%");
//            preparedStatement.executeQuery();
//            //resultset for print and execute query
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                //get encrypt text
//                int id = resultSet.getInt("id");
//                encryptedTextOne = resultSet.getString("name_app");
//                encryptedPassword = resultSet.getString("password");
//
//                // decrypt data
//                decryptedTextOne = hashPassword.decrypt(encryptedTextOne, key);
//                decryptedPassword = hashPassword.decrypt(encryptedPassword, key);
//                System.out.println(decryptedTextOne);
//                System.out.println("id : '" + id + "' pass: '" + decryptedPassword + "'");
//            }
//            connection.close();
//        } catch (SQLException e) {
//            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
//            System.out.println(e);
//        }
//    }

    public void ListDataPasswordManager(int id_user) throws Exception {
        try {
            connection = ConnectToDatabase();
            Key key = hashPassword.getOrGenerateSecretKey();
            //to take keyhash
            query = "select * from data where id_user = ?";
            preparedStatement = connection.prepareStatement(query);
            //object standart sql use for execute sql query
            preparedStatement.setInt(1, id_user);
            preparedStatement.executeQuery();
            //resultset for print and execute query
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //get encrypt text
                int id = resultSet.getInt("id");
                encryptedTextOne = resultSet.getString("name_app");
                encryptedPassword = resultSet.getString("password");

                // decrypt data
                decryptedTextOne = hashPassword.decrypt(encryptedTextOne, key);
                decryptedPassword = hashPassword.decrypt(encryptedPassword, key);

                System.out.println("id : '" + id + "' acc: '" + decryptedTextOne + "' pass: '" + decryptedPassword + "'");
            }
            connection.close();
            //if pick / throw data is finish it will to close connection the database so this doesnt happen busy / error database
        } catch (SQLException e) {
            //if a 'username' on database is a value > 0 get message to change username and password, Because col 'username' is unique
            System.out.println(e);
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
