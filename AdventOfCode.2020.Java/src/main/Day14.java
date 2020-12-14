package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {

	static String emptyBin = "000000000000000000000000000000000000";
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day14_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);
		
		Map<Long, Long> addrValues = new HashMap<Long, Long>();
		
		String mask = "";
		for (String line : lines) {
			if (line.length() == 43) {
				mask = line.substring(7);
			}
			else {
				int mem = Integer.parseInt(line.substring(line.indexOf("[")+1, line.indexOf("]")));
				long val = Integer.parseInt(line.substring(line.indexOf("=")+2)); 
				String binValStr = emptyBin.concat(Long.toBinaryString(mem));
				char[] binValArr = binValStr.substring(binValStr.length()-36).toCharArray();
				
				int floaters = 0;
				for (int i = 0; i < binValArr.length; i++) {
					char repl = mask.charAt(i);
					if(repl == '0'){
						continue;
					}
					else {
						binValArr[i]=repl;
						if(repl == 'X'){
							floaters++;
						}
					}
				}
				
				List<String> keys = new ArrayList<String>();
				for (int i=0; i<Math.pow(2, floaters); i++) {
					int num = Integer.parseInt(Integer.toBinaryString(i));
					keys.add(String.format("%0"+floaters+"d", num));
				}
				
				List<String> binStrLst = new ArrayList<String>();
				for (int i = 0; i < keys.size(); i++) {
					char[] newBin = Arrays.copyOf(binValArr, binValArr.length);
					char[] key = keys.get(i).toCharArray();

					for (int j=0, k=0;j<binValArr.length;j++) {
						if (binValArr[j] == 'X') {
							newBin[j] = key[k];
							k++;
						}
					}
					
					String newBinStr = new String(newBin);
					binStrLst.add(newBinStr);
				}

				for (String string : binStrLst) {
					long floatVal = Long.parseLong(string, 2);
					addrValues.put(floatVal, val);
				}
			}
		}
		
		long tot = 0;
		for (Map.Entry<Long, Long> entry : addrValues.entrySet()) {
			tot += entry.getValue();
		}
		
		System.out.println(tot);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}
}
