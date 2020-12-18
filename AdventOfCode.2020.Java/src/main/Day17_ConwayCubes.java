package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Day17_ConwayCubes {

	static final int CYCLES = 6;
	static int gridXLenght, gridYLenght, actCount;
	static String[] lines;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day17_input.txt");
		lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		gridXLenght = lines[0].length();
		gridYLenght = lines.length;

		Boolean[][][] cube = createEmptyCube();
		cube = fillInitialCube(cube);

		for (int i = 0; i < CYCLES; i++) {
			cube = runCycle(cube);
		}

		System.out.println("[Part 1] Active cubes: " + actCount);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	// Run rules for every square and return a cube with the new states
	public static Boolean[][][] runCycle(Boolean[][][] oldCube) {
		Boolean[][][] newCube = createEmptyCube();

		actCount = 0;
		for (int z = 1; z < oldCube.length - 1; z++) {
			for (int y = 1; y < oldCube[z].length - 1; y++) {
				for (int x = 1; x < oldCube[z][y].length - 1; x++) {
					int count = countSurrSqrs(oldCube, x, y, z);
					if (oldCube[z][y][x] == true) {
						if (count == 2 || count == 3) {
							newCube[z][y][x] = true;
							actCount++;
						} else {
							newCube[z][y][x] = false;
						}
					} else {
						if (count == 3) {
							newCube[z][y][x] = true;
							actCount++;
						} else {
							newCube[z][y][x] = false;
						}
					}
				}
			}
		}

		return newCube;
	}

	// Count the surrounding active squares for a position
	public static int countSurrSqrs(Boolean[][][] cube, int x, int y, int z) {
		int act = 0;
		for (int relZ = -1; relZ < 2; relZ++) {
			for (int relY = -1; relY < 2; relY++) {
				for (int relX = -1; relX < 2; relX++) {
					if (relX == 0 && relY == 0 && relZ == 0) {
						continue;
					}
					if (cube[z - relZ][y - relY][x - relX] == true) {
						act++;
					}
				}
			}
		}
		return act;
	}

	// Fill the empty cube with the input
	public static Boolean[][][] fillInitialCube(Boolean[][][] zArr) {
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].length(); j++) {
				if (lines[i].charAt(j) == '#') {
					zArr[CYCLES][i + CYCLES][j + CYCLES] = true;
				}
			}
		}
		return zArr;
	}

	// Create empty cube
	public static Boolean[][][] createEmptyCube() {
		Boolean[][][] cube = new Boolean[1 + (CYCLES * 2)][][];
		for (int z = 0; z < cube.length; z++) {
			Boolean[][] yArr = new Boolean[gridYLenght + ((1 + CYCLES) * 2)][];
			for (int y = 0; y < yArr.length; y++) {
				Boolean[] xArr = new Boolean[gridXLenght + ((1 + CYCLES) * 2)];
				for (int x = 0; x < xArr.length; x++) {
					xArr[x] = false;
				}
				yArr[y] = xArr;
			}
			cube[z] = yArr;
		}
		return cube;
	}

	// Just for debugging
	public static void printCube(Boolean[][][] cube) {
		for (int z = 0; z < cube.length; z++) {
			System.out.println("z" + z);
			for (int y = 0; y < cube[z].length; y++) {

				for (int x = 0; x < cube[z][y].length; x++) {
					if (cube[z][y][x] == true) {
						System.out.print(1);
					} else {
						System.out.print(0);
					}
				}
				System.out.println();
			}
		}
	}
}
