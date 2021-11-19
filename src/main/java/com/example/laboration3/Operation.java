package com.example.laboration3;

import java.util.List;

public abstract class Operation {
    public void undo(List<Shape> shapes){}
}

class Create extends Operation {
    @Override
    public void undo(List<Shape> shapes){
        shapes.remove(shapes.size() - 1);
    }
}

class Replace extends Operation {
    private final int idx;
    private final double oldSize;
    private final String oldColor;

    public Replace(int idx, double oldSize, String oldColor) {
        this.idx = idx;
        this.oldSize = oldSize;
        this.oldColor = oldColor;
    }

    @Override
    public void undo(List<Shape> shapes){
        shapes.get(idx).setSize(oldSize);
        shapes.get(idx).setColor(oldColor);
    }
}