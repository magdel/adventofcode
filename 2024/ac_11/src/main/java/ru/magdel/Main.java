package ru.magdel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac11!");
        String input = Files.readString(Path.of("input.txt"));
        var list = Arrays.stream(input.split(" "))
                .map(val -> new BigDecimal(val))
                .toList();
        System.out.println("list=" + list);
        List<BigDecimal> nextList = new ArrayList<>();
        nextList = makeIteration(list);
        for (int i = 0; i < 74; i++) {
            nextList = makeIteration(nextList);
            System.out.println("it=" + i + "next list=" + nextList.size());
        }
        System.out.println("next list=" + nextList.size());
    }

    private static List<BigDecimal> makeIteration(List<BigDecimal> list) {
        List<BigDecimal> nextList = new ArrayList<>();
        for (int x = 0; x < list.size(); x++) {
            var number = list.get(x);
            if (number.equals(BigDecimal.ZERO)) {
                nextList.add(new BigDecimal(1));
                continue;
            }
            String value = number.toPlainString();
            if (value.length() % 2 == 0) {
                nextList.add(new BigDecimal(value.substring(0, value.length() / 2)));
                nextList.add(new BigDecimal(value.substring(value.length() / 2)));
                continue;
            }
            nextList.add(number.multiply(new BigDecimal(2024)));
        }
        return nextList;
    }

}
