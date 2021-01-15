package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day20_JurassicJigsaw {

	public static long cornersMult = 1;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day20_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		List<Tile> tiles = getTilesList(lines);
		tiles = calcEdges(tiles);

		System.out.println("[Part 1] Multiplication of all corners: " + cornersMult);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static List<Tile> calcEdges(List<Tile> tiles) {
		// every tile
		for (Tile tile : tiles) {
			// every side of tile
			for (Side side : tile.sides) {
				side:
				// every other tile
				for (Tile compTile : tiles) {
					if (tile.id == compTile.id)
						continue; // don't check the same tile
					// every side of other tile
					for (Side compSide : compTile.sides) {
						if (side.val.equals(compSide.val)
								|| new StringBuilder(side.val).reverse().toString().equals(compSide.val)) {
							side.inEdge = true;
							tile.inEdges++;
							break side;
						}
					}
				}
			}
			// Count corner pieces for [part 1]
			if (tile.inEdges == 2) {
				cornersMult *= tile.id;
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
		tile.sides[0] = new Side();
		tile.sides[0].val = String.valueOf(tile.grid[0]);
		tile.sides[1] = new Side();
		tile.sides[1].val = rightSide;
		tile.sides[2] = new Side();
		tile.sides[2].val = String.valueOf(tile.grid[tile.grid.length - 1]);
		tile.sides[3] = new Side();
		tile.sides[3].val = leftSide;
		return tile;
	}
}

class Tile {
	int id;
	char[][] grid;
	Side[] sides = new Side[4];
	int inEdges;
}

class Side {
	String val;
	boolean inEdge;
}