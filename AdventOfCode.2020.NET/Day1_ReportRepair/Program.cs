using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using System.Diagnostics;

namespace Day1_ReportRepair
{
    class Program
    {
        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");
            
            int[] nrs = Array.ConvertAll(lines, int.Parse);
            List<int> test = nrs.Where(n1 => nrs.Any(n2 => nrs.Any(n3 => n1 + n2 + n3 == 2020))).ToList();
            Console.WriteLine(test[0] + " + " + test[1] + " + " + test[2] + " = " + (test[0] + test[1] + test[2]));
            Console.WriteLine(test[0] + " * " + test[1] + " * " + test[2] + " = " + (test[0] * test[1] * test[2]));

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
