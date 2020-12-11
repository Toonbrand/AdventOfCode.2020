package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Day11_visual {

	static char[][] seatsGrid;
	static char[][] drawGrid;
	static int freeSeats;
	static int occupiedSeats;
	static int part = 1;

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

		File file = new File("src/main/day11_input.txt");
		String[] seats = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		// Create 2D array and surround with X's
		seatsGrid = new char[seats.length + 2][];
		char[] wallRow = new char[seats[0].length() + 2];
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

		drawGrid = seatsGrid;
		char[][] freshSeats = Arrays.stream(seatsGrid).map(char[]::clone).toArray(char[][]::new);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (true) {
			part = 1;
			seatsGrid = freshSeats;
			System.out.println("[part 1] occupied seats: " + predictSeats(3, false));
			seatsGrid = freshSeats;
			part = 2;
			System.out.println("[part 2] occupied seats: " + predictSeats(4, true));
		}
	}

	private static void createAndShowGUI() {
		JFrame f = new JFrame("Day 11");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new MyPanel());
		f.setBackground(Color.BLACK);
		f.pack();
		f.setVisible(true);
	}

	static int predictSeats(int tolerance, Boolean vision) {
		boolean loop = true;
		boolean draw = true;

		
		
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

			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			draw= !draw;
			if(draw) {
				drawGrid=newSeats;
			}
			seatsGrid = newSeats;
			freeSeats = (int) Arrays.stream(seatsGrid).map(CharBuffer::wrap).flatMapToInt(CharBuffer::chars)
					.filter(i -> i == 'L').count();
			occupiedSeats = (int) Arrays.stream(seatsGrid).map(CharBuffer::wrap).flatMapToInt(CharBuffer::chars)
					.filter(i -> i == '#').count();
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

@SuppressWarnings("serial")
class MyPanel extends JPanel {

	Color wallCol = new Color(43, 43, 43);
	Color occCol= new Color(43, 140, 130);
	Color openCol  = new Color(216, 202, 4);
	
	public MyPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.BLACK);
	}

	public Dimension getPreferredSize() {
		return new Dimension(Day11_visual.seatsGrid[0].length * 10, (Day11_visual.seatsGrid.length * 10) + 25);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

			char[][] seatsGrid = Day11_visual.drawGrid;
			for (int y = 0; y < seatsGrid.length; y++) {
				for (int x = 0; x < seatsGrid[y].length; x++) {
					char c = seatsGrid[y][x];
					if (c == '.' || c == 'X') {
						g.setColor(wallCol);
					}
					if (c == '#') {
						g.setColor(occCol);
					}
					if (c == 'L') {
						g.setColor(openCol);
					}
					g.fillRect(10 * x, 10 * y, 10, 10);
				}
			}

			g.setFont(new Font("default", Font.BOLD, 16));
			g.setColor(Color.WHITE);
			g.drawString("Part " + Day11_visual.part, 10, (seatsGrid.length * 10) + 20);
			g.setColor(openCol);
			g.drawString("Free seats: " + Day11_visual.freeSeats, (Day11_visual.seatsGrid[0].length * 10 / 2) - 200,
					(seatsGrid.length * 10) + 20);
			g.setColor(occCol);
			g.drawString("Occupied seats: " + Day11_visual.occupiedSeats,
					(Day11_visual.seatsGrid[0].length * 10 / 2) + 100, (seatsGrid.length * 10) + 20);

		repaint();
	}
}
