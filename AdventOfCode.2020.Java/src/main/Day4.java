package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {
	static List<String> reqFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
	static List<String> eyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day4_input.txt");
		String linesStr = new String(Files.readAllBytes(file.toPath())) + "\n\r";
		String[] linesArr = linesStr.replace("\n", " ").split(" ");
		List<Map<String, String>> passMap = createPassMap(linesArr);

		int validPass = 0;
		for (Map<String, String> map : passMap) {
			if (checkFields(map) && validateFields(map))
				validPass++;
		}

		System.out.println("Out of " + passMap.size() + " passports, " + validPass + " were valid. ");
		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static Boolean checkFields(Map<String, String> map) {
		for (String field : reqFields) {
			if (!map.containsKey(field)) {
				return false;
			}
		}
		return true;
	}

	public static Boolean validateFields(Map<String, String> map) {
		int byr = Integer.parseInt(map.get("byr").toString());
		int iyr = Integer.parseInt(map.get("iyr").toString());
		int eyr = Integer.parseInt(map.get("eyr").toString());
		String hgt = map.get("hgt").toString();
		String hcl = map.get("hcl").toString();
		String ecl = map.get("ecl").toString();
		String pid = map.get("pid").toString();

		if (byr < 1920 || byr > 2002)
			return false;
		if (iyr < 2010 || iyr > 2020)
			return false;
		if (eyr < 2020 || eyr > 2030)
			return false;
		if (!hcl.substring(0, 1).equals("#") || !hcl.substring(1, hcl.length()).matches("[a-f0-9]*"))
			return false;
		if (!eyeColors.contains(ecl))
			return false;
		if (pid.length() != 9)
			return false;

		if (hgt.contains("cm")) {
			int hgtCm = Integer.parseInt(hgt.substring(0, hgt.indexOf("cm")));
			if (hgtCm < 150 || hgtCm > 193)
				return false;
		} else if (hgt.contains("in")) {
			int hgtIn = Integer.parseInt(hgt.substring(0, hgt.indexOf("in")));
			if (hgtIn < 59 || hgtIn > 76)
				return false;
		} else
			return false;

		return true;
	}

	public static List<Map<String, String>> createPassMap(String[] lines) {
		List<Map<String, String>> passportMap = new ArrayList<Map<String, String>>();
		Map<String, String> passMap = new HashMap<String, String>();
		for (String pair : lines) {
			if (!pair.equals("\r")) {
				String[] keyValue = pair.split(":");
				passMap.put(keyValue[0].replace("\r", ""), keyValue[1].replace("\r", ""));
			} else {
				passportMap.add(passMap);
				passMap = new HashMap<String, String>();
			}
		}
		return passportMap;
	}
}
