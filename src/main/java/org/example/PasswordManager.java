package org.example;

import java.util.Scanner;

public class PasswordManager {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionDb connectionDb = new ConnectionDb();

    public void menuPasswordManager(int id_user) throws Exception {
        System.out.println("Usage:\n" +
                " [OPTIONS]... [VALUES]\n" +
                "\n" +
                "Options:\n" +
                "  -a, --add [program] [password]    Add a new program and its associated password.\n" +
                "  -u, --update [program] [password] Update the password for an existing program.\n" +
                "  -d, --delete [program]            Delete a program and its associated password.\n" +
                "  -r, --read [program]              Read and display the password for a specific program.\n" +
                "  -l, --list                        List all stored programs and their passwords.\n" +
                "  -h, --help                        Display usage, available options, and help.\n");
        System.out.print("your input : ");
        String input = scanner.nextLine();
        //use nextline for take all line
        String[] nilai = input.split(" ");
        //for separate each word with regex with value ' ' or space
        if (nilai.length == 3 && nilai[0].equals("-a") || nilai[0].equals("-add")) {
            String app_name = String.valueOf(nilai[1]);
            String app_password = String.valueOf(nilai[2]);
            connectionDb.InsertDataPasswordManager(id_user, app_name, app_password);
        }else if (nilai.length == 2 && nilai[0].equals("-d") || nilai[0].equals("-delete")) {
            String app_name = String.valueOf(nilai[1]);
//            connectionDb.UpdateDataPasswordManager(id_user, app_name, app_password);
        }else {
            System.out.println("kuncen: missing operand.");
        }
    }
}
