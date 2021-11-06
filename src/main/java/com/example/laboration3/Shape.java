package com.example.laboration3;

public abstract class Shape {
    private double x;
    private double y;
    private double size;
    private String color;

    public Shape(double x, double y, double size, String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

class ShapeCircle extends Shape{
    public ShapeCircle(double x, double y, double size, String color) {
        super(x, y, size, color);
    }
}

class ShapeSquare extends Shape{
    public ShapeSquare(double x, double y, double size, String color) {
        super(x, y, size, color);
    }
}