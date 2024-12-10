package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main2 {



    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac10!");
        String input = Files.readString(Path.of("input.txt"));
        var linesArray = input.replace("\r", "").split("\n");
        int[][] map = new int[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = Integer.parseInt("" + linesArray[y].charAt(x));
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);

        // need to scan every cell for start and then make deep search

        int[][] trailHeadScores = new int[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] != 0) {
                    continue;
                }
                boolean[][] reachedHeights = new boolean[linesArray.length][linesArray[0].length()];
                var path = new ArrayList<Cell>();
                path.add(new Cell(x, y, map[y][x]));
                var rate = deepSearchMapForScores(path, map, reachedHeights, 0);
                //var score = getReachedHeightsCount(reachedHeights);
                trailHeadScores[y][x] = rate;
            }
        }

        System.out.println();
        int resultCount = getScoresSums(trailHeadScores);


        System.out.println("Scores sum: " + resultCount);


    }

    private static int getScoresSums(int[][] trailHeadScores) {
        int resultCount = 0;
        for (int y = 0; y < trailHeadScores.length; y++) {
            for (int x = 0; x < trailHeadScores[0].length; x++) {
                //System.out.print(reachedHeights[y][x] ? 'X' : '.');
                resultCount += trailHeadScores[y][x];
            }
            //System.out.println();
        }
        return resultCount;
    }

    private static int getReachedHeightsCount(boolean[][] reachedHeights) {
        int resultCount = 0;
        for (int y = 0; y < reachedHeights.length; y++) {
            for (int x = 0; x < reachedHeights[0].length; x++) {
                //System.out.print(reachedHeights[y][x] ? 'X' : '.');
                if (reachedHeights[y][x]) {
                    resultCount++;
                }
            }
            //System.out.println();
        }
        return resultCount;
    }

    private static int deepSearchMapForScores(ArrayList<Cell> path, int[][] map, boolean[][] reachedHeights, int rate) {
        if (!isCorrectSlope(path)) {
            return rate;
        }
        var lastCell = path.get(path.size() - 1);
        if (path.size() ==10 && lastCell.symbol == 9) {
            reachedHeights[lastCell.y][lastCell.x] = true;
            return rate+1;
        }
        //берем ячейки вокруг и проверяем, подходят ли они нам.
        //если подходят - переходим на нее
        if (lastCell.x > 0) {
            Cell cell = new Cell(lastCell.x - 1, lastCell.y, map[lastCell.y][lastCell.x - 1]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                   rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.x < map[0].length - 1) {
            Cell cell = new Cell(lastCell.x + 1, lastCell.y, map[lastCell.y][lastCell.x + 1]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.y > 0) {
            Cell cell = new Cell(lastCell.x, lastCell.y - 1, map[lastCell.y - 1][lastCell.x]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.y < map.length - 1) {
            Cell cell = new Cell(lastCell.x, lastCell.y + 1, map[lastCell.y + 1][lastCell.x]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        return rate;
    }

    private static boolean notInPath(Cell cell, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++)
            if (path.get(i).x == cell.x && path.get(i).y == cell.y) {
                return false;
            }
        return true;
    }

    private static boolean isCorrectSlope(ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++)
            if (path.get(i).symbol != i) {
                return false;
            }
        return true;
    }

    record Cell(int x, int y, int symbol) {
    }

}
