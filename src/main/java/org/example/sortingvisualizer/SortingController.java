package org.example.sortingvisualizer;

import java.util.Random;

public class SortingController {

    private int[] values;

    public SortingController(int length, int maxValue){
        generateRandomArray(length, maxValue);
    }

    private void generateRandomArray(int length, int maxValue) {
        Random rand = new Random();
        values = new int[length];
        for (int i = 0; i < values.length; i++) {
            values[i] = rand.nextInt(maxValue) + 50;
        }
    }

    public int[] getValues(){
        return values;
    }

}
