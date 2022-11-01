package engine;

import engine.support.Vec2d;

import java.util.List;

public class Utility {
    public static boolean inBoundingBox(Vec2d start, Vec2d end, Vec2d coordinate) {
        boolean xInRange = (start.x <= coordinate.x && coordinate.x <= end.x);
        boolean yInRange = (start.y <= coordinate.y && coordinate.y <= end.y);
        return xInRange && yInRange;
    }

    /**
     * Clamp Double values to a given range
     *
     * @param value     the value to clamp
     * @param min       the minimum value
     * @param max       the maximum value
     * @return          the clamped value
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double maxOrMin(double value, double min, double max) {
        if ((max - value) >= (value - min)) {
            return min;
        } else {
            return max;
        }
    }

    public static Vec2d closestPoint(Vec2d p, Vec2d p1, Vec2d p2) {
        double distance1 = p.dist(p1);
        double distance2 = p.dist(p2);
        if (distance1 <= distance2) {
            return p1;
        } else {
            return p2;
        }
    }

    public static Vec2d closestPoint(Vec2d p, List<Vec2d> points) {
        assert points.size() > 0;
        double distance = p.dist2(points.get(0));
        Vec2d toReturn = points.get(0);
        for (Vec2d point : points) {
            double distance1 = p.dist2(point);
            if (distance1 < distance) {
                distance = distance1;
                toReturn = point;
            }
        }

        return toReturn;
    }
}
