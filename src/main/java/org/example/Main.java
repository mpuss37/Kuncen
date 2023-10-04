package org.example;

public class Main {
    private static Users users = new Users();
    private static String username, password;

    public Main() throws Exception {
        ConnectionDb connectionDb = new ConnectionDb();
    }

    private static void menuMain(String[] args) throws Exception {
        if (args.length == 2 && args[0].equals("-l") || args[0].equals("--login")) {
            username = String.valueOf(args[1]);
            users.Loginuser(username);
        } else if (args.length == 3 && args[0].equals("-s") || args[0].equals("--signup")) {
            username = String.valueOf(args[1]);
            password = String.valueOf(args[2]);
            users.InsertUser(username, password);
        } else if (args[0].equals("-h") || args[0].equals("--help")) {
            System.out.println("Kuncen-Manager (version 1.0, revision 9)");
            System.out.println("Usage:\n" +
                    " kuncen [OPTIONS]...[VALUES]\t\n" +
                    "  -l, --login [username] [password]    Log in to an existing user account.\n" +
                    "  -s, --signup [username] [password]    Sign up to register user account.\n" +
                    "  -h, --help          Display usage,options and help.\n");
        } else {
            System.out.println("kuncen: missing operand\n" +
                    "Try 'kuncen -h or --help' for more information.");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("kuncen: missing operand\n" +
                    "Try 'kuncen -h or --help' for more information.");
        } else {
            menuMain(args);
        }
    }
}

