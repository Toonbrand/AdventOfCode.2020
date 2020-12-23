package main;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19_MonsterMessages {

	public static List<String> recMessages;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day19_input.txt");
		String[] lines = Files.readAllLines(file.toPath(), Charset.defaultCharset()).toArray(new String[0]);

		Map<Integer, String> rulesPt1 = new HashMap<Integer, String>();
		int i = 0;
		for (; i < lines.length; i++) {
			String line = lines[i];
			if (line.length() == 0)
				break;
			Integer ruleNr = Integer.parseInt(line.substring(0, line.indexOf(":")));
			String rule = line.substring(line.indexOf(":") + 2, line.length());
			rulesPt1.put(ruleNr, rule);
		}

		recMessages = new ArrayList<String>();
		for (i++; i < lines.length; i++) {
			recMessages.add(lines[i]);
		}

		Map<Integer, String> rulesPt2 = new HashMap<Integer, String>(rulesPt1);
		rulesPt2.put(8, "42 | 42 42 | 42 42 42 | 42 42 42 42 | 42 42 42 42 42");
		rulesPt2.put(11, "42 31 | 42 42 31 31 | 42 42 42 31 31 31 | 42 42 42 42 31 31 31 31");

		System.out.println("[Part 1]: " + countValids(rulesPt1));
		System.out.println("[Part 2]: " + countValids(rulesPt2));

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static int countValids(Map<Integer, String> rules) {
		List<String> in = new ArrayList<String>();
		in.add("");
		List<String> messages = getMessages(rules.get(0).split(" "), in, rules);

		int res = 0;
		for (String message : messages) {
			if (recMessages.contains(message)) {
				res++;
			}
		}
		return res;
	}

	public static List<String> getMessages(String[] numbers, List<String> res, Map<Integer, String> rules) {
		for (String s : numbers) {
			res = removeInvalids(res);

			int i = Integer.parseInt(s);
			String rule = rules.get(i);
			if (rule.contains("a")) {
				for (int j = 0; j < res.size(); j++) {
					res.set(j, res.get(j) + "a");
				}
			} else if (rule.contains("b")) {
				for (int j = 0; j < res.size(); j++) {
					res.set(j, res.get(j) + "b");
				}
			} else if (rule.contains("|")) {
				String[] splt = rule.split("\\|");
				List<String> tmp = new ArrayList<String>();

				for (String string : splt) {
					String[] nmbrs = string.trim().split(" ");
					tmp.addAll(getMessages(nmbrs, new ArrayList<>(res), rules));
				}
				res = tmp;
			} else {
				res = getMessages(rule.split(" "), res, rules);
			}
		}

		return res;
	}

	public static List<String> removeInvalids(List<String> res) {
		for (int k = 0; k < res.size(); k++) {
			String mess = res.get(k);
			if (!recMessages.stream().anyMatch((a) -> a.startsWith(mess))) {
				res.remove(k);
				k--;
			}
		}
		return res;
	}
}
