package core;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Vec {
	
	public double x;
	public double y;
	
	public boolean fixed = false; //True if vector has a position.
	public double posX;
	public double posY;
	
	public Vec(double x, double y){	//Floating vector.
		set(x, y);
	}
	
	public Vec(double x, double y, double posX, double posY){ //Fixed vector.
		this.x = x;
		this.y = y;
		
		fixed = true;
		this.posX = posX;
		this.posY = posY;
	}
	
	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void change(double x, double y){
		this.x += x;
		this.y += y;
	}
	
	public void change(Vec m){
		this.x += m.x;
		this.y += m.y;
	}
	
	public void move(Vec m){
		this.posX += m.x;
		this.posY += m.y;
	}
	
	public double getMag(){ //Returns magnitude.
		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	}
	
	public double getAng(){ //Returns angle from North.
		if(getMag() == 0)return 0;
		if(x>=0)return Math.acos(-y/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))));
		return 2*Math.PI - Math.acos(-y/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))));
	}
	
	public Vec getUnit(){ //Returns unit vector.
		return new Vec(x/getMag(), y/getMag());
	}
	
	public static Vec scale(double s, Vec v){
		if(v.fixed)return new Vec(s*v.x, s*v.y, v.posX, v.posY);
		return new Vec(s*v.x, s*v.y);
	}
	
	public static Vec rotate(Vec v, double r){ //Rotates
		if(v.fixed){
			
			double posX = v.posX*Math.cos(-r) + v.posY*Math.sin(-r);
			double posY = -v.posX*Math.sin(-r) + v.posY*Math.cos(-r);
			
			double x = (v.x+v.posX)*Math.cos(-r) + (v.y+v.posY)*Math.sin(-r) - posX;
			double y = -(v.x+v.posX)*Math.sin(-r) + (v.y+v.posY)*Math.cos(-r) - posY;
			
			return new Vec(x, y, posX, posY);
		}
		return new Vec(v.x*Math.cos(-r) + v.y*Math.sin(-r),
			-v.x*Math.sin(-r) + v.y*Math.cos(-r));
	}
	
	public static Point2D.Double scale(Point2D.Double p, Vec v){
		return new Point2D.Double(p.x*v.x, p.x*v.y);
	}
	
	public static Point2D.Double add(Point2D.Double p, Vec v){
		return new Point2D.Double(p.x+v.x, p.x+v.y);
	}
	
	public static Vec add(Vec a, Vec b){
		if(a.fixed && b.fixed)return new Vec(a.x+b.x, a.y+b.y, (a.posX+b.posX)/2, (a.posY+b.posY)/2); //New location is set to average location of vectors.
		if(a.fixed)return new Vec(a.x+b.x, a.y+b.y, a.posX, a.posY);
		if(b.fixed)return new Vec(a.x+b.x, a.y+b.y, b.posX, b.posY);
		return new Vec(a.x+b.x, a.y+b.y);
	}
	
	public static Vec add(ArrayList<Vec> vs){
		Vec r = new Vec(0,0);
		for(Vec v : vs)r = add(r, v);
		return r;
	}
	
	public static double dot(Vec a, Vec b){
		return a.x*b.x + a.y*b.y;
	}
	
	public static double cross(Vec a, Vec b){
		
		double tmp = a.x*b.y-a.y*b.x;
		
		if(tmp>=0)return Math.sqrt(Math.pow(tmp, 2));
		return -Math.sqrt(Math.pow(tmp, 2));
	}
	
	public static Vec position(Point2D.Double p, Vec v){ //Returns fixed vector at position p.
		return new Vec(v.x, v.y, p.x, p.y);
	}

}
