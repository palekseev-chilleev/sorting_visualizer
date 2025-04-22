package org.example.sorting_algorithms;

import org.example.sortingvisualizer.QuickSortingStep;
import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;

public class QuickSorting extends NumbersSorting {
    ArrayList<QuickSortingStep> recursion_steps;

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
        int actual_steps = 0;
        for (QuickSortingStep s : recursion_steps) {
            if (s.recursion_depth > actual_steps)
                actual_steps = s.recursion_depth;
        }

        for (int i = 0; i < actual_steps; i++) {
            int[] step = sorted_data.clone();
            ArrayList<QuickSortingStep> one_recursion_steps = new ArrayList<>();
            for (QuickSortingStep s : recursion_steps) {
                if (s.recursion_depth == i + 1)
                    if (one_recursion_steps.size() == 0)
                        one_recursion_steps.add(s);
                    else if (s.comparative_element > one_recursion_steps.get(0).comparative_element)
                        one_recursion_steps.addLast(s);
                    else
                        one_recursion_steps.addFirst(s);
            }
            for (QuickSortingStep ors : one_recursion_steps) {
                int comparative_element_id = -1;
                if (comparative_element_id == ors.comparative_element)
                    continue;
                for (int j = 0; step[j] != ors.comparative_element; j++)
                    comparative_element_id = j;
                System.arraycopy(ors.left_array, 0, step, comparative_element_id - ors.left_array.length + 1, ors.left_array.length);
                System.arraycopy(ors.right_array, 0, step, comparative_element_id + 1, ors.right_array.length);
            }
            sorting_steps.add(new SortingStep(step));
        }
    }

    private int[] reqursiveQuickSorting(int[] sub_array, int recursion_depth) {
        recursion_depth++;
        if (sub_array.length <= 1) {
            recursion_steps.add(new QuickSortingStep(
                    recursion_depth, sub_array.clone(), null, -1));
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
            recursion_steps.add(new QuickSortingStep(
                    recursion_depth, left_sub_array.clone(), right_sub_array.clone(), comparative_element));
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
        return result;
    }
}
