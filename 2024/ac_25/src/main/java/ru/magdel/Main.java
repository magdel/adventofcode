package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String input = Files.readString(Path.of("input.txt"));
        var sSchemas = input.replace("\r", "").split("\n\n");

        List<Lock> lockSet = new ArrayList<>();
        List<Lock> keySet = new ArrayList<>();
        for (var sSchema : sSchemas) {
            var sPinsLines = sSchema.split("\n");
            int topHatchC = 0;
            List<Integer> iPins = new ArrayList<>();
            for (int x = 0; x < 5; x++) {
                topHatchC = 0;
                if (sPinsLines[0].equals("#####")) {
                    for (int y = 0; y < 5; y++) {
                        char c = sPinsLines[y + 1].charAt(x);
                        if (c == '#') {
                            topHatchC++;
                        }
                    }
                    iPins.add(topHatchC);
                }
                if (sPinsLines[6].equals("#####")) {
                    for (int y = 5; y > 0; y--) {
                        char c = sPinsLines[y].charAt(x);
                        if (c == '#') {
                            topHatchC++;
                        }
                    }
                    iPins.add(topHatchC);
                }
            }
            if (sPinsLines[0].equals("#####")) {
                lockSet.add(new Lock(iPins));
            }
            if (sPinsLines[6].equals("#####")) {
                keySet.add(new Lock(iPins));
            }
        }
        int compatible = 0;
        for (var key : keySet) {
            for (var lock : lockSet) {
                if (key.opens(lock)) {
                    compatible++;
                }

            }
        }

        System.out.println("keys: " + keySet);
        System.out.println("locks: " + lockSet);
        System.out.println("compatible=" + compatible);
    }

    record Lock(List<Integer> pins) {
        public boolean opens(Lock lock) {
            for (int i = 0; i < pins.size(); i++) {
                int sumPins = pins.get(i) + lock.pins.get(i);
                if (sumPins > 5) {
                    return false;
                }
            }
            return true;
        }
    }

}