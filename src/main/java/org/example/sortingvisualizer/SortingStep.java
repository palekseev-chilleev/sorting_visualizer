package org.example.sortingvisualizer;

public class SortingStep {
    private final int[] values;
    private int comparativeElementId;

    public int[] getValues(){
        return values;
    }

    public SortingStep(int[] steps) {
        values = steps;
    }
}
