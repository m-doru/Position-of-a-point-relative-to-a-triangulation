package util;

import java.util.ArrayList;
import java.util.Collections;

public class PoligonMonoton extends Poligon {
	
	public PoligonMonoton(){}
	
	public PoligonMonoton(ArrayList<Punct> puncte){
		super(puncte);
	}
	
	@Override
	public void makeTriangulare(){
		if(varfuri.size() <= 2)
			return;
		
		ArrayList<PunctLant> varfuriSortate = new ArrayList<>();
		PunctLant start = new PunctLant(new Punct(Double.MIN_VALUE, Double.MIN_VALUE), VertexConstants.START),
				end = new PunctLant(new Punct(Double.MAX_VALUE, Double.MAX_VALUE), VertexConstants.FINAL);
		for(int i = 0; i < varfuri.size(); i++){
			if(this.tipVarf(varfuri.get(i)) == VertexConstants.REGULAR_LEFT){
				varfuriSortate.add(new PunctLant(varfuri.get(i),VertexConstants.REGULAR_LEFT));
			}
			else
				if(this.tipVarf(varfuri.get(i)) == VertexConstants.REGULAR_RIGHT){
					varfuriSortate.add(new PunctLant(varfuri.get(i),VertexConstants.REGULAR_RIGHT));
				}
				else
					if(this.tipVarf(varfuri.get(i)) == VertexConstants.START){
						if(varfuri.get(i).y > start.y){
							if(start.x <= varfuri.get(i).x){
								start.lant = VertexConstants.REGULAR_LEFT;
							}
							else
								start.lant = VertexConstants.REGULAR_RIGHT;
							start = new PunctLant(varfuri.get(i), VertexConstants.START);
							varfuriSortate.add(start);
						}
						else{
							if(varfuri.get(i).x <= start.x){
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_LEFT));
							}
							else
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_RIGHT));
						}
					}
					else
						if(this.tipVarf(varfuri.get(i)) == VertexConstants.FINAL){
							if(varfuri.get(i).y < end.y){
								if(end.x <= varfuri.get(i).x)
									end.lant = VertexConstants.REGULAR_LEFT;
								else
									end.lant = VertexConstants.REGULAR_RIGHT;
								end = new PunctLant(varfuri.get(i), VertexConstants.FINAL);
								varfuriSortate.add(end);
							}
							else if(varfuri.get(i).y > end.y){
								if(varfuri.get(i).x < end.x)
									varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_LEFT));
								else
									varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_RIGHT));
							}
							else{
								if(end.x < varfuri.get(i).x){
									end.lant = VertexConstants.REGULAR_LEFT;
									end = new PunctLant(varfuri.get(i), VertexConstants.FINAL);
									varfuriSortate.add(end);
								}
								else{
									varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_LEFT));
								}
									
							}
//							varfuriSortate.add(new PunctLant(varfuri.get(i),VertexConstants.FINAL));
						}
						else if(this.tipVarf(varfuri.get(i)) == VertexConstants.MERGE){
							if(varfuri.get((i-1+varfuri.size())%varfuri.size()).y > varfuri.get(i).y)
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_LEFT));
							else
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_RIGHT));
						}
						else if(this.tipVarf(varfuri.get(i)) == VertexConstants.SPLIT){
							if(varfuri.get((i-1+varfuri.size())%varfuri.size()).y >= varfuri.get(i).y)
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_LEFT));
							else
								varfuriSortate.add(new PunctLant(varfuri.get(i), VertexConstants.REGULAR_RIGHT));
						}
		}
		
		Collections.sort(varfuriSortate);
		//System.out.println(varfuriSortate + "\n");
		triangulare = new Triangulare();
		ArrayList<PunctLant> stiva = new ArrayList<>();
		
		stiva.add(varfuriSortate.get(0));
		stiva.add(varfuriSortate.get(1));

		for(int i = 2; i < varfuriSortate.size() - 1; i++){
			if(varfuriSortate.get(i).lant != stiva.get(stiva.size() - 1).lant){
				while(stiva.size() > 1){

					PunctLant top = stiva.remove(stiva.size() - 1);
					triangulare.add(new Triunghi(varfuriSortate.get(i), top, stiva.get(stiva.size() - 1)));
				}
				stiva.remove(stiva.size() - 1);
				stiva.add(varfuriSortate.get(i - 1));
				stiva.add(varfuriSortate.get(i));
			}
			else{
				boolean diagonalaInterior = false;
				PunctLant top = stiva.remove(stiva.size() - 1);
				do{
					diagonalaInterior = false;
					if(Triunghi.determinant(stiva.get(stiva.size()-1), top, varfuriSortate.get(i)) < 0 && varfuriSortate.get(i).lant == VertexConstants.REGULAR_RIGHT){
						triangulare.add(new Triunghi(stiva.get(stiva.size() - 1), varfuriSortate.get(i), top));
						top = stiva.remove(stiva.size() - 1);
						diagonalaInterior = true;
					}
					else
						if(Triunghi.determinant(stiva.get(stiva.size()-1), top, varfuriSortate.get(i)) > 0 && varfuriSortate.get(i).lant == VertexConstants.REGULAR_LEFT){
							triangulare.add(new Triunghi(stiva.get(stiva.size() - 1), varfuriSortate.get(i), top));
							top = stiva.remove(stiva.size() - 1);
							diagonalaInterior = true;
						}

				}while(diagonalaInterior && stiva.size() >= 1);
				stiva.add(top);
				stiva.add(varfuriSortate.get(i));
			}
		}
		
		PunctLant ultimulVarf = varfuriSortate.get(varfuriSortate.size() - 1);
		while(stiva.size() > 1){
			PunctLant top = stiva.remove(stiva.size() - 1);	
			triangulare.add(new Triunghi(stiva.get(stiva.size() - 1), ultimulVarf, top));
		}
	}
}
