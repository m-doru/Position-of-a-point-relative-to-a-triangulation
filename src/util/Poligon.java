package util;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Poligon {
	private ArrayList<PoligonMonoton> poligoaneMonotone;
	ArrayList<Punct> varfuri;
	Triangulare triangulare;
	public Poligon(){
		this.poligoaneMonotone = null;
		this.varfuri = new ArrayList<>();
		this.triangulare = null;
	}
	
	public Poligon(ArrayList<Punct> puncte){
		this.poligoaneMonotone = null;
		this.varfuri = new ArrayList<>();
		this.varfuri.addAll(puncte);
		this.triangulare = null;
	}
	
	public Poligon(Poligon p){
		this.poligoaneMonotone = null;
		this.varfuri = new ArrayList<>();
		this.varfuri.addAll(p.varfuri);
		this.triangulare = new Triangulare(p.triangulare);
	}
	public String toString(){
		return varfuri.toString();
	}
	private ArrayList<PoligonMonoton> makeMonoton(){
		TreeMap<Double,Segment> T = new TreeMap<>();
		PriorityQueue<Punct> Q = new PriorityQueue<Punct>(varfuri);
		ArrayList<Segment> diagonale = new ArrayList<>();
		Punct _varf;
		while(!Q.isEmpty()){
			_varf = Q.poll();
			switch(this.tipVarf(_varf)){
			case START:{
					//inseram muchia (vi,vi+1) si setam helper la vi
					Segment muchiePrecedenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)+1)%varfuri.size()),_varf);
					T.put(muchiePrecedenta.right.x, muchiePrecedenta);
				}
				break;
			case FINAL:{
					//extragem din T muchia (vi-1; vi)
					Segment muchiePrecedentaCurenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)-1 + varfuri.size())%varfuri.size()));
					Segment muchiePrecedenta = T.get(muchiePrecedentaCurenta.right.x);
					//verificam daca helper e de tip merge si inseram
					if(this.tipVarf(muchiePrecedenta.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchiePrecedenta.helper));
					//scoatem (vi-1, vi) din T
					T.remove(muchiePrecedenta.right.x);
				}
				break;
			case SPLIT:{
					//cautam in T cea mai apropiata muchie din partea stanga a lui vi si care a fost procesata de Linia de baleiere
					Segment muchieOpusa = T.get(T.floorKey(_varf.x));
					
					//inseram diagonala de la vi la helperul muchiei gasite
					diagonale.add(new Segment(_varf, muchieOpusa.helper));
					//setam helperul muchiei gasite la vi
					muchieOpusa.helper = _varf;
					
					//adaugam (vi; vi+1) in T
					Segment muchieCurenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)+1)%varfuri.size()), _varf);
					T.put(muchieCurenta.right.x, muchieCurenta);
				}
				break;
			case MERGE:{
					//extragem din T muchia (vi-1; vi)
					Segment muchiePrecedentaCurenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)-1 + varfuri.size())%varfuri.size()));
					Segment muchiePrecedenta = T.get(muchiePrecedentaCurenta.right.x);
					//daca helperul muchiei e de tip merge, trasam diagnoala intre vi si helper
					if(this.tipVarf(muchiePrecedenta.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchiePrecedenta.helper));
					
					T.remove(muchiePrecedenta.right.x);
					
					//cautam in T cea mai apropiata muchie din partea stanga a lui vi si care a fost procesata de Linia de baleiere
					Segment muchieOpusa = T.get(T.floorKey(_varf.x));
					
					if(this.tipVarf(muchieOpusa.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchieOpusa.helper));
					muchieOpusa.helper = _varf;
				}
				break;
			case REGULAR_LEFT:{
					Segment muchiePrecedentaCurenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)-1 + varfuri.size())%varfuri.size()));
					Segment muchiePrecedenta = T.get(muchiePrecedentaCurenta.right.x);
					if(this.tipVarf(muchiePrecedenta.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchiePrecedenta.helper));
					T.remove(muchiePrecedenta.right.x);
					
					Segment muchieCurenta = new Segment(_varf, varfuri.get((varfuri.indexOf(_varf)+1)%varfuri.size()), _varf);
					T.put(muchieCurenta.right.x, muchieCurenta);
				}
				break;
			case REGULAR_RIGHT:{
					Segment muchieOpusa = T.get(T.floorKey(_varf.x));
					if(this.tipVarf(muchieOpusa.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchieOpusa.helper));
					muchieOpusa.helper = _varf;
				}
				break;
			default:
				break;
			}
		}
		return getMonotonPolygons(diagonale);
		
	}
	private ArrayList<PoligonMonoton> getMonotonPolygons(ArrayList<Segment> diagonale){
		ArrayList<PoligonMonoton> poligoaneMonotone = new ArrayList<>();
		Graph dcel = new Graph(varfuri.size());
		for(int i = 0; i < varfuri.size() - 1; ++i)
			dcel.add(i, i+1);
		dcel.add(varfuri.size() - 1, 0);
		
		for(Segment diagonala : diagonale){
			dcel.add(varfuri.indexOf(diagonala.left), varfuri.indexOf(diagonala.right));
			dcel.add(varfuri.indexOf(diagonala.right), varfuri.indexOf(diagonala.left));
		}
		int notEmptyPosition;
		while((notEmptyPosition = dcel.getNotEmptyPosition()) != -1){
			ArrayList<Integer> vertexIndexes = dcel.parcurgere(notEmptyPosition);
			ArrayList<Punct> puncteMonotone = new ArrayList<>();
			for(int vertexIndex : vertexIndexes)
				puncteMonotone.add(varfuri.get(vertexIndex));
			poligoaneMonotone.add(new PoligonMonoton(puncteMonotone));
		}
		return poligoaneMonotone;
	}
	public void makeTriangulare(){
		if(this.poligoaneMonotone == null)
			this.poligoaneMonotone = makeMonoton();
		
		if(this.triangulare == null){
			
			triangulare = new Triangulare();
			
			for(PoligonMonoton poligonMonoton : this.poligoaneMonotone){
				poligonMonoton.makeTriangulare();
				this.triangulare.add(poligonMonoton.triangulare.triunghiuri);
			}
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
			if(det < 0)
				return VertexConstants.MERGE;
			else
				return VertexConstants.FINAL;		
		}
		else
		if(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()).y < x.y && varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()).y < x.y){
			double det = Triunghi.determinant(varfuri.get((varfuri.indexOf(x) + varfuri.size() - 1) % varfuri.size()), x, varfuri.get((varfuri.indexOf(x) + varfuri.size() + 1) % varfuri.size()));
			if(det < 0)
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
