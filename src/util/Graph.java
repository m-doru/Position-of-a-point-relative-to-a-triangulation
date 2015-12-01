package util;

import java.util.ArrayList;

public class Graph {
	ArrayList<ArrayList<Integer>> vecini;
	int numarNoduri;
	Graph(int numarNoduri){
		vecini = new ArrayList<>();
		this.numarNoduri = numarNoduri;
		for(int i = 0; i < numarNoduri; i++)
			vecini.add(new ArrayList<Integer>());
	}
	
	public void add(int nod, int vecin){
		if(nod >= numarNoduri)
			throw new IndexOutOfBoundsException();
		if(vecini.get(nod).size() == 0){
			vecini.get(nod).add(vecin);
			return;
		}
		
		int i;
		for(i = vecini.get(nod).size() - 1; i >= 0; i--){
			if(vecin > nod && vecin > vecini.get(nod).get(i) && vecini.get(nod).get(i) > nod)
				break;
			if(vecin < nod && vecin < vecini.get(nod).get(i))
				break;
		}
		
		i++;
		vecini.get(nod).add(i, vecin);
	}
	
	public int getNotEmptyPosition(){
		int i;
		for(i = 0; i < vecini.size(); i++)
			if(vecini.get(i).size() > 0)
				break;
		
		if(i == vecini.size())
			return -1;
		
		return i;
	}
	
	public ArrayList<Integer> parcurgere(int start){
		ArrayList<Integer> indexi = new ArrayList<>();
		int curent, curentVechi;
		indexi.add(start);
		curent = vecini.get(start).get(0);
		vecini.get(start).remove(0);
		while(curent != start){
			indexi.add(curent);
			for(int i = vecini.get(curent).size() - 1; i >= 0; i--){
				if(vecini.get(curent).get(i) <= start || vecini.get(curent).get(i) > curent){
					curentVechi = curent;
					curent = vecini.get(curent).get(i);
					vecini.get(curentVechi).remove(i);
					break;
				}
			}
		}
		
		return indexi;
	}
}
