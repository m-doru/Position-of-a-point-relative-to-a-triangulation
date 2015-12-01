package util;

public class PozitiePunct {
	public Punct a;
	public Punct b;
	public Punct c;
	public PunctFataDePoligon pozitie;
	
	PozitiePunct(PunctFataDePoligon pozitie){
		this.a = null;
		this.b = null;
		this.pozitie = pozitie;
	}
	
	PozitiePunct(Punct a, Punct b, PunctFataDePoligon pozitie){
		this.a = a;
		this.b = b;
		this.pozitie = pozitie;
	}
	
	PozitiePunct(Punct a, Punct b, Punct c, PunctFataDePoligon pozitie){
		this.a = a;
		this.b = b;
		this.c = c;
		this.pozitie = pozitie;
	}
}
