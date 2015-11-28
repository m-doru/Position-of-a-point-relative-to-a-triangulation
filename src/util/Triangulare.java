package util;

import java.util.ArrayList;

public class Triangulare {
	ArrayList<Triunghi> triunghiuri;
	public Triangulare(){triunghiuri = new ArrayList<>();}
	public void add(Triunghi t){
		triunghiuri.add(t);
	}
	public void add(ArrayList<Triunghi> triunghiuri){
		this.triunghiuri.addAll(triunghiuri);
	}
	public void remove(Triunghi t){
		triunghiuri.remove(t);
	}
}
