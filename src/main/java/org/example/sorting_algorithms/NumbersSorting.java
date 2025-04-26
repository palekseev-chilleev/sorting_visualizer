package org.example.sorting_algorithms;

import java.util.*;

import org.example.sortingvisualizer.SortingStep;

abstract public class NumbersSorting implements Sorting {
    protected int[] unsorted_data;
    protected int[] sorted_data;
    protected ArrayList<SortingStep> sorting_steps;
    protected int[] steps_inversions;
    long memory_used;
    long time_used;

    public NumbersSorting(int[] new_unsorted_array) {
        sorting_steps = new ArrayList<>();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long before = runtime.totalMemory() - runtime.freeMemory();

        setData(new_unsorted_array);
        sorting_steps.add(new SortingStep(unsorted_data.clone()));

//        long startTime = System.nanoTime();
        performSorting();
//        long endTime = System.nanoTime();

        long after = runtime.totalMemory() - runtime.freeMemory();
        memory_used = after - before;
        verifySorting();
        countInversions();
//        time_used = endTime - startTime;

        // Gathering memory statistic is affects sorting time. Measuring time separately.
        measureTimeUsed();
    }

    private void measureTimeUsed(){
        long startTime = System.nanoTime();
        performSorting();
        long endTime = System.nanoTime();
//        long durationNano =
//        time_used = (endTime - startTime) / 1_000_000;
        time_used = endTime - startTime;
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

    public SortingStep getSortingStepByIndex(int index) {
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

    private void countInversions() {
        steps_inversions = new int[sorting_steps.size()];
        for (int i = 0; i < steps_inversions.length; i++) {
            sorting_steps.get(i).getValues();
            for (int j = 0; j < sorted_data.length; j++)
                if (sorted_data[j] != sorting_steps.get(i).getValues()[j])
                    steps_inversions[i]++;
        }
    }

    public int[] getStepsInversions() {
        return steps_inversions;
    }

    public long getMemoryUsed(){
        return memory_used;
    }

    public long getTimeUsed(){
        return time_used;
    }
}
