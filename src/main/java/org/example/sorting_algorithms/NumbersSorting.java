package org.example.sorting_algorithms;

import java.util.*;

import org.example.sortingvisualizer.SortingStep;

abstract public class NumbersSorting implements Sorting {
    int[] unsorted_data;
    int[] sorted_data;

    ArrayList<SortingStep> sorting_steps;


    public NumbersSorting(int[] unsorted_array) {
        this.sorting_steps = new ArrayList<>();
        this.setData(unsorted_array);
        sorting_steps.add(new SortingStep(unsorted_data.clone()));
        this.performSorting();
        this.verifySorting();
    }

    public NumbersSorting() {
    }

    public void shuffleArray() {
        Random rnd = new Random();
        for (int i = unsorted_data.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = unsorted_data[index];
            unsorted_data[index] = unsorted_data[i];
            unsorted_data[i] = a;
        }
        this.sorting_steps = new ArrayList<>();
        sorting_steps.add(new SortingStep(unsorted_data.clone()));
        this.performSorting();
        this.verifySorting();
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

    public SortingStep getZeroSortingStep() {
        return this.sorting_steps.getFirst();
    }


    public boolean verifySorting() {
        int[] tmp = this.unsorted_data.clone();
        Arrays.sort(tmp);
        boolean res = Arrays.equals(tmp, this.sorted_data);
        if (res) {
            System.out.println("Array is sorted correctly with " + this.toString() + " class object.");
        } else {
            System.out.println("Array is not sorted correctly! " + this.toString() + " class is not working properly!\n" + "Correct sorting: ");
            System.out.println("Actual result:");
        }
        return res;
    }
}
