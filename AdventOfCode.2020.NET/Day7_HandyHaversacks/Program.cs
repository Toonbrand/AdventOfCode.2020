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



            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }

    public partial class Bag{
        public string color { get; set; }
        public Dictionary<Bag, int> bagRules { get; set; }
    }

}
