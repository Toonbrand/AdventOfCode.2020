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

            List<Bag> bags = new List<Bag>();

            foreach (string line in lines)
            {
                string bagColor = line.Substring(0, line.IndexOf(" bag"));
                List<string> rules = line.Substring(line.IndexOf("contain ")+"contain ".Length).Split(", ").ToList();

                Boolean newBag = false;
                Bag bag = bags.Where(b => b.color == bagColor).FirstOrDefault();

                if (bag == null)
                {
                    bag = new Bag();
                    bag.color = bagColor;
                    newBag = true;
                }

                bag.bagRules = new List<Bag>();
                foreach (string rule in rules)
                {
                    if (rule.Equals("no other bags.")) continue;
                    string ruleColor = rule.Substring(2, rule.IndexOf(" bag") - 2);
                    int ruleInt = Int32.Parse(rule.Substring(0, 1));
                    Bag ruleBag = bags.Where(b => b.color == ruleColor).FirstOrDefault();
                    if (ruleBag == null)
                    {
                        ruleBag = new Bag() { color = ruleColor };
                        bags.Add(ruleBag);
                    }
                    bag.bagRules.Add(ruleBag);
                }

                if (newBag)
                {
                    bags.Add(bag);
                }
            }

            bags = bags.OrderBy(b => b.color).ToList();

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }

    public partial class Bag{
        public string color { get; set; }
        public List<Bag> bagRules { get; set; }
    }

}
