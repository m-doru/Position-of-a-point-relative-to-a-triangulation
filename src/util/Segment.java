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
	//TODO equals method
}
