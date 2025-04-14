package org.example.sortingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.abs;


public class HistogramView extends Application {
    private static int BAR_WIDTH = 20;
    private static final int CONTROL_PANE_WIDTH = 150;
    private static int CANVAS_WIDTH;
    private static int CANVAS_HEIGHT;
    private static int BAR_SPACING = 2;
    private static SortingController SORTER;
    private GraphicsContext gc;


    public static void setParams(int width, int height, SortingController sortingController) {
        CANVAS_WIDTH = width;
        CANVAS_HEIGHT = height;
        SORTER = sortingController;
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        gc = canvas.getGraphicsContext2D();

        Button startButton = new Button("Start Sort");
        startButton.setOnAction(e -> animateSort());

        Button revertButton = new Button("Revert Sort");
        revertButton.setOnAction(e -> revertSort());

        Button shuffleButton = new Button("Shuffle Sort");
        shuffleButton.setOnAction(e -> shuffleNumbers());

        HBox root = new HBox();
        root.setSpacing(20); // расстояние между холстом и кнопкой
        root.setPadding(new Insets(20)); // отступы от краёв

        VBox controlPanel = new VBox(startButton, revertButton, shuffleButton);
        controlPanel.setSpacing(10);
        controlPanel.setAlignment(Pos.TOP_CENTER); // выравнивание по центру
        root.getChildren().addAll(canvasWrapper, controlPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + CONTROL_PANE_WIDTH + 40, CANVAS_HEIGHT + 40);

        primaryStage.setTitle("org.example.sorting_algorithms.Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        calculateBarWidth();
        drawStep(SORTER.getZeroSortingStep());
    }

    private void revertSort() {
        drawStep(SORTER.getZeroSortingStep());
    }

    private void shuffleNumbers() {
        SORTER.shuffleArray();
        drawStep(SORTER.getZeroSortingStep());
    }

    private void calculateBarWidth() {
        BAR_WIDTH = CANVAS_WIDTH / SORTER.getValues().length;
    }

//    private void drawArray() {
//        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
//        int[] values = SORTER.getValues();
//        values = normalizeValues(values,1, CANVAS_HEIGHT);
//        for (int i = 0; i < values.length; i++) {
//            gc.setFill(Color.CORNFLOWERBLUE);
//            gc.fillRect(i * BAR_WIDTH, CANVAS_HEIGHT - values[i], BAR_WIDTH - BAR_SPACING, values[i]);
//        }
//    }

    private int[] normalizeValues(int[] array, int newMin, int newMax) {
        int[] normalized = new int[array.length];

        // Найдём текущие минимальное и максимальное значения
        int oldMin = array[0];
        int oldMax = array[0];
        for (int value : array) {
            if (value < oldMin) oldMin = value;
            if (value > oldMax) oldMax = value;
        }
        // Нормализация каждого значения
        for (int i = 0; i < array.length; i++) {
            normalized[i] = (int) Math.round(
                    (array[i] - oldMin) * 1.0 / (oldMax - oldMin) * (newMax - newMin) + newMin
            );
        }
        return normalized;
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
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        int[] values = step.getValues();
        values = normalizeValues(values,1, CANVAS_HEIGHT);
        calculateBarWidth();
//        int side_spacing = abs(CANVAS_WIDTH - (values.length * (BAR_WIDTH + BAR_SPACING) - BAR_SPACING)) / 2;
        for (int i = 0; i < values.length; i++) {
            double intensity = (double) values[i] / CANVAS_HEIGHT;
            Color color = Color.color(0.39, 0.58, 0.93 * intensity);

//            gc.setFill(Color.CORNFLOWERBLUE);
            gc.setFill(color);
            gc.fillRect(i * BAR_WIDTH, CANVAS_HEIGHT - values[i], BAR_WIDTH - BAR_SPACING, values[i]);
        }
    }

    public static void launchApp() {
        launch();
    }

}
