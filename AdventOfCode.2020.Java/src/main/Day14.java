package main;

import java.io.File;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day14 {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day14_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		Map<Integer, Long> addrValues = new HashMap<Integer, Long>();
		String emptyBin = "000000000000000000000000000000000000";
		String mask = "";
		
		for (String line : lines) {
			if (line.length() == 43) {
				mask = line.substring(7);
			}
			else {
				int mem = Integer.parseInt(line.substring(line.indexOf("[")+1, line.indexOf("]")));
				long val = Integer.parseInt(line.substring(line.indexOf("=")+2)); 
				String binValStr = emptyBin.concat(Long.toBinaryString(val));
				char[] binValArr = binValStr.substring(binValStr.length()-36).toCharArray();
				
				for (int i = 0; i < binValArr.length; i++) {
					char repl = mask.charAt(i);
					if(repl == 'X'){
						continue;
					}
					else {
						binValArr[i]=repl;
					}
				}

				long newVal = Long.parseLong(new String(binValArr), 2);
				addrValues.put(mem, newVal);
			}
		}
		
		long tot = 0;
		for (Map.Entry<Integer, Long> entry : addrValues.entrySet()) {
			tot += entry.getValue();
		}
		
		System.out.println(tot);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

}
