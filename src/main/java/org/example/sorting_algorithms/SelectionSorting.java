package org.example.sorting_algorithms;

public class SelectionSorting extends NumbersSorting {
    public SelectionSorting(int[] unsorted_array) {
        super(unsorted_array);
    }

    @Override
    public void performSorting() {
        int[] sorted_data = this.unsorted_data.clone();
        int min_elem, min_elem_id;
        for (int i = 0; i < sorted_data.length; i++) {
            min_elem = sorted_data[i];
            min_elem_id = i;
            for (int j = i; j < sorted_data.length; j++){
                if (sorted_data[j] < min_elem) {
                    min_elem = sorted_data[j];
                    min_elem_id = j;
                }
            }
            sorted_data[min_elem_id] = sorted_data[i];
            sorted_data[i] = min_elem;
        }
        this.sorted_data = sorted_data;
    }
}
