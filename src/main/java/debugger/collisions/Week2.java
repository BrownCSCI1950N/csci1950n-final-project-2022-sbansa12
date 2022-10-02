package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week2Reqs;
import engine.Interval;
import engine.Utility;

/**
 * Fill this class in during Week 2.
 */
public final class Week2 extends Week2Reqs {

	// AXIS-ALIGNED BOXES
	
	@Override
	public boolean isColliding(AABShape s1, AABShape s2) {
		Vec2d xAxis = new Vec2d(1, 0);
		Vec2d yAxis = new Vec2d(0, 1);

		Interval s1IntervalX = new Interval(s1.getTopLeft().dot(xAxis), s1.getTopLeft().plus(s1.getSize()).dot(xAxis));
		Interval s1IntervalY = new Interval(s1.getTopLeft().dot(yAxis), s1.getTopLeft().plus(s1.getSize()).dot(yAxis));

		Interval s2IntervalX = new Interval(s2.getTopLeft().dot(xAxis), s2.getTopLeft().plus(s2.getSize()).dot(xAxis));
		Interval s2IntervalY = new Interval(s2.getTopLeft().dot(yAxis), s2.getTopLeft().plus(s2.getSize()).dot(yAxis));

		return s1IntervalX.overlap(s2IntervalX) && s1IntervalY.overlap(s2IntervalY);
	}

	@Override
	public boolean isColliding(AABShape s1, CircleShape s2) {
		Vec2d center = s2.getCenter();
		Vec2d startAAB = s1.getTopLeft();
		Vec2d endAAB = s1.getTopLeft().plus(s1.getSize());

		Vec2d closestPoint = new Vec2d(Utility.clamp(center.x, startAAB.x, endAAB.x), Utility.clamp(center.y, startAAB.y, endAAB.y));

		return isColliding(s2, closestPoint);
	}

	@Override
	public boolean isColliding(AABShape s1, Vec2d s2) {
		Vec2d xAxis = new Vec2d(1, 0);
		Vec2d yAxis = new Vec2d(0, 1);

		Interval s1IntervalX = new Interval(s1.getTopLeft().dot(xAxis), s1.getTopLeft().plus(s1.getSize()).dot(xAxis));
		Interval s1IntervalY = new Interval(s1.getTopLeft().dot(yAxis), s1.getTopLeft().plus(s1.getSize()).dot(yAxis));

		return s1IntervalX.inInterval(s2.x) && s1IntervalY.inInterval(s2.y);
	}

	// CIRCLES
	
	@Override
	public boolean isColliding(CircleShape s1, AABShape s2) {
		return isColliding(s2, s1);
	}

	@Override
	public boolean isColliding(CircleShape s1, CircleShape s2) {
		double distance = s1.getCenter().minus(s2.getCenter()).mag();
		double sumRadius = s1.getRadius() + s2.getRadius();
		return (distance * distance) <= (sumRadius * sumRadius);
	}

	@Override
	public boolean isColliding(CircleShape s1, Vec2d s2) {
		double distance = s2.minus(s1.getCenter()).mag();
		return (distance * distance) <= (s1.getRadius() * s1.getRadius());
	}
}
