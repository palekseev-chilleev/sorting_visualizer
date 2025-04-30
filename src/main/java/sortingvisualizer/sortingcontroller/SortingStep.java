package sortingvisualizer.sortingcontroller;

public class SortingStep {
    protected final int[] values;

    public int[] getValues(){
        return values;
    }

    public SortingStep(int[] steps) {
        values = steps;
    }

}
