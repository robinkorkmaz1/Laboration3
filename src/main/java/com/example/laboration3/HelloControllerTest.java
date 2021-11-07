package com.example.laboration3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HelloControllerTest {

    @Test
    void undoClicked() {
        HelloController helloController = new HelloController();
        helloController.setShapes(new ArrayList<>() {{
            new Circle(100, 200, 60, "yellow");
        }});
        helloController.setOperations(new ArrayList<>() {{
            new Create();
            new Replace(0, 50, "red");
        }});
        helloController.undoClicked();
        assertTrue(helloController.getShapes().get(0).getSize() == 50 &&
                helloController.getShapes().get(0).getColor() == "red");
        assertTrue(helloController.getOperations().size() == 1);
        helloController.undoClicked();
        assertTrue(helloController.getShapes().size() == 0);
        assertTrue(helloController.getOperations().size() == 0);
    }

    @Test
    void findSelectedShape() {
        HelloController helloController = new HelloController();
        helloController.setShapes(new ArrayList<>() {{
            new Circle(100, 200, 60, "yellow");
        }});
        helloController.setOperations(new ArrayList<>());
        assertTrue(helloController.findSelectedShape(55, 200));
        assertFalse(helloController.findSelectedShape(100, 255));
    }
}