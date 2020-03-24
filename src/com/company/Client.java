package com.company;

import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            String address = Utils.findAddress().toString();
            Socket socket = new Socket(address.substring(1), Integer.parseInt(args[1]));
            Game game = new Game(socket, Mode.MYSESION, args[2]);
            new Thread(game, "Client").start();
        } catch (Exception ex) {
            System.out.println("client cannot start");
            ex.printStackTrace();
        }
        System.out.println("client started");
    }
}
