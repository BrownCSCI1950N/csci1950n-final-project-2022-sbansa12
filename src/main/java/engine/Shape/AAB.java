package engine.Shape;

import engine.Interval;
import engine.Utility;
import engine.support.Vec2d;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AAB implements Shape {

    protected Vec2d topLeft;
    protected Vec2d size;
    protected Vec2d velocity;

    public AAB(Vec2d topLeft, Vec2d size) {
        this.topLeft = topLeft;
        this.size = size;
        this.velocity = new Vec2d(0,0);
    }

    public Vec2d getTopLeft() {
        return topLeft;
    }

    public Vec2d getSize() {
        return size;
    }

    @Override
    public void updatePosition(Vec2d update) {
        this.topLeft = update;
    }

    @Override
    public void setVelocity(Vec2d velocity) {
        this.velocity = velocity;
    }

    @Override
    public boolean isColliding(Shape o) {
        return o.isCollidingAAB(this);
    }

    @Override
    public boolean isCollidingCircle(Circle c) {
        Vec2d center = c.getCenter();
        Vec2d startAAB = this.getTopLeft();
        Vec2d endAAB = this.getTopLeft().plus(this.getSize());

        Vec2d closestPoint = new Vec2d(Utility.clamp(center.x, startAAB.x, endAAB.x), Utility.clamp(center.y, startAAB.y, endAAB.y));

        return c.isCollidingPoint(closestPoint);
    }

    @Override
    public boolean isCollidingAAB(AAB aab) {
        Vec2d xAxis = new Vec2d(1, 0);
        Vec2d yAxis = new Vec2d(0, 1);

        Interval thisIntervalX = new Interval(this.getTopLeft().dot(xAxis), this.getTopLeft().plus(this.getSize()).dot(xAxis));
        Interval thisIntervalY = new Interval(this.getTopLeft().dot(yAxis), this.getTopLeft().plus(this.getSize()).dot(yAxis));

        Interval aabIntervalX = new Interval(aab.getTopLeft().dot(xAxis), aab.getTopLeft().plus(aab.getSize()).dot(xAxis));
        Interval aabIntervalY = new Interval(aab.getTopLeft().dot(yAxis), aab.getTopLeft().plus(aab.getSize()).dot(yAxis));

        return thisIntervalX.overlap(aabIntervalX) && thisIntervalY.overlap(aabIntervalY);
    }

    @Override
    public boolean isCollidingPolygon(Polygon p) {
        return MTVPolygon(p, false) != null;
    }

    @Override
    public boolean isCollidingPoint(Vec2d p) {
        Vec2d xAxis = new Vec2d(1, 0);
        Vec2d yAxis = new Vec2d(0, 1);

        Interval thisIntervalX = new Interval(this.getTopLeft().dot(xAxis), this.getTopLeft().plus(this.getSize()).dot(xAxis));
        Interval thisIntervalY = new Interval(this.getTopLeft().dot(yAxis), this.getTopLeft().plus(this.getSize()).dot(yAxis));

        return thisIntervalX.inInterval(p.x) && thisIntervalY.inInterval(p.y);
    }

    @Override
    public Vec2d MTV(Shape o, boolean accountVelocity) {
        return o.MTVAAB(this, accountVelocity);
    }

    @Override
    public Vec2d MTVCircle(Circle c, boolean accountVelocity) {
        if (!isColliding(c)){
            return null;
        }
        Vec2d s1Start = this.getTopLeft();
        Vec2d s1End = s1Start.plus(this.getSize());
        Vec2d s2Center = c.getCenter();
        if (Utility.inBoundingBox(s1Start, s1End, s2Center)) {
            // find p = closet point on AAB edge from circle center
            Vec2d closestPointAttempt1 = new Vec2d(Utility.clamp(s2Center.x, s1Start.x, s1End.x), Utility.maxOrMin(s2Center.y, s1Start.y, s1End.y)); // y-axis move direction
            Vec2d closestPointAttempt2 = new Vec2d(Utility.maxOrMin(s2Center.x, s1Start.x, s1End.x), Utility.clamp(s2Center.y, s1Start.y, s1End.y)); // x-axis move direction
            Vec2d closestPointBorder = Utility.closestPoint(s2Center, closestPointAttempt1, closestPointAttempt2);

            // length of MTV is radius + distance (center p)
            double magnitude = c.getRadius() + closestPointBorder.dist(s2Center);

            // mtv is parallel to x or y axis
            Vec2d direction;
            if (closestPointBorder.equals(closestPointAttempt1)) {
                if (closestPointAttempt1.y == s1Start.y) {
                    direction = new Vec2d(0, 1);
                } else {
                    direction = new Vec2d(0, -1);
                }
            } else {
                if (closestPointAttempt2.x == s1Start.x) {
                    direction = new Vec2d(1, 0);
                } else {
                    direction = new Vec2d(-1, 0);
                }
            }

            return direction.smult(magnitude);
        } else {
            // clamp circle center to the aab
            Vec2d closestPoint = new Vec2d(Utility.clamp(s2Center.x, s1Start.x, s1End.x), Utility.clamp(s2Center.y, s1Start.y, s1End.y));

            // length of mtv is radius - distance(center, clamped center)
            double magnitude = c.getRadius() - closestPoint.dist(s2Center);

            // mtv is parallel to the line connecting the two points
            Vec2d direction = closestPoint.minus(s2Center).normalize();

            return direction.smult(magnitude);
        }
    }

    private Vec2d setX(double x, double moveLeft, double moveRight) {
        if (x == moveLeft) {
            return new Vec2d(-moveLeft, 0);
        } else {
            // moveRight
            return new Vec2d(moveRight, 0);
        }
    }

    private Vec2d setY(double y, double moveUp, double moveDown) {
        if (y == moveUp) {
            return new Vec2d(0, -moveUp);
        } else {
            // moveDown
            return new Vec2d(0, moveDown);
        }
    }

    /**
     * Returns the MTV between this AAB shape and the input AAB if there is a collision
     * @param aab - the AAB we want to calculate the MTV with respect to
     * @return - returns the MTV between this AAB shape and the input AAB if they are colliding, null otherwise. Takes into account velocity.
     */
    @Override
    public Vec2d MTVAAB(AAB aab, boolean accountVelocity) {
        if (!isColliding(aab)){
            return null;
        }
        Vec2d s1Start = this.getTopLeft();
        Vec2d s1End = s1Start.plus(this.getSize());
        Vec2d s2Start = aab.getTopLeft();
        Vec2d s2End = s2Start.plus(aab.getSize());

        double moveLeft = Math.abs(s1End.x - s2Start.x);
        double moveRight= Math.abs(s1Start.x - s2End.x);
        double moveUp = Math.abs(s1End.y - s2Start.y);
        double moveDown = Math.abs(s1Start.y - s2End.y);

        double x = Math.min(moveLeft, moveRight);
        double y = Math.min(moveUp, moveDown);

        if (x < y) {
            return setX(x, moveLeft, moveRight);
        } else if (x > y) {
            return setY(y, moveUp, moveDown);
        } else {
            // x == y: Decide based on velocity
            if (accountVelocity) {
                if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
                    if (velocity.x > 0) {
                        return new Vec2d(-moveLeft, 0);
                    } else {
                        return new Vec2d(moveRight, 0);
                    }
                } else if (Math.abs(velocity.x) < Math.abs(velocity.y)) {
                    if (velocity.y > 0) {
                        return new Vec2d(0, -moveUp);
                    } else {
                        return new Vec2d(0, moveDown);
                    }
                } else {
                    // velocity.x == velocity.y
                    if (velocity.x == 0) {
                        return new Vec2d(0, 0);
                    }
                    return setX(x, moveLeft, moveRight).plus(setY(y,moveUp,moveDown)); // Case Needs Fixing Eventually Maybe
                }
            } else {
                return setX(x, moveLeft, moveRight);
            }
        }
    }

    @Override
    public Vec2d MTVPoint(Vec2d p) {
        if (!isCollidingPoint(p)){
            return null;
        }

        Vec2d s1Start = this.getTopLeft();
        Vec2d s1End = s1Start.plus(this.getSize());

        Vec2d closestPointAttempt1 = new Vec2d(Utility.clamp(p.x, s1Start.x, s1End.x), Utility.maxOrMin(p.y, s1Start.y, s1End.y)); // y-axis move direction
        Vec2d closestPointAttempt2 = new Vec2d(Utility.maxOrMin(p.x, s1Start.x, s1End.x), Utility.clamp(p.y, s1Start.y, s1End.y)); // x-axis move direction
        Vec2d closestPointBorder = Utility.closestPoint(p, closestPointAttempt1, closestPointAttempt2);

        // length of MTV is radius + distance (center p)
        double magnitude = closestPointBorder.dist(p);

        // mtv is parallel to x or y axis
        Vec2d direction;
        if (closestPointBorder.equals(closestPointAttempt1)) {
            if (closestPointAttempt1.y == s1Start.y) {
                direction = new Vec2d(0, 1);
            } else {
                direction = new Vec2d(0, -1);
            }
        } else {
            if (closestPointAttempt2.x == s1Start.x) {
                direction = new Vec2d(1, 0);
            } else {
                direction = new Vec2d(-1, 0);
            }
        }

        return direction.smult(magnitude);
    }

    @Override
    public Vec2d MTVPolygon(Polygon p, boolean accountVelocity) {
        List<Vec2d> aabSeparatingAxis = List.of(new Vec2d(1,0), new Vec2d(0,1));
        List<Vec2d> polySeparatingAxis = p.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

        List<Vec2d> separatingAxis = new LinkedList<>();
        separatingAxis.addAll(aabSeparatingAxis);
        separatingAxis.addAll(polySeparatingAxis);

        double minMagnitude = Double.POSITIVE_INFINITY;
        Vec2d mtv = null;

        for (Vec2d axis : separatingAxis) {
            Interval aab = project(axis);
            Interval poly = p.project(axis);
            Double mtv1d = aab.mtv(poly);
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
        Vec2d topLeft = this.getTopLeft();
        Vec2d topRight = this.getTopLeft().plus(this.size.x, 0);
        Vec2d bottomLeft = this.getTopLeft().plus(0, this.size.y);
        Vec2d bottomRight = this.getTopLeft().plus(this.size);

        List<Vec2d> points = List.of(topLeft, topRight, bottomLeft, bottomRight);

        return projectPoints(axis, points);
    }

    @Override
    public String toString() {
        return "AAB: Position: " + topLeft + ", Size: " + size;
    }
}
