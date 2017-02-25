package limbs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;

import core.Vec3D;

public class Body {
	public ArrayList<Limb> limbs = new ArrayList<Limb>();
	public double x;
	public double y;
	
	ArrayList<Limb> renderQueue;
	
	//Integers referring to each limb:
	static final int PELVIS = 0;
	static final int CHEST = 1;
	static final int HEAD = 2;
	
	static final int ARMR0 = 3; //Upper arm
	static final int ARMR1 = 4; //Lower arm
	
	static final int ARML0 = 5;
	static final int ARML1 = 6;
	
	static final int LEGR0 = 7; //Upper arm
	static final int LEGR1 = 8; //Lower arm
	
	static final int LEGL0 = 9;
	static final int LEGL1 = 10;
	
	static final int NREF = 11; //Number of reference limbs. Keep updated.
	
	Limb[] ref = new Limb[NREF]; //Used to refer to a specific limb.
	
	Rotation[] pose = new Rotation[NREF];
	public Animation anim = new Animation(); // declared as new Rotation[length][NREF];
	
	public Body(){
		ref[PELVIS] = new Limb(new Vec3D(0,0,10, 0,0,20), 15, Color.ORANGE, Limb.DSTART, 0);
		limbs.add(ref[PELVIS]);
		
		ref[CHEST] = new Limb(new Vec3D(0,0,20, 0,0,0), 15, Color.ORANGE, Limb.DEND, 0);
		ref[PELVIS].children.add(ref[CHEST]);
		
		ref[HEAD] = new LimbBall(new Vec3D(0,0,0, 0,-1,7), 25, Color.PINK, 0);
		ref[HEAD].children.add(new LimbBall(new Vec3D(8,-35,2, 4,-10,2), 5, Color.WHITE, 0));
		ref[HEAD].children.add(new LimbBall(new Vec3D(-8,-35,2, -4,-10,2), 5, Color.WHITE, 0));
		ref[CHEST].children.add(ref[HEAD]);
		
		ref[ARML0] = new Limb(new Vec3D(10,0,-5, 5,0,-4), 5, Color.ORANGE, Limb.DEND, 0);
		ref[ARML1] = new Limb(new Vec3D(10,-2,-5, 0,0,0), 5, Color.PINK, Limb.DBOTH, 0);
		ref[ARML0].children.add(ref[ARML1]);
		ref[CHEST].children.add(ref[ARML0]);
		
		ref[ARMR0] = new Limb(new Vec3D(-10,0,-5, -5,0,-4), 5, Color.ORANGE, Limb.DEND, 0);
		ref[ARMR1] = new Limb(new Vec3D(-10,-2,-5, 0,0,0), 5, Color.PINK, Limb.DBOTH, 0);
		ref[ARMR0].children.add(ref[ARMR1]);
		ref[CHEST].children.add(ref[ARMR0]);
		
		
		ref[LEGL0] = new Limb(new Vec3D(0,0,-10, 5,0,-10), 5, Color.GRAY, Limb.DSTART, 0);
		ref[LEGL1] = new Limb(new Vec3D(0,0,-10, 0,0,0), 5, Color.GRAY, Limb.DEND, 0);
		ref[LEGL0].children.add(ref[LEGL1]);
		ref[PELVIS].children.add(ref[LEGL0]);
		
		ref[LEGR0] = new Limb(new Vec3D(0,0,-10, -5,0,-10), 5, Color.GRAY, Limb.DSTART, 0);
		ref[LEGR1] = new Limb(new Vec3D(0,0,-10, 0,0,0), 5, Color.GRAY, Limb.DEND, 0);
		ref[LEGR0].children.add(ref[LEGR1]);
		ref[PELVIS].children.add(ref[LEGR0]);
		
		for(int i=0; i<NREF; i++)pose[i] = new Rotation(0,0,0);
	}
	
	public void Draw(Graphics2D g, double x, double y, double z, double r){

		renderQueue = new ArrayList<Limb>();
		
		pose = anim.getPose();
		
		//TODO: Rotation must use local space, not parent.
		
		for(Limb l : limbs)l.resetPose();
		//for(int i=0; i<NREF; i++)ref[i].b = Vec3D.rotate(ref[i].b, pose[i], ref[i].b.posX, ref[i].b.posY, ref[i].b.posZ); //Apply pose.
		for(int i=0; i<NREF; i++)ref[i].setPose(pose[i], ref[i].b.posX, ref[i].b.posY, ref[i].b.posZ);
		
		for(Limb l : limbs)l.addToRenderQueue(g, renderQueue, pose, x, y, z, r);
		Collections.sort(renderQueue);
		for(Limb l : renderQueue)l.Draw(g);
		
	}
}
