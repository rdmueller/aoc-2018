package de.devhill.misc.aoc2018;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Day03 {
    public static void main(String[] args) throws Exception {
        String[] input = readInput("Day03.txt");
        HashMap m= part1(input);
        part2(input,m);
    }

    public static String[] readInput(String fileName) throws Exception {
        ClassLoader cL = Thread.currentThread().getContextClassLoader();
        InputStream is = cL.getResourceAsStream(fileName);

        InputStreamReader sR = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(sR);

        ArrayList<String> list = new ArrayList<>();

        for (String line; (line = br.readLine()) != null; ) {
            list.add(line);
        }
        String[] array = new String[list.size()];
        array = list.toArray(array);

        return array;

    }

    public static HashMap<String, Integer> part1(String[] input) {
        int count = 0;
        Integer temp = 0;
        int xLimit = 0;
        int xStart = 0;
        int yLimit = 0;
        int yStart = 0;

        HashMap<String, Integer> map = new HashMap<>();

        for (String areaStr : input) {

            xStart = Integer.valueOf(areaStr.split("@ ")[1].split(",")[0]);
            xLimit = xStart + Integer.valueOf(areaStr.split(": ")[1].split("x")[0]);

            yStart = Integer.valueOf(areaStr.split("@ ")[1].split(",")[1].split(":")[0]);
            yLimit = yStart + Integer.valueOf(areaStr.split("x")[1]);

            for (int i = xStart + 1; i <= xLimit; i++) {
                for (int j = yStart + 1; j <= yLimit; j++) {
                    temp = map.get(i + "," + j);
                    if (temp != null) {
                        map.put(i + "," + j, (temp + 1));
                        count = temp == 1 ? count + 1 : count;
                    } else {
                        map.put(i + "," + j, 1);
                    }
                }
            }


        }

        System.out.println("Output part1: " + (count));
        return map;
    }


    public static void part2(String[] input, HashMap<String, Integer> map) {
        int count = 0;
        int temp = 0;
        int xLimit = 0;
        int xStart = 0;
        int yLimit = 0;
        int yStart = 0;

        for (String areaStr : input) {


            xStart = Integer.valueOf(areaStr.split("@ ")[1].split(",")[0]);
            xLimit = xStart + Integer.valueOf(areaStr.split(": ")[1].split("x")[0]);

            yStart = Integer.valueOf(areaStr.split("@ ")[1].split(",")[1].split(":")[0]);
            yLimit = yStart + Integer.valueOf(areaStr.split("x")[1]);

            for (int i = xStart + 1; i <= xLimit; i++) {
                for (int j = yStart + 1; j <= yLimit; j++) {
                    temp = map.get(i + "," + j);
                    if ( (temp>1)) {

                        count++;
                    }
                }
            }
            if (count == 0) {
                System.out.println("Output part2: " + areaStr.split("@ ")[0]);
                return;
            }
            count=0;

        }
        System.out.println("Part2 failed");
    }


}
