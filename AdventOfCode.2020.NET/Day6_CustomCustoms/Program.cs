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

            int ansCount1 = 0;
            int ansCount2 = 0;

            List<string> answers = new List<string>();
            foreach (string  line in lines)
            {
                if (line.Equals(""))
                {
                    foreach (char ch in answers.First())
                    {
                        if (answers.All(a => a.Contains(ch))) ansCount2++;
                    }
                    ansCount1 += string.Join("", answers.ToArray()).Distinct().Count();
                    answers = new List<string>();
                }
                else answers.Add(line);
            }

            Console.WriteLine("Part one answers: "+ ansCount1);
            Console.WriteLine("Part two answers: " + ansCount2);
            
            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
