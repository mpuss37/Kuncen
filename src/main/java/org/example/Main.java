package org.example;

public class Main {
    private static Users users = new Users();

    public Main() throws Exception {
        ConnectionDb connectionDb = new ConnectionDb();
    }

    public static void main(String[] args) throws Exception {

        if (args.length == 3 && args[0].equals("-a")) {
            String username = String.valueOf(args[1]);
            String password = String.valueOf(args[2]);
            users.InsertUser(username, password);
        }
    }
}

