package sortingvisualizer.sortingalgorithms;

import sortingvisualizer.visualizer.SortingStep;

import java.util.ArrayList;

public interface Sorting {

    public void performSorting();

    boolean verifySorting(); // true if sorting verified and false if not.

    void shuffleArray();

    public int[] getSortedData();

    public int[] getUnsortedData();

    public SortingStep getZeroSortingStep();

    public ArrayList<SortingStep> getSortingSteps();

    public SortingStep getSortingStepByIndex(int index);

    public int[] getStepsInversions();

    public long getMemoryUsed();

    public long getTimeUsed();


}
