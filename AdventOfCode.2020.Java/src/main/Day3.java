package main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class Day3 {

	static List<String> field;
	
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		
		File file = new File("src/main/day3_input.txt");
		field = Files.readAllLines(file.toPath(), Charset.defaultCharset());

		int check1 = countTrees(1, 1);
		int check2 = countTrees(3, 1);
		int check3 = countTrees(5, 1);
		int check4 = countTrees(7, 1);
		int check5 = countTrees(1, 2);
		
		int total = check1*check2*check3*check4*check5;
		
		System.out.println("Total multiplied: "+total);
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: "+(stopTime - startTime)+"ms");
    }
	
	public static int countTrees(int stepX, int stepY) {
		int trees = 0;
		
		for (int yPos = 0, xPos = 0; yPos < field.size(); yPos += stepY) {
			String row = field.get(yPos);
			
			if (row.charAt(xPos)=='#') trees++;
			
			xPos = (xPos+stepX)%row.length();
		}
		
		System.out.println("Right "+stepX+", down "+stepY+" = " + trees + " trees.");
		return trees;
	}
}
