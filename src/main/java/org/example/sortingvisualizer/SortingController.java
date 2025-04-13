package org.example.sortingvisualizer;

import org.example.sorting_algorithms.BubbleSorting;
import org.example.sorting_algorithms.Sorting;
import org.example.sorting_algorithms.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class SortingController {

    private int[] unsorted_array;
    private int[] sorted_array;
    private SortingStep[] sortingSteps;
    private Sorting sorter_obj;


    public SortingController(int length, int maxValue){

        generateRandomArray(length, maxValue);
//        int[] generated_unsorted_array = Utils.generateRandomIntArray(1500);
        sorter_obj = new BubbleSorting(unsorted_array);


    }

    private void generateRandomArray(int length, int maxValue) {
        Random rand = new Random();
        unsorted_array = new int[length];
        for (int i = 0; i < unsorted_array.length; i++) {
            unsorted_array[i] = rand.nextInt(maxValue) + 50;
        }
    }


    public void performSorting() {
        sorter_obj.performSorting();
        sorted_array = sorter_obj.getSortedData();
    }

    public ArrayList<SortingStep> getSortingSteps(){
        return sorter_obj.getSortingSteps();
    }

    public int[] getValues(){
        if (sorted_array == null){
            return unsorted_array;
        }
        return sorted_array;
    }

}




