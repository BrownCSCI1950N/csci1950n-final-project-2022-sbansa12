package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week3Reqs;
import engine.Utility;

/**
 * Fill this class in during Week 3. Make sure to also change the week variable in Display.java.
 */
public final class Week3 extends Week3Reqs {

	Week2 checkCollisions = new Week2();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		if (!checkCollisions.isColliding(s1, s2)){
			return null;
		}
		Vec2d s1Start = s1.getTopLeft();
		Vec2d s1End = s1Start.plus(s1.getSize());
		Vec2d s2Start = s2.getTopLeft();
		Vec2d s2End = s2Start.plus(s2.getSize());

		double moveLeft = Math.abs(s1End.x - s2Start.x);
		double moveRight= Math.abs(s1Start.x - s2End.x);
		double moveUp = Math.abs(s1End.y - s2Start.y);
		double moveDown = Math.abs(s1Start.y - s2End.y);

		double x = Math.min(moveLeft, moveRight);
		double y = Math.min(moveUp, moveDown);

		if (x <= y) {
			if (x == moveLeft) {
				return new Vec2d(-moveLeft, 0);
			} else {
				return new Vec2d(moveRight, 0);
			}
		} else {
			if (y == moveUp) {
				return new Vec2d(0, -moveUp);
			} else {
				return new Vec2d(0, moveDown);
			}
		}
	}

	public static boolean inBoundingBox(Vec2d start, Vec2d end, Vec2d coordinate) {
		boolean xInRange = (start.x <= coordinate.x && coordinate.x <= end.x);
		boolean yInRange = (start.y <= coordinate.y && coordinate.y <= end.y);
		return xInRange && yInRange;
	}

	public Vec2d closestPoint(Vec2d p, Vec2d p1, Vec2d p2) {
		double distance1 = p.dist(p1);
		double distance2 = p.dist(p2);
		if (distance1 <= distance2) {
			return p1;
		} else {
			return p2;
		}
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		if (!checkCollisions.isColliding(s1, s2)){
			return null;
		}
		Vec2d s1Start = s1.getTopLeft();
		Vec2d s1End = s1Start.plus(s1.getSize());
		Vec2d s2Center = s2.getCenter();
		if (inBoundingBox(s1Start, s1End, s2Center)) {
			// find p = closet point on AAB edge from circle center
			Vec2d closestPointAttempt1 = new Vec2d(Utility.clamp(s2Center.x, s1Start.x, s1End.x), Utility.maxOrMin(s2Center.y, s1Start.y, s1End.y)); // y-axis move direction
			Vec2d closestPointAttempt2 = new Vec2d(Utility.maxOrMin(s2Center.x, s1Start.x, s1End.x), Utility.clamp(s2Center.y, s1Start.y, s1End.y)); // x-axis move direction
			Vec2d closestPointBorder = closestPoint(s2Center, closestPointAttempt1, closestPointAttempt2);

			// length of MTV is radius + distance (center p)
			double magnitude = s2.getRadius() + closestPointBorder.dist(s2Center);

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
			double magnitude = s2.getRadius() - closestPoint.dist(s2Center);

			// mtv is parallel to the line connecting the two points
			Vec2d direction = closestPoint.minus(s2Center).normalize();

			return direction.smult(magnitude);
		}
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		if (!checkCollisions.isColliding(s1, s2)){
			return null;
		}

		Vec2d s1Start = s1.getTopLeft();
		Vec2d s1End = s1Start.plus(s1.getSize());

		Vec2d closestPointAttempt1 = new Vec2d(Utility.clamp(s2.x, s1Start.x, s1End.x), Utility.maxOrMin(s2.y, s1Start.y, s1End.y)); // y-axis move direction
		Vec2d closestPointAttempt2 = new Vec2d(Utility.maxOrMin(s2.x, s1Start.x, s1End.x), Utility.clamp(s2.y, s1Start.y, s1End.y)); // x-axis move direction
		Vec2d closestPointBorder = closestPoint(s2, closestPointAttempt1, closestPointAttempt2);

		// length of MTV is radius + distance (center p)
		double magnitude = closestPointBorder.dist(s2);

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
	
	// CIRCLES

	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		if (!checkCollisions.isColliding(s1, s2)){
			return null;
		}
		double magnitude = s1.getRadius() + s2.getRadius() - s1.getCenter().dist(s2.getCenter());
		Vec2d direction;
		if (!s1.getCenter().minus(s2.getCenter()).isZero()) {
			direction = s1.getCenter().minus(s2.getCenter()).normalize();
		} else {
			// If centers exactly overlap, this fails as normalize fails the assert statement. The way to get around this is to store velocity then use the inverse of the velocity for the direction.
			direction = new Vec2d(0,1 );
		}

		return direction.smult(magnitude);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		if (!checkCollisions.isColliding(s1, s2)){
			return null;
		}
		double magnitude = s1.getRadius() - s1.getCenter().dist(s2);
		Vec2d direction = s1.getCenter().minus(s2).normalize();

		return direction.smult(magnitude);
	}
}
