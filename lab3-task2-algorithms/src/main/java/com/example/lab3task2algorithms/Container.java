package com.example.lab3task2algorithms;
import com.example.lab3task2algorithms.MyRectangle;

import java.util.ArrayList;
import java.util.List;

class Container {
    int width;
    int height;
    List<MyRectangle> rectangles;

    public Container(int width, int height) {
        this.width = width;
        this.height = height;
        this.rectangles = new ArrayList<>();
    }

    public boolean addRectangle(MyRectangle r) {
        if (r.width <= width && r.height <= height) {
            r.x = 0;
            r.y = 0;
            rectangles.add(r);
            return true;
        }
        return false;
    }

    public int getOccupiedArea() {
        int area = 0;
        for (MyRectangle r : rectangles) {
            area += r.width * r.height;
        }
        return area;
    }
}