package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public abstract class Entity implements Comparable<Entity>{
	
	private static final double S = 20; //Base shadow overflow (i.e. shadow expansion at z=0).

	Game game;
	
	public String name = "UNKNOWN";
	public boolean fixed = false;
	
	public int width = 50;
	public int height = 50;
	public int depth = 50;
	
	//Coordinates and velocities:
	public Vec pos = new Vec(0, 0);
	public Vec vel = new Vec(0, 0);
	
	public double z = 0;
	public double zV = 0;
	
	public double r = 0;
	//public double rV = 0;
	
	public double drag = 0.01; //Deceleration.
	
	public void draw(Graphics2D g, double x, double y){
		drawShadow(g, x, y);
	}
	
	public Entity(int x, int y, int z, Game game){
		pos.set(x, y);
		this.z = z;
		this.game = game;
	}
	
	public void tick(){
		if(!fixed){
			pos = Vec.add(pos, vel);
			z += zV;
			//r += rV;
			
			if(vel.getMag()>=drag)vel = Vec.add(vel, Vec.scale(-drag, vel.getUnit()));
			else vel.set(0, 0);
			
			if(z>=game.gravity)zV -= game.gravity;
			else{
				zV = 0;
				z = 0;
			}
			
		}
	}
	
	public void drawShadow(Graphics2D g, double x, double y){
		g.setColor(new Color(0, 0, 0, (int)Math.round(Math.max( (0.1-z/1200)*255 , 0 ))));
		double overflow = S+z/4;
		g.fill(new Ellipse2D.Double(x-width/2-overflow/2, y-depth/(Main.R*2)-overflow/2, width+overflow, depth/(Main.R)+overflow));
	}
	
	public void drawHUD(Graphics2D g, double x, double y){ //Draws all HUD elements relating to entity.
		if(Main.DEBUG){
			g.setColor(Color.BLUE);
			
			//Bounds:
			//g.draw(new Rectangle2D.Double(pos.x-width/2, pos.y-height-z, width, height)); //2D bounds.
			g.draw(new Rectangle2D.Double(x-width/2, y-height-depth/(Main.R*2)-z, width, height));
			g.draw(new Rectangle2D.Double(x-width/2, y-height+depth/(Main.R*2)-z, width, height));
			
			//Velocity vector:
			g.draw(new Line2D.Double(x, y, x+vel.x*20, y+vel.y*(20/Main.R))); 
		}
	}
	
	@Override
	public int compareTo(Entity e) {
		if(pos.y < e.pos.y)return -1;
		if (pos.y > e.pos.y)return 1;

		return 0;
	}
	
}
