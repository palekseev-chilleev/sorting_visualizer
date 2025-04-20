package org.example.sortingvisualizer;

public class SortingStep {
    private final int[] values;
    private int sorting_step_id;

    public int[] getValues(){
        return this.values;
    }

    public SortingStep(int[] steps) {
        this.values = steps;
    }

    public SortingStep(int[] steps, int step_id) {
        this.values = steps;
        this.sorting_step_id = step_id;
    }
}
