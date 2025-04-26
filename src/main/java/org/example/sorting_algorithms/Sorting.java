package org.example.sorting_algorithms;

import org.example.sortingvisualizer.SortingStep;

import java.util.ArrayList;

public interface Sorting {

    public void performSorting();

    boolean verifySorting(); // true if sorting verified and false if not.

    void shuffleArray();

    public int[] getSortedData();

    public SortingStep getZeroSortingStep();

    public ArrayList<SortingStep> getSortingSteps();

    public SortingStep getSortingStepByIndex(int index);

    public int[] getStepsInversions();

    public long getMemoryUsed();
    public long getTimeUsed();


}
