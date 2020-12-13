using System;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day12_ShuttleSearch
{
    class Program
    {
        static void Main(string[] args)
        {

            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");
            string[] busses = lines[1].Split(',').Where(s => s != "x").ToArray();

            int earliestTime = Int32.Parse(lines[0]);

            int[] busIds = Array.ConvertAll(busses, s=> int.Parse(s));

            int idealId = int.MaxValue;
            int idealTime = int.MaxValue;

            foreach (int busId in busIds)
            {
                int puTime = earliestTime-(earliestTime % busId)+busId;
                if (puTime<idealTime)
                {
                    idealTime = puTime;
                    idealId = busId;
                }
            }

            Console.WriteLine((idealTime-earliestTime) *idealId);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
