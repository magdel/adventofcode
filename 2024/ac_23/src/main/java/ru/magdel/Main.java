package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        var sPairs = input.replace("\r", "").split("\n");

        var linkList = Stream.of(sPairs).map(p -> {
            var pairs = p.split("-");
            return new Link(new Pair(pairs[0], pairs[1]));
        }).collect(Collectors.toList());

        System.out.println("links: " + linkList.size());

        Map<String, List<Pair>> lan = new HashMap<>();
        for (var link : linkList) {
            lan.compute(link.pair.a, new BiFunction<String, List<Pair>, List<Pair>>() {
                @Override
                public List<Pair> apply(String a, List<Pair> pairs) {
                    if (pairs == null) {
                        ArrayList<Pair> list = new ArrayList<>();
                        list.add(link.pair);
                        return list;
                    }
                    pairs.add(link.pair);

                    return pairs;
                }
            });
            lan.compute(link.pair.b, new BiFunction<String, List<Pair>, List<Pair>>() {
                @Override
                public List<Pair> apply(String a, List<Pair> pairs) {
                    if (pairs == null) {
                        ArrayList<Pair> list = new ArrayList<>();
                        list.add(link.pair);
                        return list;
                    }
                    pairs.add(link.pair);

                    return pairs;
                }
            });
        }

        System.out.println("lan: " + lan.size());

        Set<TriplePc> triples = new HashSet<>();

        for (var pcLinks : lan.entrySet()) {
            var pc = pcLinks.getKey();
            /*if (pcLinks.getValue().size()!=3)
                continue;*/

            for (var pcLinks2 : lan.entrySet()) {
                    var pc2 = pcLinks2.getKey();
                if (pc.equals(pc2))
                    continue;

                for (var pcLinks3 : lan.entrySet()) {
                    var pc3 = pcLinks3.getKey();
                    if (pc.equals(pc3))
                        continue;
                    if (pc2.equals(pc3))
                        continue;

                    boolean b= isConnected(pc, pc2, lan);
                    boolean b2= isConnected(pc, pc3, lan);
                    boolean b3= isConnected(pc2, pc3, lan);
                    if (b && b2 && b3) {
                        triples.add(new TriplePc(pc, pc2, pc3));
                    }

                }
            }
        }

        System.out.println("triples: " + triples.size());
        //надо отобрать уникальные наборы

        int c=0;
        for (var tr :triples) {
           if (tr.a.charAt(0)=='t' || tr.b.charAt(0)=='t' || tr.c.charAt(0)=='t')
               c++;
        }
        System.out.println("c: " + c);
            //Map<String, Integer> pcColors = new HashMap<>();

        //int color = 1;
        //fillWithColor(lan, pcColors, color);

        //System.out.println("pcColors: " + pcColors.size());

    }

    private static boolean isConnected(String pc, String pc2, Map<String, List<Pair>> lan) {
        var pairs = lan.get(pc);
        for (var p: pairs) {
           if (p.a.equals(pc2) || p.b.equals(pc2))
               return true;
        }
        return false;
    }

    private static void fillWithColor(Map<String, List<Pair>> lan, Map<String, Integer> pcColors, int startColor) {
        for (var pcLinks : lan.entrySet()) {
            var pc = pcLinks.getKey();
            int nearByColors = 0;
            for (var pair : pcLinks.getValue()) {
                var existingColor = pcColors.get(pair.a.equals(pc) ? pair.b: pair.a);
                if (existingColor != null) {
                    nearByColors = existingColor;
                    break;
                }
            }
            if (nearByColors > 0) {
                pcColors.put(pc, nearByColors);
            } else {
                pcColors.put(pc, startColor);
                startColor++;
            }
        }
    }


    record Pair(String a, String b) {
    }

    record TriplePc(String a, String b, String c) {

        TriplePc(String a, String b, String c) {
            List<String> list = new ArrayList<>(Arrays.asList(a, b, c));
            list.sort(Comparator.naturalOrder());
            this.a = list.get(0);
            this.b = list.get(1);
            this.c = list.get(2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TriplePc triplePc = (TriplePc) o;
            return Objects.equals(a, triplePc.a) && Objects.equals(b, triplePc.b) && Objects.equals(c, triplePc.c);
        }

        @Override
        public int hashCode() {
            int result = Objects.hashCode(a);
            result = 31 * result + Objects.hashCode(b);
            result = 31 * result + Objects.hashCode(c);
            return result;
        }
    }

    static class Link {
        private final String pairName;
        private final Pair pair;

        public Link(Pair p) {
            this.pair = p;
            this.pairName = p.a.compareTo(p.b) > 0 ? p.a + p.b : p.b + p.a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Link link = (Link) o;
            return pairName.equals(link.pairName);
        }

        @Override
        public int hashCode() {
            return pairName.hashCode();
        }
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