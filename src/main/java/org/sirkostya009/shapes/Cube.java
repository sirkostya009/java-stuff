package org.sirkostya009.shapes;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sirkostya009.Shape;

@AllArgsConstructor
public @Data class Cube implements Shape {
    private double side;

    @Override
    public double getVolume() {
        return 6 * side * side;
    }
}
