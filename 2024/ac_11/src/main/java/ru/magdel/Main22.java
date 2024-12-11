package ru.magdel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.StringTokenizer;

public class Main22 {


    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac11!");
        String input = Files.readString(Path.of("input.txt"));


/*
        var list = Arrays.stream(input.split(" "))
                .map(val -> new BigDecimal(val))
                .toList();
*/
        var list = input;
        System.out.println("list=" + list);
        String nextList = "";
        nextList = makeIteration(list);
        for (int i = 0; i < 74; i++) {
            nextList = makeIteration(nextList);
            long spaceCount = spaceCount(nextList);
            System.out.println("it=" + i + "next list=" + spaceCount);
        }
        long spaceCount = spaceCount(nextList);
        System.out.println("next list=" + spaceCount);


    }

    private static long spaceCount(String nextList) {
        long c = 0;
        for (int i = 0; i < nextList.length(); i++) {
            if (nextList.charAt(i) == ' ') {
                c++;
            }
        }
        return c;
    }

    private static String makeIteration(String list) {
        StringBuilder nextList = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(list);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("0")) {
                nextList.append(" 1");
                continue;
            }
            if (token.length() % 2 == 0) {
                nextList.append(' ');
                nextList.append(token.substring(0, token.length() / 2));
                nextList.append(' ');
                nextList.append(new BigDecimal(token.substring(token.length() / 2)).toPlainString());
                continue;
            }
            nextList.append(' ');
            nextList.append(new BigDecimal(token).multiply(new BigDecimal(2024)).toPlainString());
        }
        return nextList.toString();
    }

}
