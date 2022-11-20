package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Cylinder implements Shape {
    private double radius, height;

    @Override
    public double getVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }
}
