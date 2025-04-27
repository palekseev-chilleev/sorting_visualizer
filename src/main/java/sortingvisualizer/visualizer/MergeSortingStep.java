package sortingvisualizer.visualizer;

public class MergeSortingStep extends SortingStep{
    public int recursion_depth;

    public MergeSortingStep(int[] steps, int curr_recursion_depth) {
        super(steps);
        recursion_depth = curr_recursion_depth;
    }

}
