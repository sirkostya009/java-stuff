package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sirkostya009.Shape;

@AllArgsConstructor
public @Data class Sphere implements Shape {
    private double radius;

    @Override
    public double getVolume() {
        return 4 * Math.PI * radius * radius;
    }
}
