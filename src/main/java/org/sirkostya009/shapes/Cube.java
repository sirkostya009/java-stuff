package org.sirkostya009.shapes;

import org.sirkostya009.Shape;

public class Cube implements Shape {
    private double side;

    public Cube(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getVolume() {
        return 6 * side * side;
    }
}
