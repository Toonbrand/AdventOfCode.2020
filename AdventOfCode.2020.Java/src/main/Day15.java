package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

	static String emptyBin = "000000000000000000000000000000000000";

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day15_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		String[] nrsArr = lines[0].split(",");
		int turns = 30000000;
		
		List<Integer> nrs = new ArrayList<Integer>();
		for (String str : nrsArr) {
			nrs.add(Integer.parseInt(str));
		}
		
		for (int i = nrs.size()-1; i < turns-1; i++) {
			List<Integer> nrsPrev = new ArrayList<Integer>(nrs);
			nrsPrev.remove(nrs.size()-1);
			int curr = nrs.get(i);
			if (!nrsPrev.contains(curr)) {
				nrs.add(0);
			}
			else {
				nrs.add(nrs.lastIndexOf(curr)-nrsPrev.lastIndexOf(curr));
			}
		}
		
		System.out.println("[Part 1] The " +turns+"th number spoken is " + nrs.get(nrs.size()-1));
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}
}
