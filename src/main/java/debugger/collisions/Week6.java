package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week6Reqs;

/**
 * Fill this class in during Week 6. Make sure to also change the week variable in Display.java.
 */
public final class Week6 extends Week6Reqs {

	Week5 mtv = new Week5();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		return mtv.collision(s1,s2);
	}

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		return mtv.collision(s1,s2);
	}
	
	// POLYGONS

	@Override
	public Vec2d collision(PolygonShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, CircleShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(PolygonShape s1, Vec2d s2) {
		return mtv.collision(s1,s2);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		return mtv.collision(s1,s2);
	}
	
	// RAYCASTING
	
	@Override
	public float raycast(AABShape s1, Ray s2) {
		return raycast(s1.toPolygon(), s2);
	}
	
	@Override
	public float raycast(CircleShape s1, Ray s2) {
		// project center onto ray
		Vec2d projection = s1.center.projectOntoLine(s2.src, s2.src.plus(s2.dir));

		if (collision(s1, s2.src) == null) {
			// source outside

			// if projection positive and projection point in circle: collide
			if (s1.center.minus(s2.src).dot(s2.dir) > 0) {

				if (collision(s1, projection) != null) {
					double L = projection.minus(s2.src).mag();
					double r = s1.radius;
					double x = projection.minus(s1.center).mag();
					return (float) (L - Math.sqrt((r * r) - (x * x)));
				}
			}
		} else {
			// source inside

			double L = projection.minus(s2.src).mag();
			double r = s1.radius;
			double x = projection.minus(s1.center).mag();
			return (float) (L + Math.sqrt((r * r) - (x * x)));
		}

		return -1;
	}
	
	@Override
	public float raycast(PolygonShape s1, Ray s2) {
		float minT = Float.POSITIVE_INFINITY;
		boolean foundIntersection = false;
		for (int i = 0; i < s1.getNumPoints(); i++) {
			float t = raycastEdge(s2, s1.getPoint(i), s1.getPoint((i+1) % s1.getNumPoints()));
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

	private float raycastEdge(Ray ray, Vec2d a, Vec2d b) {
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
}
