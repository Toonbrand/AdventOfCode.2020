package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19_MonsterMessages {

	static Map<Integer, String> rules;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day19_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		rules = new HashMap<Integer, String>();
		int i = 0;
		for (;i < lines.length; i++) {
			String line = lines[i];
			if (line.length()==0)
				break;
			Integer ruleNr = Integer.parseInt(line.substring(0, line.indexOf(":")));
			String rule = line.substring(line.indexOf(":") + 2, line.length());
			rules.put(ruleNr, rule);
		}
				
		List<String> recMessages = new ArrayList<String>();
		for (i++; i < lines.length; i++) {
			recMessages.add(lines[i]);
		}
		
		
		List<String> in = new ArrayList<String>();
		in.add("");
		List<String> messages = getMessages(rules.get(0).split(" "), in);

		int res = 0;
		for (String message : messages) {
			if(recMessages.contains(message)){
				res++;
				recMessages.remove(message);
			}
		}
		
		System.out.println(res);
		
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static List<String> getMessages(String[] numbers, List<String> res) {
		
		for (String s : numbers) {
			int i = Integer.parseInt(s);
			String rule = rules.get(i);
			if (rule.contains("a")) {
				for (int j = 0; j < res.size(); j++) {
					res.set(j, res.get(j)+"a");
				}
			} else if (rule.contains("b")) {
				for (int j = 0; j < res.size(); j++) {
					res.set(j, res.get(j)+"b");
				}
			} else if (rule.contains("|")) {
				String[] splt = rule.split("\\|");
				List<String> tmp = new ArrayList<String>();
				
				for (String string : splt) {
					String[] nmbrs = string.trim().split(" ");
					tmp.addAll(getMessages(nmbrs, new ArrayList<>(res)));
				}
				res = tmp;
			} else {
				res = getMessages(rule.split(" "), res);
			}
		}

		return res;
	}
}
