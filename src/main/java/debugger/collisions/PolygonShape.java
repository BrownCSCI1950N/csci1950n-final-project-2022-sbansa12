package debugger.collisions;

import debugger.support.Vec2d;
import debugger.support.shapes.Shape;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class PolygonShape extends Shape {
	
	protected Vec2d[] points;
	
	public PolygonShape(Vec2d ... points) {
		this.points = points;
	}
	
	public int getNumPoints() {
		return points.length;
	}
	
	public Vec2d getPoint(int i) {
		return points[i];
	}


	public List<Vec2d> getPoints() {
		return Arrays.asList(points);
	}
	public List<Vec2d> getEdgeVectors() {
		List<Vec2d> edgeVectors = new LinkedList<>();
		for (int i = 0; i < getNumPoints(); i++) {
			edgeVectors.add(getPoint((i+1) % getNumPoints()).minus(getPoint(i)));
		}
		return edgeVectors;
	}
}
