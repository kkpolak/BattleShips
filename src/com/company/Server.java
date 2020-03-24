package com.company;

import java.net.*;

public class Server implements Runnable{

    private ServerSocket serverSocket;
    private static String path;
    private Server(InetAddress address, int port){
        try {
            serverSocket = new ServerSocket(port, 10000, address); //TODO
            System.out.println("Running server at address: " + address + ", port: " + port);
            path = "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            InetAddress address = Utils.findAddress();
            Server server = new Server(address, Integer.parseInt(args[1]));
            path = args[2];
            new Thread(server, "Server").start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    @Override
    public void run() {
        try {
            Socket socket = serverSocket.accept();
            Game game = new Game(socket, Mode.ENEMYSESION, path);
            new Thread(game, "Server").start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}