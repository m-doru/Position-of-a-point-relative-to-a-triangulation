package util;

public class Punct implements Comparable<Punct>{
	public double x, y;
	Punct(){}
	Punct(double x, double y){
		this.x = x;
		this.y = y;
	}
	Punct(Punct a){
		this.x = a.x;
		this.y = a.y;
	}
	@Override
	public int compareTo(Punct x) {
		return (-1)*compareY(x);
	}
	@Override
	public boolean equals(Object c){
		Punct _punct = (Punct)c;
		if(this.x == _punct.x && this.y == _punct.y)
			return true;
		return false;
	}
	public int compareY(Punct x){
		if(this.y < x.y)
			return -1;
		if(this.y == x.y){
			if(this.x < x.x)
				return -1;
			return 1;
		}
		return 1;
	}
	public int compareX(Punct x){
		if(this.x < x.x)
			return -1;
		if(this.x == x.x){
			if(this.y < x.y)
				return -1;
			return 1;
		}
		return 1;
	}
	public int compareStrictlyY(Punct x){
		if(this.y < x.y)
			return -1;	
		if(this.y > x.y)
			return 1;
		return 0;
	}
	public int compareStriclyX(Punct x){
		if(this.x < x.x)
			return -1;
		if(this.x > x.x)
			return 1;
		return 0;
	}
	public double distance(Punct x){
		return Math.sqrt((x.x - this.x) * (x.x - this.x) + (x.y - this.y) * (x.y - this.y));
	}
	
}
