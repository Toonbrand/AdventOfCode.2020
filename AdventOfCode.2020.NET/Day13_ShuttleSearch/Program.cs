using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;

namespace Day12_ShuttleSearch
{
    class Program
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");

            Console.WriteLine("Part 1: " + part1(lines));
            Console.WriteLine("Part 2: " + part2(lines));

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static long part2(string[] busses)
        {
            int[] allBusses = busses[1].Replace('x', '1').Split(',').ToList().ConvertAll(b => int.Parse(b)).ToArray();

            long t = 0;
            long inc = allBusses[0];

            for (int i = 1; i < allBusses.Length; i++)
            {
                int nextBus = allBusses[i];
                if (nextBus == 1)
                    continue;

                long t2 = nextBus;
                while (true)
                {
                    t += inc;
                    if ((t + i) % t2 == 0)
                    {
                        inc *= t2;
                        break;
                    }
                }
            }

            return t;
        }

        static int part1(string[] lines)
        {
            int earliestTime = Int32.Parse(lines[0]);
            int[] busIds = Array.ConvertAll(lines[1].Split(',').Where(s => s != "x").ToArray(), s => int.Parse(s));

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
