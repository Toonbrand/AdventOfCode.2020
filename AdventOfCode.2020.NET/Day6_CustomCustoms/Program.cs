using System;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using System.Diagnostics;

namespace Day1_ReportRepair
{
    class Program
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");

            string answers = "";
            int answerCount =0;
            foreach (string line in lines)
            {
                if (line.Equals(""))
                {
                    answerCount += answers.Distinct().Count();
                    answers = "";
                }
                else
                {
                    answers += line;
                }
            }


            Console.WriteLine("Answers: "+answerCount);
            
            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
