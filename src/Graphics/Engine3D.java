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
	private ArrayList<Triangle>	lineBin;
	private ArrayList<Triangle>	triBin;
	private ArrayList<Triangle>	rectBin;
	private ArrayList<Triangle>	polyBin;
	private Color				addColor;
	private boolean				lineDirty	= false;
	private boolean				triDirty	= false;
	private boolean				rectDirty	= false;
	private boolean				polyDirty	= false;

	public Engine3D( int width, int height, int depth, boolean enablePerspective ) {
		super();

		this.ENABLE_PERSPECTIVE = enablePerspective;

		lineBin = new ArrayList<Triangle>();
		triBin = new ArrayList<Triangle>();
		rectBin = new ArrayList<Triangle>();
		polyBin = new ArrayList<Triangle>();

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

	public boolean addLine( Coordinate3D pos0, Coordinate3D pos1 ) {
		return false;
	}

	public boolean addTriangle( Coordinate3D pos0, Coordinate3D pos1,
			Coordinate3D pos2 ) {
		triBin.add( new Triangle( pos0, pos1, pos2, addColor ) );
		triDirty = true;
		return true;
	}

	public void addRect( Coordinate3D pos0, Coordinate3D pos1, Coordinate3D pos2,
			Coordinate3D pos3 ) {
		// is it co-planer?
		rectBin.add( new Triangle( pos0, pos1, pos2, addColor ) );
		rectBin.add( new Triangle( pos2, pos3, pos0, addColor ) );

	}

	public boolean addPolygon( Coordinate3D[] pos ) {
		return false;
	}

	public void setColor( Color color ) {
		addColor = color;
	}

	public void clear() {
		lineBin.clear();
		triBin.clear();
		rectBin.clear();
		polyBin.clear();
	}

	public void clear( Bin bin ) throws Exception {
		switch ( bin ) {
		case LINE:
			lineBin.clear();
			break;
		case RECTANGLE:
			rectBin.clear();
			break;
		case POLYGON:
			rectBin.clear();
		case ALL:
			this.clear();
			break;
		default:
			throw new Exception( "Invalid Bin Identifier" );
		}
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

		//do stuff for lines

		drawTriBin( g, Bin.TRIANGLE );
		drawTriBin( g, Bin.RECTANGLE );
		drawTriBin( g, Bin.POLYGON );

	}

	private boolean drawTriBin( Graphics g, Bin bin ) {
		ArrayList<Triangle> geometry;
		switch ( bin ) {
		case LINE:
			if (lineDirty)
				Collections.sort( lineBin );
			geometry = lineBin;
			break;
		case TRIANGLE:
			if (triDirty)
				Collections.sort( triBin );
			geometry = triBin;
			break;
		case RECTANGLE:
			if (rectDirty)
				Collections.sort( rectBin );
			geometry = rectBin;
			break;
		case POLYGON:
			if (polyDirty)
				Collections.sort( polyBin );
			geometry = polyBin;
			break;
		default:
			return false;
		}

		for ( int i = 0; i < geometry.size(); i++ ) {
			int[] pt0 = new int[2];
			int[] pt1 = new int[2];
			int[] pt2 = new int[2];
			Coordinate3D[] coords = geometry.get( i ).getPoints();
			pt0 = worldToScreen( coords[0] );
			pt1 = worldToScreen( coords[1] );
			pt2 = worldToScreen( coords[2] );
			int[] x = { pt0[0], pt1[0], pt2[0] };
			int[] y = { pt0[1], pt1[1], pt2[1] };
			g.setColor( geometry.get( i ).getColor() );
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
