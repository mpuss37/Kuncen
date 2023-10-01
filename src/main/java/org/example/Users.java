package org.example;

import java.security.Key;
import java.sql.Connection;
import java.util.Scanner;

public class Users {
    private static String username;
    private static String password;
    private static String encryptedPassword;
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionDb connectionDb = new ConnectionDb();
    private static HashPassword hashPassword = new HashPassword();

    public static void InsertUser(String username, String password) throws Exception {
        Key key = hashPassword.getOrGenerateSecretKey();
        encryptedPassword = hashPassword.encrypt(password,key);
        connectionDb.insertDataUser(username, encryptedPassword);
    }

    static void UpdateUser() throws Exception {
//        System.out.print("Input id : ");
//        int id_user = scanner.nextInt();
//        System.out.print("Input your username : ");
//        username = scanner.nextLine();
//        System.out.print("Input your password : ");
//        password = scanner.nextLine();
//        encryptedPassword = hashPassword.encrypt(password,key);
//        connectionDb.updateDataUser(id_user,username,encryptedPassword);
    }

}
