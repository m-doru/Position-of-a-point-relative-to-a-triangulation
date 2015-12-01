package util;

public class PunctLant extends Punct{
	
	public VertexConstants lant;
	
	PunctLant(){}
	
	PunctLant(Punct punct){
		super(punct);
	}
	
	PunctLant(Punct punct, VertexConstants lant){
		super(punct);
		this.lant = lant;
	}
	
}
