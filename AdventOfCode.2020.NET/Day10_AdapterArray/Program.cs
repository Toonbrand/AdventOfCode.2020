using System;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day10_AdapterArray
{
    class Program
    {
        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");
            int[] adapters = Array.ConvertAll(lines, l => int.Parse(l)).OrderBy(l=>l).ToArray();

            int jltDiff1 = 1, jltDiff2 = 0, jltDiff3 = 1;
            for (int i = 0; i < adapters.Length-1; i++)
            {
                int jmp = adapters[i + 1] - adapters[i];
                switch (jmp)
                {
                    case 1:
                        jltDiff1++;
                        break;
                    case 2:
                        jltDiff2++;
                        break;
                    case 3:
                        jltDiff3++;
                        break;
                }
            }

            Console.WriteLine("Jolt difference 1: " + jltDiff1);
            Console.WriteLine("Jolt difference 2: " + jltDiff2);
            Console.WriteLine("Jolt difference 3: " + jltDiff3);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
