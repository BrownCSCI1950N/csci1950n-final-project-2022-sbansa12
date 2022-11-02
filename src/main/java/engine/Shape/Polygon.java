package engine.Shape;

import engine.Interval;
import engine.support.Vec2d;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static engine.Utility.closestPoint;

public class Polygon implements Shape {
    protected Vec2d topLeftPoint;
    protected List<Vec2d> points;
    protected Vec2d velocity;

    public Polygon(Vec2d topLeftPoint, List<Vec2d> points) {
        this.topLeftPoint = topLeftPoint;
        this.points = points;
        this.velocity = new Vec2d(0,0);
    }

    @Override
    public void updatePosition(Vec2d update) {
        Vec2d translate = update.minus(topLeftPoint);
        points.replaceAll(vec2d -> vec2d.plus(translate));
        topLeftPoint = update;
    }

    @Override
    public void setVelocity(Vec2d velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean isColliding(Shape o) {
        return o.isCollidingPolygon(this);
    }

    @Override
    public boolean isCollidingCircle(Circle c) {
        return MTVCircle(c, false) != null;
    }

    @Override
    public boolean isCollidingAAB(AAB aab) {
        return MTVAAB(aab, false) != null;
    }

    @Override
    public boolean isCollidingPolygon(Polygon p) {
        return MTVPolygon(p, false) != null;
    }

    @Override
    public boolean isCollidingPoint(Vec2d p) {
        return MTVPoint(p) != null;
    }

    @Override
    public Vec2d MTV(Shape o, boolean accountVelocity) {
        return o.MTVPolygon(this, accountVelocity);
    }

    @Override
    public Vec2d MTVCircle(Circle c, boolean accountVelocity) {
        Vec2d f = c.MTVPolygon(this, accountVelocity);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d MTVAAB(AAB aab, boolean accountVelocity) {
        Vec2d f = aab.MTVPolygon(this, accountVelocity);
        return f == null ? null : f.reflect();
    }

    @Override
    public Vec2d MTVPoint(Vec2d p) {
        for (int i = 0; i < points.size(); i++) {
            Vec2d edgeVector = points.get((i + 1) % points.size()).minus(points.get(i));
            Vec2d vectorPoint = p.minus(points.get(i));
            if (edgeVector.cross(vectorPoint) > 0) {
                return null;
            }
        }

        // Point is Inside Poly
        Vec2d closestPoint = closestPoint(p, points);
        // WIERD: POLY POINT MTV
        return closestPoint.minus(p);
    }

    @Override
    public Vec2d MTVPolygon(Polygon p, boolean accountVelocity) {
        List<Vec2d> polySeparatingAxis1 = this.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());
        List<Vec2d> polySeparatingAxis2 = p.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

        List<Vec2d> separatingAxis = new LinkedList<>();
        separatingAxis.addAll(polySeparatingAxis1);
        separatingAxis.addAll(polySeparatingAxis2);

        double minMagnitude = Double.POSITIVE_INFINITY;
        Vec2d mtv = null;

        for (Vec2d axis : separatingAxis) {
            Interval poly1 = this.project(axis);
            Interval poly2 = p.project(axis);
            Double mtv1d = poly1.mtv(poly2);
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
        return projectPoints(axis, points);
    }

    @Override
    public double raycast(Ray ray) {
        double minT = Double.POSITIVE_INFINITY;
        boolean foundIntersection = false;
        for (int i = 0; i < points.size(); i++) {
            double t = raycastEdge(ray, points.get(i), points.get((i+1) % points.size()));
            if (t != -1) {
                foundIntersection = true;
                if (t < minT) {
                    minT = t;
                }
            }
        }

        if (foundIntersection) {
            return minT;
        } else {
            return -1;
        }
    }

    private double raycastEdge(Ray ray, Vec2d a, Vec2d b) {
        Vec2d p = ray.src;
        Vec2d d = ray.dir;

        Vec2d m = b.minus(a).normalize();
        Vec2d n = m.perpendicular().normalize();

        double checkStraddle = a.minus(p).cross(d) * b.minus(p).cross(d);
        if (checkStraddle > 0) {
            return -1;
        }

        return (float) ((b.minus(p).dot(n))/d.dot(n));
    }

    public List<Vec2d> getEdgeVectors() {
        List<Vec2d> edgeVectors = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {
            edgeVectors.add(points.get((i+1) % points.size()).minus(points.get(i)));
        }
        return edgeVectors;
    }

    public List<Vec2d> getPoints() {
        return points;
    }
}
