package org.example.sortingvisualizer;

public class QuickSortingSteps {
    public int recursion_depth;
    public int[] left_array;
    public int[] right_array;
    public int comparative_element;

    public QuickSortingSteps(int curr_recursion_depth, int[] new_left_array, int[] new_right_array,
                             int new_comparative_element) {
//        super(null);
        recursion_depth = curr_recursion_depth;
        left_array = new_left_array;
        right_array = new_right_array;
        comparative_element = new_comparative_element;
    }

    protected int[] concatArrays(int[] array_a, int[] array_b) {
        int[] result = new int[array_a.length + array_b.length];
        System.arraycopy(array_a, 0, result, 0, array_a.length);
        System.arraycopy(array_b, 0, result, array_a.length, array_b.length);
        return result;
    }

}
