
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Graphics.Rectangle;

public class MainGUI extends JFrame implements KeyListener {
	double[][][] line = new double[12][2][3];
	double[][][] toRender = new double[12][2][3];
	ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
	ArrayList<Rectangle> rectBin = new ArrayList<Rectangle>();
	double scale = 1000;
	double offset = 300;
	double distance = 100;
	Color colors[] = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.PINK};

	double alpha=0, beta=0, gamma=0;

	public MainGUI (){
		super("TEST RENDER");
		double[] a = {-10,-10,-10};
		double[] b = {-10,10,-10};
		double[] c = {10,10,-10};
		double[] d = {10,-10,-10};
		double[] aa = {-10,-10,10};
		double[] bb = {-10,10,10};
		double[] cc = {10,10,10};
		double[] dd = {10,-10,10};

		rects.add(new Rectangle(a,b,c,d));
		rects.add(new Rectangle(aa,bb,cc,dd));
		rects.add(new Rectangle(aa, bb, b, a));
		rects.add(new Rectangle(bb, cc, c, b));
		rects.add(new Rectangle(cc, dd, d, c));
		rects.add(new Rectangle(dd, aa, a, d));


		line[0][0] = (a);
		line[0][1] = (b);

		line[1][0] = (b);
		line[1][1] = (c);

		line[2][0] = (c);
		line[2][1] =(d);

		line[3][0] = d;
		line[3][1] = a;

		line[4][0] = a;
		line[4][1] = aa;

		line[5][0] = b;
		line[5][1] = bb;

		line[6][0] = c;		
		line[6][1] = cc;

		line[7][0] = d;
		line[7][1] = dd;

		line[8][0] = aa;
		line[8][1] = bb;

		line[9][0] = bb;
		line[9][1] = cc;

		line[10][0] = cc;
		line[10][1] = dd;

		line[11][0] = dd;
		line[11][1] = aa;


		transform();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

		JPanel drawPanel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				RenderingHints rh = new RenderingHints(
						RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHints(rh);
				Collections.sort(rectBin);


				for(int i = 0; i < line.length; i++){
					int[] pt1 = new int[2];
					int[] pt2 = new int[2];
					double scale = 1000;
					double offset = 200;
					double distance = 100;

					pt1 = worldToScreen(toRender[i][0]);
					pt2 = worldToScreen(toRender[i][1]);

					if (i < 4){
						g.setColor(Color.RED);
					}else {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.drawLine(pt1[0], pt1[1], pt2[0], pt2[1]);
				}
				for(int i = rectBin.size() - 1; i >= 0; i--){
					int[] pt1 = new int[2];
					int[] pt2 = new int[2];
					int[] pt3 = new int[2];
					int[] pt4 = new int[2];

					double[][] rect = rectBin.get(i).getPoints();
					pt1 = worldToScreen(rect[0]);
					pt2 = worldToScreen(rect[1]);
					pt3 = worldToScreen(rect[2]);
					pt4 = worldToScreen(rect[3]);
					int[] x = {pt1[0], pt2[0], pt3[0], pt4[0]};
					int[] y = {pt1[1], pt2[1], pt3[1], pt4[1]};
					g.setColor(rectBin.get(i).getColor());
					g.fillPolygon(x, y, 4);
					g.setColor(Color.BLACK);
					g.drawPolygon(x, y, 4);
				}

			}
		};
		this.add(drawPanel);
		this.addKeyListener(this);
		drawPanel.setPreferredSize(new Dimension(640, 640));
		drawPanel.setBackground(Color.BLACK);
		this.pack();

	}

	private int[] worldToScreen(double[] world){
		int[] screen = new int[2];
		screen[0] = (int)(offset + scale * world[0] / ( world[2] + distance));
		screen[1] = (int)(offset + scale * world[1] / ( world[2] + distance));
		return screen;
	}

	private void transform(double alpha) {
		double[][] trans = getAlphaMatrix();
		for(int i = 0; i < line.length; i++){
			for(int j = 0; j < line[i].length; j++){
				applyTransformMatrix(toRender[i][j],line[i][j], trans);
			}
		}

		repaint();
	}

	private void transform() {
		double[][] transA = getAlphaMatrix();
		double[][] transB = getBetaMatrix();
		double[][] transC = getGammaMatrix();
		for(int i = 0; i < line.length; i++){
			for(int j = 0; j < line[i].length; j++){
				applyTransformMatrix(toRender[i][j],line[i][j], transA);
				applyTransformMatrix(toRender[i][j],toRender[i][j], transB);
				applyTransformMatrix(toRender[i][j],toRender[i][j], transC);
			}
		}
		rectBin.clear();
		for(int i = 0; i < rects.size(); i++){
			double pt[][] = rects.get(i).getPoints();
			double renderpt[][] = new double[4][3];
			for(int j = 0; j < pt.length; j++){
				applyTransformMatrix(renderpt[j],pt[j], transA);
				applyTransformMatrix(renderpt[j],renderpt[j], transB);
				applyTransformMatrix(renderpt[j],renderpt[j], transC);
			}
			Rectangle r = new Rectangle(renderpt[0], renderpt[1], renderpt[2], renderpt[3]);
			r.setColor(colors[i%6]);
			rectBin.add(r);

		}
		repaint();
	}




	private void applyTransformMatrix(double[] XYZ, double[] xyz, double[][] trans){
		double x = xyz[0];
		double y = xyz[1];
		double z = xyz[2];
		XYZ[0] = (x * trans[0][0] + y * trans[0][1] + z * trans[0][2]);
		XYZ[1] = (x * trans[1][0] + y * trans[1][1] + z * trans[1][2]);
		XYZ[2] = (x * trans[2][0] + y * trans[2][1] + z * trans[2][2]);
	}

	private double[][] getAlphaMatrix(){
		double trans[][] = new double[3][3];

		trans[0][0] = Math.cos(alpha);
		trans[0][1] = Math.sin(alpha);
		trans[0][2] = 0;
		trans[1][0] = -1* Math.sin(alpha);
		trans[1][1] = Math.cos( alpha );
		trans[1][2] = 0;
		trans[2][0] = 0;
		trans[2][1] = 0;
		trans[2][2] = 1;

		return trans;
	}

	private double[][] getBetaMatrix(){
		double trans[][] = new double[3][3];

		trans[0][0] = 1;
		trans[0][1] = 0;
		trans[0][2] = 0;
		trans[1][0] = 0;
		trans[1][1] = Math.cos( beta );
		trans[1][2] = Math.sin( beta );
		trans[2][0] = 0;
		trans[2][1] = -1 * Math.sin(beta);
		trans[2][2] = Math.cos( beta );

		return trans;
	}

	private double[][] getGammaMatrix(){
		double trans[][] = new double[3][3];

		trans[0][0] = Math.cos(gamma);
		trans[0][1] = 0;
		trans[0][2] = Math.sin(gamma);
		trans[1][0] = 0;
		trans[1][1] = 1;
		trans[1][2] = 0;
		trans[2][0] = -1 * Math.sin(gamma);
		trans[2][1] = 0;
		trans[2][2] = Math.cos(gamma);

		return trans;
	}


	private double[][] getTransformMatrix(double alpha, double beta, double gamma){
		double trans[][] = new double[3][3];

		trans[0][0] = Math.cos(alpha) * Math.cos(beta);
		trans[0][1] = Math.sin(gamma) * Math.cos(beta);
		trans[0][2] = -1*Math.sin(beta);

		trans[1][0] = Math.sin(alpha) * Math.sin(beta) * Math.cos(gamma) -
				Math.cos(alpha) * Math.sin(gamma);
		trans[1][1] = Math.sin(alpha) * Math.sin(beta) * Math.sin(gamma) +
				Math.cos(alpha) * Math.cos(gamma);
		trans[1][2] = Math.sin(alpha) * Math.cos(beta);

		trans[2][0] = Math.cos(alpha) * Math.sin(beta) * Math.cos(gamma) +
				Math.sin(alpha) * Math.sin(gamma);
		trans[2][1] = Math.cos(alpha) * Math.sin(beta) * Math.sin(gamma) -
				Math.sin(alpha) * Math.cos(gamma);
		trans[2][2] = Math.cos(alpha) * Math.cos(beta);

		return trans;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (e.getKeyChar()){
		case 'e':
			alpha -= .1;
			break;
		case 'q':
			alpha += .1;
			break;
		case 's':
			beta -= .1;
			break;
		case 'w':
			beta += .1;
			break;
		case 'd':
			gamma -= .1;
			break;
		case 'a':
			gamma += .1;
			break;
		case ' ':
			alpha = 0;
			beta = 0;
			gamma = 0;
			break;
		case '-':
			distance += 1;
			break;
		case '=':
			distance -=1;
		}

		transform();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}