package sortingvisualizer.visualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sortingvisualizer.sortingcontroller.SortingController;

import java.io.IOException;
import java.util.ArrayList;

public class HistogramView extends Application {
    private static int max_array_length;
    private static final int CONTROL_PANE_WIDTH = 150;
    private static int CANVAS_WIDTH;
    private static int CANVAS_HEIGHT;


    private static SortingController sorting_controller;


    private VisualizerController visualizerController;



    public static void setParams(int width, int height) {
        CANVAS_WIDTH = width;
        CANVAS_HEIGHT = height;

        /* Max array length is limited by canvas width.
        Considering 1 pixel is a space between histogram
        columns and 1 pixel as a column width, max array
        length is canvas width/2. Additional 2 pixels are
         just to have some space between border of canvas
         and columns. */
        max_array_length = CANVAS_WIDTH / 2 - 2;
        sorting_controller = new SortingController(max_array_length / 2);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sortingvisualizer/visualizer/main-view.fxml"));
        Parent root = loader.load();
        visualizerController = loader.getController();
        visualizerController.loadParameters(sorting_controller);


        Scene scene = new Scene(root, CANVAS_WIDTH + CONTROL_PANE_WIDTH + 400, CANVAS_HEIGHT + 100);
        primaryStage.setTitle("Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        visualizerController.initDraw();

//        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
//        StackPane canvasWrapper = new StackPane(canvas);
//        canvasWrapper.setStyle("-fx-border-color: black; -fx-border-width: 2;");
//
//        gc = canvas.getGraphicsContext2D();
//
//        pause = new PauseTransition(Duration.millis(300));
//
//        Slider arrayLengthSlider = new Slider(10, max_array_length, sorting_controller.array_length);
//        arrayLengthSlider.setPrefWidth(CANVAS_WIDTH);
//        arrayLengthSlider.setMajorTickUnit(100);
//        arrayLengthSlider.setMinorTickCount(10);
//        arrayLengthSlider.setBlockIncrement(10);
//        arrayLengthSlider.setShowTickLabels(true);
//        arrayLengthSlider.setShowTickMarks(true);
//        active_controls.add(arrayLengthSlider);
//
//        arrayLengthLabel = new Label("Array Size: " + sorting_controller.array_length);
//
//        arrayLengthSlider.valueProperty().addListener(this::handleArrayLengthChange);
//
//        NumberAxis xAxis = new NumberAxis();
//        xAxis.setLabel("Step");
//
//        NumberAxis yAxis = new NumberAxis();
//        yAxis.setLabel("Inversions");
//
//        orderednessChart = new LineChart<>(xAxis, yAxis);
//        orderednessChart.setTitle("Array Orderedness Over Steps");
//        orderednessChart.setCreateSymbols(false);
//        orderednessChart.setLegendVisible(false);
//
//        series = new XYChart.Series<>();
//        series.setName("Inversions");
//        setChartSeries();
//
//        statsText = new Text(statsString);
//        statsText.setFont(Font.font(16));
//        statsText.setFill(Color.BLACK);
//
//        HBox root = new HBox();
//        root.setSpacing(20);
//        root.setPadding(new Insets(20));
//
//        HBox navigationBox = new HBox();
//        navigationBox.getChildren().addAll(createNavigationButtons());
//
//        VBox arrayLengthBox = new VBox(arrayLengthSlider, arrayLengthLabel);
//
//        HBox lowControlPanel = new HBox(arrayLengthBox);
//
//        VBox canvasAndLowControls = new VBox();
//        canvasAndLowControls.setSpacing(10);
//        canvasAndLowControls.getChildren().addAll(canvasWrapper, lowControlPanel);
//
//        VBox buttonsBox = new VBox();
//        buttonsBox.getChildren().addAll(createActionButtons());
//        buttonsBox.getChildren().addAll(navigationBox);
//        buttonsBox.setSpacing(10);
//        buttonsBox.setAlignment(Pos.CENTER);
//        VBox radioButtonsBox = new VBox();
//        radioButtonsBox.getChildren().addAll(createRadioButtons());
//        radioButtonsBox.setSpacing(10);
//        radioButtonsBox.setAlignment(Pos.BASELINE_LEFT);
//        HBox allButtonsBox = new HBox(buttonsBox, radioButtonsBox);
//        allButtonsBox.setSpacing(10);
//        allButtonsBox.setAlignment(Pos.CENTER);
//
//        updateStatsText();
//        VBox chartAndStats = new VBox(orderednessChart, statsText);
//        chartAndStats.setSpacing(10);
//
//        VBox rightControlPanel = new VBox(allButtonsBox, chartAndStats);
//
//        rightControlPanel.setSpacing(10);
//        rightControlPanel.setAlignment(Pos.TOP_CENTER);
//
//        root.getChildren().addAll(canvasAndLowControls, rightControlPanel);
//
//        Scene scene = new Scene(root, CANVAS_WIDTH + CONTROL_PANE_WIDTH + 400, CANVAS_HEIGHT + 100);
//
//        primaryStage.setTitle("sorting_visualizer.sorting_algorithms.Sorting Visualizer");
//        primaryStage.setScene(scene);
//        primaryStage.sizeToScene();
//        primaryStage.show();
//
//        calculateBarWidth();
//        drawStep(sorting_controller.getZeroSortingStep());
//        sorting_controller.current_step_index = 0;
    }



    public static void launchApp() {
        launch();
    }

}
