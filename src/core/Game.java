package core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;

import entities.NPC;

public class Game extends GUI{
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public NPC player;
	public NPC follower;
	
	public double gravity = 0.5;
	
	public double x = 0;
	public double y = 0;

	public Game(int width, int height){
		super(width, height);
		player = new NPC("Bob", 100, 200, 0, this);
		entities.add(player);
		
		//follower = new NPC("Bill", 500, 800, 0, this);
		//entities.add(follower);
	}
	
	public void draw(Graphics2D g){
		super.draw(g);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.GREEN);
		g.fillRect(500, 500, 50, 50);
		
		Collections.sort(entities);
		for(Entity e : entities)e.draw(g, e.pos.x+x, (e.pos.y+y)/Main.R);
		
		if(Main.DEBUG){
			g.setColor(Color.GRAY);
			g.draw(new Line2D.Double(player.pos.x+x, (player.pos.y+y)/Main.R, Input.mouseX, Input.mouseY));
		}
	}
	
	public void tick(){
		
		//follower.vel = Vec.add(follower.vel, Vec.scale(0.001, Vec.add(player.pos, Vec.scale(-1, follower.pos))));
		//follower.r = Vec.add(player.pos, Vec.scale(-1, follower.pos)).getAng();
		
		if(Input.pressed[Input.UP])player.walkUp();
		if(Input.pressed[Input.DOWN])player.walkDown();
		if(Input.pressed[Input.LEFT])player.walkLeft();
		if(Input.pressed[Input.RIGHT])player.walkRight();
		
		if(!Input.pressed[Input.UP]
			&& !Input.pressed[Input.DOWN]
			&& !Input.pressed[Input.LEFT]
			&& !Input.pressed[Input.RIGHT])player.resistMovement();
		
		if(Input.pressed[Input.JUMP])player.jump();
		
		for(Entity e : entities)e.tick();
		
		player.body.anim.next();
		
		player.r = new Vec(Input.mouseX-player.pos.x-x, Input.mouseY-(player.pos.y+y)/Main.R).getAng();
				
		//Follow player:
		//x=-player.pos.x+width/2;
		//y=-player.pos.y+height;
	}
}
