package ru.magdel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main23 {

    //working !!
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac11!");
        long startTime = System.currentTimeMillis();
        String input = Files.readString(Path.of("input1000.txt"));
        var list = Arrays.stream(input.split(" "))
                .map(val -> new Value(new BigDecimal(val), BigDecimal.ONE))
                .collect(Collectors.toList());
        //System.out.println("list=" + list);
        List<Value> nextList;
        nextList = makeIteration(list);
        for (int i = 0; i < 9999; i++) {
            nextList = makeIteration(nextList);
            //System.out.println("it=" + i + "next list=" + nextList.size());
        }
        BigDecimal count = BigDecimal.ZERO;
        for (int i = 0; i < nextList.size(); i++) {
            count = count.add(nextList.get(i).count);
        }
        System.out.println("count=" + count + ", time=" + (System.currentTimeMillis() - startTime)+"ms");
    }

    private static List<Value> makeIteration(List<Value> list) {
        list = collapse(list);
        List<Value> nextList = new ArrayList<>();
        for (int x = 0; x < list.size(); x++) {
            var number = list.get(x);
            if (number.val.equals(BigDecimal.ZERO)) {
                nextList.add(new Value(new BigDecimal(1), number.count));
                continue;
            }
            String value = number.val.toPlainString();
            if (value.length() % 2 == 0) {
                nextList.add(new Value(new BigDecimal(value.substring(0, value.length() / 2)), number.count));
                nextList.add(new Value(new BigDecimal(value.substring(value.length() / 2)), number.count));
                continue;
            }
            nextList.add(new Value(number.val.multiply(new BigDecimal(2024)), number.count));
        }
        return nextList;
    }

    private static List<Value> collapse(List<Value> list) {
        list.sort(Comparator.comparing(Value::val));
        List<Value> newList = new ArrayList<>();
        Value last = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            var val = list.get(i);
            if (val.val.equals(last.val)) {
                last = new Value(last.val, last.count.add(val.count));
                continue;
            }
            newList.add(last);
            last = val;
        }
        newList.add(last);

        return newList;
    }

    record Value(BigDecimal val, BigDecimal count) {
    }

}
