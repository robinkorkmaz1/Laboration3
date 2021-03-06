package com.example.laboration3;

import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.File;
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

    private List<Shape> shapes;

    private List<Operation> operations;

    private boolean editing;

    private int editingShapeIdx;

    private Shape editingShape;

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

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
        operations = new ArrayList<>();
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
                shapes.add(new Circle(x, y, size, color));
            else
                shapes.add(new Square(x, y, size, color));
            operations.add(new Create());

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
        if (operations.size() > 0) {
            Operation last = operations.get(operations.size() - 1);
            last.undo(shapes);
            operations.remove(operations.size()-1);
            redrawShapes();
        }
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

            operations.add(new Replace(editingShapeIdx, editingShape.getSize(), editingShape.getColor()));

            editingShape.setSize(size);
            editingShape.setColor(color);

            redrawShapes();
        }
    }

    @FXML
    public void saveClicked() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Saving image");
        chooser.setInitialDirectory(new File("./"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("image file(*.png)", "*.png");
        chooser.getExtensionFilters().add(filter);
        File file = chooser.showSaveDialog(new Stage());
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("Tips");
            alert.headerTextProperty().set("No file selected!");
            alert.showAndWait();
        } else {
            Image image = pane.snapshot(new SnapshotParameters(), null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Tips");
                alert.headerTextProperty().set("Image saved successfully!");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.titleProperty().set("Tips");
                alert.headerTextProperty().set("Fail to save image!");
                alert.showAndWait();
            }
        }
    }

    public boolean findSelectedShape(double x, double y) {
        for (int i = shapes.size()-1; i>=0; i--) {
            Shape current = shapes.get(i);
            if (current instanceof Circle) {
                if (Math.pow(current.getSize(), 2) >=
                        Math.pow(x - current.getX(), 2) + Math.pow(y - current.getY(), 2)) {
                    editingShapeIdx = i;
                    editingShape = current;
                    return true;
                }
            } else {
                if (x >= current.getX() - current.getSize() && x <= current.getX() + current.getSize()
                        && y >= current.getY() -current.getSize() && y <= current.getY() + current.getSize()) {
                    editingShapeIdx = i;
                    editingShape = current;
                    return true;
                }
            }
        }
        return false;
    }

    public void redrawShapes() {
        pane.getChildren().clear();
        for (Shape s : shapes) {
            s.draw(pane);
        }
    }
}