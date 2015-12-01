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
	
}
