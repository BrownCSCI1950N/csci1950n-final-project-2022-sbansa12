package engine.Shape;

import engine.Interval;
import engine.support.Vec2d;

import java.util.List;

public interface Shape {
    void updatePosition(Vec2d update);
    void setVelocity(Vec2d velocity);
    boolean isColliding(Shape o);
    boolean isCollidingCircle(Circle c);
    boolean isCollidingAAB(AAB aab);
    boolean isCollidingPolygon(Polygon p);
    boolean isCollidingPoint(Vec2d p);
    Vec2d MTV(Shape o, boolean accountVelocity);

    /**
     * Returns the MTV between this shape and the input Circle if there is a collision
     * @param c - the circle we want to calculate the MTV with respect to
     * @param accountVelocity - should velocity be taken into account or not
     * @return - returns the MTV between this shape and the input Circle if they are colliding, null otherwise
     */
    Vec2d MTVCircle(Circle c, boolean accountVelocity);

    /**
     * Returns the MTV between this shape and the input AAB if there is a collision
     * @param aab - the AAB we want to calculate the MTV with respect to
     * @param accountVelocity - should velocity be taken into account or not
     * @return - returns the MTV between this shape and the input AAB if they are colliding, null otherwise
     */
    Vec2d MTVAAB(AAB aab, boolean accountVelocity);

    /**
     * eturns the MTV between this shape and the input point if there is a collision
     * @param p - the point we want to calculate the MTV with respect to
     * @return - returns the MTV between this shape and the input point if they are colliding, null otherwise
     */
    Vec2d MTVPoint(Vec2d p);

    /**
     * eturns the MTV between this shape and the input Polygon if there is a collision
     * @param p - the Polygon we want to calculate the MTV with respect to
     * @param accountVelocity - should velocity be taken into account or not
     * @return - returns the MTV between this shape and the input Polygon if they are colliding, null otherwise
     */
    Vec2d MTVPolygon(Polygon p, boolean accountVelocity);

    Interval project(Vec2d axis);

    /**
     * Project a List of Points onto an Axis and Find the Interval
     * @param axis - axis to project onto
     * @param points - list of points to project
     * @return - Interval between the two furthest away points
     */
    default Interval projectPoints(Vec2d axis, List<Vec2d> points) {
        double min = axis.dot(points.get(0));
        double max = min;

        for (int i = 1; i < points.size(); i++) {
            double p = axis.dot(points.get(i));
            if (p < min) {
                min = p;
            }
            if (p > max) {
                max = p;
            }
        }

        return new Interval(min, max);
    }
}
