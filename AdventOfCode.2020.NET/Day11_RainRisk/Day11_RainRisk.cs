using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;

namespace Day11_RainRisk
{
    class Day11_RainRisk
    {
        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Day11_Input.txt");

            int[] ship1Pos = new int[2] { 0, 0 };   //X, Y
            int ship1Face = 2;                      //E
            int[] ship2Pos = new int[2] { 0, 0 };   //X, Y
            int[] wayPos = new int[2] { 10, -1 };   //X, Y

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
                        wayPos[1] -= dist;
                        ship1Pos[1] -= dist;
                        break;
                    case 'S':
                        wayPos[1] += dist;
                        ship1Pos[1] += dist;
                        break;
                    case 'E':
                        wayPos[0] += dist;
                        ship1Pos[0] += dist;
                        break;
                    case 'W':
                        wayPos[0] -= dist;
                        ship1Pos[0] -= dist;
                        break;
                    case 'L':
                        ship1Face = MathMod(ship1Face - (dist / 90) - 1, 4) + 1;
                        for (int i = 0; i < dist / 90; i++)
                        {
                            wayPos = new int[2] { wayPos[1], wayPos[0] * -1 };
                        }
                        break;
                    case 'R':
                        ship1Face = MathMod((dist / 90) + ship1Face - 1, 4) + 1;
                        for (int i = 0; i < dist / 90; i++)
                        {
                            wayPos = new int[2] { wayPos[1] * -1, wayPos[0] };
                        }
                        break;
                    case 'F':
                        ship1Pos[0] += faces[ship1Face][0] * dist;
                        ship1Pos[1] += faces[ship1Face][1] * dist;
                        for (int i = 0; i < dist; i++)
                        {
                            ship2Pos[0] += wayPos[0];
                            ship2Pos[1] += wayPos[1];
                        }
                        break;
                    default:
                        break;
                }
            }

            Console.WriteLine("Final position: " + (Math.Abs(ship1Pos[0]) + Math.Abs(ship1Pos[1])));
            Console.WriteLine("Final position: " + (Math.Abs(ship2Pos[0]) + Math.Abs(ship2Pos[1])));

            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static int MathMod(int a, int b)
        {
            return (Math.Abs(a * b) + a) % b;
        }
    }
}
