package Graphics;

import java.awt.Color;
import java.util.ArrayList;

import Graphics.Rectangle;




public class EulerAngles {

	private double alpha,beta,gamma;
	private ArrayList<Rectangle> rectangles;
	private double scale = 1000;
	private double offset = 300;
	private double distance = 100;
	
	public EulerAngles(){
		
	}
	
	public double alpha(){
		return alpha;
	}
	
	public void setAlpha(double alpha){
		this.alpha=alpha;
	}
	
	public double beta(){
		return beta;
	}
	
	public void setBeta( double beta ){
		this.beta = beta;
	}
	
	public double gamma(){
		return gamma;
	}
	
	public void setGamma( double gamma ){
		this.gamma = gamma;
	}
	
}
