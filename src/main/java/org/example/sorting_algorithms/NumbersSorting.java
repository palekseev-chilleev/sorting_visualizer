package org.example.sorting_algorithms;

import java.util.ArrayList;
import java.util.Arrays;

import org.example.sortingvisualizer.SortingStep;

abstract public class NumbersSorting implements Sorting {
    int[] unsorted_data;
    int[] sorted_data;

    ArrayList<SortingStep> sorting_steps;


    public NumbersSorting(int[] unsorted_array) {
        this.sorting_steps = new ArrayList<>();
        this.setData(unsorted_array);
        this.performSorting();
        this.verifySorting();
    }

    public NumbersSorting() {
    }

    public void setData(int[] unsorted_array) {
        this.unsorted_data = unsorted_array.clone();
    }

    public int[] getUnsortedData() {
        return this.unsorted_data;
    }

    public int[] getSortedData() {
        return this.sorted_data;
    }

    public ArrayList<SortingStep> getSortingSteps() {
        return this.sorting_steps;
    }


    public boolean verifySorting() {
        int[] tmp = this.unsorted_data.clone();
        Arrays.sort(tmp);
        boolean res = Arrays.equals(tmp, this.sorted_data);
        if (res) {
            System.out.println("Array is sorted correctly with " + this.toString() + " class object.");
        } else {
            System.out.println("Array is not sorted correctly! " + this.toString() + " class is not working properly!\n" +
                    "Correct sorting: ");
//            Main.printArray(tmp);
            System.out.println("Actual result:");
//            Main.printArray(this.sorted_data);
        }
        return res;
    }
}
