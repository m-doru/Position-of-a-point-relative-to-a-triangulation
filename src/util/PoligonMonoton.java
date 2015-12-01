package util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;

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
						varfuriSortate.add(new PunctLant(varfuri.get(i),VertexConstants.START));
					}
					else
						if(this.tipVarf(varfuri.get(i)) == VertexConstants.FINAL){
							varfuriSortate.add(new PunctLant(varfuri.get(i),VertexConstants.FINAL));
						}
		}
		
		Collections.sort(varfuriSortate);
		//System.out.println(varfuriSortate + "\n");
		triangulare = new Triangulare();
		Deque<PunctLant> stiva = new ArrayDeque<>();
		
		stiva.push(varfuriSortate.get(0));
		stiva.push(varfuriSortate.get(1));

		for(int i = 2; i < varfuriSortate.size() - 1; i++){
			if(varfuriSortate.get(i).lant != stiva.peek().lant){
				while(stiva.size() > 1){
					PunctLant top = stiva.pop();
					triangulare.add(new Triunghi(varfuriSortate.get(i), top, stiva.peek()));
					System.out.println(top + " " + varfuriSortate.get(i));
				}
				stiva.pop();
				stiva.add(varfuriSortate.get(i - 1));
				stiva.add(varfuriSortate.get(i));
			}
			else{
				boolean diagonalaInterior = false;
				PunctLant top;
				do{
					diagonalaInterior = false;
				    top = stiva.pop();
					if(Triunghi.determinant(stiva.peek(), top, varfuriSortate.get(i)) < 0 && varfuriSortate.get(i).lant == VertexConstants.REGULAR_RIGHT){
						triangulare.add(new Triunghi(stiva.peek(), varfuriSortate.get(i), top));
						System.out.println(top + " " + varfuriSortate.get(i));
						diagonalaInterior = true;
					}
					else
						if(Triunghi.determinant(stiva.peek(), top, varfuriSortate.get(i)) > 0 && varfuriSortate.get(i).lant == VertexConstants.REGULAR_LEFT){
							triangulare.add(new Triunghi(stiva.peek(), varfuriSortate.get(i), top));
							System.out.println(top + " " + varfuriSortate.get(i));
							diagonalaInterior = true;
						}

				}while(diagonalaInterior && stiva.size() > 1);
				
				stiva.push(top);
				stiva.push(varfuriSortate.get(i));
			}
		}
		
		PunctLant ultimulVarf = varfuriSortate.get(varfuriSortate.size() - 1);
		PunctLant top = stiva.pop();
		triangulare.add(new Triunghi(stiva.pop(), ultimulVarf, top));
		System.out.println(top + " " + ultimulVarf);
		
	}
}
