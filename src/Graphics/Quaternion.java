package Graphics;

import javax.swing.JPanel;

public class Quaternion extends JPanel{
	
	public enum Bin { LINE, TRIANGLE, RECTANGLE, POLYGON, ALL, N_BIN }

	public Quaternion(){
		super();
	}
	
	public boolean addLine( Coordinate pos0, Coordinate pos1 ) {
		return false;
	}
	
	public boolean addTriangle( Coordinate pos0, Coordinate pos1, Coordinate pos2) {
		return false;
	}
	
	public boolean addRect( Coordinate pos0, Coordinate pos1, Coordinate pos2, Coordinate pos3 ){
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
	
	
	
}
