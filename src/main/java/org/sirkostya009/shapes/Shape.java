package org.sirkostya009.shapes;

public interface Shape extends Comparable<Shape> {
    double getVolume();

    @Override
    default int compareTo(Shape o) {
        return Double.compare(getVolume(), o.getVolume());
    }
}
