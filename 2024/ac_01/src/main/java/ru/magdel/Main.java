package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        var lines = input.split("\n");
        var list1 = Arrays.stream(lines)
                .map(line -> Integer.valueOf(line.substring(0, 5)))
                .sorted()
                .toList();
        var list2 = Arrays.stream(lines)
                .map(line -> Integer.valueOf(line.substring(8, 13)))
                .sorted()
                .toList();
        long sum = 0;
        for (int i = 0; i < list1.size(); i++) {
            sum += Math.abs(list1.get(i) - list2.get(i));
        }
        System.out.println(sum);
    }
}