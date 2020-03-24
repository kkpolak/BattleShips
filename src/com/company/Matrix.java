package com.company;

public class Matrix {
    private char[][] matrix;

    Matrix() {
        matrix = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                matrix[i][j] = '0';
            }
        }
    }
    Matrix(char [][] arr) {
        matrix = new char[10][10];
        for (int i = 0; i < 10; i++) {
            System.arraycopy(arr[i], 0, matrix[i], 0, 10);
        }
    }

    char getMatrix(int f, int s){
        return matrix[f][s];
    }

    public void setMatrix(char value, int firstIndex, int secondIndex) {
        matrix[firstIndex][secondIndex] = value;
    }
}
