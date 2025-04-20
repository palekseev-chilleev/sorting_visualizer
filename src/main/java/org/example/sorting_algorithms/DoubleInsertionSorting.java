package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

public class DoubleInsertionSorting extends NumbersSorting {

    public DoubleInsertionSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data_res = unsorted_data.clone();
        int max, max_id, min, min_id;
        for (int i = 0; i < sorted_data_res.length - i; i++) {
            max = sorted_data_res[i];
            max_id = i;
            min = sorted_data_res[i];
            min_id = i;

            for (int j = i; j < sorted_data_res.length - i; j++) {
                if (sorted_data_res[j] > max) {
                    max = sorted_data_res[j];
                    max_id = j;
                }
                if (sorted_data_res[j] < min) {
                    min = sorted_data_res[j];
                    min_id = j;
                }
            }
            sorted_data_res[max_id] = sorted_data_res[sorted_data_res.length - i - 1];
            sorted_data_res[sorted_data_res.length - i - 1] = max;
            if (min_id == sorted_data_res.length - i - 1) {
                min_id = max_id;
            }
            sorted_data_res[min_id] = sorted_data_res[i];
            sorted_data_res[i] = min;
            sorting_steps.add(new SortingStep(sorted_data_res.clone()));
        }
        this.sorted_data = sorted_data_res;
    }
}
