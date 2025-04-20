package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSorting extends NumbersSorting {
    private int recursion_depth = 0;
    ArrayList<SortingStep> recursion_steps;

    public MergeSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        this.recursion_steps = new ArrayList<>();
        sorted_data = recursiveMergeSorting(sorted_data);
        this.sorted_data = sorted_data;
        this.buildSortingSteps();
    }

    private void buildSortingSteps(){
        int sorting_steps_length = (int) Math.sqrt(sorted_data.length);
        int step_index = this.unsorted_data.length;
//        for (int i = 1; i <= sorting_steps_length; i++) {
        while (!this.recursion_steps.isEmpty()){
            // finding max length of recursive steps
            int max_length = -1;
            for (SortingStep s : recursion_steps) {
                if (s.getValues().length > max_length)
                    max_length = s.getValues().length;
            }

            int[] step = {};
            ArrayList<SortingStep> steps_to_delete = new ArrayList<>();
            for (SortingStep s : recursion_steps) {
                if (s.getValues().length == max_length){
//                if (s.getValues().length == max_length || s.getValues().length == max_length - 1){
                    step = concatArrays(step, s.getValues());
                    steps_to_delete.add(s);
                }
                else
                    continue;
                int a = 11;
            }

            this.sorting_steps.add(new SortingStep(step));
            for (SortingStep s : steps_to_delete){
                recursion_steps.remove(s);
            }
            int a = 0;

        }
    }

    private int[] recursiveMergeSorting(int[] sub_array) {
        this.recursion_depth++;
        recursion_steps.add(new SortingStep(sub_array.clone()));
        if (sub_array.length <= 1) {
            return sub_array;
        } else {
            int middle_index = sub_array.length / 2;
            int[] sub_array_a = this.recursiveMergeSorting(Arrays.copyOfRange(sub_array, 0, middle_index));
            int[] sub_array_b = this.recursiveMergeSorting(Arrays.copyOfRange(sub_array, middle_index, sub_array.length));
            return this.mergeSubArrays(sub_array_a, sub_array_b);
        }
    }

    protected int[] concatArrays(int[] array_a, int[] array_b) {
        int[] result = new int[array_a.length + array_b.length];
        System.arraycopy(array_a, 0, result, 0, array_a.length);
        System.arraycopy(array_b, 0, result, array_a.length, array_b.length);
        return result;
    }

    private int[] mergeSubArrays(int[] sub_array_a, int[] sub_array_b) {
        int[] result = new int[sub_array_a.length + sub_array_b.length];
        int a = 0;
        int b = 0;
        for (int r = 0; r < result.length; r++) {
            if (a == sub_array_a.length) {
                result[r] = sub_array_b[b];
                b++;
                continue;
            }
            if (b == sub_array_b.length) {
                result[r] = sub_array_a[a];
                a++;
                continue;
            }
            if (sub_array_a[a] > sub_array_b[b]) {
                result[r] = sub_array_b[b];
                b++;
            } else {
                result[r] = sub_array_a[a];
                a++;
            }
        }
        return result;
    }


}
