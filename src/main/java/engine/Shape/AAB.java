package engine.Shape;

import engine.Interval;
import engine.Utility;
import engine.support.Vec2d;

public class AAB implements Shape{

    protected Vec2d topLeft;
    protected Vec2d size;

    public AAB(Vec2d topLeft, Vec2d size) {
        this.topLeft = topLeft;
        this.size = size;
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
    public boolean collides(Shape o) {
        return o.collidesAAB(this);
    }

    @Override
    public boolean collidesCircle(Circle c) {
        Vec2d center = c.getCenter();
        Vec2d startAAB = this.getTopLeft();
        Vec2d endAAB = this.getTopLeft().plus(this.getSize());

        Vec2d closestPoint = new Vec2d(Utility.clamp(center.x, startAAB.x, endAAB.x), Utility.clamp(center.y, startAAB.y, endAAB.y));

        return c.collidesPoint(closestPoint);
    }

    @Override
    public boolean collidesAAB(AAB aab) {
        Vec2d xAxis = new Vec2d(1, 0);
        Vec2d yAxis = new Vec2d(0, 1);

        Interval thisIntervalX = new Interval(this.getTopLeft().dot(xAxis), this.getTopLeft().plus(this.getSize()).dot(xAxis));
        Interval thisIntervalY = new Interval(this.getTopLeft().dot(yAxis), this.getTopLeft().plus(this.getSize()).dot(yAxis));

        Interval aabIntervalX = new Interval(aab.getTopLeft().dot(xAxis), aab.getTopLeft().plus(aab.getSize()).dot(xAxis));
        Interval aabIntervalY = new Interval(aab.getTopLeft().dot(yAxis), aab.getTopLeft().plus(aab.getSize()).dot(yAxis));

        return thisIntervalX.overlap(aabIntervalX) && thisIntervalY.overlap(aabIntervalY);
    }

    @Override
    public boolean collidesPoint(Vec2d p) {
        Vec2d xAxis = new Vec2d(1, 0);
        Vec2d yAxis = new Vec2d(0, 1);

        Interval thisIntervalX = new Interval(this.getTopLeft().dot(xAxis), this.getTopLeft().plus(this.getSize()).dot(xAxis));
        Interval thisIntervalY = new Interval(this.getTopLeft().dot(yAxis), this.getTopLeft().plus(this.getSize()).dot(yAxis));

        return thisIntervalX.inInterval(p.x) && thisIntervalY.inInterval(p.y);
    }
}
