package org.example.sorting_algorithms;

import java.util.*;

import org.example.sortingvisualizer.SortingStep;

abstract public class NumbersSorting implements Sorting {
    protected int[] unsorted_data;
    protected int[] sorted_data;
    protected ArrayList<SortingStep> sorting_steps;


    public NumbersSorting(int[] new_unsorted_array) {
        sorting_steps = new ArrayList<>();
        setData(new_unsorted_array);
        sorting_steps.add(new SortingStep(unsorted_data.clone()));
        performSorting();
        verifySorting();
    }

    public void shuffleArray() {
        Random rnd = new Random();
        for (int i = unsorted_data.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = unsorted_data[index];
            unsorted_data[index] = unsorted_data[i];
            unsorted_data[i] = a;
        }
        sorting_steps = new ArrayList<>();
        sorting_steps.add(new SortingStep(unsorted_data.clone()));
        performSorting();
        verifySorting();
    }


    public void setData(int[] new_unsorted_array) {
        unsorted_data = new_unsorted_array.clone();
    }

    public int[] getUnsortedData() {
        return unsorted_data;
    }

    public int[] getSortedData() {
        return sorted_data;
    }

    public ArrayList<SortingStep> getSortingSteps() {
        return sorting_steps;
    }

    public SortingStep getZeroSortingStep() {
        return sorting_steps.getFirst();
    }

    public SortingStep getSortingStepByIndex(int index){
        return sorting_steps.get(index);
    }


    public boolean verifySorting() {
        int[] tmp = unsorted_data.clone();
        Arrays.sort(tmp);
        boolean res = Arrays.equals(tmp, sorted_data);
        if (res) {
            System.out.println("Array is sorted correctly with " + this.toString() + " class object.");
        } else {
            System.out.println("Array is not sorted correctly! " + this.toString() + " class is not working properly!\n" + "Correct sorting: ");
            System.out.println("Actual result:");
        }
        return res;
    }
}
