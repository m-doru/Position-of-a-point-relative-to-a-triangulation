package grafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.*;

import util.Punct;
import util.Triangulare;
import util.Triunghi;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1510801660620433157L;
	private static final int SCALAR_FACTOR = 25;
	public Triangulare triangulare;
	ArrayList<Punct> puncte;
	Punct punctInCauza;
	public Canvas(){
		;
	}
	public Canvas(Triangulare triangulare, Punct punct){
		setPreferredSize(new Dimension(700, 700));
		this.punctInCauza = punct;
		this.triangulare = new Triangulare(triangulare);
	}
	public Canvas(ArrayList<Punct> puncte){
		setPreferredSize(new Dimension(700, 700));
		this.triangulare = null;
		this.puncte = puncte;
	}
	private void draw(Graphics2D g){
		if(triangulare != null){
		for(Triunghi t : triangulare.triunghiuri){
			Punct a = Punct.transforma(t.a, SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
			Punct b = Punct.transforma(t.b, SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
			Punct c = Punct.transforma(t.c, SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
			a = Punct.rotateY(a, Math.PI);
			b = Punct.rotateY(b, Math.PI);
			c = Punct.rotateY(c, Math.PI);
			g.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
			g.draw(new Line2D.Double(a.x, a.y, c.x, c.y));
			g.draw(new Line2D.Double(b.x, b.y, c.x, c.y));
			}
		
		 punctInCauza = Punct.transforma(punctInCauza, SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
		 punctInCauza = Punct.rotateY(punctInCauza, Math.PI);
		 g.setColor(Color.BLUE);
		 //g.draw(new Line2D.Double(punctInCauza.x, punctInCauza.y, punctInCauza.x + 0.5, punctInCauza.y + 0.5));
		 g.draw(new Rectangle2D.Double(punctInCauza.x - 1.5, punctInCauza.y - 1.5,3,3));
		}
		else{
			for(int i = 0; i < puncte.size(); ++i){
				Punct a = Punct.transforma(puncte.get(i), SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
				a = Punct.rotateY(a, Math.PI);
				Punct b = Punct.transforma(puncte.get((i+1) % puncte.size()), SCALAR_FACTOR, 0, 0, SCALAR_FACTOR, 0, 0);
				b = Punct.rotateY(b, Math.PI);
				g.draw(new Line2D.Double(a.x, a.y, b.x, b.y));
			}
		}
	}
	@Override
	public void paintComponent(Graphics gr){
		Graphics2D g = (Graphics2D)gr;

		g.translate(getWidth()/4, 3*getHeight()/4);
		super.paintComponent(g);
		draw(g);
	}
}
