using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using System.Collections;

namespace Day16_TicketTranslation
{
    class Program
    {
        static Rule[] rules;
        static int[] yourTicket;
        static int[][] nearbyTickets;

        static void Main(string[] args)
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");
            readInput(lines);

            foreach (int[] ticket in nearbyTickets)
            {
                //rules.Where(r => ticket.Any(t => r.range1.Contains(t) || r.range2.Contains(t)));
                IEnumerable<int> test = ticket.Where(t => rules.Where(r => r.range1.Contains(t) || r.range2.Contains(t)).Any());
            }

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static void readInput(string[] lines)
        {
            int firstWs = Array.FindIndex(lines, l => l == "");
            int lastWs = Array.FindLastIndex(lines, l => l == "");

            rules = new Rule[firstWs];
            for (int i = 0; i < firstWs; i++)
            {
                string line = lines[i];
                string range1 = line.Substring(line.IndexOf(": ") + 2, line.Length - (line.IndexOf(" or ") + 4));
                string range2 = line.Substring(line.IndexOf(" or ") + 4, line.Length - (line.IndexOf(" or ") + 4));
                int range1Min = int.Parse(range1.Split("-")[0]);
                int range1Max = int.Parse(range1.Split("-")[1]);
                int range2Min = int.Parse(range2.Split("-")[0]);
                int range2Max = int.Parse(range2.Split("-")[1]);

                rules[i] = new Rule
                {
                    name = line.Substring(0, line.IndexOf(":")),
                    range1 = Enumerable.Range(range1Min, range1Max-range1Min),
                    range2 = Enumerable.Range(range2Min, range2Max - range2Min)
                };
            }

            yourTicket = lines[firstWs + 2].Split(",").Select(int.Parse).ToArray();

            nearbyTickets = new int[lines.Length - (lastWs + 2)][];
            for (int i = lastWs + 2, j = 0; i < lines.Length; i++, j++)
            {
                nearbyTickets[j] = lines[i].Split(",").Select(int.Parse).ToArray();
            }
        }
    }

    
    class Rule
    {
        public string name { get; set; }
        public IEnumerable<int> range1 { get; set; }
        public IEnumerable<int> range2 { get; set; }
    }
}


