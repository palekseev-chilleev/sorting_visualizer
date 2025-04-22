package org.example.sorting_algorithms;

import org.example.sortingvisualizer.MergeSortingStep;
import org.example.sortingvisualizer.QuickSortingSteps;
import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;

public class QuickSorting extends NumbersSorting {
    ArrayList<MergeSortingStep> recursion_steps;
    public QuickSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    public void performSorting() {
        recursion_steps = new ArrayList<>();
        int[] sorted_data_res = unsorted_data.clone();
        sorted_data = reqursiveQuickSorting(sorted_data_res, 0);
        buildSortingSteps();
    }

    private void buildSortingSteps() {
        // int total_steps = 2 * (int) Math.floor(Math.log(unsorted_data.length) / Math.log(2));
        // Taking way to much steps prediction just in case.
        int total_steps = (int) Math.sqrt(unsorted_data.length);

        int actual_steps = 0;
        for (MergeSortingStep s : recursion_steps){
            if (s.recursion_depth>actual_steps)
                actual_steps=s.recursion_depth;
        }
        actual_steps++;
        ArrayList<MergeSortingStep> steps_to_delete = new ArrayList<>();

        for (int i = 0; i < actual_steps; i++) {
            int[] step = {};
            for (MergeSortingStep s : recursion_steps) {
                if (s.recursion_depth == i) {
                    step = concatArrays(step, s.getValues());
                    steps_to_delete.add(s);
                }
            }
            if (step.length > 1)
                // Calculated total steps amount may be different from actual.
                // In this case empty step will be added.
                sorting_steps.add(0, new SortingStep(step));
            for (MergeSortingStep s : steps_to_delete) {
                recursion_steps.remove(s);
            }
        }

        sorting_steps.remove(sorting_steps.size() - 1);

    }


//    private void buildSortingSteps() {
//        // int total_steps = 2 * (int) Math.floor(Math.log(unsorted_data.length) / Math.log(2));
//        // Taking way to much steps prediction just in case.
//        int total_steps = (int) Math.sqrt(unsorted_data.length);
//
//        int actual_steps = 0;
//        for (QuickSortingSteps s : recursion_steps){
//            if (s.recursion_depth>actual_steps)
//                actual_steps=s.recursion_depth;
//        }
//        actual_steps++;
//        ArrayList<QuickSortingSteps> steps_to_delete = new ArrayList<>();
//
//        for (int i = 0; i < actual_steps; i++) {
//            int[] step = {};
//            for (QuickSortingSteps s : recursion_steps) {
//                if (s.recursion_depth == i) {
//                    if(s.comparative_element == -1){
////                        step = new int[1];
//                        step = concatArrays(step, s.left_array);
//                    }
//                    else {
//                        step = new int[s.left_array.length + s.right_array.length + 1];
//                        System.arraycopy(s.left_array, 0, step, 0, s.left_array.length);
//                        System.arraycopy(s.right_array, 0, step, s.left_array.length + 1, s.right_array.length);
//                        step[s.left_array.length] = s.comparative_element;
//                    }
//
//                    steps_to_delete.add(s);
//                }
//            }
//            if (step.length > 1)
//                // Calculated total steps amount may be different from actual.
//                // In this case empty step will be added.
//                sorting_steps.add(0, new SortingStep(step));
//            for (QuickSortingSteps s : steps_to_delete) {
//                recursion_steps.remove(s);
//            }
//        }
//
////        sorting_steps.remove(sorting_steps.size() - 1);
//
//    }

    protected int[] concatArrays(int[] array_a, int[] array_b) {
        int[] result = new int[array_a.length + array_b.length];
        System.arraycopy(array_a, 0, result, 0, array_a.length);
        System.arraycopy(array_b, 0, result, array_a.length, array_b.length);
        return result;
    }

    private int[] reqursiveQuickSorting(int[] sub_array, int recursion_depth) {
        recursion_depth++;
        if (sub_array.length <= 1) {
            recursion_steps.add(new MergeSortingStep(sub_array.clone(), recursion_depth));
//            recursion_steps.add(new QuickSortingSteps(
//                    recursion_depth, sub_array.clone(),  null, -1));
            return sub_array;
        } else {
            int comparative_element = sub_array[sub_array.length - 1];
            int less_elements_count = 0;
            // Before creating sub arrays, I have to understand the length of both sub arrays,
            // so I look through sub array twice.
            // I don't want to use dynamic array in this class.
            for (int i : sub_array) {
                if (i < comparative_element)
                    less_elements_count++;
            }
            int[] left_sub_array = new int[less_elements_count];
            int[] right_sub_array = new int[sub_array.length - less_elements_count - 1];
            int l = 0;
            int r = 0;
            for (int i = 0; i < sub_array.length - 1; i++) {
                if (sub_array[i] < comparative_element) {
                    left_sub_array[l] = sub_array[i];
                    l++;
                } else {
                    right_sub_array[r] = sub_array[i];
                    r++;
                }
            }
//            recursion_steps.add(new QuickSortingSteps(
//                    recursion_depth, left_sub_array.clone(),  right_sub_array.clone(), comparative_element));
            int[] result = mergeSubArrays(left_sub_array, comparative_element,
                    right_sub_array, recursion_depth);
            recursion_steps.add(new MergeSortingStep(result.clone(), recursion_depth));
//            int[] ci = new int[1];
//            ci[0] = comparative_element;
//            recursion_steps.add(new MergeSortingStep(ci, recursion_depth + 1));
            //[234, 601, 1015, 546, 976, 292, 526, 926, 349, 253]
            return mergeSubArrays(
                    reqursiveQuickSorting(left_sub_array, recursion_depth), comparative_element,
                    reqursiveQuickSorting(right_sub_array, recursion_depth), recursion_depth);
        }
    }

    private int[] mergeSubArrays(int[] left_sub_array, int middle_element, int[] right_sub_array, int recur_step) {
        int[] result = new int[left_sub_array.length + right_sub_array.length + 1];
        if (left_sub_array.length >= 1)
            System.arraycopy(left_sub_array, 0, result, 0, left_sub_array.length);
        result[left_sub_array.length] = middle_element;
        if (right_sub_array.length >= 1)
            System.arraycopy(right_sub_array, 0, result, left_sub_array.length + 1, right_sub_array.length);
//        recursion_steps.add(new QuickSortingSteps(
//                recur_step, left_sub_array.clone(),  right_sub_array.clone(), middle_element));
//        recursion_steps.add(new MergeSortingStep(result.clone(), recur_step));
        return result;
    }
}
