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
import java.util.Arrays;
import java.util.Random;

public class HelloApplication extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int BAR_WIDTH = 20;
    private int[] values;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Button startButton = new Button("Start Sort");
        startButton.setOnAction(e -> animateBubbleSort());

        VBox root = new VBox(10, canvas, startButton);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle("Sorting Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();

        generateRandomArray();
        drawArray();
    }

    private void generateRandomArray() {
        Random rand = new Random();
        values = new int[WIDTH / BAR_WIDTH];
        for (int i = 0; i < values.length; i++) {
            values[i] = rand.nextInt(HEIGHT - 50) + 50;
        }
    }

    private void drawArray() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < values.length; i++) {
            gc.setFill(Color.CORNFLOWERBLUE);
            gc.fillRect(i * BAR_WIDTH, HEIGHT - values[i], BAR_WIDTH - 2, values[i]);
        }
    }

    private void animateBubbleSort() {
        Timeline timeline = new Timeline();
        int n = values.length;
        int delay = 50; // Скорость анимации

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

    public static void main(String[] args) {
        launch(args);
    }
}


//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class HelloApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}