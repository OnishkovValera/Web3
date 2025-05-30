package beans.mbeans.MXBeanImpl;

import beans.Point;
import beans.mbeans.PointsDistanceMXBean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class PointsDistance implements PointsDistanceMXBean, Serializable {
    private Queue<Point> lastPoints = new LinkedList<>();
    private static final int MAX_POINTS = 10;

    public void addPoint(Point point) {
        lastPoints.offer(point);
        if (lastPoints.size() > MAX_POINTS) {
            lastPoints.poll();
        }
    }

    @Override
    public double getAverageDistance() {
        if (lastPoints.size() < 2) {
            return 0.0;
        }

        Point[] points = lastPoints.toArray(new Point[0]);
        double totalDistance = 0;
        int pairs = 0;

        for (int i = 0; i < points.length - 1; i++) {
            totalDistance += calculateDistance(points[i], points[i + 1]);
            pairs++;
        }

        return totalDistance / pairs;
    }

    private double calculateDistance(Point p1, Point p2) {
        return Math.sqrt(
                Math.pow(p2.getX() - p1.getX(), 2) +
                        Math.pow(p2.getY() - p1.getY(), 2)
        );
    }
}