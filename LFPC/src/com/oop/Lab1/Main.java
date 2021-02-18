package com.oop.Lab1;
import java.util.Scanner;

class Main {
    static final int S = 0, A = 1, C = 2, D = 3, X = 4, NaN = -1;
    static int state = S;

    // Start rules set for S
    static void spoint(char c) {
        if (c == 'a') {
            state = A;
        }
        else {
            state = NaN;
        }
    }

    // Rules set for A
    static void A_state(char c) {
        if (c == 'b') {
            state = S;
        } else if (c == 'd') {
            state = D;
        } else {
            state = -1;
        }
    }

    // Rules set for C
    static void C_state(char c) {
        if (c == 'b') {
            state = A;
        } else if (c == 'a') {
            state = X;
        } else {
            state = -1;
        }
    }

    // Rules set for D
    static void D_state(char c) {
        if (c == 'b') {
            state = C;
        } else if (c == 'a') {
            state = D;
        } else {
            state = -1;
        }
    }

    // State (X)
    static void X_state(char c) {
        state = -1;
    }

    // Parse the string and check if belongs
    static boolean belongs(char test[]) {
        int i, len = test.length;

        for (i = 0; i < len; i++) {
            if (state == S)
                spoint(test[i]);

            else if (state == A)
                A_state(test[i]);

            else if (state == C)
                C_state(test[i]);

            else if (state == D)
                D_state(test[i]);

            else if (state == X)
                X_state(test[i]);
            else
                return false;
        }

        if (state == C) {
            return false;
        } else
            return true;
    }

    // Main
    public static void main(String[] args) {
        System.out.printf("Enter the string that needs to be checked \n ");
        Scanner input = new Scanner(System.in);
        String expression = input.nextLine();
        char test[] = expression.toCharArray();

        if (belongs(test) == true)
            System.out.printf("The entered string belongs to the grammar rules");
        else
            System.out.printf("The entered string does not belong to the grammar rules");
    }
}