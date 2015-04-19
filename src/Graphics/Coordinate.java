/**
 * 
 */
package Graphics;

/**
 * @author mtv
 *
 */
public class Coordinate {
	private double x, y, z;
	
	public Coordinate( double x, double y, double z ) {
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
	
	public static double distance( Coordinate pos0, Coordinate pos1 ) {
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
	
}
