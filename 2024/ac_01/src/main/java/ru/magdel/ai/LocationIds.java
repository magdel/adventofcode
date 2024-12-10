package ru.magdel.ai;

import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

public class LocationIds {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        String line;
        int[] leftList = new int[1000];
        int[] rightList = new int[1000];
        int leftIndex = 0;
        int rightIndex = 0;
        int totalDistance = 0;

        while ((line = reader.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int id1 = Integer.parseInt(tokenizer.nextToken());
            int id2 = Integer.parseInt(tokenizer.nextToken());

            leftList[leftIndex] = id1;
            rightList[rightIndex] = id2;

            leftIndex++;
            rightIndex++;
        }

        Arrays.sort(leftList);
        Arrays.sort(rightList);

        while (leftIndex > 0 && rightIndex < 50) {
            int distance = Math.abs(leftList[leftIndex - 1] - rightList[rightIndex]);
            if (distance == 0) {
                leftIndex--;
                rightIndex++;
            } else if (distance < Math.abs(leftList[leftIndex - 1] - rightList[rightIndex - 1])) {
                leftIndex--;
            } else {
                rightIndex++;
            }
            totalDistance += distance;
        }

        System.out.println(totalDistance);
    }
}