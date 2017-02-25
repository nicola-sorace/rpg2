package limbs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import core.Main;
import core.Vec;
import core.Vec3D;

public class LimbBall extends Limb{

	public LimbBall(Vec3D bone, double thickness, Color color, int layer) {
		super(bone, thickness, color, Limb.DNONE, layer);
	}
	
	@Override
	public void Draw(Graphics2D g){
		
		//g.draw(new Line2D.Double(x+b.posX, y-z-b.posZ+b.posY/Main.R, x+b.posX+b.x, y-z-b.posZ-b.z+b.posY/Main.R+b.y/Main.R));
		
		Vec b2d = new Vec(b.x, b.y/Main.R-b.z, x+b.posX, y/Main.R-z-b.posZ+b.posY/Main.R);
		
		g.setColor(color);
		g.fill(new Ellipse2D.Double(b2d.posX-thickness/2, b2d.posY-thickness/2, thickness, thickness));
		g.setColor(Color.BLACK);
		g.draw(new Ellipse2D.Double(b2d.posX-thickness/2, b2d.posY-thickness/2, thickness, thickness));
		
	}
	
}
