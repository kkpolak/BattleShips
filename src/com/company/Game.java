package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Game implements Runnable {
    private Socket socket;
    private Mode mode;
    private BufferedWriter out;
    private BufferedReader in;
    private String message;
    private Map shipMap;
    private Map enemyMap;
    private static char[] letters = {'A','B','C','D','E','F','G','H','I','J'};

    public static java.util.Map<Character, Integer> marks = java.util
            .Map.of('A', 1, 'B', 2,
                    'C', 3, 'D', 4, 'E',
                    5, 'F', 6, 'G', 7,
                    'H', 8, 'I', 9, 'J', 10);


    public Game(Socket s, Mode m, String path) {
        try {
            socket = s;
            mode = m;
            out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            message = "";
            shipMap = new Map();
            shipMap.loadMap(path);
            enemyMap = new Map();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while(true) {
                if (mode == Mode.MYSESION) {
                    myTurn();
                } else if (mode == Mode.ENEMYSESION) {
                    enemyTurn();
                } else if (mode == Mode.PRINTMAP) {
                    mapPrinter();
                } else if (mode == Mode.END) {
                    System.out.println("Game Over");
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void send(String mess) {
        try {
            out.write(mess);
            out.newLine();
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private String checkedField(String input) {
        return input.substring(input.indexOf(';') + 1);
    }

    private String gameStatus(String input) {
        if (input.contains(";")) {
            return input.substring(0, input.indexOf(';'));
        } else {
            return input;
        }
    }

    private void myTurn() {
        try {
            Scanner scanner = new Scanner(System.in);
            String field = scanner.nextLine();
            //String field = getInput();
            if (isStartMessage(message)) { //wysylanie start
                send(field);
            } else { //pozostale przypadki
                send(message + ";" + field);
            }
            mode = Mode.ENEMYSESION; //przechodzimy do tury przeciwnika
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enemyTurn() {
        try {
            String input = getInput();
            System.out.println("response: " + input); //printujemy odpowiedz
            String status = gameStatus(input); //sprawdzamy czy juz nastapil koniec gry
            if (status.equals("ostatni zatopiony")) {
                winProcedure();
            } else {
                String field = checkedField(input); //pole do sprawdzenia
                int first = marks.get(field.charAt(0)) - 1; // pierwszy index
                int second = Integer.parseInt(field.substring(1)) - 1; //drugi index
                char fieldValue = shipMap.getMap().getMatrix(first,second);//wartosc tego pola
                if (fieldValue == '.' || fieldValue == '~') { //jesli pudlo
                    ifMiss(first, second);
                } else if (fieldValue == '#' || fieldValue == '@') { //jesli trafienie
                    ifHit(first, second);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void mapPrinter() {
        try {
            String input = getInput();
            enemyMap = Map.mapFromString(input);
            System.out.println("Enemy map: ");
            enemyMap.printMap();
            System.out.println("My map: ");
            shipMap.printMap();
            System.out.println();
            send(shipMap.toString());
            mode = Mode.END;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void winProcedure() {
        try {
            System.out.println("Wygrana");
            message = shipMap.toString();
            send(message);
            mode = Mode.PRINTMAP;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getInput() {
        /*
        Random rn = new Random();
        int x = rn.nextInt(10 -1 + 1) + 1;
        int y = rn.nextInt(10 + 1);
        String inputx = letters[y] + Integer.toString(x);
        return inputx;
           */


        String input = null;
        try {
            while (true){//czeka na dane
                input = in.readLine();
                if(input!=null) break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return input;
    }

    private boolean isStartMessage(String message) {
        return message.equals("");
    }

    private void ifMiss(int a, int b) {
        message = "pudło";
        shipMap.setMap('~', a, b);
        mode = Mode.MYSESION;
    }

    private void ifHit(int a, int b) {
        try {
            shipMap.setMap('@', a, b); // zaznacz trafienie
            if (shipMap.isLast()) { //jesli zniszczony zostal ostatni
                message = "ostatni zatopiony";
                System.out.println("Przegrana");
                send(message);
                mode = Mode.PRINTMAP;
            } else if (shipMap.isDestroyed(a, b, "")) { //jesli zniszczyl caly statek
                message = "trafiony zatopiony";
                mode = Mode.MYSESION;
            } else {
                message = "trafiony";
                mode = Mode.MYSESION;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}