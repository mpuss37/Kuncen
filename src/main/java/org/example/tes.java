package org.example;

public class tes {
    public static void main(String[] args) {
        if (args.length == 3 && args[0].equals("-h")) {
            String nama = args[1];
            String password = args[2];
            System.out.println(nama);
            System.out.println(password);
        } else {
            System.out.println("Format argumen salah. Gunakan: java Main -h");
        }
    }
}
