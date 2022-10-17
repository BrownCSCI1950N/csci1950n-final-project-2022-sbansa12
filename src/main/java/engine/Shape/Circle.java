package engine.Shape;

import engine.support.Vec2d;

public class Circle implements Shape {

    protected Vec2d center;
    protected float radius;
    protected Vec2d velocity;

    public Circle(Vec2d center, float radius) {
        this.center = center;
        this.radius = radius;
        this.velocity = new Vec2d(0,0);
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
    public void setVelocity(Vec2d velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean isColliding(Shape o) {
        return o.isCollidingCircle(this);
    }

    @Override
    public boolean isCollidingCircle(Circle c) {
        double distance = this.getCenter().dist(c.getCenter());
        double sumRadius = this.getRadius() + c.getRadius();
        return (distance * distance) <= (sumRadius * sumRadius);
    }

    @Override
    public boolean isCollidingAAB(AAB aab) {
        return aab.isCollidingCircle(this);
    }

    @Override
    public boolean isCollidingPoint(Vec2d p) {
        double distance = p.dist(this.getCenter());
        return (distance * distance) <= (this.getRadius() * this.getRadius());
    }

    @Override
    public Vec2d MTV(Shape o, boolean accountVelocity) {
        return o.MTVCircle(this, accountVelocity);
    }

    /**
     * Returns the MTV between this Circle shape and the input Circle if there is a collision
     * @param c - the Circle we want to calculate the MTV with respect to
     * @return - returns the MTV between this Circle shape and the input Circle if they are colliding, null otherwise. Takes into account velocity.
     */
    @Override
    public Vec2d MTVCircle(Circle c, boolean accountVelocity) {
        if (!isColliding(c)){
            return null;
        }
        double magnitude = this.getRadius() + c.getRadius() - this.getCenter().dist(c.getCenter());
        Vec2d direction;
        if (!this.getCenter().minus(c.getCenter()).isZero()) {
            direction = this.getCenter().minus(c.getCenter()).normalize();
        } else {
            // If centers exactly overlap, this fails as normalize fails the assert statement. The way to get around this is to store velocity then use the inverse of the velocity for the direction.
            if (accountVelocity) {
                direction = velocity.normalize().smult(-1);
            } else {
                direction = new Vec2d(0,1 );
            }

        }

        return direction.smult(magnitude);
    }

    @Override
    public Vec2d MTVAAB(AAB aab, boolean accountVelocity) {
        Vec2d f = aab.MTV(this, accountVelocity);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d MTVPoint(Vec2d p) {
        if (!isCollidingPoint(p)){
            return null;
        }
        double magnitude = this.getRadius() - this.getCenter().dist(p);
        Vec2d direction = this.getCenter().minus(p).normalize();

        return direction.smult(magnitude);
    }
}
