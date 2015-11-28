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
	public int contains(Punct p){
		throw new UnsupportedOperationException();
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
