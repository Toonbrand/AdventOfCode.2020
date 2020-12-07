using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;

namespace Day5_BinaryBoarding
{
    class Program
    {
        static readonly List<int> totalRows = new List<int>();
        static readonly List<int> totalCols = new List<int>();

        static void Main()
        {
            Stopwatch stopwatch = Stopwatch.StartNew();
            string[] lines = File.ReadAllLines(@"Input.txt");

            for (int i = 0; i < 128; i++) totalRows.Add(i);
            for (int i = 0; i < 8; i++) totalCols.Add(i);

            List<int> seatIds = new List<int>();
            foreach (string line in lines) seatIds.Add(getSeatId(line));
            seatIds.Sort();

            int lastSeat = 0;
            foreach (int seatId in seatIds)
            {
                if (seatId-lastSeat==2)
                {
                    Console.WriteLine("Your seat ID: " + (seatId-1));
                    break;
                }
                lastSeat = seatId;
            }

            Console.WriteLine("Highest seat ID: " + seatIds.Max());
            stopwatch.Stop();
            Console.WriteLine("Executed in: " + stopwatch.ElapsedMilliseconds + "ms");
        }

        static int getSeatId(String input)
        {
            List<int> rows = new List<int>(totalRows);
            List<int> cols = new List<int>(totalCols);
            foreach (char ch in input)
            {
                switch (ch)
                {
                    case'F':
                        rows.RemoveRange(rows.Count / 2, rows.Count / 2);
                        break;
                    case'B':
                        rows.RemoveRange(0, rows.Count / 2);
                        break;
                    case 'L':
                        cols.RemoveRange(cols.Count / 2, cols.Count / 2);
                        break;
                    case 'R':
                        cols.RemoveRange(0, cols.Count / 2);
                        break;
                }
            }

            return rows.FirstOrDefault()*8+cols.FirstOrDefault();
        }
    }
}