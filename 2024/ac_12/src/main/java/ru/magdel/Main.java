package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final char EMPTY = '0';

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac12!");
        String input = Files.readString(Path.of("input.txt"));
        var linesArray = input.replace("\r", "").split("\n");
        char[][] map = new char[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = linesArray[y].charAt(x);
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);

        // need to scan every cell for start and then make deep search
        List<List<Cell>> areas = new ArrayList<>();

        //int[][] trailHeadScores = new int[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == EMPTY) {
                    continue;
                }
                if (isInAreas(areas, x, y)) {
                    continue;
                }
                ArrayList<Cell> area = new ArrayList<>();
                var path = new ArrayList<Cell>();
                int perimeter = getPerimeter(x, y, map, map[y][x]);
                path.add(new Cell(x, y, map[y][x], perimeter));
                area.add(new Cell(x, y, map[y][x], perimeter));
                deepSearchMapForArea(path, map, area);
                areas.add(area);
                paintAreaUseless(area, map);
                System.out.println("Area found, size=" + area.size());
            }
        }




        System.out.println();
        System.out.println("Areas count=" + areas.size());
        long result = getTotalSum(areas);


        System.out.println("Total: " + result);


    }

    private static void paintAreaUseless(ArrayList<Cell> area, char[][] map) {
        for (int i = 0; i < area.size(); i++) {
            Cell cell = area.get(i);
            map[cell.y][cell.x] = EMPTY;
        }
    }

    private static long getTotalSum(List<List<Cell>> areas) {
        long sum = 0;
        for (int i = 0; i < areas.size(); i++) {
            List<Cell> area = areas.get(i);
            sum += getAreaSquare(area) * getAreaPerimeter(area);
        }
        return sum;
    }

    private static long getAreaPerimeter(List<Cell> area) {
        long sum = 0;
        for (int i = 0; i < area.size(); i++) {
            Cell cell = area.get(i);
            sum += cell.perimeter;
        }
        return sum;
    }

    private static long getAreaSquare(List<Cell> area) {
        return area.size();
    }

    private static boolean isInAreas(List<List<Cell>> areas, int x, int y) {
        for (int i = 0; i < areas.size(); i++) {
            List<Cell> area = areas.get(i);
            for (int j = 0; j < area.size(); j++) {
                Cell cell = area.get(j);
                if (cell.x == x && cell.y == y) {
                    return true;
                }
            }
        }
        return false;
    }


    private static void deepSearchMapForArea(ArrayList<Cell> path, char[][] map, ArrayList<Cell> area) {
/*
        if (!isCorrectSlope(path)) {
            return;
        }
*/
        var lastCell = path.get(path.size() - 1);
        if (lastCell.x > 0) {
            int perimeter = getPerimeter(lastCell.x - 1, lastCell.y, map, map[lastCell.y][lastCell.x - 1]);
            Cell cell = new Cell(lastCell.x - 1, lastCell.y, map[lastCell.y][lastCell.x - 1], perimeter);
            if (samePlot(lastCell, cell) && notInPath(cell, path) && notInPath(cell, area)) {
                path.add(cell);
                area.add(cell);
                deepSearchMapForArea(path, map, area);
                path.removeLast();
            }
        }
        if (lastCell.x < map[0].length - 1) {
            int perimeter = getPerimeter(lastCell.x + 1, lastCell.y, map, map[lastCell.y][lastCell.x + 1]);
            Cell cell = new Cell(lastCell.x + 1, lastCell.y, map[lastCell.y][lastCell.x + 1], perimeter);
            if (samePlot(lastCell, cell) && notInPath(cell, path) && notInPath(cell, area)) {
                path.add(cell);
                area.add(cell);
                deepSearchMapForArea(path, map, area);
                path.removeLast();
            }
        }
        if (lastCell.y > 0) {
            int perimeter = getPerimeter(lastCell.x, lastCell.y - 1, map, map[lastCell.y - 1][lastCell.x]);
            Cell cell = new Cell(lastCell.x, lastCell.y - 1, map[lastCell.y - 1][lastCell.x], perimeter);
            if (samePlot(lastCell, cell) && notInPath(cell, path) && notInPath(cell, area)) {
                path.add(cell);
                area.add(cell);
                deepSearchMapForArea(path, map, area);
                path.removeLast();
            }
        }
        if (lastCell.y < map.length - 1) {
            int perimeter = getPerimeter(lastCell.x, lastCell.y + 1, map, map[lastCell.y + 1][lastCell.x]);
            Cell cell = new Cell(lastCell.x, lastCell.y + 1, map[lastCell.y + 1][lastCell.x], perimeter);
            if (samePlot(lastCell, cell) && notInPath(cell, path) && notInPath(cell, area)) {
                path.add(cell);
                area.add(cell);
                deepSearchMapForArea(path, map, area);
                path.removeLast();
            }
        }
    }

    private static int getPerimeter(int x, int y, char[][] map, char symbol) {
        int perimeter = 0;
        if (x > 0) {
            char s = map[y][x - 1];
            if (s != symbol) {
                perimeter++;
            }
        } else {
            perimeter++;
        }
        if (x < map[0].length - 1) {
            char s = map[y][x + 1];
            if (s != symbol) {
                perimeter++;
            }
        } else {
            perimeter++;
        }
        if (y > 0) {
            char s = map[y - 1][x];
            if (s != symbol) {
                perimeter++;
            }
        } else {
            perimeter++;
        }
        if (y < map.length - 1) {
            char s = map[y + 1][x];
            if (s != symbol) {
                perimeter++;
            }
        } else {
            perimeter++;
        }
        return perimeter;
    }

    private static boolean samePlot(Cell lastCell, Cell cell) {
        return lastCell.symbol == cell.symbol;
    }

    private static boolean notInPath(Cell cell, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).x == cell.x && path.get(i).y == cell.y) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCorrectSlope(ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).symbol != i) {
                return false;
            }
        }
        return true;
    }

    record Cell(int x, int y, char symbol, int perimeter) {
    }

}
