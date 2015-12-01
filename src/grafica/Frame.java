package grafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicArrowButton;
import util.*;
public class Frame extends JFrame{
	ArrayList<Punct> points;
	Punct punctInCauza;
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
		canvas = new Canvas(poligon.triangulare);
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
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								Frame.this.drawTriangulare();	
							}
						});
						//aici sa facem cautarea punctului
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
