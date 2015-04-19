package Graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Engine3D extends JPanel {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 2534085965217000216L;

	public enum Bin {
		LINE, TRIANGLE, RECTANGLE, POLYGON, ALL, N_BIN
	}

	private double	offset	= 0, scale = 0, distance = 0;
	private ArrayList<Triangle>	tri, rect, poly;

	public Engine3D( double width, double height, double depth ) {
		super();
		tri = new ArrayList<Triangle>();
		rect = new ArrayList<Triangle>();
		poly = new ArrayList<Triangle>();

		distance = depth / 2.0;
		scale = 100;
		offset = 300;
	}

	public boolean addLine( Coordinate pos0, Coordinate pos1 ) {
		return false;
	}

	public boolean addTriangle( Coordinate pos0, Coordinate pos1,
			Coordinate pos2 ) {
		return false;
	}

	public boolean addRect( Coordinate pos0, Coordinate pos1, Coordinate pos2,
			Coordinate pos3 ) {
		return false;
	}

	public boolean addPolygon( Coordinate[] pos ) {
		return false;
	}

	public boolean clear() {
		return false;
	}

	public boolean clear( Bin bin ) {
		return false;
	}

	public int getBinSize() {
		return -1;
	}

	public int getBinSize( Bin bin ) {
		return -1;
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHints(rh);

		//do stuff for lines

		//do stuff for triangles

		drawTriBin( g, Bin.TRIANGLE );
		drawTriBin( g, Bin.RECTANGLE );
		drawTriBin( g, Bin.POLYGON );

		// for(int i = 0; i < line.length; i++){
		// int[] pt1 = new int[2];
		// int[] pt2 = new int[2];
		// double scale = 1000;
		// double offset = 200;
		// double distance = 100;
		//
		// pt1 = worldToScreen(toRender[i][0]);
		// pt2 = worldToScreen(toRender[i][1]);
		//
		// if (i < 4){
		// g.setColor(Color.RED);
		// }else {
		// g.setColor(Color.LIGHT_GRAY);
		// }
		// g.drawLine(pt1[0], pt1[1], pt2[0], pt2[1]);
		// }

	}

	private boolean drawTriBin( Graphics g, Bin bin ) {
		ArrayList<Triangle> geometry;
		switch ( bin ) {
		case TRIANGLE:
			geometry = tri;
			break;
		case RECTANGLE:
			geometry = rect;
			break;
		case POLYGON:
			geometry = poly;
			break;
		default:
			return false;
		}

		for ( int i = 0; i < geometry.size(); i++ ) {
			int[] pt0 = new int[2];
			int[] pt1 = new int[2];
			int[] pt2 = new int[2];
			Coordinate[] coords = geometry.get( i ).getPoints();
			pt0 = worldToScreen( coords[0] );
			pt1 = worldToScreen( coords[1] );
			pt2 = worldToScreen( coords[2] );
			int[] x = { pt0[0], pt1[0], pt2[0] };
			int[] y = { pt0[1], pt1[1], pt2[1] };
			g.setColor( rect.get( i ).getColor() );
			g.fillPolygon( x, y, 3 );
		}
		return true;
	}

	private int[] worldToScreen( Coordinate world ) {
		int[] screen = new int[2];
		screen[0] = ( int ) ( offset + scale * world.getX()
				/ ( world.getZ() + distance ) );
		screen[1] = ( int ) ( offset + scale * world.getY()
				/ ( world.getZ() + distance ) );
		return screen;
	}
}
