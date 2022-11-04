package org.sirkostya009.shapes;

import org.sirkostya009.Shape;

public record Cube(double side) implements Shape {
    @Override
    public double getVolume() {
        return 6 * side * side;
    }
}
