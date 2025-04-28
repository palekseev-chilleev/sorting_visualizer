package sortingvisualizer.sortingalgorithms;

import java.lang.Math;
import java.util.Random;

public class ArrayUtils {

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
            res[i] = ArrayUtils.generateRandomInt(exponent);
        }
        return res;
    }

    public static int[] generateRandomIntArray(int array_length, int maxValue) {
        Random rand = new Random();
        int[] res = new int[array_length];
        for (int i = 0; i < res.length; i++) {
            res[i] = rand.nextInt(maxValue);
        }
        return res;
    }

}
