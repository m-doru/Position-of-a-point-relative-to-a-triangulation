package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import grafica.Frame;
import grafica.SwingConsole;
import util.Punct;

public class Test {

	public static void main(String[] args) {
		File inputFile = new File("F:\\GitHub\\src\\util\\default.in");
		Scanner input;
		try {
			input = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		ArrayList<Punct> inputArray = new ArrayList<>();
		while(input.hasNextDouble()){
			inputArray.add(new Punct(input.nextDouble(), input.nextDouble()));
		}
		
		input.close();
		Frame frame = new Frame();
		frame.setPoints(inputArray);
		SwingConsole.run(frame, 800, 900);
	}

}
