package engine.Shape;

import engine.support.Vec2d;

public interface Shape {
    void updatePosition(Vec2d update);
    boolean collides(Shape o);
    boolean collidesCircle(Circle c);
    boolean collidesAAB(AAB aab);
    boolean collidesPoint(Vec2d p);
}
