package com.example.lab3task2algorithms;
import com.example.lab3task2algorithms.Container;
import com.example.lab3task2algorithms.MyRectangle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PackingVisualization extends Application {

    public List<MyRectangle> generateRectangles(int n) {
        List<MyRectangle> rectangles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int width = rand.nextInt(15) + 5;
            int height = rand.nextInt(15) + 5;
            rectangles.add(new MyRectangle(width, height));
        }
        return rectangles;
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        // Генерируем прямоугольники и выполняем упаковку
        List<MyRectangle> rectangles = generateRectangles(10);
        Container container = new Container(500, 500);
        for (MyRectangle r : rectangles) {
            container.addRectangle(r);
        }

        // Визуализация упакованных прямоугольников
        for (MyRectangle r : container.rectangles) {
            Rectangle rect = new Rectangle(r.x, r.y, r.width, r.height);
            rect.setFill(Color.color(Math.random(), Math.random(), Math.random()));
            root.getChildren().add(rect);
        }

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Packing Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}