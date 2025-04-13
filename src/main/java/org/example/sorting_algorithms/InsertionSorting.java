package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;

public class InsertionSorting extends NumbersSorting {
    public InsertionSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        int max, max_id;
        for (int i = 0; i < sorted_data.length; i++) {
            max = sorted_data[0];
            max_id = 0;
            for (int j = 0; j < sorted_data.length - i; j++) {
                if (sorted_data[j] > max) {
                    max = sorted_data[j];
                    max_id = j;
                }
            }
            sorted_data[max_id] = sorted_data[sorted_data.length - i - 1];
            sorted_data[sorted_data.length - i - 1] = max;
        }
        this.sorted_data = sorted_data;
    }

}
