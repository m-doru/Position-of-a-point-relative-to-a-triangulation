package grafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import util.*;

public class Frame extends JFrame{
	private static final long serialVersionUID = 1L;
	ArrayList<Punct> points;
	Punct punctInCauza, punct;
	ReadPanel readPanel = new ReadPanel();
	JButton inputDoneButton = new JButton();
	Poligon poligon;
	Canvas canvas;
	boolean pressed = false;
	public void drawPoligon(){
		poligon = new Poligon(points);
		poligon.makeTriangulare();
		canvas = new Canvas(poligon.varfuri);
		this.add(BorderLayout.SOUTH,canvas);
	}
	public void drawTriangulare(){
		if(poligon == null){
			poligon = new Poligon(points);
			poligon.makeTriangulare();
		}
		this.remove(canvas);
		this.repaint();
		canvas = new Canvas(poligon.triangulare,punct);
		this.add(BorderLayout.SOUTH, canvas);
	}
	public Frame(){
		this.add(BorderLayout.NORTH, readPanel);
		this.setLayout(new FlowLayout());
		readPanel.add(inputDoneButton);
		inputDoneButton.setText("Trianguleaza");
		inputDoneButton.setToolTipText("Apasa doar dupa ce ai introdus varfurile poligonului");
		inputDoneButton.addActionListener(new ActionListener(){
			int pressed = 0;

			private void firstTime(){
				//TODO remove comment
//				points = readPanel.getPoints();
				readPanel.changeInputActionListenerToReadLastPoint();
				Frame.this.drawPoligon();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pressed == 2){
					readPanel.givePointsInputEvent(new ActionEvent("", 0, ""));
					return;
				}
				if(pressed == 1){
						readPanel.givePointsInputEvent(new ActionEvent("", 0, ""));
						punct = readPanel.getPunctInCauza();
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								Frame.this.drawTriangulare();	
							}
						});
						//aici sa facem cautarea punctului
						
						PozitiePunct pp = poligon.getPosition(punct);
						
						if(pp.pozitie == PunctFataDePoligon.LATURA)
							System.out.println("Punctul este pe latura: " + pp.a + " " + pp.b);
						else
							if(pp.pozitie == PunctFataDePoligon.INTERIOR){
								
								System.out.println("Punctul se afla in triunghiul: " + pp.a + " " + pp.b + " " + pp.c);
							}
							else
								System.out.println(pp.pozitie);
						
						pressed = 2;
						return;
				}
				pressed = 1;
				firstTime();
			}
			
		});
	}
	public void setPoints(ArrayList<Punct> points){
		this.points = new ArrayList<>(points);
	}
}
