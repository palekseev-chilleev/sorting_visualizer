package org.example.sortingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class HistogramView extends Application {
    private static int WIDTH;
    private static int HEIGHT;
    private static int BAR_WIDTH = 20;
    private static SortingController SORTER;
    private GraphicsContext gc;


    public static void setParams(int width, int height, SortingController sortingController) {
        WIDTH = width;
        HEIGHT = height;
        SORTER = sortingController;
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Button startButton = new Button("Start Sort");
//        startButton.setOnAction(e -> animateBubbleSort());
        startButton.setOnAction(e -> animateSort());

        VBox root = new VBox(10, canvas, startButton);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("org.example.sorting_algorithms.Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawArray();
    }

    private void drawArray() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        int[] values = SORTER.getValues();
        for (int i = 0; i < values.length; i++) {
            gc.setFill(Color.CORNFLOWERBLUE);
            gc.fillRect(i * BAR_WIDTH, HEIGHT - values[i], BAR_WIDTH - 2, values[i]);
        }
    }

    private void animateBubbleSort() {
        Timeline timeline = new Timeline();
        int[] values = SORTER.getValues();
        int n = values.length;
        int delay = 5; // Скорость анимации

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                int index = j;
                KeyFrame keyFrame = new KeyFrame(Duration.millis(delay * (i * n + j)), e -> {
                    if (values[index] > values[index + 1]) {
                        int temp = values[index];
                        values[index] = values[index + 1];
                        values[index + 1] = temp;
                        drawArray();
                    }
                });
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        timeline.play();
    }

    private void animateSort() {
        Timeline timeline = new Timeline();
        ArrayList<SortingStep> sorting_steps = SORTER.getSortingSteps();
        int delay = 50; // Скорость анимации
        for (int i = 0; i < sorting_steps.size(); i++) {
            SortingStep step = sorting_steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(delay * i), e -> {
                drawStep(step);
            });
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
    }

    private void drawStep(SortingStep step) {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        int[] values = step.getValues();
        for (int i = 0; i < values.length; i++) {
            gc.setFill(Color.CORNFLOWERBLUE);
            gc.fillRect(i * BAR_WIDTH, HEIGHT - values[i], BAR_WIDTH - 2, values[i]);
        }
    }

    public static void launchApp() {
        launch();
    }

}
