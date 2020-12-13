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

            //string[] idBusses = lines[1].Split(',').Where(s => s != "x").ToArray();
            //Console.WriteLine("Part 1: " + part1(idBusses, Int32.Parse(lines[0])));

            int[] allBusses = lines[1].Replace('x', '1').Split(',').ToList().ConvertAll(b => int.Parse(b)).ToArray();

            Boolean loop = true;
            int highestBus = allBusses.Max();
            int highestBusIndex = Array.IndexOf(allBusses, highestBus);

            for (int i = 1; loop; i++)
            {
                long t = highestBus * i;
                Boolean success = true;

                for (int j = 0; j < allBusses.Length; j++)
                {
                    if(j == highestBusIndex)
                    {
                        continue;
                    }
                    int bus = allBusses[j];

                    long goal = t + (j - highestBusIndex);
                    long over = (goal % bus);

                    if (over == 0)
                    {
                        continue;
                    }
                    else
                    {
                        success = false;
                        break;
                    }

                }

                if (success)
                {
                    loop = false;
                    Console.WriteLine(t-highestBusIndex);
                }
            }

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static int part1(string[] busses, int earliestTime)
        {
            int[] busIds = Array.ConvertAll(busses, s => int.Parse(s));

            int idealId = int.MaxValue;
            int idealTime = int.MaxValue;

            foreach (int busId in busIds)
            {
                int puTime = earliestTime - (earliestTime % busId) + busId;
                if (puTime < idealTime)
                {
                    idealTime = puTime;
                    idealId = busId;
                }
            }

            return ((idealTime - earliestTime) * idealId);
        }
    }
}
