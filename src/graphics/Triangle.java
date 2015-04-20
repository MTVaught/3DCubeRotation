package graphics;

import java.awt.Color;
import java.util.Arrays;

import math.Coordinate3D;

public class Triangle implements Comparable<Triangle>{

	Coordinate3D[] points;
	Color color;

	public Triangle( Coordinate3D pos0, Coordinate3D pos1, Coordinate3D pos2, Color color) {
		this.points = new Coordinate3D[3];
		points[0] = pos0;
		points[1] = pos1;
		points[2] = pos2;
		this.color = color;
	}

	public Coordinate3D[] getPoints() {
		return Arrays.copyOf(points, points.length);
	}

	public double getDepth() {
		return ( points[0].getZ() + points[1].getZ() + points[2].getZ() ) / 2.0;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public int compareTo(Triangle o) {
		Double a = this.getDepth();
		Double b = o.getDepth();
		return b.compareTo( a );
	}

}
