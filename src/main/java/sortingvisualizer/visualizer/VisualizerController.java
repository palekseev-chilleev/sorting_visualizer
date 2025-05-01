package sortingvisualizer.visualizer;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sortingvisualizer.sortingalgorithms.*;
import sortingvisualizer.sortingcontroller.SortingController;
import sortingvisualizer.sortingcontroller.SortingStep;
import sortingvisualizer.sortingcontroller.SortingType;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class VisualizerController extends Control {
    private static final int CONTROL_PANE_WIDTH = 150;
    private static int BAR_WIDTH = 20;
    private static int CANVAS_WIDTH;
    private static int CANVAS_HEIGHT;
    private static int BAR_SPACING = 1;
    private static int ANIMATION_DELAY = 50;

    private final ArrayList<Control> active_controls = new ArrayList<>();
    @FXML
    private Label arrayLengthLabel;

    @FXML
    private Button nextStepButton;


    @FXML
    private RadioButton bubbleButton;
    @FXML
    private RadioButton selectionButton;
    @FXML
    private RadioButton insertionButton;
    @FXML
    private RadioButton doubleInsertionButton;
    @FXML
    private RadioButton mergeButton;
    @FXML
    private RadioButton quickButton;
    @FXML
    private final ToggleGroup sortingAlgorithmsGroup = new ToggleGroup();

    @FXML
    private Slider arrayLengthSlider;
    @FXML
    private Canvas canvas;


    @FXML
    NumberAxis xAxis;
    @FXML
    NumberAxis yAxis;

    @FXML
    LineChart<Number, Number> orderednessChart;


    @FXML
    private Text statsText;

    private XYChart.Series<Number, Number> series;

    private SortingController sorting_controller;

    private GraphicsContext gc;
    private String statsString;
    private PauseTransition pause;


    public VisualizerController() {
        CANVAS_HEIGHT = 800;
        CANVAS_WIDTH = 600;
    }

    private void setSortingAlgorithm(SortingType type) {
        sorting_controller.setSortingAlgorithm(type);
        updateChart();
        updateStatsText();
        System.out.println("Selected algorithm: " + type);
    }

    public void loadParameters(SortingController new_sorting_controller) {
        sorting_controller = new_sorting_controller;
    }

    @FXML
    public void initialize() {
        bubbleButton.setToggleGroup(sortingAlgorithmsGroup);
        bubbleButton.setUserData(SortingType.BUBBLE);

        selectionButton.setToggleGroup(sortingAlgorithmsGroup);
        selectionButton.setUserData(SortingType.SELECTION);

        insertionButton.setToggleGroup(sortingAlgorithmsGroup);
        insertionButton.setUserData(SortingType.INSERTION);

        doubleInsertionButton.setToggleGroup(sortingAlgorithmsGroup);
        doubleInsertionButton.setUserData(SortingType.DOUBLE_INSERTION);

        mergeButton.setToggleGroup(sortingAlgorithmsGroup);
        mergeButton.setUserData(SortingType.MERGE);

        quickButton.setToggleGroup(sortingAlgorithmsGroup);
        quickButton.setUserData(SortingType.QUICK);

        pause = new PauseTransition(Duration.millis(300));


        sortingAlgorithmsGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                SortingType selectedType = (SortingType) newToggle.getUserData();
                setSortingAlgorithm(selectedType);
            }
        });

        arrayLengthSlider.valueProperty().addListener(this::handleArrayLengthChange);

        series = new XYChart.Series<>();
        series.setName("Inversions");
    }

    public void initDraw() {

        gc = canvas.getGraphicsContext2D();
        drawStep(sorting_controller.getZeroSortingStep());
    }

    private void updateStatsText() {
        statsString = sorting_controller.getStatsText();
        statsText.setText(statsString);
    }


    @FXML
    private void animateSort() {
        setDisabledActiveControls(true);
        Timeline timeline = new Timeline();
        ArrayList<SortingStep> sorting_steps = sorting_controller.sorter_obj.getSortingSteps();
        for (int i = 0; i < sorting_steps.size(); i++) {
            SortingStep step = sorting_steps.get(i);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY * i), e -> {
                drawStep(step);
            });
            sorting_controller.current_step_index = i;
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
        timeline.setOnFinished(event -> setDisabledActiveControls(false));
    }


    @FXML
    private void handleStepButtons(ActionEvent event) {
        Object source = event.getSource();
        if (source == nextStepButton) {
            sorting_controller.current_step_index++;
            if (sorting_controller.current_step_index > sorting_controller.sorter_obj.getSortingSteps().toArray().length)
                sorting_controller.current_step_index = sorting_controller.sorter_obj.getSortingSteps().toArray().length;
            drawStep(sorting_controller.sorter_obj.getSortingStepByIndex(sorting_controller.current_step_index));
        } else {
            sorting_controller.current_step_index--;
            if (sorting_controller.current_step_index < 0)
                sorting_controller.current_step_index = 0;
            drawStep(sorting_controller.sorter_obj.getSortingStepByIndex(sorting_controller.current_step_index));
        }
    }

    @FXML
    private void revertSort() {
        drawStep(sorting_controller.getZeroSortingStep());
        sorting_controller.current_step_index = 0;
    }


    private void handleArrayLengthChange(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
        int new_array_length = newVal.intValue();
        arrayLengthLabel.setText("Array Size: " + new_array_length);
        pause.stop();
        pause.setOnFinished(event -> {
            sorting_controller.setArrayLength(new_array_length);
        });
        pause.playFromStart();
        drawStep(sorting_controller.sorter_obj.getZeroSortingStep());
        updateChart();
        updateStatsText();
    }

    @FXML
    private void shuffleNumbers() {
        sorting_controller.sorter_obj.shuffleArray();
        sorting_controller.unsorted_array = sorting_controller.sorter_obj.getUnsortedData();
        drawStep(sorting_controller.sorter_obj.getZeroSortingStep());
        sorting_controller.current_step_index = 0;
        updateChart();
        updateStatsText();
    }

    private void updateChart() {
        setDisabledActiveControls(true);
        orderednessChart.getData().clear();
        setChartSeries();
        setDisabledActiveControls(false);
    }

    private void setChartSeries() {
        series.getData().clear();
        orderednessChart.getData().clear();
        for (int i = 0; i < sorting_controller.getSortingSteps().size(); i++)
            series.getData().add(new XYChart.Data<>(i, sorting_controller.getStepsInversions()[i]));

        orderednessChart.getData().add(series);
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

    private void calculateBarWidth() {
        BAR_WIDTH = CANVAS_WIDTH / sorting_controller.sorter_obj.getSortedData().length;
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

}
