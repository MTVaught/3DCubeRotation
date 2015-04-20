package math;

public class Vector3D {

	public final Coordinate3D	A;
	public final Coordinate3D	B;
	public final double			x, y, z;
	public final double			MAGNITUDE;

	public Vector3D( Coordinate3D a, Coordinate3D b ) {
		this.A = a;
		this.B = b;
		x = B.x - A.x;
		y = B.y - A.y;
		z = B.z - A.z;
		this.MAGNITUDE = Math.abs( Coordinate3D.distance( a, b ) );
	}

	public static Vector3D crossProduct( Vector3D v0, Vector3D v1 )
			throws Exception {

		if (!( v0.A.equals( v1.A ) ))
			throw new Exception( "Can't take the cross product of vectors "
					+ "with different origins" );
		double x = v0.A.x, y = v0.A.y, z = v0.A.z;
		x += v0.y * v1.z - v0.z * v1.y;
		y += v0.z * v1.x - v0.x * v1.z;
		z += v0.x * v1.y - v0.y * v1.x;

		return new Vector3D( v0.A, new Coordinate3D( x, y, z ) );
	}

	public static double dotProduct( Vector3D v0, Vector3D v1 )
			throws Exception {

		if (!( v0.A.equals( v1.A ) ))
			throw new Exception( "Can't take the dot product of vectors "
					+ "with different origins" );

		double rtn = 0;
		rtn += v0.x * v1.x;
		rtn += v0.y * v1.y;
		rtn += v0.z * v1.z;

		return rtn;
	}

	@Override
	public boolean equals( Object o ) {
		if (!( o instanceof Vector3D )) {
			return false;
		}
		Vector3D v = ( Vector3D ) o;
		return this.A.equals( v.A ) && this.B.equals( v.B );
	}

}
