package sortingvisualizer.sortingalgorithms;

import sortingvisualizer.visualizer.SortingStep;

public class SelectionSorting extends NumbersSorting {
    public SelectionSorting(int[] new_unsorted_array) {
        super(new_unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data_res = unsorted_data.clone();
        int min_elem, min_elem_id;
        for (int i = 0; i < sorted_data_res.length; i++) {
            min_elem = sorted_data_res[i];
            min_elem_id = i;
            for (int j = i; j < sorted_data_res.length; j++){
                if (sorted_data_res[j] < min_elem) {
                    min_elem = sorted_data_res[j];
                    min_elem_id = j;
                }
            }
            sorted_data_res[min_elem_id] = sorted_data_res[i];
            sorted_data_res[i] = min_elem;
        }
        sorted_data = sorted_data_res;
    }

    @Override
    protected void buildSortingSteps() {
        int[] sorted_data_res = unsorted_data.clone();
        int min_elem, min_elem_id;
        for (int i = 0; i < sorted_data_res.length; i++) {
            min_elem = sorted_data_res[i];
            min_elem_id = i;
            for (int j = i; j < sorted_data_res.length; j++){
                if (sorted_data_res[j] < min_elem) {
                    min_elem = sorted_data_res[j];
                    min_elem_id = j;
                }
            }
            sorted_data_res[min_elem_id] = sorted_data_res[i];
            sorted_data_res[i] = min_elem;
            sorting_steps.add(new SortingStep(sorted_data_res.clone()));
        }
    }
}
