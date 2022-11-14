package engine.Shape;

import engine.Interval;
import engine.support.Vec2d;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static engine.Utility.closestPoint;

public class Circle implements Shape {

    protected Vec2d center;
    protected float radius;
    protected Vec2d velocity;

    public Circle(Vec2d center, float radius) {
        this.center = center;
        this.radius = radius;
        this.velocity = new Vec2d(0,0);
    }

    @Override
    public Vec2d getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    @Override
    public void updatePosition(Vec2d update) {
        assert false;
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
    public boolean isCollidingPolygon(Polygon p) {
        return MTVPolygon(p, false) != null;
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
        Vec2d f = aab.MTVCircle(this, accountVelocity);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d MTVPoint(Vec2d p) {
        if (!isCollidingPoint(p)){
            return null;
        }
        double magnitude = this.getRadius() - this.getCenter().dist(p);
        Vec2d direction = this.getCenter().minus(p);
        if (!direction.isZero()) {
            direction = direction.normalize();
        } else {
            direction = new Vec2d(0,1);
        }

        return direction.smult(magnitude);
    }

    @Override
    public Vec2d MTVPolygon(Polygon p, boolean accountVelocity) {
        // Find the Closest Vertex of Polygon to Circle Center
        Vec2d closestPoint = closestPoint(this.center, p.getPoints());
        List<Vec2d> circleSeparatingAxis = List.of(closestPoint.minus(this.center).normalize());

        List<Vec2d> polySeparatingAxis = p.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

        List<Vec2d> separatingAxis = new LinkedList<>();
        separatingAxis.addAll(circleSeparatingAxis);
        separatingAxis.addAll(polySeparatingAxis);

        double minMagnitude = Double.POSITIVE_INFINITY;
        Vec2d mtv = null;

        for (Vec2d axis : separatingAxis) {
            Interval circle = project(axis);
            Interval poly = p.project( axis);
            Double mtv1d = circle.mtv(poly);
            if (mtv1d == null) {
                return null;
            }
            if (Math.abs(mtv1d) < minMagnitude) {
                minMagnitude = Math.abs(mtv1d);
                mtv = axis.smult(mtv1d);
            }
        }

        return mtv;
    }

    @Override
    public Interval project(Vec2d axis) {
        Vec2d center = this.center.projectOnto(axis);
        Vec2d translate = axis.smult(this.radius);
        List<Vec2d> points = List.of(center.plus(translate), center.minus(translate));

        return projectPoints(axis, points);
    }

    @Override
    public double raycast(Ray ray) {
        // project center onto ray
        Vec2d projection = center.projectOntoLine(ray.src, ray.src.plus(ray.dir));

        if (!isCollidingPoint(ray.src)) {
            // source outside

            // if projection positive and projection point in circle: collide
            if (center.minus(ray.src).dot(ray.dir) > 0) {

                if (isCollidingPoint(projection)) {
                    double L = projection.minus(ray.src).mag();
                    double r = radius;
                    double x = projection.minus(center).mag();
                    return (float) (L - Math.sqrt((r * r) - (x * x)));
                }
            }
        } else {
            // source inside

            double L = projection.minus(ray.src).mag();
            double r = radius;
            double x = projection.minus(center).mag();
            return (float) (L + Math.sqrt((r * r) - (x * x)));
        }

        return -1;
    }
}
