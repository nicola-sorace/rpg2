package limbs;

public class Rotation {
	public double x = 0; //Rotation around local x-axis.
	public double y = 0; //Rotation around local y-axis.
	public double z = 0; //Rotation around local z-axis.
	
	//TODO Local bone-axis?
	
	public Rotation(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
