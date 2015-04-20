package Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import Math.Coordinate3D;
import Math.Vector3D;

public class Engine3D extends JPanel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 2534085965217000216L;

	public enum Bin {
		LINE, TRIANGLE, RECTANGLE, POLYGON, ALL
	}

	private final boolean		ENABLE_PERSPECTIVE;
	private final double		V_OFFSET;
	private final double		H_OFFSET;
	private final double		D_OFFSET;
	private final double		SCALE;
	private double				zoom		= 0;
	private ArrayList<Triangle>	triBin;
	private Color				addColor;
	private boolean				triDirty	= false;

	public Engine3D( int width, int height, int depth, boolean enablePerspective ) {
		super();

		this.ENABLE_PERSPECTIVE = enablePerspective;

		triBin = new ArrayList<Triangle>();

		V_OFFSET = height / 2.0;
		H_OFFSET = width / 2.0;
		D_OFFSET = depth / 2.0;

		if (ENABLE_PERSPECTIVE) {
			SCALE = D_OFFSET;
		} else {
			SCALE = 1;
		}

		zoom = 1;

		this.setPreferredSize( new Dimension( width, height ) );

		addColor = Color.GRAY;
	}

	public void addLine( Coordinate3D pos0, Coordinate3D pos1, double thickness )
			throws Exception {
		double slope = ( pos1.y - pos0.y ) / ( pos1.x - pos0.x );
		double perpSlope = -1.0 / slope;
		double halfDist = thickness / 2.0;
		double deltaX = Math.sqrt( Math.pow( halfDist, 2.0 )
				/ ( 1.0 + Math.pow( perpSlope, 2.0 ) ) );
		double deltaY = perpSlope * deltaX;
		Coordinate3D a = new Coordinate3D( pos0.x - deltaX, pos0.y - deltaY,
				pos0.z );
		Coordinate3D b = new Coordinate3D( pos0.x + deltaX, pos0.y + deltaY,
				pos0.z );
		Coordinate3D c = new Coordinate3D( pos1.x + deltaX, pos1.y + deltaY,
				pos1.z );
		Coordinate3D d = new Coordinate3D( pos1.x - deltaX, pos1.y - deltaY,
				pos1.z );
		System.out.println( deltaX + " " + deltaY );
		this.addRect( a, b, c, d );

	}

	public boolean addTriangle( Coordinate3D pos0, Coordinate3D pos1,
			Coordinate3D pos2 ) {
		triBin.add( new Triangle( pos0, pos1, pos2, addColor ) );
		triDirty = true;
		return true;
	}

	public void addRect( Coordinate3D pos0, Coordinate3D pos1, Coordinate3D pos2,
 Coordinate3D pos3 ) throws Exception {

		Vector3D ab = new Vector3D( pos0, pos1 );
		Vector3D ac = new Vector3D( pos0, pos2 );
		Vector3D norm = Vector3D.crossProduct( ab, ac );
		Vector3D ad = new Vector3D( pos0, pos3 );
		double coplanar = Vector3D.dotProduct( norm, ad );
		if (!( coplanar < .000001 )) {
			System.err.println( coplanar );
			throw new Exception( "Rectangle is not co-planar. " );
		}

		triBin.add( new Triangle( pos0, pos1, pos2, addColor ) );
		triBin.add( new Triangle( pos2, pos3, pos0, addColor ) );

		triDirty = true;
	}

	public boolean addPolygon( Coordinate3D[] pos ) {
		return false;
	}

	public void setColor( Color color ) {
		addColor = color;
	}

	public void clear() {
		triBin.clear();
	}

	public int getBinSize() {
		return -1;
	}

	public int getBinSize( Bin bin ) {
		return -1;
	}

	public void setZoom( double zoom ) {
		this.zoom = zoom;
	}

	public double getZoom() {
		return this.zoom;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON );
		((Graphics2D) g).setRenderingHints(rh);

		drawTriBin( g );

	}

	private boolean drawTriBin( Graphics g ) {

		if (triDirty) {
			Collections.sort( triBin );
			triDirty = false;
		}

		for ( int i = 0; i < triBin.size(); i++ ) {
			int[] pt0 = new int[2];
			int[] pt1 = new int[2];
			int[] pt2 = new int[2];
			Coordinate3D[] coords = triBin.get( i ).getPoints();
			pt0 = worldToScreen( coords[0] );
			pt1 = worldToScreen( coords[1] );
			pt2 = worldToScreen( coords[2] );
			int[] x = { pt0[0], pt1[0], pt2[0] };
			int[] y = { pt0[1], pt1[1], pt2[1] };
			g.setColor( triBin.get( i ).getColor() );
			g.fillPolygon( x, y, 3 );
		}
		return true;
	}

	private int[] worldToScreen( Coordinate3D world ) {
		int[] screen = new int[2];
		double depthModifier = 1.0;
		double scaleFactor = SCALE * zoom;

		if (ENABLE_PERSPECTIVE) {
			depthModifier = world.getZ() + D_OFFSET;
		}

		screen[0] = ( int ) ( H_OFFSET + scaleFactor * world.getX()
				/ depthModifier );
		screen[1] = ( int ) ( V_OFFSET + scaleFactor * world.getY()
				/ depthModifier );
		return screen;
	}
}
