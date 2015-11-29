package util;

import java.util.ArrayList;
import java.util.Collections;

public class Triunghi {
	Punct a,b,c;
	public Triunghi(){
		this.a = new Punct();
		this.b = new Punct();
		this.c = new Punct();
	}
	public Triunghi(Punct a, Punct b, Punct c){
		this.a = new Punct(a);
		this.b = new Punct(b);
		this.c = new Punct(c);
	}
	public Triunghi(Triunghi t){
		this.a = new Punct(t.a);
		this.b = new Punct(t.b);
		this.c = new Punct(t.c);
	}
	public static double determinant(Punct A, Punct B, Punct C){
		return (A.x-C.x)*(B.y-C.y) - (B.x - C.x)*(A.y - C.y);
	}
	public static double getArie(Punct A, Punct B, Punct C){
		double rez = determinant(A, B, C);
	    return 1/2*(Math.abs(rez));
	}
	private static boolean equals(double _a, double _b){
		if(Math.abs(_b-_a) > 0.00000000000001)
			return false;
		return true;
	}
	public int contains(Punct p){
		double _total, _arie1, _arie2, _arie3;
		_total = getArie(this.a, this.b, this.c);
		_arie1 = getArie(this.a, this.b, p);
		_arie2 = getArie(this.a, this.c, p);
		_arie3 = getArie(this.b, this.c, p);
		if(equals(_total, _arie1+_arie2+_arie3)){
			
		}
		return -1;
	}
	@Override
	public boolean equals(Object t){
		Triunghi _triunghi = (Triunghi) t;
		ArrayList<Punct> _puncteThis = new ArrayList<>(),
				_puncteObject = new ArrayList<>();
		_puncteThis.add(a);
		_puncteThis.add(b);
		_puncteThis.add(c);
		Collections.sort(_puncteThis);
		_puncteObject.add(_triunghi.a);
		_puncteObject.add(_triunghi.b);
		_puncteObject.add(_triunghi.c);
		Collections.sort(_puncteObject);
		if(_puncteThis.containsAll(_puncteObject) && _puncteObject.containsAll(_puncteThis))
			return true;
		return false;
	}
}
