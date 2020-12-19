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

            Console.WriteLine("[Part 1] sum: " + calcTotal(lines, true));
            Console.WriteLine("[Part 2] sum: " + calcTotal(lines, false));

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        public static long calcTotal(string[] lines, bool part1)
        {
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

                    long res = part1 ? calcLeftRight(calc) : calcByPrecedence(calc);

                    exp.RemoveRange(openPar, calc.Length + 2);
                    exp.InsertRange(openPar, res.ToString().ToList());
                }

                string input = new string(exp.ToArray());
                tot += part1 ? calcLeftRight(input) : calcByPrecedence(input);
            }
            return tot;
        }

        public static long calcByPrecedence(string input)
        {
            List<string> inputArr = input.Split(" ").ToList();

            while (inputArr.Contains("+"))
            {
                int i = inputArr.IndexOf("+");
                long sum = long.Parse(inputArr[i - 1]) + long.Parse(inputArr[i + 1]);
                inputArr.RemoveRange(i - 1, 3);
                inputArr.Insert(i - 1, sum.ToString());
            }

            while (inputArr.Contains("*"))
            {
                int i = inputArr.IndexOf("*");
                long sum = long.Parse(inputArr[i - 1]) * long.Parse(inputArr[i + 1]);
                inputArr.RemoveRange(i - 1, 3);
                inputArr.Insert(i - 1, sum.ToString());
            }

            return long.Parse(inputArr.First());
        }

        public static long calcLeftRight(string input)
        {
            List<string> inputArr = input.Split(" ").ToList();

            while (inputArr.Contains("+") || inputArr.Contains("*"))
            {
                int i = Array.FindIndex(inputArr.ToArray(), i => i == "+" || i == "*");
                long sum = inputArr[i] == "+" ?
                    long.Parse(inputArr[i - 1]) + long.Parse(inputArr[i + 1]) :
                    long.Parse(inputArr[i - 1]) * long.Parse(inputArr[i + 1]);
                inputArr.RemoveRange(i - 1, 3);
                inputArr.Insert(i - 1, sum.ToString());
            }

            return long.Parse(inputArr.First());
        }
    }
}
