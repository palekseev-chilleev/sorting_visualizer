package sortingvisualizer.sortingcontroller;

import sortingvisualizer.sortingalgorithms.*;
import sortingvisualizer.sortingalgorithms.ArrayUtils;

import java.util.ArrayList;

import static sortingvisualizer.sortingcontroller.SortingType.*;

public class SortingController {

    public int[] unsorted_array;
    public int array_length;
    public Sorting sorter_obj;
    public int current_step_index;


    public SortingController(int new_array_length) {
        initNewSortingObj(new_array_length, BUBBLE);
    }

    protected void initNewSortingObj(int new_array_length, SortingType new_sorting_type) {
        array_length = new_array_length;
        unsorted_array = ArrayUtils.generateRandomIntArray(new_array_length, 1000);
        current_step_index = 0;
        createSorterObjByType(new_sorting_type);
    }

    protected void createSorterObjByType(SortingType new_sorting_type) {
        switch (new_sorting_type) {
            case BUBBLE:
                sorter_obj = new BubbleSorting(unsorted_array);
                break;
            case SELECTION:
                sorter_obj = new SelectionSorting(unsorted_array);
                break;
            case INSERTION:
                sorter_obj = new InsertionSorting(unsorted_array);
                break;
            case QUICK:
                sorter_obj = new QuickSorting(unsorted_array);
                break;
            case DOUBLE_INSERTION:
                sorter_obj = new DoubleInsertionSorting(unsorted_array);
                break;
            case MERGE:
                sorter_obj = new MergeSorting(unsorted_array);
                break;
        }
    }


    public SortingStep getZeroSortingStep(){
        return sorter_obj.getZeroSortingStep();
    }

    public SortingStep getCurrentSortingStep(){
        return sorter_obj.getSortingStepByIndex(current_step_index);
    }

    public ArrayList<SortingStep> getSortingSteps() {
        return sorter_obj.getSortingSteps();
    }

    public String getStatsText(){
        return String.format(
                "%-15s\t\t%s\n%-15s %s\n%-15s\t%s",
                "Steps:", sorter_obj.getSortingSteps().size(),
                "Memory used:", (sorter_obj.getMemoryUsed() / 1024) + " KB",
                "Execution time:", sorter_obj.getTimeUsed() + " ns"
        );
    }

    public int[] getStepsInversions(){
        return sorter_obj.getStepsInversions();
    }

    public void setSortingAlgorithm(SortingType new_sorting_type){
        initNewSortingObj(array_length, new_sorting_type);
    }

    public void setArrayLength(int new_array_length){
        array_length = new_array_length;
        unsorted_array = ArrayUtils.generateRandomIntArray(new_array_length, 1000);
        current_step_index = 0;
        sorter_obj.init(unsorted_array);
    }

}
