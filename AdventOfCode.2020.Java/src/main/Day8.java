package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day8 {
	static int acc;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day8_input.txt");
		String[] linesArr = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		tryRun(linesArr);
		System.out.println("[part 1] acc when ran until stuck in loop: " + acc);

		for (int i = 0; i < linesArr.length; i++, acc = 0) {
			String line = linesArr[i];

			if ((linesArr[i] = line.replace("nop", "jmp")) != line) {
				if (tryRun(linesArr))
					break;
			}
			if ((linesArr[i] = line.replace("jmp", "nop")) != line) {
				if (tryRun(linesArr))
					break;
			}
			linesArr[i] = line;
		}

		System.out.println("[part 2] acc when ran until end: " + acc);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static Boolean tryRun(String[] linesArr) throws Exception {
		int lineNr = 0;
		List<Integer> passedLines = new ArrayList<Integer>();

		while (lineNr < linesArr.length) {
			if (passedLines.contains(lineNr))
				return false;

			String line = linesArr[lineNr];
			passedLines.add(lineNr);
			String lineComm = line.substring(0, 3);

			int lineInt = Integer.parseInt(line.substring(4, line.length()));

			switch (lineComm) {
			case "jmp":
				lineNr += lineInt;
				break;
			case "acc":
				acc += lineInt;
				lineNr++;
				break;
			case "nop":
				lineNr++;
				break;
			}
		}

		return true;
	}
}
