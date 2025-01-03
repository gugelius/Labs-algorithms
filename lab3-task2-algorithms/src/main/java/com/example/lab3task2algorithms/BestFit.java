package com.example.lab3task2algorithms;

import com.example.lab3task2algorithms.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BestFit {
    public static void main(String[] args) {
        int n = 10; // Число прямоугольников
        List<Rectangle> rectangles = generateRectangles(n);

        // Задаем размеры контейнера
        int containerWidth = 50;
        int containerHeight = 50;

        // Создаем список контейнеров
        List<Container> containerList = new ArrayList<>();
        containerList.add(new Container(containerWidth, containerHeight));

        // Выполняем алгоритм Best Fit
        for (Rectangle r : rectangles) {
            Container bestFitContainer = null;
            int minWasteArea = Integer.MAX_VALUE;

            for (Container c : containerList) {
                if (c.addRectangle(r)) {
                    int wasteArea = (c.width * c.height) - c.getOccupiedArea();
                    if (wasteArea < minWasteArea) {
                        minWasteArea = wasteArea;
                        bestFitContainer = c;
                    }
                    c.rectangles.remove(r);
                }
            }

            if (bestFitContainer == null) {
                bestFitContainer = new Container(containerWidth, containerHeight);
                containerList.add(bestFitContainer);
            }
            bestFitContainer.addRectangle(r);
        }

        // Выводим результаты
        System.out.println("Best Fit Algorithm");
        int totalRectangles = 0;
        int totalOccupiedArea = 0;
        for (Container c : containerList) {
            totalRectangles += c.rectangles.size();
            totalOccupiedArea += c.getOccupiedArea();
        }
        System.out.println("Total rectangles packed: " + totalRectangles);
        System.out.println("Total occupied area: " + totalOccupiedArea + " / " + (containerWidth * containerHeight * containerList.size()));
    }

    public static List<Rectangle> generateRectangles(int n) {
        List<Rectangle> rectangles = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int width = rand.nextInt(15) + 5;
            int height = rand.nextInt(15) + 5;
            rectangles.add(new Rectangle(width, height));
        }
        return rectangles;
    }
}