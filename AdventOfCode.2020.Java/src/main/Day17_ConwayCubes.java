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

		Boolean[][][][] hCube = createEmptyHCube();
		hCube = fillInitialHCube(hCube);

		for (int i = 0; i < CYCLES; i++) {
			hCube = runCycle(hCube);
		}

		System.out.println("[Part 1] Active cubes: " + actCount);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	// Run rules for every square and return a cube with the new states

	public static Boolean[][][][] runCycle(Boolean[][][][] oldHCube) {
		Boolean[][][][] newHCube = createEmptyHCube();

		actCount = 0;
		for (int w = 1; w < oldHCube.length - 1; w++) {
			for (int z = 1; z < oldHCube[w].length - 1; z++) {
				for (int y = 1; y < oldHCube[w][z].length - 1; y++) {
					for (int x = 1; x < oldHCube[w][z][y].length - 1; x++) {
						int count = countSurrSqrs(oldHCube, w, z, y, x);
						if (oldHCube[w][z][y][x] == true) {
							if (count == 2 || count == 3) {
								newHCube[w][z][y][x] = true;
								actCount++;
							} else {
								newHCube[w][z][y][x] = false;
							}
						} else {
							if (count == 3) {
								newHCube[w][z][y][x] = true;
								actCount++;
							} else {
								newHCube[w][z][y][x] = false;
							}
						}
					}
				}
			}
		}
		return newHCube;
	}

	// Count the surrounding active squares for a position
	public static int countSurrSqrs(Boolean[][][][] cube, int w, int z, int y, int x) {
		int act = 0;
		for (int relW = -1; relW < 2; relW++) {
			for (int relZ = -1; relZ < 2; relZ++) {
				for (int relY = -1; relY < 2; relY++) {
					for (int relX = -1; relX < 2; relX++) {
						if (relW == 0 && relX == 0 && relY == 0 && relZ == 0) {
							continue;
						}
						if (cube[w - relW][z - relZ][y - relY][x - relX] == true) {
							act++;
						}
					}
				}
			}
		}
		return act;
	}

	// Fill the empty cube with the input
	public static Boolean[][][][] fillInitialHCube(Boolean[][][][] zArr) {
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].length(); j++) {
				if (lines[i].charAt(j) == '#') {
					zArr[CYCLES + 1][CYCLES + 1][i + CYCLES][j + CYCLES] = true;
				}
			}
		}
		return zArr;
	}

	// Create empty cube

	public static Boolean[][][][] createEmptyHCube() {
		Boolean[][][][] hyperCube = new Boolean[3 + (CYCLES * 2)][][][];
		for (int w = 0; w < hyperCube.length; w++) {
			Boolean[][][] cube = new Boolean[3 + (CYCLES * 2)][][];
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
			hyperCube[w] = cube;
		}
		return hyperCube;
	}

	// Just for debugging
	public static void printCube(Boolean[][][][] cube) {
		for (int w = 0; w < cube.length; w++) {
			for (int z = 0; z < cube[w].length; z++) {
				System.out.println("Z=" + z + ", W=" + w);
				for (int y = 0; y < cube[w][z].length; y++) {
					for (int x = 0; x < cube[w][z][y].length; x++) {
						if (cube[w][z][y][x] == true) {
							System.out.print('#');
						} else {
							System.out.print('.');
						}
					}
					System.out.println();
				}
			}
		}
	}
}
