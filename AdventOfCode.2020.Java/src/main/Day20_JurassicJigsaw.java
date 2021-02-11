package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day20_JurassicJigsaw {

	public static List<Tile> cornerTiles = new ArrayList<Tile>();
	public static List<Tile> sideTiles = new ArrayList<Tile>();
	public static List<Tile> midTiles = new ArrayList<Tile>();

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day20_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		List<Tile> tiles = getTilesList(lines);
		tiles = calcEdges(tiles);

		long cornersMult = 1;
		for (Tile tile : cornerTiles)
			cornersMult *= tile.id;
		System.out.println("[Part 1] Multiplication of all corners: " + cornersMult);

		Tile[][] solvedPuzzle = solvePuzzle(tiles.size());
		
		System.out.println("\n\nSolved puzzle by id:");
		
		for (Tile[] solvedTiles : solvedPuzzle) {
			for (Tile tile : solvedTiles) {
				System.out.print(tile.id + " ");
			}
			System.out.println();
		}
		
		int gridSize = tiles.get(0).grid.length;
		int a = tiles.size();
		int b = gridSize;
		String[] solvedPuzzleArr = new String[(tiles.size()/3)*(gridSize-2)];
		
		int pIndex = 0;
		for (Tile[] row : solvedPuzzle) {
			for(int i = gridSize-2; i >= 1; i--, pIndex++) {
				solvedPuzzleArr[pIndex] = "";
				for (Tile tile : row) {
					solvedPuzzleArr[pIndex] += new String(tile.grid[i]).substring(1, gridSize-1);
				}
			}
		}
		
		System.out.println("\nSolved puzzle image:");
		for (String string : solvedPuzzleArr) {
			System.out.println(string);
		}
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}
	
	public static Tile[][] solvePuzzle(int size) {
		Tile[][] puzzle = new Tile[(int) Math.sqrt(size)][(int) Math.sqrt(size)];

		int length = puzzle.length;
		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				if (x == 0 && y == 0) {
					System.out.print("\nBottom left corner");
					Tile tile = cornerTiles.get(0);
					while (true) {
						if (tile.sideN.inEdge && tile.sideE.inEdge) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
						tile = rotateClockWise(tile);
					}
				} else if (x == 0 && y == length - 1) {
					System.out.print("\nBottom right corner");
					for (Tile tile : cornerTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (tile.sideN.inEdge && tile.sideW.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}

						if (tile.sideW.val.equals(new StringBuilder(puzzle[x][y - 1].sideE.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}

						tile = flipTileHor(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						if (tile.sideW.val.equals(new StringBuilder(puzzle[x][y - 1].sideE.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
				} else if (x == length - 1 && y == 0) {
					System.out.print("\nTop left Corner");
					for (Tile tile : cornerTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (tile.sideE.inEdge && tile.sideS.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}

						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}

						tile = flipTileHor(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}

				} else if (x == length - 1 && y == length - 1) {
					System.out.print("\nTop right Corner");
					for (Tile tile : cornerTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (tile.sideS.inEdge && tile.sideW.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}

						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}

						tile = flipTileHor(tile);
						tile = rotateClockWise(tile);
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
				} else if (x == 0) {
					System.out.print("\nBottom edge");
					for (Tile tile : sideTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (!tile.sideS.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}
						if (tile.sideW.val.equals(new StringBuilder(puzzle[x][y - 1].sideE.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
						tile = flipTileHor(tile);
						if (tile.sideW.val.equals(new StringBuilder(puzzle[x][y - 1].sideE.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
					if (puzzle[x][y] == null) {
						System.out.print("\npanic");
					}
				} else if (y == 0) {
					System.out.print("\nLeft edge");
					for (Tile tile : sideTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (!tile.sideW.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
						tile = flipTileHor(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
				} else if (x == length - 1) {
					System.out.print("\nTop edge");
					for (Tile tile : sideTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (!tile.sideN.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
						tile = flipTileHor(tile);
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
				} else if (y == length - 1) {
					System.out.print("\nRight edge");
					for (Tile tile : sideTiles) {
						if (tile.locked) {
							continue;
						}
						while (true) {
							if (!tile.sideE.inEdge) {
								break;
							}
							tile = rotateClockWise(tile);
						}
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
						tile = flipTileHor(tile);
						tile = rotateClockWise(tile);
						tile = rotateClockWise(tile);
						if (tile.sideS.val.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())) {
							tile.locked = true;
							System.out.print(" [locked]");
							puzzle[x][y] = tile;
							break;
						}
					}
				} else {
					// Middle
					System.out.print("\nMiddle");
					loop: for (Tile tile : midTiles) {
						if (tile.locked) {
							continue;
						}
						for (int i = 0; i < 2; i++) {
							for (int j = 0; j < 4; j++) {
								if (tile.sideS.val
										.equals(new StringBuilder(puzzle[x - 1][y].sideN.val).reverse().toString())
										&& tile.sideW.val.equals(
												new StringBuilder(puzzle[x][y - 1].sideE.val).reverse().toString())) {
									tile.locked = true;
									System.out.print(" [locked]");
									puzzle[x][y] = tile;
									break loop;
								}
								tile = rotateClockWise(tile);
							}
							tile = flipTileHor(tile);
						}
					}
				}
			}
		}
		return puzzle;
	}

	public static List<Tile> calcEdges(List<Tile> tiles) {
		// every tile
		for (Tile tile : tiles) {
			// every side of tile
			List<Side> sides = new ArrayList<Side>();
			sides.add(tile.sideN);
			sides.add(tile.sideE);
			sides.add(tile.sideS);
			sides.add(tile.sideW);
			for (Side side : sides) {
				side:
				// every other tile
				for (Tile compTile : tiles) {
					if (tile.id == compTile.id)
						continue; // don't check the same tile
					// every side of other tile
					List<Side> compSides = new ArrayList<Side>();
					compSides.add(compTile.sideN);
					compSides.add(compTile.sideE);
					compSides.add(compTile.sideS);
					compSides.add(compTile.sideW);
					for (Side compSide : compSides) {
						if (side.val.equals(compSide.val)
								|| new StringBuilder(side.val).reverse().toString().equals(compSide.val)) {
							side.inEdge = true;
							tile.inEdges++;
							break side;
						}
					}
				}
			}
			if (tile.inEdges == 2) {
				cornerTiles.add(tile);
			} else if (tile.inEdges == 3) {
				sideTiles.add(tile);
			} else if (tile.inEdges == 4) {
				midTiles.add(tile);
			}
		}
		return tiles;
	}

	public static List<Tile> getTilesList(String[] lines) {
		List<Tile> tiles = new ArrayList<Tile>();
		Tile tile = new Tile();
		String leftSide = "";
		String rightSide = "";
		for (int i = 0, j = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.length() == 0) {
				tiles.add(fillSides(tile, leftSide, rightSide));
				leftSide = "";
				rightSide = "";
				tile = new Tile();
			} else if (line.startsWith("T")) {
				tile.id = Integer.parseInt(line.substring(line.indexOf(" "), line.length() - 1).trim());
				tile.grid = new char[lines[i + 1].length()][];
				j = 0;
			} else {
				tile.grid[j] = line.toCharArray();
				leftSide += line.charAt(0);
				rightSide += line.charAt(line.length() - 1);
				j++;
			}
		}
		tiles.add(fillSides(tile, leftSide, rightSide));
		return tiles;
	}

	public static Tile fillSides(Tile tile, String leftSide, String rightSide) {
		tile.sideN = new Side();
		tile.sideN.val = String.valueOf(tile.grid[0]);
		tile.sideE = new Side();
		tile.sideE.val = rightSide;
		tile.sideS = new Side();
		tile.sideS.val = new StringBuilder(String.valueOf(tile.grid[tile.grid.length - 1])).reverse().toString();
		tile.sideW = new Side();
		tile.sideW.val = new StringBuilder(leftSide).reverse().toString();
		return tile;
	}

	private static Tile flipTileHor(Tile tile) {
		char[][] retArr = new char[tile.grid.length][tile.grid.length];
		for (int i = 0; i < tile.grid.length; i++) {
			char[] row = tile.grid[i];
			for (int j = 0; j < row.length; j++) {
				char tmp = row[j];
				retArr[i][j] = row[row.length - j - 1];
				retArr[i][row.length - j - 1] = tmp;
			}
		}
		tile.grid = retArr;

		Boolean eEdge = tile.sideE.inEdge;
		tile.sideE.inEdge = tile.sideW.inEdge;
		tile.sideW.inEdge = eEdge;

		String tmp = tile.sideE.val;
		tile.sideE.val = new StringBuilder(tile.sideW.val).reverse().toString();
		tile.sideW.val = new StringBuilder(tmp).reverse().toString();
		tile.sideN.val = new StringBuilder(tile.sideN.val).reverse().toString();
		tile.sideS.val = new StringBuilder(tile.sideS.val).reverse().toString();

		return tile;
	}

	private static Tile rotateClockWise(Tile tile) {
		int size = tile.grid.length;
		char[][] newGrid = new char[size][size];

		for (int i = 0; i < size; ++i)
			for (int j = 0; j < size; ++j)
				newGrid[i][j] = tile.grid[size - j - 1][i];
		tile.grid = newGrid;
		Side tmp = tile.sideW;
		tile.sideW = tile.sideS;
		tile.sideS = tile.sideE;
		tile.sideE = tile.sideN;
		tile.sideN = tmp;

		return tile;
	}
}

class Tile {
	int id;
	char[][] grid;
	int inEdges;
	boolean locked;
	Side sideN;
	Side sideE;
	Side sideS;
	Side sideW;
}

class Side {
	String val;
	boolean inEdge;
}