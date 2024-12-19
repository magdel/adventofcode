package ru.magdel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {
    static List<String> towels = new ArrayList<>();
    static List<String> sortedTowels = new ArrayList<>();
    static Map<String, Long> towelsMap = new TreeMap<>();
    static List<String> designs = new ArrayList<>();

    static final int MAX_L = 8;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;

        // Read available towel patterns
        line = reader.readLine();
        if (line != null && !line.isEmpty()) {
            towels = Arrays.asList(line.split(",\\s*"));
        }
        sortedTowels = new ArrayList<>(towels);
        sortedTowels.sort(String::compareTo);
        towelsMap = towels.stream().collect(Collectors.toMap(s -> s, s -> 0L, (v1, v2) -> v1 + v2, () -> new TreeMap<>()));
        int maxL = towels.stream().map(String::length).max(Integer::compareTo).get();

        // Skip blank line
        reader.readLine();

        // Read desired designs
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                designs.add(line);
            }
        }

        int possibleDesigns = 0;
        int c = 0;
        long sumCountNotZero = 0;
        long sumCount = 0;
        for (String design : designs) {
            var count_dp = countDP(design);
            if (count_dp!=0)
                sumCountNotZero++;
            sumCount+=count_dp;
            /*if (isPossible(design)) {
                possibleDesigns++;
            }*/
            c++;
        }

        System.out.println("sumCountNotZero=" + sumCountNotZero);
        System.out.println("sumCount=" + sumCount);
    }


    static long countDP(String goal) {
        var dp = new long[goal.length() + 1];
        dp[0] = 1;

        for (var i = 1; i < dp.length; ++i) {
            for (String towel : towels) {

                if (towel.length() <= i && goal.substring((i - towel.length()), i).equals(towel))
                    dp[i] += dp[i - towel.length()];

            }
        }
        return dp[dp.length-1];
    }

    private static boolean isPossible(String design) {
        return backtrack(design, 0);
    }

    private static boolean backtrack(String design, int index){
        if (index == design.length()) {
            return true;
        }
        for (int i = index + MAX_L; i >= index; i--) {
            if (i <= index) continue;
            if (i > design.length()) continue;
            String towelToFind = design.substring(index, i);
            if (towelsMap.containsKey(towelToFind)) {
                if (backtrack(design, index + towelToFind.length())) {
                    return true;
                }
            }
        }

/*
        for (int i = index + 1; i < design.length() + 1 && (i - index < 10); i++) {
            String towelToFind = design.substring(index, i);
            if (towelsMap.containsKey(towelToFind)) {
                if (backtrack(design, index + towelToFind.length())) {
                    return true;
                }
            }
        }
*/

/*
        for (int i = 0; i < towels.size(); i++) {
            String towel = towels.get(i);
            if (design.startsWith(towel, index)) {
                if (backtrack(design, index + towel.length())) {
                    return true;
                }
            }
        }
*/

        return false;
    }
}