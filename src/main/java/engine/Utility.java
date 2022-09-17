package engine;

import engine.support.Vec2d;

public class Utility {
    public static boolean inBoundingBox(Vec2d start, Vec2d end, Vec2d coordinate) {
        boolean xInRange = (start.x < coordinate.x && coordinate.x < end.x);
        boolean yInRange = (start.y < coordinate.y && coordinate.y < end.y);
        return xInRange && yInRange;
    }
}
