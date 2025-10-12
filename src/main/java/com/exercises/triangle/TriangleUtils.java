package com.exercises.triangle;

import java.util.Objects;

/*
Acquired through:
https://github.com/lucas-ifsp/software-testing/blob/main/src/main/java/br/edu/ifsp/testing/practice/TriangleUtils.java
 */
public class TriangleUtils {

    public record Statistics(int nonTriangle, int equilateral, int isosceles, int scalene) {}

    public Statistics computeTypeStatistics(double[][] data) {
        Objects.requireNonNull(data);

        int nonTriangle = 0;
        int equilateral = 0;
        int isosceles = 0;
        int scalene = 0;

        for (int i = 0; i < data.length; i++) {
            double[] datum = data[i];
            if (datum.length != 6) throw new IllegalArgumentException();

            double x1 = datum[0]; double y1 = datum[1];
            double x2 = datum[2]; double y2 = datum[3];
            double x3 = datum[4]; double y3 = datum[5];

            double a = distance(x1, y1, x2, y2);
            double b = distance(x2, y2, x3, y3);
            double c = distance(x3, y3, x1, y1);

            if (a + b <= c || a + c <= b || b + c <= a) nonTriangle++;
            else if (a == b && b == c) equilateral++;
            else if (a == b || a == c || b == c) isosceles++;
            else scalene++;
        }
        return new Statistics(nonTriangle, equilateral, isosceles, scalene);
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        final double euclideanDistance = Math.sqrt(dx * dx + dy * dy);
        return Math.round(euclideanDistance * 1000.0) / 1000.0;
    }
}