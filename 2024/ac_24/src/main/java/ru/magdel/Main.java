package ru.magdel;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        var sParts = input.replace("\r", "").split("\n\n");
        var sInputBits = sParts[0].replace("\r", "").split("\n");
        var sWires = sParts[1].replace("\r", "").split("\n");


        Map<String, Boolean> inputs = Stream.of(sInputBits).map(p -> {
            var pairs = p.split(":");
            return pairs;
        }).collect(Collectors.toMap(p -> p[0], p -> p[1].equals(" 1")));
        System.out.println("inputs: " + inputs);
        System.out.println("inputs: " + inputs.size());

        Map<String, Wire> inputWires = inputs.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> new Wire(e.getKey(), e.getValue())));

        Map<String, WireSupplier> allWires = HashMap.newHashMap(1000);
        inputWires.entrySet().forEach(e -> {
            allWires.put(e.getKey(), new ValueWireSupplier(e.getKey(), e.getValue()));
        });
        System.out.println("allWires: " + inputWires);

        var gateList = Stream.of(sWires).map(p -> {
                    var inOuts = p.split("->");
                    var ins = inOuts[0].trim().split(" ");
                    Gate gate = switch (ins[1]) {
                        case "OR" ->
                                new Gate(new MapWireSupplier(allWires, ins[0]), new MapWireSupplier(allWires, ins[2]), WT.OR, inOuts[1].trim());
                        case "XOR" ->
                                new Gate(new MapWireSupplier(allWires, ins[0]), new MapWireSupplier(allWires, ins[2]), WT.XOR, inOuts[1].trim());
                        case "AND" ->
                                new Gate(new MapWireSupplier(allWires, ins[0]), new MapWireSupplier(allWires, ins[2]), WT.AND, inOuts[1].trim());
                        default -> throw new IllegalArgumentException(ins[1]);
                    };
                    allWires.put(gate.nameOutput, gate.output());
                    return gate;
                })
                .toList();

        System.out.println("gateList: " + gateList);
        Map<String, WireSupplier> newWireValues = HashMap.newHashMap(100);
        for (Gate g : gateList) {
            newWireValues.put(g.nameOutput, g.output());
        }
        System.out.println("newWireValues: " + newWireValues);
        BitSet bs = new BitSet();
        newWireValues.forEach(new BiConsumer<String, WireSupplier>() {
            @Override
            public void accept(String s, WireSupplier wireSupplier) {
                if (s.charAt(0)!='z')
                    return;
                System.out.println("name: " + s+ " ="+wireSupplier.get().value);
                int bitNum = Integer.parseInt(s.substring(1));
                bs.set(bitNum, wireSupplier.get().value);
            }
        });
        System.out.println("bs=" +bs.toString());
        System.out.println("b=" + bs.toLongArray()[0]);


    }

    record Gate(WireSupplier a, WireSupplier b, WT type, String nameOutput) {
        WireSupplier output() {
            if (a != null && b != null) {
                return new OutputWire(a, b, type, nameOutput);
            }
            return null;
        }
    }

    record Wire(String name, Boolean value) {

    }

    enum WT {
        AND,
        OR,
        XOR
    }

    interface WireSupplier extends Supplier<Wire> {
        String name();
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

}