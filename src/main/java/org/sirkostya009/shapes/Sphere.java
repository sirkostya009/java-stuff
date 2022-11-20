package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Sphere implements Shape {
    private double radius;

    @Override
    public double getVolume() {
        return (4.0 / 3.0) * Math.PI * Math.pow(radius, 3);
    }
}
