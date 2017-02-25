package limbs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import core.Main;
import core.Vec;
import core.Vec3D;

public class Limb implements Comparable<Limb>{
	
	public Vec3D bone;
	Vec3D b; //Transformed bone.
	
	double thickness;
	Color color;
	Rotation rot; //Stores the bone's rotation in current pose
	
	boolean dStart = false; //Whether to draw stroke at each limb edge.
	boolean dEnd = false;
	
	double x; //Used for rendering.
	double y;
	double z;
	double r;
	
	int layer = 0; //Force draw above/below others.
	
	ArrayList<Limb> children = new ArrayList<Limb>();
	
	public static final double END = 2d; //Length of extra limb length when drawing.
	
	public static final int DNONE = 0;
	public static final int DSTART = 1;
	public static final int DEND = 2;
	public static final int DBOTH = 3;
	
	public Limb(Vec3D bone, double thickness, Color color, int draw, int layer){
		this.bone = bone;
		this.thickness = thickness;
		this.color = color;
		this.layer = layer;
		
		switch(draw){
			case DNONE:
				break;
			case DSTART:
				dStart = true;
				break;
			case DEND:
				dEnd = true;
				break;
			case DBOTH:
				dStart = true;
				dEnd = true;
		}
		
	}
	
	public void addToRenderQueue(Graphics2D g, ArrayList<Limb> queue, Rotation[] pose, double x, double y, double z, double r){
		this.x=x;
		this.y=y;
		this.z=z;
		this.r=r;
		
		b = Vec3D.rotateZ(b, r); //Apply body's rotation.
		
		queue.add(this);
		
		for(Limb c : children)c.addToRenderQueue(g, queue, pose, x+b.posX+b.x, y+b.posY+b.y, z+b.posZ+b.z, r);
	}
	
	public void Draw(Graphics2D g){
		
		//g.draw(new Line2D.Double(x+b.posX, y-z-b.posZ+b.posY/Main.R, x+b.posX+b.x, y-z-b.posZ-b.z+b.posY/Main.R+b.y/Main.R));
		
		Vec b2d = new Vec(b.x, b.y/Main.R-b.z, x+b.posX, y/Main.R-z-b.posZ+b.posY/Main.R);
		b2d.move(Vec.scale(-END, b2d.getUnit())); //Add extra start length.
		b2d.change(Vec.scale(END*2, b2d.getUnit())); //Add extra end length.
		
		Vec norm = Vec.scale(thickness/2, Vec.rotate(b2d, -Math.PI/2).getUnit());
		
		Path2D.Double shape = new Path2D.Double();
		shape.moveTo(b2d.posX-norm.x, b2d.posY-norm.y); //Move to starting corner.
		
		shape.lineTo(b2d.posX+norm.x, b2d.posY+norm.y); //Start thickness.
		shape.lineTo(b2d.posX+b2d.x+norm.x, b2d.posY+b2d.y+norm.y); //Bone edge 1.
		shape.lineTo(b2d.posX+b2d.x-norm.x, b2d.posY+b2d.y-norm.y); //End thickness.
		//shape.lineTo(b2d.posX-norm.x, b2d.posY-norm.y); //Bone edge 2.
		shape.closePath();
		
		g.setColor(color);
		g.fill(shape);
		g.setColor(Color.BLACK);
		g.draw(new Line2D.Double(b2d.posX+norm.x, b2d.posY+norm.y, b2d.posX+b2d.x+norm.x, b2d.posY+b2d.y+norm.y)); //Draw edge 1.
		g.draw(new Line2D.Double(b2d.posX+b2d.x-norm.x, b2d.posY+b2d.y-norm.y, b2d.posX-norm.x, b2d.posY-norm.y)); //Draw edge 2.
		if(dStart)g.draw(new Line2D.Double(b2d.posX-norm.x, b2d.posY-norm.y, b2d.posX+norm.x, b2d.posY+norm.y)); //Draw start edge?
		if(dEnd)g.draw(new Line2D.Double(b2d.posX+b2d.x+norm.x, b2d.posY+b2d.y+norm.y, b2d.posX+b2d.x-norm.x, b2d.posY+b2d.y-norm.y)); //Draw start edge?
		//g.draw(shape);
	}
	
	public void resetPose(){
		b = new Vec3D(bone);
		for(Limb l : children)l.resetPose();
	}
	
	public void setPose(Rotation r, double x, double y, double z){
		Vec3D tmp = Vec3D.rotate(new Vec3D(0,0,0, 0,0,0), r, x,y,z);
		b = Vec3D.rotate(b, r, x,y,z);
		if(x!=b.posX||y!=b.posY||z!=b.posZ)b = new Vec3D(b.x, b.y, b.z, b.posX-tmp.posX, b.posY-tmp.posY, b.posZ-tmp.posZ); //TODO This shit's messy yo!
		
		for(Limb l : children)l.setPose(r, x-b.x,y-b.y,z-b.z);
	}

	@Override
	public int compareTo(Limb e) {
		if(layer < e.layer)return -1;
		if (layer > e.layer)return 1;
		if((2*(y+b.posY)+b.y+thickness)/2 < (2*(e.y+e.b.posY)+e.b.y+e.thickness)/2)return -1;
		if((2*(y+b.posY)+b.y+thickness)/2 > (2*(e.y+e.b.posY)+e.b.y+e.thickness)/2)return 1;
		return 0;
	}
	
}
