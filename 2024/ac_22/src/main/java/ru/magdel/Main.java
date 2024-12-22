package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static List<String> towels = new ArrayList<>();
    static List<String> sortedTowels = new ArrayList<>();
    static Map<String, Long> towelsMap = new TreeMap<>();
    static List<String> designs = new ArrayList<>();

    static final int MAX_L = 8;

    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        var inits = input.replace("\r", "").split("\n");

        var secrets = Stream.of(inits).map(Long::parseLong).collect(Collectors.toList());
        long[][] secValues = new long[secrets.size()][2000];
        int[][] sellPrices = new int[secrets.size()][2000];
        int[][] changes = new int[secrets.size()][2000];

        int iterations = 0;

        int maxIterations = 2000;
        while (iterations < maxIterations) {
            for (int i = 0; i < secrets.size(); i++) {
                long numOrig = secrets.get(i);
                long mul64 = numOrig * 64;
                long num = mul64 ^ numOrig;
                num = num % 16777216;
                long numdiv32 = num / 32;
                num = numdiv32 ^ num;
                num = num % 16777216;
                long mul2024 = num * 2048;
                num = mul2024 ^ num;
                num = num % 16777216;
                secrets.set(i, num);
                secValues[i][iterations] = num;
                if (iterations == 0) {
                    String sNum = Long.toString(secValues[i][iterations]);
                    String sPrevNum = Long.toString(numOrig);
                    changes[i][iterations] = sNum.charAt(sNum.length() - 1) - sPrevNum.charAt(sPrevNum.length() - 1);
                }
                if (iterations > 0) {
                    String sNum = Long.toString(secValues[i][iterations]);
                    String sPrevNum = Long.toString(secValues[i][iterations - 1]);
                    changes[i][iterations] = sNum.charAt(sNum.length() - 1) - sPrevNum.charAt(sPrevNum.length() - 1);
                    sellPrices[i][iterations] = sNum.charAt(sNum.length() - 1) - '0';
                }
            }
            iterations++;
        }
        //Map<PriceMove, Long> priceMoveSumMap = new HashMap<>();
        List<Map<PriceMove, Long>> buyerFirstSellPrice = new ArrayList<>();

        //PriceMove pmTest = new PriceMove(-1, -1, 0, 2);
        //priceMoveSumMap.put(pmTest, 1L);
        for (int b = 0; b < sellPrices.length; b++) {
            HashMap<PriceMove, Long> priceMoveSumMap = new HashMap<>();
            buyerFirstSellPrice.add(priceMoveSumMap);
            for (int i = 3; i < maxIterations; i++) {
                long currentSellPrice = sellPrices[b][i];
                PriceMove pm = new PriceMove(changes[b][i - 3], changes[b][i - 2], changes[b][i - 1], changes[b][i]);
                priceMoveSumMap.compute(pm, new BiFunction<PriceMove, Long, Long>() {
                    @Override
                    public Long apply(PriceMove priceMove, Long aLong) {
                        if (aLong == null) {
                            return currentSellPrice;
                        }
                        return aLong;
                    }
                });
            }
        }

        Set<PriceMove> allPriceMoves = new HashSet<>();
        for (int b = 0; b < sellPrices.length; b++) {
            allPriceMoves.addAll(buyerFirstSellPrice.get(b).keySet());
        }

        HashMap<PriceMove, Long> finalMap = new HashMap<>();

        for (var pm: allPriceMoves){
            for (int b = 0; b < sellPrices.length; b++) {
                Long sumSellForBuyer = buyerFirstSellPrice.get(b).get(pm);
                if (sumSellForBuyer==null)
                    continue;
                finalMap.compute(pm, new BiFunction<PriceMove, Long, Long>() {
                    @Override
                    public Long apply(PriceMove priceMove, Long aLong) {
                        if (aLong == null) {
                            return sumSellForBuyer;
                        }
                        return aLong +sumSellForBuyer;
                    }
                });
            }
        }



        long maxSell = finalMap.values().stream().max(Long::compareTo).get();
        var maxSellSeq = finalMap.entrySet().stream().filter(e-> e.getValue()==maxSell).toList();

        System.out.println("MAX: "+maxSellSeq);

       /* long sumsecrets = 0;
        for (var num : secrets) {
            System.out.println(num);
            sumsecrets += num;
        }
        System.out.println("Sum=" + sumsecrets);*/

//        System.out.println("sumCountNotZero=" + sumCountNotZero);
        //      System.out.println("sumCount=" + sumCount);
    }


    static long countDP(String goal) {
        var dp = new long[goal.length() + 1];
        dp[0] = 1;

        for (var i = 1; i < dp.length; ++i) {
            for (String towel : towels) {

                if (towel.length() <= i && goal.substring((i - towel.length()), i).equals(towel)) {
                    dp[i] += dp[i - towel.length()];
                }

            }
        }
        return dp[dp.length - 1];
    }

    private static boolean isPossible(String design) {
        return backtrack(design, 0);
    }

    private static boolean backtrack(String design, int index) {
        if (index == design.length()) {
            return true;
        }
        for (int i = index + MAX_L; i >= index; i--) {
            if (i <= index) {
                continue;
            }
            if (i > design.length()) {
                continue;
            }
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

    record PriceMove(int p1, int p2, int p3, int p4) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PriceMove priceMove = (PriceMove) o;
            return p1 == priceMove.p1 && p2 == priceMove.p2 && p3 == priceMove.p3 && p4 == priceMove.p4;
        }

        @Override
        public int hashCode() {
            int result = p1;
            result = 31 * result + p2;
            result = 31 * result + p3;
            result = 31 * result + p4;
            return result;
        }
    }
}