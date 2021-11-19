package com.example.laboration3;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Shape {
    private final double x;
    private final double y;
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

    public double getY() {
        return y;
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

    public void draw(Pane pane) {}
}

class Circle extends Shape{
    public Circle(double x, double y, double size, String color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Pane pane) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();

        circle.setCenterX(getX());
        circle.setCenterY(getY());
        circle.setRadius(getSize());
        if (getColor().equals("red"))
            circle.setFill(Color.RED);
        else if (getColor().equals("yellow"))
            circle.setFill(Color.YELLOW);
        else
            circle.setFill(Color.BLUE);
        circle.setStroke(Color.BLACK);

        pane.getChildren().add(circle);
    }
}

class Square extends Shape{
    public Square(double x, double y, double size, String color) {
        super(x, y, size, color);
    }

    @Override
    public void draw(Pane pane) {
        Rectangle rectangle = new Rectangle();

        rectangle.setX(getX() - getSize());
        rectangle.setY(getY() - getSize());
        rectangle.setWidth(2 * getSize());
        rectangle.setHeight(2 * getSize());
        if (getColor().equals("red"))
            rectangle.setFill(Color.RED);
        else if (getColor().equals("yellow"))
            rectangle.setFill(Color.YELLOW);
        else
            rectangle.setFill(Color.BLUE);
        rectangle.setStroke(Color.BLACK);

        pane.getChildren().add(rectangle);
    }
}
