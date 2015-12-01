package util;

import java.util.ArrayList;

public class Triangulare {
	public ArrayList<Triunghi> triunghiuri;
	public Triangulare(){
		this.triunghiuri = new ArrayList<>();
	}
	public Triangulare(Triangulare triangulare){
		this.triunghiuri = new ArrayList<>();
		this.triunghiuri.addAll(triangulare.triunghiuri);
	}
	public Triangulare(ArrayList<Triunghi> triunghiuri){
		this.triunghiuri = new ArrayList<>();
		this.triunghiuri.addAll(triunghiuri);
	}
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
