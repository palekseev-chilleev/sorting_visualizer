package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

public class DoubleInsertionSorting extends NumbersSorting {

    public DoubleInsertionSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        int max, max_id, min, min_id;
        for (int i = 0; i < sorted_data.length - i; i++) {
            max = sorted_data[i];
            max_id = i;
            min = sorted_data[i];
            min_id = i;

            for (int j = i; j < sorted_data.length - i; j++) {
                if (sorted_data[j] > max) {
                    max = sorted_data[j];
                    max_id = j;
                }
                if (sorted_data[j] < min) {
                    min = sorted_data[j];
                    min_id = j;
                }
            }
            sorted_data[max_id] = sorted_data[sorted_data.length - i - 1];
            sorted_data[sorted_data.length - i - 1] = max;
            if (min_id == sorted_data.length - i - 1) {
                min_id = max_id;
            }
            sorted_data[min_id] = sorted_data[i];
            sorted_data[i] = min;
            sorting_steps.add(new SortingStep(sorted_data.clone()));
        }
        this.sorted_data = sorted_data;
    }
}
