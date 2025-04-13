package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;

public interface Sorting {

    void performSorting();

    boolean verifySorting(); // true if sorting verified and false if not.

    public ArrayList<SortingStep> getSortingSteps();

    public int[] getUnsortedData();

    public int[] getSortedData();

}
