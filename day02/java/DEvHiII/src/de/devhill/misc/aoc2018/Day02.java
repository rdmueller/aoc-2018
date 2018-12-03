package de.devhill.misc.aoc2018;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Day02 {
    public static void main(String[] args) throws Exception {
        String[] input = readInput("Day02.txt");
        part1(input);
        part2(input);
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

    public static void part1(String[] input) {
        int count2 = 0;
        int count3 = 0;
        Integer temp = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        for (String boxID : input) {
            for (Character c :
                    boxID.toCharArray()) {
                temp = map.get(c);
                map.put(c, (temp != null ? temp + 1 : 1));
            }
            if (map.containsValue(2)) count2++;
            if (map.containsValue(3)) count3++;
            map.clear();
        }

        System.out.println("Output part1: " + (count2 * count3));
    }


    public static void part2(String[] input) {
        int out=-1;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if(i!=j){
                    out=almostMatch(input[i],input[j]);
                    if(out>-1){
                        System.out.println(input[i].substring(0,out)+input[i].substring(out+1));
                        return;
                    }

                }

            }
        }
    }

    public static int almostMatch(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        int delta = 0;
        int pos=0;

        for (int i = 0; i < c1.length; i++) {
            if (c1[i] != c2[i]){
                delta++;
                pos=i;
            }
        }
        return delta == 1?pos:-1;
    }
}
