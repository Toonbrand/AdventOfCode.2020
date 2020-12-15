package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

	static String[] nrsArr;
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day15_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		nrsArr = lines[0].split(",");
		
		System.out.println("[Part 1] The 2020th number spoken is " + getNthTurnNr(2020));
		System.out.println("[Part 2] The 30000000th number spoken is " + getNthTurnNr(30000000));
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}
	
	public static int getNthTurnNr(int turns){
		int next = Integer.parseInt(nrsArr[nrsArr.length-1]);
		
		HashMap<Integer, Integer> nrs = new HashMap<Integer, Integer>();
		for (int i=1;i<nrsArr.length;i++) {
			nrs.put(Integer.parseInt(nrsArr[i-1]), i);
		}
		
		for (int turn = nrs.size()+1; turn < turns; turn++) {
			if(nrs.get(next)==null){
				nrs.put(next, turn);
				next = 0;
			}
			else {
				int diff = turn-nrs.get(next);
				nrs.put(next, turn);
				next = diff;
			}
		}		
		return next;
	}
}
