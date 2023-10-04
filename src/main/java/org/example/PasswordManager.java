package org.example;

import java.util.Scanner;

public class PasswordManager {
    private static Scanner scanner = new Scanner(System.in);
    private static ConnectionDb connectionDb = new ConnectionDb();
    private boolean benar;
    private String app_name, password, id_app;
    private int id;

    public void menuPasswordManager(int id_user) throws Exception {
        System.out.println("ID "+id_user+" Kuncen-Manager (version 1.0, revision 9)");
        System.out.println("Usage:" +
                " [OPTIONS]... [VALUES]" +
                "\n" +
                "Options:\n" +
                " -a, --add [acc] [pass]                 Add a new program and its associated password.\n" +
                " -u, --update [acc] [pass] [id]         Update the password for an existing program.\n" +
                " -d, --delete [id]                     Delete a program and its associated password.\n" +
//                " -r, --read [acc]                       Read and display the password for a specific program.\n" +
                " -l, --list                                 List all stored programs and their passwords.\n" +
                " -h, --help                                 Display usage, available options, and help.\n" +
                " -x, --exit                                 Close program and enjoy your day.\n");
        do {
            System.out.print("your input : -");
            String input = scanner.nextLine();
            //use nextline for take all line
            String[] nilai = input.split(" ");
            //for separate each word with regex with value ' ' or space
            if (nilai.length == 3 && nilai[0].equals("a") || nilai[0].equals("-add")) {
                app_name = String.valueOf(nilai[1]);
                password = String.valueOf(nilai[2]);
                connectionDb.InsertDataPasswordManager(id_user, app_name, password);
                benar = false;
            } else if (nilai.length == 4 && nilai[0].equals("u") || nilai[0].equals("--update")) {
                app_name = String.valueOf(nilai[1]);
                password = String.valueOf(nilai[2]);
                id = Integer.valueOf(nilai[3]);
                connectionDb.UpdateDataPasswordManager(id_user, app_name, password, id);
                benar = false;
            } else if (nilai.length == 2 && nilai[0].equals("d") || nilai[0].equals("--delete")) {
                id = Integer.valueOf(nilai[1]);
                connectionDb.DeleteDataPasswordManager(id_user, id);
                benar = false;
            }
//            else if (nilai.length == 2 && nilai[0].equals("r") || nilai[0].equals("--read")) {
//                app_name = String.valueOf(nilai[1]);
//            connectionDb.ReadDataPasswordManager(id_user, app_name);
//                benar = false;
//            }
            else if (nilai.length == 1 && nilai[0].equals("l") || nilai[0].equals("--list")) {
                connectionDb.ListDataPasswordManager(id_user);
                benar = false;
            } else if (nilai.length == 1 && nilai[0].equals("x") || nilai[0].equals("--exit")) {
                System.out.println("Ok thanks, Program is closed");
                benar = true;
            } else {
                benar = false;
                System.out.println("kuncen: missing operand\n" +
                        "Try 'kuncen -h or --help' for more information.");
            }
        } while (benar == false);
    }
}
