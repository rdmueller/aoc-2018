package de.devhill.misc.aoc2018;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Day01 {
    public static void main(String[] args) throws Exception {
        ClassLoader cL = Thread.currentThread().getContextClassLoader();
        InputStream is = cL.getResourceAsStream("Day01.txt");

        InputStreamReader sR = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(sR);

        int freq=0;

        for(String line; (line =br.readLine())!=null;){
            freq+=Integer.valueOf(line);
        }

        System.out.println("Output: "+ freq);
    }

}
