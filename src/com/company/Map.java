package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    private  Matrix matrix;
    public Map(){
        matrix = new Matrix();
    }

    public Map(char[][] tmp){
        matrix = new Matrix(tmp);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.append(matrix.getMatrix(i,j));
            }
        }
        return builder.toString();
    }

    public void setMap(char value, int firstIndex, int secondIndex) {
        matrix.setMatrix(value,firstIndex,secondIndex);
    }


    public void loadMap(String path) {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            final var lines = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                lines.add(scanner.nextLine());
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    matrix.setMatrix(lines.get(i).charAt(j),i,j);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void printMap() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                builder.append(matrix.getMatrix(i,j));
            }
            builder.append('\n');
        }
        System.out.println(builder.toString());
    }

    public boolean isLast() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix.getMatrix(i,j) == '#') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isDestroyed(int firstIndex, int secondIndex, String directory) {
        if(firstIndex-1>=0 && firstIndex-1<=10){
            return matrix.getMatrix(firstIndex - 1, secondIndex) != '#';
        }else if(firstIndex+1>=0 && firstIndex+1<=10) {
            return matrix.getMatrix(firstIndex + 1, secondIndex) != '#';
        }else if(secondIndex-1>=0 && secondIndex-1<=10){
            return matrix.getMatrix(firstIndex, secondIndex - 1) != '#';
        }else if(secondIndex+1>=0 && secondIndex+1<=10) {
            return matrix.getMatrix(firstIndex, secondIndex + 1) != '#';
        }
        return true;
    }

    public static Map mapFromString(String input) {
        char[][] result = new char[10][10];
        String betterInput = input.replaceAll("\n", "");
        for (int i = 0; i < 100; i++) {
            result[i / 10][i % 10] = betterInput.charAt(i);
        }
        return new Map(result);
    }

    public Matrix getMap() {
        return matrix;
    }
}
