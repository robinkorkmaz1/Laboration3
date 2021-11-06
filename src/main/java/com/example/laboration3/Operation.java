package com.example.laboration3;

public abstract class Operation { }

class Create extends Operation { }

class Replace extends Operation {
    private int idx;
    private double oldSize;
    private String oldColor;

    public Replace(int idx, double oldSize, String oldColor) {
        this.idx = idx;
        this.oldSize = oldSize;
        this.oldColor = oldColor;
    }

    public int getIdx() {
        return idx;
    }

    public double getOldSize() {
        return oldSize;
    }

    public String getOldColor() {
        return oldColor;
    }
}