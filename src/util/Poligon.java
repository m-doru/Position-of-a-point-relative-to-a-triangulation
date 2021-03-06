package util;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Poligon {
	public ArrayList<PoligonMonoton> poligoaneMonotone;
	public ArrayList<Punct> varfuri;
	public Triangulare triangulare;
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
	void rotateZ(double theta){
		for(Punct varf : varfuri)
			varf.rotateZ(theta);
		
	}
	private boolean isMonoton(){
		Punct maxim = new Punct(Double.MIN_VALUE, Double.MIN_VALUE),
				minim = new Punct(Double.MAX_VALUE, Double.MAX_VALUE);
		for(Punct p : varfuri){
			if(maxim.compareStrictlyY(p) < 0)
				maxim = p;
			else if(minim.compareStrictlyY(p) > 0)
				minim = p;
		}
		int maxPoz, minPoz;
		maxPoz = varfuri.indexOf(maxim);
		minPoz = varfuri.indexOf(minim);
		Punct maxPunct = varfuri.get(maxPoz);
		Punct minPunct = varfuri.get(minPoz);
		for(int i = maxPoz; !maxPunct.equals(minPunct); i = (i+1) % varfuri.size()){
			Punct curent = varfuri.get((i+1)%varfuri.size());
			if(maxPunct.y < curent.y)
				return false;
			maxPunct = curent;
		}
		maxPunct = varfuri.get(maxPoz);
		minPunct = varfuri.get(minPoz);
		for(int i = minPoz; !minPunct.equals(maxPunct); i = (i+1)%varfuri.size()){
			Punct curent = varfuri.get((i+1)%varfuri.size());
			if(minPunct.y > curent.y)
				return false;
			minPunct = curent;
		}
		return true;
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
					if(muchiePrecedenta != null){
					if(this.tipVarf(muchiePrecedenta.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchiePrecedenta.helper));
					//scoatem (vi-1, vi) din T
					T.remove(muchiePrecedenta.right.x);
					}
				}
				break;
			case SPLIT:{
					//cautam in T cea mai apropiata muchie din partea stanga a lui vi si care a fost procesata de Linia de baleiere
					Double cheieMuchieOpusa = T.floorKey(_varf.x);
					if(cheieMuchieOpusa == null)
						cheieMuchieOpusa = T.ceilingKey(_varf.x);
					if(cheieMuchieOpusa != null){
					Segment muchieOpusa = T.get(cheieMuchieOpusa);
					if(muchieOpusa.left.x < _varf.x){	
					//inseram diagonala de la vi la helperul muchiei gasite
					diagonale.add(new Segment(_varf, muchieOpusa.helper));
					//setam helperul muchiei gasite la vi
					muchieOpusa.helper = _varf;
					}
					}
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
					if(muchiePrecedenta != null){
					if(this.tipVarf(muchiePrecedenta.helper) == VertexConstants.MERGE)
						diagonale.add(new Segment(_varf, muchiePrecedenta.helper));
					
					T.remove(muchiePrecedenta.right.x);
					}
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
					Double muchieOpusaKey = T.floorKey(_varf.x);
					if(muchieOpusaKey == null)
						muchieOpusaKey = T.ceilingKey(_varf.x);
					Segment muchieOpusa = T.get(muchieOpusaKey);
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
	
	VertexConstants tipVarf(Punct p){
		if(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()).compareStrictlyY(p) > 0 && varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()).compareStrictlyY(p) < 0)
			return VertexConstants.REGULAR_LEFT;
		else
		if(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()).compareStrictlyY(p) < 0 && varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()).compareStrictlyY(p) > 0)
			return VertexConstants.REGULAR_RIGHT;
		else
		if(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()).y >= p.y && varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()).y >= p.y){
			double det = Triunghi.determinant(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()), p, varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()));
			if(det < 0)
				return VertexConstants.MERGE;
			else
				return VertexConstants.FINAL;		
		}
		else
		if(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()).y <= p.y && varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()).y <= p.y){
			double det = Triunghi.determinant(varfuri.get((varfuri.indexOf(p) + varfuri.size() - 1) % varfuri.size()), p, varfuri.get((varfuri.indexOf(p) + varfuri.size() + 1) % varfuri.size()));
			if(det < 0)
				return VertexConstants.SPLIT;
			else
				return VertexConstants.START;
		}
		else
			return VertexConstants.UNDEFINE;
	}
	
	public PozitiePunct getPosition(Punct p){
		
		PozitiePunct pozitiePunct = null;
		
		for(int i = 0; i < poligoaneMonotone.size(); i++){
			for(int j = 0; j < poligoaneMonotone.get(i).triangulare.triunghiuri.size(); j++){
				Triunghi triunghi = poligoaneMonotone.get(i).triangulare.triunghiuri.get(j);
				
				switch(triunghi.contains(p)){
				case LATURA_AB: {
						if(Math.abs(varfuri.indexOf(triunghi.a) - varfuri.indexOf(triunghi.b)) == 1 || Math.abs(varfuri.indexOf(triunghi.a) - varfuri.indexOf(triunghi.c)) == varfuri.size() - 1){
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.b,PunctFataDePoligon.LATURA);
						}
						else
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.b,triunghi.c,PunctFataDePoligon.INTERIOR);
					}
				case LATURA_AC: {
						if(Math.abs(varfuri.indexOf(triunghi.a) - varfuri.indexOf(triunghi.c)) == 1 || Math.abs(varfuri.indexOf(triunghi.a) - varfuri.indexOf(triunghi.c)) == varfuri.size() - 1){
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.c,PunctFataDePoligon.LATURA);
						}
						else
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.b,triunghi.c,PunctFataDePoligon.INTERIOR);
					}
				case LATURA_BC: {
						if(Math.abs(varfuri.indexOf(triunghi.b) - varfuri.indexOf(triunghi.c)) == 1 || Math.abs(varfuri.indexOf(triunghi.a) - varfuri.indexOf(triunghi.c)) == varfuri.size() - 1){
							return pozitiePunct = new PozitiePunct(triunghi.b,triunghi.c,PunctFataDePoligon.LATURA);
						}
						else
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.b,triunghi.c,PunctFataDePoligon.INTERIOR);
					}
				case INTERIOR: {
							return pozitiePunct = new PozitiePunct(triunghi.a,triunghi.b,triunghi.c,PunctFataDePoligon.INTERIOR);
					}
				case EXTERIOR: {
							pozitiePunct = new PozitiePunct(PunctFataDePoligon.EXTERIOR);
					} break;
				}
			}
		}
		return pozitiePunct;
	}
}
