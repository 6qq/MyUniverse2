package mainPack;

public class Vector {
	public double x, y;
	private double size;
	public Vector(double x, double y){
		this.x = x;
		this.y = y;
		this.size = Math.sqrt(x*x + y*y);
	}
	
	public Vector(){
		this.x = 0;
		this.y = 0;
		this.size = 0;
	}
	
	public Vector(Vector v1) {
		x = v1.x;
		y = v1.y;
		size = v1.getSize();
	}
	
	public Vector unit() {
		return this.get(1/this.size);
	}
	
	public Vector negative() {
		return this.get(-1);
	}
	
	public Vector get(double n) {
		return new Vector(this.x*n, this.y*n);
	}
	
	public Vector res(Vector v1) {
		return v1.unit().get(Vector.skaler(this,v1.unit()));
	}
	
	public Vector per(Vector v1) {
		return Vector.ex(this, v1.unit().get(Vector.skaler(this,v1.unit())));
	}
	
	public static Vector sum(Vector v1, Vector v2) {
		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}
	
	public static Vector ex(Vector v1, Vector v2) {
		return new Vector(v1.x - v2.x, v1.y - v2.y);
	}
	
	public static double skaler(Vector v1, Vector v2) {
		return v1.x*v2.x + v1.y*v2.y;
	}
	
	public boolean isSameDirection(Vector v1) {
		if(Vector.skaler(this, v1) >= 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public double getSize() {
		return this.size;
	}
}
