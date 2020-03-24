package com.company;

public class Main {

    public static void main(String[] args) {
        try {
            String[] argResult = new String[3];
            argResult[0] = args[checkParams(args, "-mode")];
            argResult[1] = args[checkParams(args, "-port")];
            argResult[2] = args[checkParams(args, "-map")];

            if (argResult[0].equals("server")) {
                Server.main(argResult);
            } else {
                Client.main(argResult);
            }
        } catch (Exception ex) {
            System.out.println("too few parameters");
            ex.printStackTrace();
        }

    }

    private static int checkParams(String[] args, String str) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(str)) return i + 1;
        }
        return -1;
    }
}
