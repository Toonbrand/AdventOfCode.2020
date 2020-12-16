using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day10_AdapterArray
{
    class Day10_AdapterArray
    {
        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Day10_Input.txt");
            int[] adapters = Array.ConvertAll(lines, l => int.Parse(l)).OrderBy(l => l).ToArray();

            Dictionary<int, long> adaptBranches = new Dictionary<int, long> { { 0, 1 } };
            int oneJolt = 0, threeJolt = 0;

            for (int i = 0; i < adapters.Length; i++)
            {
                int curr = adapters[i];
                int jmp = curr - adaptBranches.ElementAt(i).Key;
                if (jmp == 1) oneJolt++;
                if (jmp == 3) threeJolt++;

                long branches = 0;
                foreach (KeyValuePair<int, long> comp in adaptBranches.Skip(count: -3))
                {
                    int diff = curr - comp.Key;
                    if (Enumerable.Range(1, 3).Contains(diff))
                    {
                        branches += comp.Value;
                    }
                }
                adaptBranches.Add(curr, branches);
            }

            Console.WriteLine("[Part 1] " + oneJolt + "*" + threeJolt + " = " + oneJolt * threeJolt);
            Console.WriteLine("[Part 2] Possible paths: " + adaptBranches.Last().Value);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
