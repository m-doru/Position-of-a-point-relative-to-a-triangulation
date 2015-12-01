package util;

public class Segment implements Comparable<Segment>{
	Punct left, right, helper;
	Segment(){
		this.left = new Punct();
		this.right = new Punct();
		this.helper = new Punct();
	}
	Segment(Punct left, Punct right){
		if(left.compareX(right) < 0){
			this.left = new Punct(left);
			this.right = new Punct(right);
		}
		else{
			this.left = new Punct(right);
			this.right = new Punct(left);
		}
	}
	Segment(Punct left, Punct right, Punct helper){
		if(left.compareX(right) < 0){
			this.left = new Punct(left);
			this.right = new Punct(right);
		}
		else{
			this.left = new Punct(right);
			this.right = new Punct(left);
		}
		this.helper = new Punct(helper);
	}
	@Override
	public int compareTo(Segment segment) {
		return this.left.compareX(segment.left);
	}
	@Override
	public boolean equals(Object o){
		Segment s = (Segment)o;
		if(this.left.equals(s.left) && this.right.equals(s.right))
			return true;
		return false;
	}
	@Override
	public String toString(){
		return "[" + left.toString() + " " + right.toString() + "]";
	}
	public static boolean contains(Segment s,Punct p){
		if(Triunghi.determinant(s.left, s.right, p) != 0)
			return false;
		if(s.left.y - s.right.y <= p.y - s.right.y)
			return true;
		return false;
	}
}
