package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class Cube implements Shape {
    private double side;

    @Override
    public double getVolume() {
        return Math.pow(side, 3);
    }
}
