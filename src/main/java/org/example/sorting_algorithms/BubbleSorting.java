package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;


public class BubbleSorting extends NumbersSorting {
    public BubbleSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        int swap;
        for (int i = 0; i < sorted_data.length; i++) {
            for (int j = 0; j < sorted_data.length - i - 1; j++) {
                if (sorted_data[j + 1] < sorted_data[j]) {
                    swap = sorted_data[j];
                    sorted_data[j] = sorted_data[j + 1];
                    sorted_data[j + 1] = swap;
                }
            }
//            List<SortingStep> step = new ArrayList<SortingStep>(sorted_data);
            sorting_steps.add(new SortingStep(sorted_data.clone()));
        }
        this.sorted_data = sorted_data;
    }

}
