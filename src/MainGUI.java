import java.awt.Color;

import javax.swing.JFrame;

import Graphics.Coordinate;
import Graphics.Engine3D;

public class MainGUI extends JFrame {
	public final Color	COLORS[]	= { Color.GREEN, Color.RED, Color.BLUE,
			Color.YELLOW, Color.MAGENTA, Color.PINK };

	public MainGUI() {
		super( "Engine3D GUI" );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.setVisible( true );
		Engine3D window = new Engine3D( 640, 640, 640 );
		this.add( window );
		window.setBackground( Color.BLACK );
		this.pack();

		window.setColor( Color.cyan );
		window.addTriangle( new Coordinate( 1, 1, 1 ), new Coordinate( 200,
				200, 200 ), new Coordinate( 100, 200, 200 ) );
		window.setColor( Color.YELLOW );
		window.addTriangle( new Coordinate( 1, 1, 1 ), new Coordinate( 200,
				200, 200 ), new Coordinate( -100, 100, 300 ) );
	}
}
