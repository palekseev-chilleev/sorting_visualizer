package org.example.sorting_algorithms;

public class QuickSorting extends NumbersSorting {
    public QuickSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        sorted_data = this.reqursiveQuickSorting(sorted_data);
        this.sorted_data = sorted_data;
    }

    private int[] reqursiveQuickSorting(int[] sub_array) {
        if (sub_array.length <= 1) {
            return sub_array;
        } else {
            int comparative_element = sub_array[sub_array.length - 1];
            int less_elements_count = 0;
            // I have to look through sub array twice because I don't want to use dynamic array in this class.
            // The same algorithm with dynamic arrays is implemented in TODO class
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
            return this.mergeSubArrays(this.reqursiveQuickSorting(left_sub_array), comparative_element,
                    this.reqursiveQuickSorting(right_sub_array));
        }
    }

    private int[] mergeSubArrays(int[] left_sub_array, int middle_element, int[] right_sub_array) {
        int[] result = new int[left_sub_array.length + right_sub_array.length + 1];
        if (left_sub_array.length >= 1)
            System.arraycopy(left_sub_array, 0, result, 0, left_sub_array.length);
        result[left_sub_array.length] = middle_element;
        if (right_sub_array.length >= 1)
            System.arraycopy(right_sub_array, 0, result, left_sub_array.length + 1, right_sub_array.length);
        return result;
    }
}
