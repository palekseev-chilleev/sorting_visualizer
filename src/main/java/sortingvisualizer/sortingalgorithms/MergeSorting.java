package sortingvisualizer.sortingalgorithms;

import sortingvisualizer.sortingcontroller.MergeSortingStep;
import sortingvisualizer.sortingcontroller.SortingStep;

import java.util.ArrayList;
import java.util.Arrays;

public class MergeSorting extends NumbersSorting {
    ArrayList<MergeSortingStep> recursion_steps;

    public MergeSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data_res = unsorted_data.clone();
        sorted_data = recursiveMergeSorting(sorted_data_res);
    }

    protected void buildSortingSteps() {
        recursion_steps = new ArrayList<>();
        int[] sorted_data_res = unsorted_data.clone();
        recordSortingSteps(sorted_data_res, 0);

        int total_steps = (int) Math.ceil(Math.log(unsorted_data.length) / Math.log(2));
        for (int i = 0; i < total_steps; i++) {
            int[] step = {};
            for (MergeSortingStep s : recursion_steps) {
                if (s.recursion_depth == i) {
                    step = concatArrays(step, s.getValues());
                }
            }
            if (step.length == unsorted_data.length)
                // Calculated total steps amount may be different from actual.
                // In this case empty step will be added.
                sorting_steps.addFirst(new SortingStep(step));
        }
    }

    private int[] recursiveMergeSorting(int[] sub_array) {
        if (sub_array.length <= 1) {
            return sub_array;
        } else {
            int middle_index = sub_array.length / 2;
            int[] sub_array_a = recursiveMergeSorting(Arrays.copyOfRange(sub_array, 0, middle_index));
            int[] sub_array_b = recursiveMergeSorting(Arrays.copyOfRange(sub_array, middle_index, sub_array.length));
            return mergeSubArrays(sub_array_a, sub_array_b);
        }
    }

    private int[] recordSortingSteps(int[] sub_array, int recursion_depth) {
        recursion_depth++;
        if (sub_array.length <= 1) {
            recursion_steps.add(new MergeSortingStep(sub_array.clone(), recursion_depth));
            return sub_array;
        } else {
            int middle_index = sub_array.length / 2;
            int[] sub_array_a = recordSortingSteps(Arrays.copyOfRange(sub_array, 0, middle_index), recursion_depth);
            int[] sub_array_b = recordSortingSteps(Arrays.copyOfRange(sub_array, middle_index, sub_array.length), recursion_depth);
            return mergeSubArraysWithRecording(sub_array_a, sub_array_b, recursion_depth);
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

    private int[] mergeSubArraysWithRecording(int[] sub_array_a, int[] sub_array_b, int recur_step) {
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
        recursion_steps.add(new MergeSortingStep(result.clone(), recur_step));
        return result;
    }


}
