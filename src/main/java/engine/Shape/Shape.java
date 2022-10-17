package engine.Shape;

import engine.support.Vec2d;

public interface Shape {
    void updatePosition(Vec2d update);
    void setVelocity(Vec2d velocity);
    boolean isColliding(Shape o);
    boolean isCollidingCircle(Circle c);
    boolean isCollidingAAB(AAB aab);
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
}
