package org.sirkostya009.shapes;

import org.sirkostya009.Shape;

public class Cylinder implements Shape {
    private double radius, height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getVolume() {
        return 2 * Math.PI * radius * height + 2 * Math.PI * radius * radius;
    }
}
