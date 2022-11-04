package org.sirkostya009.shapes;

import org.sirkostya009.Shape;

public record Cylinder(double radius, double height) implements Shape {
    @Override
    public double getVolume() {
        return 2 * Math.PI * radius * height + 2 * Math.PI * radius * radius;
    }
}
