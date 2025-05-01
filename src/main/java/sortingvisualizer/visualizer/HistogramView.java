package sortingvisualizer.visualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HistogramView extends Application {
    public static int CANVAS_WIDTH;
    public static int CANVAS_HEIGHT;

    public static void setParams(int width, int height) {
        CANVAS_WIDTH = width;
        CANVAS_HEIGHT = height;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sortingvisualizer/visualizer/main-view.fxml"));
        Parent root = loader.load();
        VisualizerController visualizerController = loader.getController();
        visualizerController.loadParameters(CANVAS_WIDTH, CANVAS_HEIGHT);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void launchApp() {
        launch();
    }

}
