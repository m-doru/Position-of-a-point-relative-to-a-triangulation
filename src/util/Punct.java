package util;

public class Punct implements Comparable<Punct>{
	public double x, y;
	Punct(){}
	public Punct(double x, double y){
		this.x = x;
		this.y = y;
	}
	Punct(Punct a){
		this.x = a.x;
		this.y = a.y;
	}
	@Override
	public int compareTo(Punct punct) {
		if(this.y < punct.y)
			return 1;
		if(this.y == punct.y){
			if(this.x < punct.x)
				return -1;
			if(this.x == punct.x)
				return 0;
			return 1;
		}
		return -1;
	}
	@Override
	public boolean equals(Object c){
		Punct _punct = (Punct)c;
		if(this.x == _punct.x && this.y == _punct.y)
			return true;
		return false;
	}
	@Override
	public String toString(){
		return "(" + this.x + ";" + this.y + ")";
	}
	public int compareY(Punct punct){
		if(this.y < punct.y)
			return 1;
		if(this.y == punct.y){
			if(this.x < punct.x)
				return -1;
			if(this.x == punct.x)
				return 0;
			return 1;
		}
		return -1;
	}
	public int compareX(Punct punct){
		if(this.x < punct.x)
			return -1;
		if(this.x == punct.x){
			if(this.y < punct.y)
				return -1;
			if(this.y == punct.y)
				return 0;
			return 1;
		}
		return 1;
	}
	public int compareStrictlyY(Punct punct){
		if(this.y < punct.y)
			return -1;	
		if(this.y > punct.y)
			return 1;
		return 0;
	}
	public int compareStriclyX(Punct punct){
		if(this.x < punct.x)
			return -1;
		if(this.x > punct.x)
			return 1;
		return 0;
	}
	public double distance(Punct punct){
		return Math.sqrt((punct.x - this.x) * (punct.x - this.x) + (punct.y - this.y) * (punct.y - this.y));
	}

	public static Punct transforma(Punct p,double sx, double shy, double tx, double sy, double shx, double ty){
		double newX = sx * p.x + shy * p.y + tx;
		double newY = sy * p.y + shx * p.x + ty;
		return new Punct(newX, newY);
	}
	public static Punct rotateZ(Punct p, double theta){
		return new Punct(p.x*Math.cos(theta) + p.y * Math.sin(theta), -p.x * Math.sin(theta) + p.y * Math.cos(theta));
	}
	public static Punct rotateY(Punct p, double theta){
		return new Punct(p.x, p.y * Math.cos(theta) - Math.sin(theta));
	}
	public void rotateZ(double theta){
		this.x = this.x*Math.cos(theta) + this.y * Math.sin(theta);
		this.y = -this.x * Math.sin(theta) + this.y * Math.cos(theta);
	}
}
