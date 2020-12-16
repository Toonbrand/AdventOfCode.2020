using System;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day2_PasswordPhilosophy
{
    class Day02_PasswordPhilosophy
    {
        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Day02_Input.txt");
            char[] delimiterChars = { ' ', '-', ':' };
            int i = 0;

            foreach (string line in lines)
            {
                string[] parts = line.Split(delimiterChars);
                int min = Int32.Parse(parts[0]);
                int max = Int32.Parse(parts[1]);
                char ch = Char.Parse(parts[2]);
                string pass = parts[4];

                if (pass[min - 1] == ch ^ pass[max - 1] == ch)
                {
                    i++;
                }
            }

            Console.WriteLine("Valid passwords: " + i);

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }
    }
}
