using System;
using System.IO;
using System.Linq;

namespace Day1_ReportRepair
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] lines = File.ReadAllLines(@"Input.txt");
            int[] nrs = Array.ConvertAll(lines, int.Parse);

            foreach (int nr in nrs)
            {
                int res = nrs.Where(n => n + nr == 2020).FirstOrDefault();
                if (res != 0)
                {
                    Console.WriteLine(nr + " * " + res + " = " + (nr * res));
                }
            }
        }
    }
}
