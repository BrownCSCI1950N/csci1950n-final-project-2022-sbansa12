package engine.Shape;

import engine.support.Vec2d;

public class Circle implements Shape {

    protected Vec2d center;
    protected float radius;

    public Circle(Vec2d center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    public Vec2d getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void updatePosition(Vec2d update) {
        this.center = update;
    }

    @Override
    public boolean collides(Shape o) {
        return o.collidesCircle(this);
    }

    @Override
    public boolean collidesCircle(Circle c) {
        double distance = this.getCenter().minus(c.getCenter()).mag();
        double sumRadius = this.getRadius() + c.getRadius();
        return (distance * distance) <= (sumRadius * sumRadius);
    }

    @Override
    public boolean collidesAAB(AAB aab) {
        return aab.collidesCircle(this);
    }

    @Override
    public boolean collidesPoint(Vec2d p) {
        double distance = p.minus(this.getCenter()).mag();
        return (distance * distance) <= (this.getRadius() * this.getRadius());
    }
}
