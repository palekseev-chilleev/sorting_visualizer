package org.example.sorting_algorithms;

import java.util.LinkedList;

public class SortingComparator {
    LinkedList<Sorting> objects = new LinkedList<>();
    int[] array_to_sort;

//    public void org.example.sorting_algorithms.SortingComparator(int[] array_to_sort, LinkedList[] classes_list){
//        this.array_to_sort = array_to_sort;
//        for (LinkedList cl: classes_list){
////            this.objects.add(new cl(this.array_to_sort));
//            int b = 0;
//        }
//    }

    public void SortingComparator(int[] array_to_sort){
        this.array_to_sort = array_to_sort;
    }

//    public void createSortingObject(Class sorting_class){
//        Sorting obj = new sorting_class(this.array_to_sort);
//        this.objects.add();
//    }


    public void addSortingObject(Sorting obj){
        this.objects.add(obj);
    }

//    public void createSortingObject(org.example.sorting_algorithms.Sorting obj, ){
//        this.objects.add(obj);
//    }

    public void performSorting(){}


}
