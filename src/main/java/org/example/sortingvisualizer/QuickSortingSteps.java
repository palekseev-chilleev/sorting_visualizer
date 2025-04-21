package org.example.sortingvisualizer;

public class QuickSortingSteps extends SortingStep {
    public int recursion_depth;
    public int[] left_array;
    public int[] right_array;
    public int comparative_element;

    public QuickSortingSteps(int[] steps, int curr_recursion_depth, int[] new_left_array, int[] new_right_array,
                             int new_comparative_element) {
        super(steps);
        recursion_depth = curr_recursion_depth;
        left_array = new_left_array;
        right_array = new_right_array;
        comparative_element = new_comparative_element;
    }

}
