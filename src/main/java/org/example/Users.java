package org.example;

import java.security.Key;
import java.util.Scanner;

public class Users {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionDb connectionDb = new ConnectionDb();
    private static PasswordManager passwordManager = new PasswordManager();

    public static void Loginuser(String username) throws Exception {
        connectionDb.CheckDataUser(username);
    }


    public static void InsertUser(String username, String password) throws Exception {
        connectionDb.InsertDataUser(username,password);
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
