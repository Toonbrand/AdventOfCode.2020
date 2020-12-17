package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Day17_ConwayCubes {

	static Map<Integer, Map<Integer, Map<Integer, Character>>> zMap = new HashMap<Integer, Map<Integer, Map<Integer, Character>>>();

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day17_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		fillZMap(lines);
		
		char[] test = new char[3];
		test[1] = '#';
		for (char c : test) {
			System.out.println(c=='#');
		}

		Map<Integer, Map<Integer, Map<Integer, Character>>> newZMap = new HashMap<Integer, Map<Integer, Map<Integer, Character>>>();
		for (int z = 0; z < zMap.size(); z++) {
			Map<Integer, Map<Integer, Character>> newYMap = new HashMap<Integer, Map<Integer, Character>>();
			for (int y = 0; y < zMap.get(z).size(); y++) {
				Map<Integer, Character> newXMap = new HashMap<Integer, Character>();
				for (int x = 0; x < zMap.get(z).get(y).size(); x++) {
					if (zMap.get(z).get(y).get(x) == '.') {
						System.out.print(".");
					} else {
						System.out.print("#");
					}
				}
				System.out.println();
			}
		}

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static void fillZMap(String[] lines) {
		Map<Integer, Map<Integer, Character>> yMap = new HashMap<Integer, Map<Integer, Character>>();
		for (int i = 0; i < lines.length; i++) {
			Map<Integer, Character> xMap = new HashMap<Integer, Character>();
			for (int j = 0; j < lines[i].toCharArray().length; j++) {
				xMap.put(j, lines[i].toCharArray()[j]);
			}
			yMap.put(i, xMap);
		}
		zMap.put(0, yMap);
	}
}
