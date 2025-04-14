package org.example.sortingvisualizer;


public class HelloApplication {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int ARRAY_LENGTH = 100;

    public static void main(String[] args) {
        SortingController sortingController = new SortingController(ARRAY_LENGTH, 1000);
        HistogramView.setParams(WIDTH - 200 , HEIGHT - 200, sortingController);
        HistogramView.launchApp();
    }
}

