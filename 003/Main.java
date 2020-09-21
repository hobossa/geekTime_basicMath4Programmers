package com.hoboss;

public class Main {
    // 有2，3，7三种面额的纸币，凑够m元(100)，所需要最少的钱币数
    private static int[][] getMatrix(int m) {

        int[][] matrix = new int[m + 1][4];
        for (int i = 0; i < m + 1; ++i) {

            if (0 == i) {
                for (int j = 0; j < 4; ++j) {
                    matrix[i][j] = 0;
                }
            } else {
                int n2 = -1;
                int n3 = -1;
                int n7 = -1;
                if (i - 2 >= 0 && matrix[i - 2][3] != -1) {
                    n2 = matrix[i - 2][3] + 1;
                }
                if (i - 3 >= 0 && matrix[i - 3][3] != -1) {
                    n3 = matrix[i - 3][3] + 1;
                }
                if (i - 7 >= 0 && matrix[i - 7][3] != -1) {
                    n7 = matrix[i - 7][3] + 1;
                }
                if (-1 == n2 && -1 == n3 && -1 == n7) {
                    matrix[i][0] = matrix[i][1] = matrix[i][2] = matrix[i][3] = -1;
                } else {
                    // 找出最小且不为-1的值。
                    int min = n2;
                    if (-1 == min || (-1 != n3 && min > n3)) {
                        min = n3;
                    }
                    if (-1 == min || (-1 != n7 && min > n7)) {
                        min = n7;
                    }

                    if (min == n2) {
                        matrix[i][0] = matrix[i - 2][0] + 1;
                        matrix[i][1] = matrix[i - 2][1];
                        matrix[i][2] = matrix[i - 2][2];
                        matrix[i][3] = matrix[i - 2][3] + 1;
                    } else if (min == n3) {
                        matrix[i][0] = matrix[i - 3][0];
                        matrix[i][1] = matrix[i - 3][1] + 1;
                        matrix[i][2] = matrix[i - 3][2];
                        matrix[i][3] = matrix[i - 3][3] + 1;
                    } else /*(min == nf7)*/ {
                        matrix[i][0] = matrix[i - 7][0];
                        matrix[i][1] = matrix[i - 7][1];
                        matrix[i][2] = matrix[i - 7][2] + 1;
                        matrix[i][3] = matrix[i - 7][3] + 1;
                    }
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        int[][] matrix = getMatrix(100);
        for (int i = 0; i < matrix.length; ++i) {
            System.out.print("" + i + ":");
            for (int j = 0; j < matrix[i].length; ++j) {
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println();
        }
    }
}
