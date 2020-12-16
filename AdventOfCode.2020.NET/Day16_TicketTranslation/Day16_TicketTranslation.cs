using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Collections.Generic;

namespace Day16_TicketTranslation
{
    class Day16_TicketTranslation
    {
        static Rule[] rules;
        static int[] myTicket;
        static int[][] nearbyTickets;

        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Day16_Input.txt");
            readInput(lines);

            //Fill list with valid tickets
            List<int[]> validTickets = new List<int[]>();
            int invalids = 0;
            foreach (int[] tickets in nearbyTickets)
            {
                IEnumerable<int> invalid = tickets.Where(t => !rules.Where(r => r.range1.Contains(t) || r.range2.Contains(t)).Any());
                invalids += invalid.FirstOrDefault();
                bool any = invalid.Any();
                if (!any)
                {
                    validTickets.Add(tickets);
                }
            }
            validTickets.Add(myTicket);

            //Calculate possible positions for each rule based on valid tickets
            for (int i = 0; i < rules.Length; i++)
            {
                Rule rule = rules[i];

                for (int j = 0; j < myTicket.Length; j++)
                {
                    bool correct = true;
                    for (int k = 0; k < validTickets.Count(); k++)
                    {
                        int toCheck = validTickets[k][j];

                        if (rule.range1.Contains(toCheck) || rule.range2.Contains(toCheck))
                        {
                            continue;
                        }
                        else
                        {
                            correct = false;
                            break;
                        }
                    }
                    if (correct)
                    {
                        rule.posPos.Add(j);
                    }
                }
            }

            //Keep filtering out rules with only one possibility and remove that possiblity from the rest
            while (rules.Where(r => r.pos == null).Any())
            {
                Rule r = rules.Where(r => r.posPos.Count() == 1 && r.pos == null).FirstOrDefault();
                int i = r.posPos.FirstOrDefault();
                r.pos = i;
                foreach (Rule rule in rules.Where(r => r.pos == null))
                {
                    rule.posPos.RemoveAll(r => r == i);
                }
            }

            //Mulitply all fields that start with "departure"
            long answ = 1;
            foreach (Rule rule in rules.Where(r => r.name.StartsWith("departure")))
            {
                int i = (int)rule.pos;
                answ *= myTicket[i];
            }

            //Print results
            Console.WriteLine("[part 1] Sum of invalid fields: " + invalids);
            Console.WriteLine("[part 2] Multiplication of your departures: " + answ);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static void readInput(string[] lines)
        {
            int firstWs = Array.FindIndex(lines, l => l == "");
            int lastWs = Array.FindLastIndex(lines, l => l == "");

            //Create and fill rules array
            rules = new Rule[firstWs];
            for (int i = 0; i < firstWs; i++)
            {
                string line = lines[i];
                string range1 = line.Substring(line.IndexOf(": ") + 2, line.Length - (line.IndexOf(" or ") + 4));
                string range2 = line[(line.IndexOf(" or ") + 4)..];
                int range1Min = int.Parse(range1.Split("-")[0]);
                int range1Max = int.Parse(range1.Split("-")[1]) + 1;
                int range2Min = int.Parse(range2.Split("-")[0]);
                int range2Max = int.Parse(range2.Split("-")[1]) + 1;

                rules[i] = new Rule
                {
                    name = line.Substring(0, line.IndexOf(":")),
                    range1 = Enumerable.Range(range1Min, range1Max - range1Min),
                    range2 = Enumerable.Range(range2Min, range2Max - range2Min),
                    posPos = new List<int>(),
                    pos = null
                };
            }

            //Create and fill my ticket
            myTicket = lines[firstWs + 2].Split(",").Select(int.Parse).ToArray();

            //Create and fill nearby tickets array
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
        public List<int> posPos { get; set; }
        public int? pos { get; set; }
    }
}