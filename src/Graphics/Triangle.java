package Graphics;

import java.awt.Color;
import java.util.Arrays;

public class Triangle implements Comparable<Triangle>{

	Coordinate[] points;
	Color color;

	public Triangle( Coordinate pos0, Coordinate pos1, Coordinate pos2, Color color) {
		this.points = new Coordinate[3];
		points[0] = pos0;
		points[1] = pos1;
		points[2] = pos2;
	}

	public Coordinate[] getPoints() {
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
		return a.compareTo(b);
	}

}
