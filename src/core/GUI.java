package core;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public abstract class GUI {
	public int width;
	public int height;
	
	public GUI(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics2D g){
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHints(rh);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	}
	
	public abstract void tick();

}
