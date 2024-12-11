package ru.magdel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main21 {


    public static final BigDecimal VAL2024 = new BigDecimal(2024);

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac11!");
        String input = Files.readString(Path.of("input.txt"));
        var list = Arrays.stream(input.split(" "))
                .map(val -> new BigDecimal(val))
                .toList();
        System.out.println("list=" + list);
        long count = 0;
        for (int i = 0; i < list.size(); i++) {
            count += countSplitDeep(list.get(i), 75);
        }

        System.out.println("count=" + count);


    }

    private static long countSplitDeep(BigDecimal number, int leftDeep) {
        if (leftDeep == 0) {
            return 1;
        }
        if (number.equals(BigDecimal.ZERO)) {
            return countSplitDeep(BigDecimal.ONE, leftDeep - 1);
        }
        //
        if (number.precision() % 2 == 0) {
            String value = number.toPlainString();
            var val1 = new BigDecimal(value.substring(0, value.length() / 2));
            var spcount = countSplitDeep(val1, leftDeep - 1);
            var val2 = new BigDecimal(value.substring(value.length() / 2));
            return spcount + countSplitDeep(val2, leftDeep - 1);
        }
        var val = number.multiply(VAL2024);
        return countSplitDeep(val, leftDeep - 1);
    }

}
