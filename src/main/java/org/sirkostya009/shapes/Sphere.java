package org.sirkostya009.shapes;

import org.sirkostya009.Shape;

public record Sphere(double radius) implements Shape {
    @Override
    public double getVolume() {
        return 4 * Math.PI * radius * radius;
    }
}
