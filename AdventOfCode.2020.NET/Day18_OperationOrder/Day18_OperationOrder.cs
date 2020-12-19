using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Collections.Generic;
using System.Data;

namespace Day18_OperationOrder
{
    class Day18_OperationOrder
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Day18_Input.txt");

            long tot = 0;
            foreach (string line in lines)
            {
                List<char> exp = line.ToCharArray().ToList();

                while (exp.Any(e => e == '('))
                {
                    int openPar = -1;
                    int closePar = -1;
                    for (int i = 0; openPar == -1 || closePar == -1; i++)
                    {
                        if (exp[i] == '(')
                        {
                            openPar = i;
                        }
                        else if (exp[i] == ')')
                        {
                            closePar = i;
                        }
                    }

                    string calc = new string(exp.GetRange(openPar + 1, closePar - 1 - openPar).ToArray());
                    long res = calcLeftRight(calc);

                    exp.RemoveRange(openPar, calc.Length + 2);
                    exp.InsertRange(openPar, res.ToString().ToList());
                }

                tot += calcLeftRight(new string(exp.ToArray()));
            }

            Console.WriteLine(tot);
            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        public static long calcLeftRight(string input)
        {
            DataTable dt = new DataTable();
            long tot = Int32.Parse(input.Substring(0, input.IndexOf(' ')));
            for (int i = 0; i < input.Length; i++)
            {
                if (input[i] == '+' || input[i] == '*')
                {
                    int next = input.IndexOf(' ', i + 2);
                    if (next > 0)
                    {
                        tot = (long)Convert.ToDouble((dt.Compute(tot + (input[i..next]+".0"), " ").ToString()));
                    }
                    else
                    {
                        tot = (long)Convert.ToDouble((dt.Compute(tot + input[i..] + ".0", " ")).ToString());
                    }
                }
            }

            return tot;
        }
    }
}
