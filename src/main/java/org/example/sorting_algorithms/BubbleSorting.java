package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;


public class BubbleSorting extends NumbersSorting {
    public BubbleSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data_res = unsorted_data.clone();
        int swap;
        for (int i = 0; i < sorted_data_res.length; i++) {
            for (int j = 0; j < sorted_data_res.length - i - 1; j++) {
                if (sorted_data_res[j + 1] < sorted_data_res[j]) {
                    swap = sorted_data_res[j];
                    sorted_data_res[j] = sorted_data_res[j + 1];
                    sorted_data_res[j + 1] = swap;
                }
            }
            sorting_steps.add(new SortingStep(sorted_data_res.clone()));
        }
        sorted_data = sorted_data_res;
    }

}
