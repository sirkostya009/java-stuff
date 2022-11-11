package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sirkostya009.Shape;

@AllArgsConstructor
public @Data class Cylinder implements Shape {
    private double radius, height;

    @Override
    public double getVolume() {
        return 2 * Math.PI * radius * height + 2 * Math.PI * radius * radius;
    }
}
