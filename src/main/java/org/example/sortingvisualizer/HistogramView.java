package org.example.sortingvisualizer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import org.example.sorting_algorithms.*;

import java.lang.reflect.Constructor;
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
    private static int BAR_SPACING = 1;
    private static int ANIMATION_DELAY = 50;

    private GraphicsContext gc;
    private static int[] unsorted_array;
    private static int array_length;
    private static Sorting sorter_obj;
    private static int current_step_index;

    // depends on canvas width
    private static int max_array_length;

    LineChart<Number, Number> orderednessChart;
    XYChart.Series<Number, Number> series;

    private String statsString;
    private Text statsText;

    private PauseTransition pause;


    private final ArrayList<Control> active_controls = new ArrayList<>();

    public static void setParams(int width, int height) {
        CANVAS_WIDTH = width;
        CANVAS_HEIGHT = height;
        max_array_length = CANVAS_WIDTH / 2 - 2;
        generateRandomArray(max_array_length / 2, 1000);
        sorter_obj = new BubbleSorting(unsorted_array);
        current_step_index = 0;
    }

    private static void generateRandomArray(int length, int maxValue) {
        Random rand = new Random();
        unsorted_array = new int[length];
        for (int i = 0; i < unsorted_array.length; i++) {
            unsorted_array[i] = rand.nextInt(maxValue);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setStyle("-fx-border-color: black; -fx-border-width: 2;");

        gc = canvas.getGraphicsContext2D();

        pause =new PauseTransition(Duration.millis(300));

        Button startButton = new Button("Start Sort");
        startButton.setOnAction(e -> animateSort());
        active_controls.add(startButton);

        Button revertButton = new Button("Revert Sort");
        revertButton.setOnAction(e -> revertSort());
        active_controls.add(revertButton);

        Button shuffleButton = new Button("Shuffle Sort");
        shuffleButton.setOnAction(e -> shuffleNumbers());
        active_controls.add(shuffleButton);

        ToggleGroup sortingAlgorithmsGroup = new ToggleGroup();
        RadioButton bubbleButton = new RadioButton("Bubble");
        bubbleButton.setToggleGroup(sortingAlgorithmsGroup);
        bubbleButton.setUserData(SortType.BUBBLE);
        active_controls.add(bubbleButton);

        RadioButton insertionButton = new RadioButton("Insertion");
        insertionButton.setToggleGroup(sortingAlgorithmsGroup);
        insertionButton.setUserData(SortType.INSERTION);
        active_controls.add(insertionButton);

        RadioButton quickButton = new RadioButton("Quick");
        quickButton.setToggleGroup(sortingAlgorithmsGroup);
        quickButton.setUserData(SortType.QUICK);
        active_controls.add(quickButton);

        RadioButton selectionButton = new RadioButton("Selection");
        selectionButton.setToggleGroup(sortingAlgorithmsGroup);
        selectionButton.setUserData(SortType.SELECTION);
        active_controls.add(selectionButton);

        RadioButton mergeButton = new RadioButton("Merge");
        mergeButton.setToggleGroup(sortingAlgorithmsGroup);
        mergeButton.setUserData(SortType.MERGE);
        active_controls.add(mergeButton);

        RadioButton doubleInsertionButton = new RadioButton("Double Insertion");
        doubleInsertionButton.setToggleGroup(sortingAlgorithmsGroup);
        doubleInsertionButton.setUserData(SortType.DOUBLE_INSERTION);
        active_controls.add(doubleInsertionButton);

        Slider arrayLengthSlider = new Slider(10, max_array_length, unsorted_array.length);
        arrayLengthSlider.setPrefWidth(CANVAS_WIDTH);
        arrayLengthSlider.setMajorTickUnit(100);
        arrayLengthSlider.setMinorTickCount(10);
        arrayLengthSlider.setBlockIncrement(10);
        arrayLengthSlider.setShowTickLabels(true);
        arrayLengthSlider.setShowTickMarks(true);
        active_controls.add(arrayLengthSlider);

        Label arrayLengthLabel = new Label("Array Size: " + unsorted_array.length);

        arrayLengthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            array_length = newVal.intValue();
            arrayLengthLabel.setText("Array Size: " + array_length);

            pause.stop();

            pause.setOnFinished(event -> {
                generateRandomArray(array_length, 1000);
                try {
                    Class<? extends Sorting> sorterClass = sorter_obj.getClass();
                    Constructor<? extends Sorting> constructor = sorterClass.getConstructor(int[].class);
                    sorter_obj = constructor.newInstance(unsorted_array);
                    drawStep(sorter_obj.getZeroSortingStep());
                    current_step_index = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.playFromStart();
            updateChart();
            updateStatsText();
        });

        bubbleButton.setSelected(true);

        sortingAlgorithmsGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                SortType selectedType = (SortType) newToggle.getUserData();
                setSortingAlgorithm(selectedType);
            }
        });

        Button nextStepButton = new Button("Next step");
        nextStepButton.setOnAction(event -> handleStepButtons(true));
        active_controls.add(nextStepButton);

        Button prevStepButton = new Button("Prev step");
        prevStepButton.setOnAction(event -> handleStepButtons(false));
        active_controls.add(prevStepButton);

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Step");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Inversions");

        orderednessChart = new LineChart<>(xAxis, yAxis);
        orderednessChart.setTitle("Array Orderedness Over Steps");
        orderednessChart.setCreateSymbols(false);
        orderednessChart.setLegendVisible(false);

        series = new XYChart.Series<>();
        series.setName("Inversions");
        setChartSeries();

        statsText = new Text(statsString);
        statsText.setFont(Font.font(16));
        statsText.setFill(Color.BLACK);

        HBox root = new HBox();
        root.setSpacing(20);
        root.setPadding(new Insets(20));

        HBox navigationBox = new HBox(prevStepButton, nextStepButton);

        VBox arrayLengthBox = new VBox(arrayLengthSlider, arrayLengthLabel);

        HBox lowControlPanel = new HBox(arrayLengthBox);

        VBox canvasAndLowControls = new VBox();
        canvasAndLowControls.setSpacing(10);
        canvasAndLowControls.getChildren().addAll(canvasWrapper, lowControlPanel);

        VBox buttonsBox = new VBox(startButton, revertButton, shuffleButton, navigationBox);
        buttonsBox.setSpacing(10);
        buttonsBox.setAlignment(Pos.CENTER);
        VBox radioButtonsBox = new VBox(bubbleButton, selectionButton,
                insertionButton, doubleInsertionButton, mergeButton, quickButton);
        radioButtonsBox.setSpacing(10);
        radioButtonsBox.setAlignment(Pos.BASELINE_LEFT);
        HBox allButtonsBox = new HBox(buttonsBox, radioButtonsBox);
        allButtonsBox.setSpacing(10);
        allButtonsBox.setAlignment(Pos.CENTER);

        updateStatsText();
        VBox chartAndStats = new VBox(orderednessChart, statsText);
        chartAndStats.setSpacing(10);

        VBox rightControlPanel = new VBox(allButtonsBox, chartAndStats);

        rightControlPanel.setSpacing(10);
        rightControlPanel.setAlignment(Pos.TOP_CENTER);

        root.getChildren().addAll(canvasAndLowControls, rightControlPanel);

        Scene scene = new Scene(root, CANVAS_WIDTH + CONTROL_PANE_WIDTH + 400, CANVAS_HEIGHT + 100);

        primaryStage.setTitle("org.example.sorting_algorithms.Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();

        calculateBarWidth();
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
    }

    private void updateStatsText() {
        statsString = String.format(
                "%-15s\t\t%s\n%-15s %s\n%-15s\t%s",
                "Steps:", sorter_obj.getSortingSteps().size(),
                "Memory used:", (sorter_obj.getMemoryUsed() / 1024) + " KB",
                "Execution time:", sorter_obj.getTimeUsed() + " ns"
        );
        statsText.setText(statsString);
    }

    private void setChartSeries() {
        series.getData().clear();
        orderednessChart.getData().clear();
        for (int i = 0; i < sorter_obj.getSortingSteps().size(); i++)
            series.getData().add(new XYChart.Data<>(i, sorter_obj.getStepsInversions()[i]));
        orderednessChart.getData().add(series);
    }

    private void updateChart() {
        setDisabledActiveControls(true);
        orderednessChart.getData().clear();
        setChartSeries();
        setDisabledActiveControls(false);
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
        updateChart();
        updateStatsText();
        System.out.println("Selected algorithm: " + type);
    }

    private void revertSort() {
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
    }

    private void shuffleNumbers() {
        sorter_obj.shuffleArray();
        unsorted_array = sorter_obj.getUnsortedData();
        drawStep(sorter_obj.getZeroSortingStep());
        current_step_index = 0;
        updateChart();
        updateStatsText();
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
        setDisabledActiveControls(true);
        Timeline timeline = new Timeline();
        ArrayList<SortingStep> sorting_steps = sorter_obj.getSortingSteps();
        for (int i = 0; i < sorting_steps.size(); i++) {
            SortingStep step = sorting_steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY * i), e -> {
                drawStep(step);
            });
            current_step_index = i;
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
        timeline.setOnFinished(event -> setDisabledActiveControls(false));
    }

    private void setDisabledActiveControls(Boolean v) {
        for (Control c : active_controls) {
            c.setDisable(v);
        }
    }

    private void drawStep(SortingStep step) {
        gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        int[] values = step.getValues();
        values = normalizeValues(values, 10, CANVAS_HEIGHT - 10);
        calculateBarWidth();
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
