package util;

import java.util.ArrayList;

public class Poligon {
	ArrayList<Punct> varfuri;
	Triangulare triangulare;
	public Poligon(){
		this.varfuri = new ArrayList<>();
		this.triangulare = null;
	}
	
	public Poligon(ArrayList<Punct> puncte){
		this.varfuri = new ArrayList<>();
		this.varfuri.addAll(puncte);
		this.triangulare = null;
	}
	
	public Poligon(Poligon p){
		this.varfuri = new ArrayList<>();
		this.varfuri.addAll(p.varfuri);
		this.triangulare = new Triangulare(p.triangulare);
	}
		
	public void add(Punct p){
		this.varfuri.add(p);
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
	
	VertexConstants tipVarf(Punct x){
		if(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()).compareStrictlyY(x) >= 0 && varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()).compareStrictlyY(x) <= 0)
			return VertexConstants.REGULAR_LEFT;
		else
		if(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()).compareStrictlyY(x) <= 0 && varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()).compareStrictlyY(x) >= 0)
			return VertexConstants.REGULAR_RIGHT;
		else
		if(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()).y > x.y && varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()).y > x.y){
			double det = Triunghi.determinant(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()), x, varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()));
			if(det > 0)
				return VertexConstants.MERGE;
			else
				return VertexConstants.FINAL;		
		}
		else
		if(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()).y < x.y && varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()).y < x.y){
			double det = Triunghi.determinant(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()), x, varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()));
			if(det > 0)
				return VertexConstants.SPLIT;
			else
				return VertexConstants.START;
		}
		else
			return VertexConstants.UNDEFINE;
	}
	
	public int getPosition(Punct p){
		throw new UnsupportedOperationException();
	}
}
