package com.marsrovers.models;

import java.util.LinkedList;

public class Surface {

    private int extremeX;
    private int extremeY;
    private LinkedList<Rover> rovers = new LinkedList<>();

    public int getExtremeX() {
        return extremeX;
    }

    public void setExtremeX(int extremeX) {
        this.extremeX = extremeX;
    }

    public int getExtremeY() {
        return extremeY;
    }

    public void setExtremeY(int extremeY) {
        this.extremeY = extremeY;
    }

    public LinkedList<Rover> getRovers() {
        return rovers;
    }
}
