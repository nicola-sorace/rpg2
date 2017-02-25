package entities;

import java.awt.Color;
import java.awt.Graphics2D;

import core.Entity;
import core.Game;
import core.Main;
import core.Vec;
import limbs.Body;

public class NPC extends Entity{
	
	public double acc = 1; //Acceleration.
	public double decc = 0.5; //Deceleration (when stopping);
	public double speed = 7; //Maximum velocity
	
	public double jumpForce = 10;
	
	//public boolean moving = false; //If false, NPC tries to remove velocities.
	
	public Body body = new Body();
	
	public NPC(String name, int x, int y, int z, Game game) {
		super(x, y, z, game);
		width = 20;
		depth = 20;
		height = 100;
		this.name = name;
	}
	
	public void draw(Graphics2D g, double x, double y) {
		super.draw(g, x, y);
		g.setColor(Color.BLACK);
		//g.fill(new Rectangle2D.Double(pos.x-width/2, pos.y-height+depth/Main.R*2-z, width, height));
		body.Draw(g, x, y*Main.R, z, r);
		super.drawHUD(g, x, y);
			
	}
	
	//TODO Should really use 'decc' instead of 'acc' when walking against velocity.
	public void walkUp(){
		vel.change(0, -acc);
	}
	public void walkDown(){
		vel.change(0, acc);
	}
	public void walkLeft(){
		vel.change(-acc, 0);
	}
	public void walkRight(){
		vel.change(acc, 0);
	}
	public void jump(){
		if(0==z)zV = jumpForce; //TODO Yoda everywhere.
	}
	
	public void resistMovement(){
		if(vel.getMag()>decc)vel = Vec.add(Vec.scale(-decc, vel.getUnit()), vel);
		else vel.set(0, 0);
	}
	
	public void tick(){
		super.tick();
		//System.out.println(vel.getMag());
		if(vel.getMag()>speed)vel = Vec.scale(speed, vel.getUnit());
	}
	
}
