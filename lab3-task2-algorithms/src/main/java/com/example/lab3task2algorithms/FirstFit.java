package com.example.lab3task2algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FirstFit {
    public static void main(String[] args) {
        int n = 10; // Число прямоугольников
        List<Rectangle> rectangles = generateRectangles(n);

        // Задаем размеры контейнера
        int containerWidth = 50;
        int containerHeight = 50;

        // Создаем контейнер
        Container container = new Container(containerWidth, containerHeight);

        // Выполняем алгоритм First Fit
        for (Rectangle r : rectangles) {
            container.addRectangle(r);
        }

        // Выводим результаты
        System.out.println("First Fit Algorithm");
        System.out.println("Total rectangles packed: " + container.rectangles.size());
        System.out.println("Total occupied area: " + container.getOccupiedArea() + " / " + (containerWidth * containerHeight));
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