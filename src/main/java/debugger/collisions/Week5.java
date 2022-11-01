package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.interfaces.Week5Reqs;
import engine.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Fill this class in during Week 5. Make sure to also change the week variable in Display.java.
 */
public final class Week5 extends Week5Reqs {

	Week3 mtv = new Week3();

	// AXIS-ALIGNED BOXES
	
	@Override
	public Vec2d collision(AABShape s1, AABShape s2) {
		return mtv.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, CircleShape s2) {
		return mtv.collision(s1, s2);
	}

	@Override
	public Vec2d collision(AABShape s1, Vec2d s2) {
		return mtv.collision(s1, s2);
	}

	public Interval projectAAB(AABShape a, Vec2d axis) {
		Vec2d topLeft = a.getTopLeft();
		Vec2d topRight = a.getTopLeft().plus(a.size.x, 0);
		Vec2d bottomLeft = a.getTopLeft().plus(0, a.size.y);
		Vec2d bottomRight = a.getTopLeft().plus(a.size);

		List<Vec2d> points = List.of(topLeft, topRight, bottomLeft, bottomRight);

		return projectPoints(axis, points);
	}

	public Interval projectPolygon(PolygonShape a, Vec2d axis) {

		List<Vec2d> points = a.getPoints();

		return projectPoints(axis, points);
	}

	private Interval projectPoints(Vec2d axis, List<Vec2d> points) {
		double min = axis.dot(points.get(0));
		double max = min;

		for (int i = 1; i < points.size(); i++) {
			double p = axis.dot(points.get(i));
			if (p < min) {
				min = p;
			}
			if (p > max) {
				max = p;
			}
		}

		return new Interval(min, max);
	}

	@Override
	public Vec2d collision(AABShape s1, PolygonShape s2) {
		List<Vec2d> aabSeparatingAxis = List.of(new Vec2d(1,0), new Vec2d(0,1));
		List<Vec2d> polySeparatingAxis = s2.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

		List<Vec2d> separatingAxis = new LinkedList<>();
		separatingAxis.addAll(aabSeparatingAxis);
		separatingAxis.addAll(polySeparatingAxis);

		double minMagnitude = Double.POSITIVE_INFINITY;
		Vec2d mtv = null;

		for (Vec2d axis : separatingAxis) {
			Interval aab = projectAAB(s1, axis);
			Interval poly = projectPolygon(s2, axis);
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

	// CIRCLES
	
	@Override
	public Vec2d collision(CircleShape s1, AABShape s2) {
		Vec2d f = collision(s2, s1);
		return f == null ? null : f.reflect();
	}

	@Override
	public Vec2d collision(CircleShape s1, CircleShape s2) {
		return mtv.collision(s1, s2);
	}

	@Override
	public Vec2d collision(CircleShape s1, Vec2d s2) {
		return mtv.collision(s1, s2);
	}

	public Vec2d closestPoint(Vec2d p, List<Vec2d> points) {
		assert points.size() > 0;
		double distance = p.dist2(points.get(0));
		Vec2d toReturn = points.get(0);
		for (Vec2d point : points) {
			double distance1 = p.dist2(point);
			if (distance1 < distance) {
				distance = distance1;
				toReturn = point;
			}
		}

		return toReturn;
	}

	public Interval projectCircle(CircleShape a, Vec2d axis) {
		Vec2d center = a.center.projectOnto(axis);
		Vec2d translate = axis.smult(a.radius);
		List<Vec2d> points = List.of(center.plus(translate), center.minus(translate));

		return projectPoints(axis, points);
	}

	@Override
	public Vec2d collision(CircleShape s1, PolygonShape s2) {
		// Find the Closest Vertex of Polygon to Circle Center
		Vec2d closestPoint = closestPoint(s1.center, s2.getPoints());
		List<Vec2d> circleSeparatingAxis = List.of(closestPoint.minus(s1.center).normalize());

		List<Vec2d> polySeparatingAxis = s2.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

		List<Vec2d> separatingAxis = new LinkedList<>();
		separatingAxis.addAll(circleSeparatingAxis);
		separatingAxis.addAll(polySeparatingAxis);

		double minMagnitude = Double.POSITIVE_INFINITY;
		Vec2d mtv = null;

		for (Vec2d axis : separatingAxis) {
			Interval circle = projectCircle(s1, axis);
			Interval poly = projectPolygon(s2, axis);
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
		for (int i = 0; i < s1.getNumPoints(); i++) {
			Vec2d edgeVector = s1.getPoint((i + 1) % s1.getNumPoints()).minus(s1.getPoint(i));
			Vec2d vectorPoint = s2.minus(s1.getPoint(i));
			if (edgeVector.cross(vectorPoint) > 0) {
				return null;
			}
		}

		// Point is Inside Poly
		Vec2d closestPoint = closestPoint(s2, s1.getPoints());
		// WIERD: POLY POINT MTV
		return closestPoint.minus(s2);
	}

	@Override
	public Vec2d collision(PolygonShape s1, PolygonShape s2) {
		List<Vec2d> polySeparatingAxis1 = s1.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());
		List<Vec2d> polySeparatingAxis2 = s2.getEdgeVectors().stream().map(x -> x.perpendicular().normalize()).collect(Collectors.toList());

		List<Vec2d> separatingAxis = new LinkedList<>();
		separatingAxis.addAll(polySeparatingAxis1);
		separatingAxis.addAll(polySeparatingAxis2);

		double minMagnitude = Double.POSITIVE_INFINITY;
		Vec2d mtv = null;

		for (Vec2d axis : separatingAxis) {
			Interval poly1 = projectPolygon(s1, axis);
			Interval poly2 = projectPolygon(s2, axis);
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
}
