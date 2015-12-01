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
import javax.swing.plaf.basic.BasicArrowButton;
import util.*;
public class Frame extends JFrame{
	ArrayList<Punct> points;
	Punct punctInCauza;
	ReadPanel readPanel = new ReadPanel();
	JButton inputDoneButton = new JButton();
	boolean pressed = false;
	public void draw(){
		Poligon poligon = new Poligon(points);
		poligon.makeTriangulare();
		Canvas canvas = new Canvas(poligon.triangulare);
		this.add(BorderLayout.SOUTH,canvas);
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
				points = readPanel.getPoints();
				readPanel.changeInputActionListenerToReadLastPoint();
				Frame.this.draw();
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pressed == 2){
					readPanel.givePointsInputEvent(new ActionEvent("", 0, ""));
					return;
				}
				if(pressed == 1){
						readPanel.givePointsInputEvent(new ActionEvent("", 0, ""));
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
