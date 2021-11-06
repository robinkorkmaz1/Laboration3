package com.example.laboration3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    Pane pane;

    @FXML
    RadioButton rbCircle, rbSquare, rbRed, rbYellow, rbBlue;

    @FXML
    TextField tfSize;

    @FXML
    Button btnEdit;

    ToggleGroup groupShape, groupColor;

    List<Shape> shapes;

    boolean editing;

    Shape editingShape;

    @FXML
    public void initialize() {
        groupShape = new ToggleGroup();
        rbCircle.setToggleGroup(groupShape);
        rbCircle.setUserData("circle");
        rbSquare.setToggleGroup(groupShape);
        rbSquare.setUserData("square");

        groupColor = new ToggleGroup();
        rbRed.setToggleGroup(groupColor);
        rbRed.setUserData("red");
        rbYellow.setToggleGroup(groupColor);
        rbYellow.setUserData("yellow");
        rbBlue.setToggleGroup(groupColor);
        rbBlue.setUserData("blue");

        shapes = new ArrayList<>();
        editing = false;
    }

    @FXML
    public void paneClicked(MouseEvent mouseEvent) {
        double x = mouseEvent.getX(), y = mouseEvent.getY();

        if (!editing) {
            String color = groupColor.getSelectedToggle().getUserData().toString();
            double size;
            try {
                size = Double.parseDouble(tfSize.getText());
            } catch (Exception e) {
                size = 25.0;
            }

            if (groupShape.getSelectedToggle().getUserData().equals("circle"))
                shapes.add(new ShapeCircle(x, y, size, color));
            else
                shapes.add(new ShapeSquare(x, y, size, color));

            redrawShapes();
        } else {
            if(findSelectedShape(x, y)) {
                btnEdit.setText("Done");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Tips");
                alert.headerTextProperty().set("One shape is selected, you can edit its size and color now!");
                alert.showAndWait();
            }
            else {
                editing = false;
                btnEdit.setText("Edit");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Tips");
                alert.headerTextProperty().set("No shape is selected!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void undoClicked() {
        if (shapes.size() > 0)
            shapes.remove(shapes.size() - 1);

        redrawShapes();
    }

    @FXML
    public void editClicked() {
        if (!editing) {
            editing = true;
            btnEdit.setText("Selecting");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("Tips");
            alert.headerTextProperty().set("Please select a shape on the canvas.");
            alert.showAndWait();
        } else {
            editing = false;
            btnEdit.setText("Edit");

            String color = groupColor.getSelectedToggle().getUserData().toString();
            double size;
            try {
                size = Double.parseDouble(tfSize.getText());
            } catch (Exception e) {
                size = 25.0;
            }

            editingShape.setSize(size);
            editingShape.setColor(color);
            redrawShapes();
        }
    }

    private boolean findSelectedShape(double x, double y) {
        for (int i = shapes.size()-1; i>=0; i--) {
            Shape current = shapes.get(i);
            if (current instanceof ShapeCircle) {
                if (Math.pow(current.getSize(), 2) >=
                        Math.pow(x - current.getX(), 2) + Math.pow(y - current.getY(), 2)) {
                    editingShape = current;
                    return true;
                }
            } else {
                if (x >= current.getX() - current.getSize() && x <= current.getX() + current.getSize()
                        && y >= current.getY() -current.getSize() && y <= current.getY() + current.getSize()) {
                    editingShape = current;
                    return true;
                }
            }
        }
        return false;
    }

    private void redrawShapes() {
        pane.getChildren().clear();

        for (Shape s : shapes) {
            if (s instanceof ShapeCircle) {
                Circle circle = new Circle();

                circle.setCenterX(s.getX());
                circle.setCenterY(s.getY());
                circle.setRadius(s.getSize());
                if (s.getColor().equals("red"))
                    circle.setFill(Color.RED);
                else if (s.getColor().equals("yellow"))
                    circle.setFill(Color.YELLOW);
                else
                    circle.setFill(Color.BLUE);
                circle.setStroke(Color.BLACK);

                pane.getChildren().add(circle);
            } else {
                Rectangle rectangle = new Rectangle();

                rectangle.setX(s.getX() - s.getSize());
                rectangle.setY(s.getY() - s.getSize());
                rectangle.setWidth(2 * s.getSize());
                rectangle.setHeight(2 * s.getSize());
                if (s.getColor().equals("red"))
                    rectangle.setFill(Color.RED);
                else if (s.getColor().equals("yellow"))
                    rectangle.setFill(Color.YELLOW);
                else
                    rectangle.setFill(Color.BLUE);
                rectangle.setStroke(Color.BLACK);

                pane.getChildren().add(rectangle);
            }
        }
    }

}