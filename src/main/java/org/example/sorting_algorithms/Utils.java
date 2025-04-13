package org.example.sorting_algorithms;

import java.lang.Math;

public class Utils {

    public static int generateRandomInt(int exponent) {
        // exponent is a power of 10 of output value.
        double rand = Math.random();
        return (int) (rand * Math.pow(10, exponent));
    }

    public static int[] generateRandomIntArray(int array_length) {
        int[] res = new int[array_length];
        int exponent = 0;
        for (int ten_pow = 1; ten_pow < array_length; ten_pow = ten_pow * 10) {
            exponent++;
        }
        for (int i = 0; i < array_length; i++) {
            res[i] = Utils.generateRandomInt(exponent);
        }
        return res;
    }

}
