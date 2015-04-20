/**
 *
 */
package math;

/**
 * @author mtv
 *
 */
public class Coordinate3D {
	public final double	x, y, z;

	public Coordinate3D( double x, double y, double z ) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double[] getPosition() {
		double[] position = {x, y, z};
		return position;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public static double distance( Coordinate3D pos0, Coordinate3D pos1 ) {
		double distance;
		double[] coord0 = pos0.getPosition();
		double[] coord1 = pos1.getPosition();

		distance = Math.sqrt(
				Math.pow((coord1[0] - coord0[0]), 2) +
				Math.pow((coord1[1] - coord0[1]), 2) +
				Math.pow((coord1[2] - coord0[2]), 2)
				);


		return distance;
	}

	@Override
	public boolean equals( Object o ) {
		if (!( o instanceof Coordinate3D )) {
			return false;
		}
		Coordinate3D coord = ( Coordinate3D ) o;
		boolean isEqual = true;
		isEqual = isEqual && almostEqual( this.x, coord.x );
		isEqual = isEqual && almostEqual( this.y, coord.y );
		isEqual = isEqual && almostEqual( this.z, coord.z );
		return isEqual;
	}

	private boolean almostEqual( double a, double b ) {
		return Math.abs( a - b ) < .000001;
	}

}
