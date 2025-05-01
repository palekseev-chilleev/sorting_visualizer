package sortingvisualizer.visualizer;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.chart.LineChart;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sortingvisualizer.sortingcontroller.SortingController;
import sortingvisualizer.sortingcontroller.SortingStep;
import sortingvisualizer.sortingcontroller.SortingType;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class VisualizerController extends Control {
    private static int CONTROL_PANE_WIDTH;
    private static int BAR_WIDTH = 20;
    private static int CANVAS_WIDTH;
    private static int CANVAS_HEIGHT;
    private static final int BAR_SPACING = 1;
    private static final int ANIMATION_DELAY = 50;
    private int max_array_length;

    private final ArrayList<Control> active_controls = new ArrayList<>();

    @FXML
    private Label array_length_label;


    @FXML
    private Button start_button;
    @FXML
    private Button revert_button;
    @FXML
    private Button shuffle_button;


    @FXML
    private Button prev_step_button;
    @FXML
    private Button next_step_button;


    @FXML
    private RadioButton bubble_button;
    @FXML
    private RadioButton selection_button;
    @FXML
    private RadioButton insertion_button;
    @FXML
    private RadioButton double_insertion_button;
    @FXML
    private RadioButton merge_button;
    @FXML
    private RadioButton quick_button;
    @FXML
    private final ToggleGroup sorting_algorithms_group = new ToggleGroup();

    @FXML
    private Slider array_length_slider;
    @FXML
    private Canvas canvas;


    @FXML
    NumberAxis x_axis;
    @FXML
    NumberAxis y_axis;

    @FXML
    LineChart<Number, Number> orderedness_chart;

    @FXML
    private Text stats_text;

    private XYChart.Series<Number, Number> series;
    private SortingController sorting_controller;
    private GraphicsContext gc;
    private PauseTransition pause;


    private void setSortingAlgorithm(SortingType type) {
        sorting_controller.setSortingAlgorithm(type);
        updateChart();
        updateStatsText();
        System.out.println("Selected algorithm: " + type);
    }

    public void loadParameters(int new_canvas_width, int new_canvas_height) {
        CANVAS_WIDTH = new_canvas_width;
        CANVAS_HEIGHT = new_canvas_height;
        CONTROL_PANE_WIDTH = new_canvas_width / 2;

        /* Max array length is limited by canvas width.
        Considering 1 pixel is a space between histogram
        columns and 1 pixel as a column width, max array
        length is canvas width/2. Additional 2 pixels are
         just to have some space between border of canvas
         and columns. */
        max_array_length = CANVAS_WIDTH / 2 - 2;
        sorting_controller = new SortingController(max_array_length / 2);
    }

    @FXML
    public void initialize() {
        bubble_button.setToggleGroup(sorting_algorithms_group);
        bubble_button.setUserData(SortingType.BUBBLE);

        selection_button.setToggleGroup(sorting_algorithms_group);
        selection_button.setUserData(SortingType.SELECTION);

        insertion_button.setToggleGroup(sorting_algorithms_group);
        insertion_button.setUserData(SortingType.INSERTION);

        double_insertion_button.setToggleGroup(sorting_algorithms_group);
        double_insertion_button.setUserData(SortingType.DOUBLE_INSERTION);

        merge_button.setToggleGroup(sorting_algorithms_group);
        merge_button.setUserData(SortingType.MERGE);

        quick_button.setToggleGroup(sorting_algorithms_group);
        quick_button.setUserData(SortingType.QUICK);

        pause = new PauseTransition(Duration.millis(300));

        sorting_algorithms_group.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                SortingType selectedType = (SortingType) newToggle.getUserData();
                setSortingAlgorithm(selectedType);
            }
        });

        array_length_slider.valueProperty().addListener(this::handleArrayLengthChange);

        series = new XYChart.Series<>();
        series.setName("Inversions");

        collectActiveControls();

        gc = canvas.getGraphicsContext2D();

        Platform.runLater(this::initDraw);
    }

    private void collectActiveControls(){
        active_controls.add(start_button);
        active_controls.add(revert_button);
        active_controls.add(shuffle_button);
        active_controls.add(prev_step_button);
        active_controls.add(next_step_button);
        active_controls.add(bubble_button);
        active_controls.add(selection_button);
        active_controls.add(insertion_button);
        active_controls.add(double_insertion_button);
        active_controls.add(merge_button);
        active_controls.add(quick_button);
        active_controls.add(array_length_slider);
    }

    public void initDraw() {
        canvas.setWidth(CANVAS_WIDTH);
        canvas.setHeight(CANVAS_HEIGHT);
        array_length_label.setPrefWidth(CANVAS_WIDTH);

        array_length_slider.setMax(max_array_length);
        array_length_slider.setValue((double) max_array_length / 2);
        bubble_button.setSelected(true);
        resetAll();
    }

    private void resetAll() {
        drawStep(sorting_controller.getCurrentSortingStep());
        updateStatsText();
        setChartSeries();
    }

    private void setChartSeries() {
        series.getData().clear();
        orderedness_chart.getData().clear();
        for (int i = 0; i < sorting_controller.getSortingSteps().size(); i++)
            series.getData().add(new XYChart.Data<>(i, sorting_controller.getStepsInversions()[i]));
        orderedness_chart.getData().add(series);
    }

    private void updateStatsText() {
        stats_text.setText(sorting_controller.getStatsText());
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
        if (source == next_step_button) {
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
        array_length_label.setText("Array Size: " + new_array_length);
        pause.stop();
        pause.setOnFinished(event -> {
            sorting_controller.setArrayLength(new_array_length);
        });
        resetAll();
        pause.playFromStart();

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
        orderedness_chart.getData().clear();
        setChartSeries();
        setDisabledActiveControls(false);
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
