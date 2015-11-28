package util;

import java.util.ArrayList;

public class Poligon {
	ArrayList<Punct> varfuri;
	Triangulare triangulare;
	public Poligon(){//TODO
		
	}
		
	private ArrayList<PoligonMonoton> makeMonoton(){
		throw new UnsupportedOperationException();
	}
	public void makeTriangulare(){
		ArrayList<PoligonMonoton> poligoaneMonotone = makeMonoton();
		for(PoligonMonoton poligonMonoton : poligoaneMonotone){
			poligonMonoton.makeTriangulare();
			triangulare.add(poligonMonoton.triangulare.triunghiuri);
		}
	}
}
