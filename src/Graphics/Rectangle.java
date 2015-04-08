package Graphics;

import java.awt.Color;
import java.util.Arrays;

public class Rectangle implements Comparable{
	double pt[][] = new double [4][3];
	double average[] = new double[3];
	double depth= 0;
	Color color = Color.gray;
	
	public Rectangle(double[] a, double[] b, double[] c, double[] d) {
		pt[0] = a;
		pt[1] = b;
		pt[2] = c;
		pt[3] = d;
		for(int i = 0; i < 4; i++ ){
			average[0] = pt[i][0];
			average[1] = pt[i][1];
			average[2] = pt[i][2];
			depth = Math.max(depth, pt[i][2]);
		}
		average[0] /= 4;
		average[1] /= 4;
		average[2] /= 4;
	}
	
	public double[][] getPoints(){
		return Arrays.copyOf(pt, pt.length);
	}
	
	public double[] getAverage(){
		return Arrays.copyOf(average, average.length);
	}
	
	public double getDepth(){
		return depth;
	}
	public void setDepth(double depth){
		
	}
	public void setColor(Color color){
		this.color = color;
	}
	public Color getColor(){
		return color;
	}

	@Override
	public int compareTo(Object arg0) {
		return (new Double(depth)).compareTo(new Double(((Rectangle)arg0).getDepth()));
	}
}