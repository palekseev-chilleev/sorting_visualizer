package org.example.sortingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sorting_algorithms.*;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

enum SortType {
    BUBBLE, INSERTION, QUICK, DOUBLE_INSERTION, SELECTION, MERGE
}


public class HistogramView extends Application {
    private static int BAR_WIDTH = 20;
    private static final int CONTROL_PANE_WIDTH = 150;
    private static int CANVAS_WIDTH;
    private static int CANVAS_HEIGHT;
    private static int BAR_SPACING = 2;

    private GraphicsContext gc;
    private static int[] unsorted_array;
    //    private int[] sorted_array;
    private static Sorting sorter_obj;
    private static int current_step_index;

    public static void setParams(int width, int height) {
        CANVAS_WIDTH = width;
        CANVAS_HEIGHT = height;
//        generateRandomArray((CANVAS_WIDTH/2)-30, 1000);
        generateRandomArray(270, 1000);
//        generateRandomArray(10, 1000);

        sorter_obj = new BubbleSorting(unsorted_array);
        current_step_index = 0;
    }

    private static void generateRandomArray(int length, int maxValue) {
        Random rand = new Random();
        unsorted_array = new int[length];
        for (int i = 0; i < unsorted_array.length; i++) {
            unsorted_array[i] = rand.nextInt(maxValue) + 50;
        }
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

        ToggleGroup sortingAlgorithmsGroup = new ToggleGroup();
        RadioButton bubbleButton = new RadioButton("Bubble");
        bubbleButton.setToggleGroup(sortingAlgorithmsGroup);
        bubbleButton.setUserData(SortType.BUBBLE);

        RadioButton insertionButton = new RadioButton("Insertion");
        insertionButton.setToggleGroup(sortingAlgorithmsGroup);
        insertionButton.setUserData(SortType.INSERTION);

        RadioButton quickButton = new RadioButton("Quick");
        quickButton.setToggleGroup(sortingAlgorithmsGroup);
        quickButton.setUserData(SortType.QUICK);

        RadioButton selectionButton = new RadioButton("Selection");
        selectionButton.setToggleGroup(sortingAlgorithmsGroup);
        selectionButton.setUserData(SortType.SELECTION);

        RadioButton mergeButton = new RadioButton("Merge");
        mergeButton.setToggleGroup(sortingAlgorithmsGroup);
        mergeButton.setUserData(SortType.MERGE);

        RadioButton doubleInsertionButton = new RadioButton("Double Insertion");
        doubleInsertionButton.setToggleGroup(sortingAlgorithmsGroup);
        doubleInsertionButton.setUserData(SortType.DOUBLE_INSERTION);

        bubbleButton.setSelected(true);

        sortingAlgorithmsGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                SortType selectedType = (SortType) newToggle.getUserData();
                setSortingAlgorithm(selectedType);
            }
        });

        Button nextStepButton = new Button("Next step");
        nextStepButton.setOnAction(event -> handleStepButtons(true));

        Button prevStepButton = new Button("Prev step");
        prevStepButton.setOnAction(event -> handleStepButtons(false));

        HBox root = new HBox();
        root.setSpacing(20); // расстояние между холстом и кнопкой
        root.setPadding(new Insets(20)); // отступы от краёв

        VBox controlPanel = new VBox(startButton, revertButton, shuffleButton, bubbleButton, selectionButton,
                insertionButton, doubleInsertionButton, mergeButton, quickButton, nextStepButton, prevStepButton);
        controlPanel.setSpacing(10);
        controlPanel.setAlignment(Pos.TOP_CENTER); // выравнивание по центру
        root.getChildren().addAll(canvasWrapper, controlPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + CONTROL_PANE_WIDTH + 40, CANVAS_HEIGHT + 40);

        primaryStage.setTitle("org.example.sorting_algorithms.Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        calculateBarWidth();
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
    }

    private void handleStepButtons(boolean forward) {
        if (forward) {
            current_step_index++;
            if (current_step_index > sorter_obj.getSortingSteps().toArray().length)
                current_step_index = sorter_obj.getSortingSteps().toArray().length;
            drawStep(sorter_obj.getSortingStepByIndex(current_step_index));

        } else {
            current_step_index--;
            if (current_step_index < 0)
                current_step_index = 0;
            drawStep(sorter_obj.getSortingStepByIndex(current_step_index));

        }

    }

    private void setSortingAlgorithm(SortType type) {
        switch (type) {
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

        System.out.println("Selected algorithm: " + type);
    }

    private void revertSort() {
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
    }

    private void shuffleNumbers() {
        sorter_obj.shuffleArray();
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
    }

    private void calculateBarWidth() {
        BAR_WIDTH = CANVAS_WIDTH / sorter_obj.getSortedData().length;
    }

    private int[] normalizeValues(int[] array, int newMin, int newMax) {
        int[] normalized = new int[array.length];

        // Local min and max values.
        int oldMin = array[0];
        int oldMax = array[0];
        for (int value : array) {
            if (value < oldMin) oldMin = value;
            if (value > oldMax) oldMax = value;
        }
        // Normalisation of each value
        for (int i = 0; i < array.length; i++) {
            normalized[i] = (int) Math.round(
                    (array[i] - oldMin) * 1.0 / (oldMax - oldMin) * (newMax - newMin) + newMin
            );
        }
        return normalized;
    }

    private void animateSort() {
        Timeline timeline = new Timeline();
        ArrayList<SortingStep> sorting_steps = sorter_obj.getSortingSteps();
        int delay = 50; // Скорость анимации
        for (int i = 0; i < sorting_steps.size(); i++) {
            SortingStep step = sorting_steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(delay * i), e -> {
                drawStep(step);
            });
            current_step_index = i;
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    private void drawStep(SortingStep step) {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        int[] values = step.getValues();
        values = normalizeValues(values, 10, CANVAS_HEIGHT - 10);
        calculateBarWidth();
//        int side_spacing = abs(CANVAS_WIDTH - (values.length * (BAR_WIDTH + BAR_SPACING) - BAR_SPACING)) / 2;
        int side_spacing = abs(CANVAS_WIDTH - (values.length * BAR_WIDTH - BAR_SPACING)) / 2;
        for (int i = 0; i < values.length; i++) {
            double intensity = (double) values[i] / CANVAS_HEIGHT;
            Color color = Color.color(0.19, 0.58, 0.93 * intensity);
            gc.setFill(color);
            gc.fillRect(side_spacing + (i * BAR_WIDTH), CANVAS_HEIGHT - values[i], BAR_WIDTH - BAR_SPACING, values[i]);
        }
    }

    public static void launchApp() {
        launch();
    }

}
