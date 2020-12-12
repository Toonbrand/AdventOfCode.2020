using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day11_RainRisk
{
    class Program
    {
        static void Main(string[] args)
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");

            int[] pos = new int[2] { 0, 0 };    //X, Y
            int face = 2;  //E

            Dictionary<int, int[]> faces = new Dictionary<int, int[]>()
            {
                {1, new int[2]{0,-1 } },    //N
                {2, new int[2]{1,0 } },     //E
                {3, new int[2]{0,1 } },     //S
                {4, new int[2]{-1,0 } }     //W
            };

            foreach (string line in lines)
            {
                char comm = line[0];
                int dist = Int32.Parse(line[1..line.Length]);

                switch (comm)
                {
                    case 'N':
                        pos[1] -= dist;
                        break;
                    case 'S':
                        pos[1] += dist;
                        break;
                    case 'E':
                        pos[0] += dist;
                        break;
                    case 'W':
                        pos[0] -= dist;
                        break;
                    case 'L':
                        face = MathMod(face - (dist / 90) - 1, 4) + 1;
                        break;
                    case 'R':
                        face = MathMod((dist / 90) + face - 1, 4) + 1;
                        break;
                    case 'F':
                        pos[0] += faces[face][0] * dist;
                        pos[1] += faces[face][1] * dist;
                        break;
                    default:
                        break;
                }
            }


            Console.WriteLine("Final position: " + (Math.Abs(pos[0]) + Math.Abs(pos[1])));

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static int MathMod(int a, int b)
        {
            return (Math.Abs(a * b) + a) % b;
        }
    }
}
