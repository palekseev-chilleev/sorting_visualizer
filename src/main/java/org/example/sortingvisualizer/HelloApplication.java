package org.example.sortingvisualizer;


public class HelloApplication {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static void main(String[] args) {
        HistogramView.setParams(WIDTH - 200 , HEIGHT - 200);
        HistogramView.launchApp();
    }
}

