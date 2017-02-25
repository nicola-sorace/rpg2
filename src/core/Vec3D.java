package core;

import java.util.ArrayList;

import limbs.Rotation;

public class Vec3D {
	
	public double x;
	public double y;
	public double z;
	
	public boolean fixed = false; //True if vector has a position.
	public double posX = 0;
	public double posY = 0;
	public double posZ = 0;
	
	public Vec3D(double x, double y, double z){ //Floating vector.
		set(x, y, z);
	}
	
	public Vec3D(double x, double y, double z, double posX, double posY, double posZ){ //Fixed vector.
		set(x, y, z);
		
		fixed = true;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}
	
	public Vec3D(Vec3D v){
		if(v.fixed){
			set(v.x, v.y, v.z);
			fixed = true;
			posX = v.posX;
			posY = v.posY;
			posZ = v.posZ;
		}
		else set(v.x, v.y, v.z);
	}
	
	public void set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void change(double x, double y, double z){
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public double getMag(){ //Returns magnitude.
		return Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
	}
	
	public double getAng(){ //Returns angle from North.
		if(getMag() == 0)return 0;
		if(x>=0)return Math.acos(-y/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))));
		return Math.PI + Math.acos(-y/(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2))));
	}
	
	public Vec3D getUnit(){ //Returns unit vector.
		return new Vec3D(x/getMag(), y/getMag(), z/getMag());
	}
	
	public static Vec3D scale(double s, Vec3D v){
		if(v.fixed)return new Vec3D(s*v.x, s*v.y, s*v.z, v.posX, v.posY, v.posZ);
		return new Vec3D(s*v.x, s*v.y, s*v.z);
	}
	
	public static Vec3D rotateZ(Vec3D v, double r){ //Rotates around global z-axis.
		if(v.fixed){
			
			double posX = v.posX*Math.cos(-r) + v.posY*Math.sin(-r);
			double posY = -v.posX*Math.sin(-r) + v.posY*Math.cos(-r);
			
			double x = (v.x+v.posX)*Math.cos(-r) + (v.y+v.posY)*Math.sin(-r) - posX;
			double y = -(v.x+v.posX)*Math.sin(-r) + (v.y+v.posY)*Math.cos(-r) - posY;
			
			return new Vec3D(x, y, v.z, posX, posY, v.posZ);
		}
		return new Vec3D(v.x*Math.cos(-r) + v.y*Math.sin(-r),
			-v.x*Math.sin(-r) + v.y*Math.cos(-r), v.z);
	}
	
	public static Vec3D rotateX(Vec3D v, double r){ //Rotates around global x-axis.
		if(v.fixed){
			
			double posY = v.posY*Math.cos(-r) + v.posZ*Math.sin(-r);
			double posZ = -v.posY*Math.sin(-r) + v.posZ*Math.cos(-r);
			
			double y = (v.y+v.posY)*Math.cos(-r) + (v.z+v.posZ)*Math.sin(-r) - posY;
			double z = -(v.y+v.posY)*Math.sin(-r) + (v.z+v.posZ)*Math.cos(-r) - posZ;
			
			return new Vec3D(v.x, y, z, v.posX, posY, posZ);
		}
		return new Vec3D(v.x*Math.cos(-r) + v.y*Math.sin(-r), //TODO THIS IS WRONG
			-v.x*Math.sin(-r) + v.y*Math.cos(-r), v.z);
	}
	
	public static Vec3D rotateY(Vec3D v, double r){ //Rotates around global y-axis.
		if(v.fixed){
			
			double posX = v.posX*Math.cos(-r) + v.posZ*Math.sin(-r);
			double posZ = -v.posX*Math.sin(-r) + v.posZ*Math.cos(-r);
			
			double x = (v.x+v.posX)*Math.cos(-r) + (v.z+v.posZ)*Math.sin(-r) - posX;
			double z = -(v.x+v.posX)*Math.sin(-r) + (v.z+v.posZ)*Math.cos(-r) - posZ;
			
			return new Vec3D(x, v.y, z, posX, v.posY, posZ);
		}
		return new Vec3D(v.x*Math.cos(-r) + v.y*Math.sin(-r), //TODO THIS IS WRONG
			-v.x*Math.sin(-r) + v.y*Math.cos(-r), v.z);
	}
	
	public static Vec3D rotate(Vec3D v, Rotation r, double px, double py, double pz){ //Rotate around point.
		
		Vec3D tmp = new Vec3D(v.x, v.y, v.z, v.posX-px, v.posY-py, v.posZ-pz);
		tmp = Vec3D.rotateX(tmp, r.x);
		tmp = Vec3D.rotateY(tmp, r.y);
		tmp = Vec3D.rotateZ(tmp, r.z);
		return new Vec3D(tmp.x, tmp.y, tmp.z, tmp.posX+px, tmp.posY+py, tmp.posZ+pz);
		
		/*
		double npx = (v.posX-px)*Math.cos(-r.z) + (v.posY-py)*Math.sin(-r.z) + px;
		double npy = -(v.posX-px)*Math.sin(-r.z) + (v.posY-py)*Math.cos(-r.z) + px;
		
		double nx = (v.x+v.posX-px)*Math.cos(-r.z) + (v.y+v.posY-py)*Math.sin(-r.z) + px - v.posX;
		double ny = -(v.x+v.posX-px)*Math.sin(-r.z) + (v.y+v.posY-py)*Math.cos(-r.z) + py - v.posX;
		
		return new Vec3D(nx, ny, v.z, npx, npy, v.posZ);
		*/
		/*
		Vec3D out = new Vec3D(v.x*Math.cos(-r.z) + v.y*Math.sin(-r.z), -v.x*Math.sin(-r.z) + v.y*Math.cos(-r.z), v.z);
		out = new Vec3D(out.x, out.y*Math.cos(-r.x) + out.z*Math.sin(-r.x), -out.y*Math.sin(-r.x) + out.z*Math.cos(-r.x));
		return new Vec3D(out.x, out.y, out.z, v.posX, v.posY, v.posZ);
		*/
	}
	
	/*public static Point2D.Double scale(Point2D.Double p, Vec3D v){
		return new Point2D.Double(p.x*v.x, p.x*v.y);
	}//TODO If necessary.
	
	public static Point2D.Double add(Point2D.Double p, Vec3D v){
		return new Point2D.Double(p.x+v.x, p.x+v.y);
	}//TODO If necessary. */
	
	public static Vec3D add(Vec3D a, Vec3D b){
		if(a.fixed && b.fixed)return new Vec3D(a.x+b.x, a.y+b.y, a.z+b.z, (a.posX+b.posX)/2, (a.posY+b.posY)/2, (a.posZ+b.posZ)/2); //New location is set to average location of vectors.
		if(a.fixed)return new Vec3D(a.x+b.x, a.y+b.y, a.z+b.z, a.posX, a.posY, a.posZ);
		if(b.fixed)return new Vec3D(a.x+b.x, a.y+b.y, a.z+b.z, b.posX, b.posY, b.posZ);
		return new Vec3D(a.x+b.x, a.y+b.y, a.z+b.z);
	}
	
	public static Vec3D add(ArrayList<Vec3D> vs){
		Vec3D r = new Vec3D(0,0,0);
		for(Vec3D v : vs)r = add(r, v);
		return r;
	}
	
	public static double dot(Vec3D a, Vec3D b){
		return a.x*b.x + a.y*b.y + a.z+b.z;
	}
	
	/*public static double cross(Vec3D a, Vec3D b){
		
		double tmp = a.x*b.y-a.y*b.x;
		
		if(tmp>=0)return Math.sqrt(Math.pow(tmp, 2));
		return -Math.sqrt(Math.pow(tmp, 2));
	}//TODO If necessary. */
	
	/*public static Vec3D position(Point3D.Double p, Vec3D v){ //Returns fixed vector at position p.
		return new Vec3D(v.x, v.y, v.z, p.x, p.y, p.z);
	}//TODO If necessary. */

}
