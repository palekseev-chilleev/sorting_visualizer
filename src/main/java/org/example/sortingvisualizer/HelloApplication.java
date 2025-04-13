package org.example.sortingvisualizer;


public class HelloApplication {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int ARRAY_LENGTH = 20;

    public static void main(String[] args) {
        SortingController sortingController = new SortingController(ARRAY_LENGTH, 100);
        HistogramView.setParams(WIDTH , HEIGHT, sortingController);
        HistogramView.launchApp();
    }
}

