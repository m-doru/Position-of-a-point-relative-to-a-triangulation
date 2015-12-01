package grafica;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

import util.Punct;
import util.Triangulare;
import util.Triunghi;

public class Canvas extends JPanel {
	private static final long serialVersionUID = 1510801660620433157L;
	private static final int BIAS = 20;
	Triangulare triangulare;
	public Canvas(){
		;
	}
	public Canvas(Triangulare triangulare){
		setPreferredSize(new Dimension(700, 700));
		this.triangulare = new Triangulare(triangulare);
	}
	public static Punct translatie(Punct p, double translX, double translY){
		return new Punct(p.x * BIAS+translX, p.y * BIAS+translY);
	}
	public static Punct rotatie(){
		throw new UnsupportedOperationException();
	}
	@Override
	public void paintComponent(Graphics gr){
		Graphics2D g = (Graphics2D)gr;

		g.translate(getWidth()/2, getHeight()/2);
		g.rotate(Math.PI);
//		g.scale(20, 20);
//		g.shear(1, );
		g.setTransform(new AffineTransform(-1, 0, -1, 1, -getWidth()/2, 0));
		super.paintComponent(g);
		for(Triunghi t : triangulare.triunghiuri){
			g.draw(new Line2D.Double(t.a.x * BIAS , t.a.y * BIAS , t.b.x * BIAS , t.b.y * BIAS ));
			g.draw(new Line2D.Double(t.a.x * BIAS , t.a.y * BIAS , t.c.x * BIAS , t.c.y * BIAS ));
			g.draw(new Line2D.Double(t.b.x * BIAS , t.b.y * BIAS , t.c.x * BIAS , t.c.y * BIAS ));
		}
//		g.draw(new Line2D.Double(0, 0, 700, 700));
	}
}
