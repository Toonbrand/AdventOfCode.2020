package main;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9 {
	static int acc;

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		File file = new File("src/main/day9_input.txt");
		long[] nrs = Arrays.stream(new String(Files.readAllBytes(file.toPath())).split("\r\n"))
				.mapToLong(Long::parseLong).toArray();

		long invalidNr = findInvalidNr(nrs, 25);
		long encWeakness = getEncryptionWeakness(invalidNr, nrs);

		System.out.println("[part 1] invalid number: " + invalidNr);
		System.out.println("[part 2] encryption weakness: " + encWeakness);

		long stopTime = System.currentTimeMillis();
		System.out.println("\nExecution time: " + (stopTime - startTime) + "ms");
	}

	public static Long findInvalidNr(long[] nrs, int preamble) {
		for (int i = preamble; i < nrs.length; i++) {
			if (!checkIfValid(nrs, i, preamble))
				return nrs[i];
		}
		return null;
	}

	public static Boolean checkIfValid(long[] nrs, int index, int preamble) {
		long sum = nrs[index];
		for (int i = index - 1; i >= index - preamble; i--) {
			for (int j = index - 1; j >= index - preamble; j--) {
				if (nrs[i] + nrs[j] == sum) {
					return true;
				}
			}
		}
		return false;
	}

	public static Long getEncryptionWeakness(Long invalidNumber, long[] nrs) {
		for (int i = 0; i < nrs.length; i++) {
			if (nrs[i] >= invalidNumber)
				continue;
			List<Long> range = new ArrayList<Long>();
			for (int j = i; j < nrs.length; j++) {
				range.add(nrs[j]);
				long sum = range.stream().mapToLong(a -> a).sum();
				if (sum > invalidNumber)
					break;
				if (sum == invalidNumber) {
					return Collections.min(range) + Collections.max(range);
				}
			}
		}
		return null;
	}
}
