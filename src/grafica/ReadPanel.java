package grafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import util.*;
import com.sun.xml.internal.ws.api.Component;

class ReadPanel extends JPanel{
	private static final long serialVersionUID = -1711538106692825836L;
	private JTextField pointsInput = new JTextField(25);
	private JLabel label = new JLabel("Introduceti punctele poligonului(press Enter)");
	private ArrayList<Punct> points = new ArrayList<>();
	Punct lastPoint = null;
	ReadPanel(){
		add(label);
		add(pointsInput);
		pointsInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Scanner input = new Scanner(pointsInput.getText());
				double[] coord = new double[2];
				for(int i = 0; i < 2; ++i){
					if(input.hasNextDouble())
						coord[i] = input.nextDouble();
					else{
						pointsInput.setText("Fail.");
						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								try {
									TimeUnit.MILLISECONDS.sleep(300);
								} catch (InterruptedException e) {
									
								}
								pointsInput.setText("Format(comma for float): xcoord ycoord");
							}
						});
						input.close();
						return;
					}
				}
				input.close();
				pointsInput.setText("");
				points.add(new Punct(coord[0],coord[1]));
				
			}
		});
	}
	public void setInputActionListener(ActionListener l){
		if(pointsInput.getActionListeners().length > 0)
			for(int i = 0; i < pointsInput.getActionListeners().length; ++i)
			pointsInput.removeActionListener(pointsInput.getActionListeners()[i]);
		pointsInput.addActionListener(l);
	}
	public void changeInputActionListenerToReadLastPoint(){
		if(pointsInput.getActionListeners().length > 0)
			for(int i = pointsInput.getActionListeners().length - 1; i  >= 0 ; --i)
				pointsInput.removeActionListener(pointsInput.getActionListeners()[i]);
		label.setText("Introduceti punctul de referinta");

		pointsInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Scanner input = new Scanner(pointsInput.getText());
				double[] coord = new double[2];
				for(int i = 0; i < 2; ++i){
					if(input.hasNextDouble())
						coord[i] = input.nextDouble();
					else{
						pointsInput.setText("Fail.");
						SwingUtilities.invokeLater(new Runnable(){
							public void run(){
								try {
									TimeUnit.MILLISECONDS.sleep(300);
								} catch (InterruptedException e) {
									
								}
								pointsInput.setText("Format(comma for float): xcoord ycoord");
							}
						});
						input.close();
						return;
					}
				}
				input.close();
				pointsInput.setText("Citire terminata");
				lastPoint = new Punct(coord[0], coord[1]);
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						changeInputActionListenerToFinishedRead();
					}
				});
			}
		});
	}
	public void changeInputActionListenerToFinishedRead(){
		if(pointsInput.getActionListeners().length > 0)
			for(int i = pointsInput.getActionListeners().length - 1; i  >= 0 ; --i)
				pointsInput.removeActionListener(pointsInput.getActionListeners()[i]);
		this.label.setText("Citire terminata");
		pointsInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pointsInput.setText("Citire deja terminata");
			}
		});
		
	}
	public Punct getPunctInCauza(){
		return lastPoint;
	}
	public void add(Component comp){
		this.add(comp);
	}
	public ArrayList<Punct> getPoints(){
		return points;
	}
	public void givePointsInputEvent(ActionEvent e){
		pointsInput.getActionListeners()[0].actionPerformed(e);
	}
}








