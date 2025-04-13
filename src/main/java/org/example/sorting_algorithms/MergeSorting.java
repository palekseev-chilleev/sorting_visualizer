package org.example.sorting_algorithms;

import java.util.Arrays;

public class MergeSorting extends NumbersSorting {

    public MergeSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        sorted_data = recursiveMergeSorting(sorted_data);
        this.sorted_data = sorted_data;
    }

    private int[] recursiveMergeSorting(int[] sub_array) {
        if (sub_array.length <= 1) {
            return sub_array;
        } else {
            int middle_index = sub_array.length / 2;
            int[] sub_array_a = this.recursiveMergeSorting(Arrays.copyOfRange(sub_array, 0, middle_index));
            int[] sub_array_b = this.recursiveMergeSorting(Arrays.copyOfRange(sub_array, middle_index, sub_array.length));
            return this.mergeSubArrays(sub_array_a, sub_array_b);
        }
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
