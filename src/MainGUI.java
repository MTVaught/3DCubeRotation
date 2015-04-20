import graphics.Engine3D;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import math.Coordinate3D;

public class MainGUI extends JFrame implements KeyListener {
	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	public final Color	COLORS[]	= { Color.GREEN, Color.RED, Color.BLUE,
			Color.YELLOW, Color.MAGENTA, Color.PINK, Color.CYAN };

	private Engine3D	window;

	public MainGUI( boolean enablePerspective ) throws Exception {
		super( "Engine3D GUI" );
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.setVisible( true );
		window = new Engine3D( 640, 640, 640, enablePerspective );
		this.add( window );
		window.setBackground( Color.BLACK );
		this.addKeyListener( this );
		this.pack();

		window.setColor( COLORS[6] );
		window.addTriangle( new Coordinate3D( 0, 0, 0 ), new Coordinate3D( 200,
				200, 200 ), new Coordinate3D( 100, 200, 200 ) );
		window.setColor( COLORS[3] );
		window.addTriangle( new Coordinate3D( 0, 0, 0 ), new Coordinate3D( 200,
				200, 200 ), new Coordinate3D( -100, 100, 300 ) );

		window.setColor( COLORS[0] );
		window.addLine( new Coordinate3D( 0, 0, 0 ), new Coordinate3D( -200,
				-200, 0 ), 1 );
		// window.addTriangle( new Coordinate3D( 0, 0, 0 ), new Coordinate3D(
		// -200, -200, 0 ), new Coordinate3D( -210, -200, 0 ) );
		// window.addTriangle( new Coordinate3D( 0, 0, 0 ), new Coordinate3D(
		// -10,
		// 0, 0 ), new Coordinate3D( -210, -200, 0 ) );

		window.setColor( COLORS[1] );
		window.addRect( new Coordinate3D( 0, -50, 0 ), new Coordinate3D( 100,
				-50, 0 ), new Coordinate3D( 100, 50, 0 ), new Coordinate3D( 0,
				50, 0 ) );

		window.setColor( COLORS[2] );
		window.addRect( new Coordinate3D( -100, -50, 0 ), new Coordinate3D( 0,
				-50, 100 ), new Coordinate3D( 0, 50, 100 ), new Coordinate3D(
				-100, 50, 0 ) );

	}

	@Override
	public void keyTyped( KeyEvent e ) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed( KeyEvent e ) {

		boolean repaint = false;

		switch ( e.getKeyChar() ) {
		case '=':
			window.setZoom( window.getZoom() * 1.1 );
			repaint = true;
			break;
		case '-':
			window.setZoom( window.getZoom() / 1.1 );
			repaint = true;
			break;
		}
		if (repaint)
			this.repaint();

	}

	@Override
	public void keyReleased( KeyEvent e ) {
		// TODO Auto-generated method stub

	}
}
