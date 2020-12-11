package main;

import java.io.File;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

public class Day11 {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day11_input.txt");
		String[] seats = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		// Create 2D array and surround with X's
		char[][] seatsGrid = new char[seats.length + 2][];
		char[] wallRow = new char[seatsGrid.length];
		Arrays.fill(wallRow, 'X');
		for (int i = 0; i < seats.length; i++) {
			if (i == 0) {
				seatsGrid[i] = wallRow;
			}
			seatsGrid[i + 1] = ("X" + seats[i] + "X").toCharArray();
			if (i == seats.length - 1) {
				seatsGrid[i + 2] = wallRow;
			}
		}

		System.out.println("[part 1] occupied seats: " + predictSeats(seatsGrid, 3, false));
		System.out.println("[part 2] occupied seats: " + predictSeats(seatsGrid, 4, true));

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	static int predictSeats(char[][] seatsGrid, int tolerance, Boolean vision) {
		boolean loop = true;
		while (loop) {
			char[][] newSeats = Arrays.stream(seatsGrid).map(char[]::clone).toArray(char[][]::new);
			loop = false;

			for (int y = 1; y < seatsGrid.length - 1; y++) {
				for (int x = 1; x < seatsGrid[y].length - 1; x++) {
					char c = seatsGrid[y][x];
					if (c == '.' || c == 'X')
						continue;
					int adjOccSeats = vision ? countFovOccSeats(seatsGrid, y, x) : countAdjOccSeats(seatsGrid, y, x);
					if (c == 'L') {
						if (adjOccSeats == 0) {
							newSeats[y][x] = '#';
							loop = true;
						}
					} else if (c == '#') {
						if (adjOccSeats > tolerance) {
							newSeats[y][x] = 'L';
							loop = true;
						}
					}
				}
			}
			seatsGrid = newSeats;
		}

		// Return count occupied seats
		return (int) Arrays.stream(seatsGrid).map(CharBuffer::wrap).flatMapToInt(CharBuffer::chars)
				.filter(i -> i == '#').count();
	}

	static int countFovOccSeats(char[][] seatsGrid, int y, int x) {
		int fovOcc = 0;

		for (int yDir = -1; yDir < 2; yDir++) {
			for (int xDir = -1; xDir < 2; xDir++) {
				for (int step = 1; step < seatsGrid.length; step++) {
					if (yDir == 0 && xDir == 0)
						continue;
					char curr = seatsGrid[y + (yDir * step)][x + (xDir * step)];
					if (curr == '.')
						continue;
					if (curr == 'L' || curr == 'X')
						break;
					if (curr == '#') {
						fovOcc++;
						break;
					}
				}
			}
		}
		return fovOcc;
	}

	static int countAdjOccSeats(char[][] seatsGrid, int y, int x) {
		int surrOcc = 0;
		for (int k = -1; k < 2; k++) {
			for (int l = -1; l < 2; l++) {
				if (k == 0 && l == 0)
					continue;
				if (seatsGrid[y + k][x + l] == '#')
					surrOcc++;
			}
		}
		return surrOcc;
	}
}
