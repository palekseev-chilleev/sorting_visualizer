package sortingvisualizer.sortingalgorithms;

import sortingvisualizer.sortingcontroller.SortingStep;

public class InsertionSorting extends NumbersSorting {
    public InsertionSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data_res = unsorted_data.clone();
        int max, max_id;
        for (int i = 0; i < sorted_data_res.length; i++) {
            max = sorted_data_res[0];
            max_id = 0;
            for (int j = 0; j < sorted_data_res.length - i; j++) {
                if (sorted_data_res[j] > max) {
                    max = sorted_data_res[j];
                    max_id = j;
                }
            }
            sorted_data_res[max_id] = sorted_data_res[sorted_data_res.length - i - 1];
            sorted_data_res[sorted_data_res.length - i - 1] = max;
        }
        sorted_data = sorted_data_res;
    }

    @Override
    protected void buildSortingSteps() {
        int[] sorted_data_res = unsorted_data.clone();
        int max, max_id;
        for (int i = 0; i < sorted_data_res.length; i++) {
            max = sorted_data_res[0];
            max_id = 0;
            for (int j = 0; j < sorted_data_res.length - i; j++) {
                if (sorted_data_res[j] > max) {
                    max = sorted_data_res[j];
                    max_id = j;
                }
            }
            sorted_data_res[max_id] = sorted_data_res[sorted_data_res.length - i - 1];
            sorted_data_res[sorted_data_res.length - i - 1] = max;
            sorting_steps.add(new SortingStep(sorted_data_res.clone()));
        }
    }

}
