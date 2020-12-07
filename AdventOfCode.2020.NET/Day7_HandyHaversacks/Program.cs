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

            List<Bag> bags = new List<Bag>();
            foreach (string line in lines)
            {
                string bagColor = line.Substring(0, line.IndexOf(" bag"));
                List<string> rules = line[(line.IndexOf("contain ") + "contain ".Length)..].Split(", ").ToList();

                Boolean newBag = false;
                Bag bag = bags.Where(b => b.color == bagColor).FirstOrDefault();

                if (bag == null)
                {
                    bag = new Bag{color = bagColor};
                    newBag = true;
                }

                bag.bagChildren = new List<Bag>();
                foreach (string rule in rules)
                {
                    if (rule.Equals("no other bags.")) continue;
                    string ruleColor = rule[2..rule.IndexOf(" bag")];
                    Bag ruleBag = bags.Where(b => b.color == ruleColor).FirstOrDefault();

                    if (ruleBag == null)
                    {
                        ruleBag = new Bag() { color = ruleColor };
                        bags.Add(ruleBag);
                    }

                    for (int i = 0; i < Int32.Parse(rule.Substring(0, 1)); i++)
                    {
                        bag.bagChildren.Add(ruleBag);
                    }
                }

                if (newBag) bags.Add(bag);
            }

            Bag mybag = bags.Where(b => b.color == "shiny gold").FirstOrDefault();
            int part2Answ = totalBagCount(mybag.bagChildren);

            int part1Answ = 0;
            foreach (Bag bag in bags) part1Answ += goldenBagCount(bag.bagChildren);

            Console.WriteLine("Part 1: " + part1Answ);
            Console.WriteLine("Part 2: " + part2Answ);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        public static int totalBagCount(IEnumerable<Bag> bags)
        {
            int ret = bags.Count();
            foreach (Bag bag in bags)
            {
                ret += totalBagCount(bag.bagChildren);
            }
            return ret;
        }

        public static int goldenBagCount(IEnumerable<Bag> bags)
        {
            bags = bags.Distinct();
            int res = bags.Where(b => b.color == "shiny gold").Count();

            foreach (Bag bag in bags)
            {
                if (res > 0) return res;
                res += goldenBagCount(bag.bagChildren);
            }
            return res;
        }
    }

    public partial class Bag{
        public string color { get; set; }
        public List<Bag> bagChildren { get; set; }
    }
}
