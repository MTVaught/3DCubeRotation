
public class Main {

	public static void main( String[] args ) throws Exception {

		System.out.println( " World" );

		System.out.println( "Hello  2" );

		// new MainEulerGUI();

		MainGUI withScale = new MainGUI( true );
		withScale.setTitle( withScale.getTitle() + " (Perspective)" );
		MainGUI withoutScale = new MainGUI( false );
		withoutScale.setTitle( withoutScale.getTitle() + " (No Perspective)" );

	}

}