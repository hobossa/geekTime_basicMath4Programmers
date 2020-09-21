package com.hoboss;

public class Main {

    private static int[][] getStringDistanceMatrix(String strA, String strB) {
        int lenA = strA.length();
        int lenB = strB.length();
        int[][] matrix = new int[lenA + 1][lenB + 1];
        for (int i = 0; i < lenA + 1; ++i) {
            for (int j = 0; j < lenB + 1; ++j) {
                if (0 == i) {
                    matrix[i][j] = j;
                } else if (0 == j) {
                    matrix[i][j] = i;
                } else {
                    int r = strA.charAt(i-1) != strB.charAt(j-1) ? 1 : 0;
                    matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1), matrix[i - 1][j - 1] + r);
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        String strA = "mouuse";
        String strB = "mouse";
        int[][] matrix = getStringDistanceMatrix(strA, strB);
        System.out.println(matrix[strA.length()][strB.length()]);
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
