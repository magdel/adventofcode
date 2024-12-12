package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main2 {

    private static final char EMPTY = '0';
    private static int SIDE_LEFT = 0b1;
    private static int SIDE_UP = 0b10;
    private static int SIDE_RIGHT = 0b100;
    private static int SIDE_DOWN = 0b1000;

    private static int CLEAR_SIDE_LEFT = ~SIDE_LEFT;
    private static int CLEAR_SIDE_UP = ~SIDE_UP;
    private static int CLEAR_SIDE_RIGHT = ~SIDE_RIGHT;
    private static int CLEAR_SIDE_DOWN = ~SIDE_DOWN;

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
        long result = getTotalSum(areas, map);


        System.out.println("Total: " + result);


    }

    private static void paintAreaUseless(ArrayList<Cell> area, char[][] map) {
        for (int i = 0; i < area.size(); i++) {
            Cell cell = area.get(i);
            map[cell.y][cell.x] = EMPTY;
        }
    }

    private static long getTotalSum(List<List<Cell>> areas, char[][] map) {
        long sum = 0;
        for (int i = 0; i < areas.size(); i++) {
            List<Cell> area = areas.get(i);
            long areaSquare = getAreaSquare(area);
            long areaSides = getAreaSides(area, map);
            System.out.println("Area " + area.get(0).symbol + " sq=" + areaSquare + ", sides=" + areaSides);

            sum += areaSquare * areaSides;
        }
        return sum;
    }

    private static long getAreaSides(List<Cell> area, char[][] map) {
        for (int i = 0; i < area.size(); i++) {
            Cell cell = area.get(i);
            if (cell.perimeter > 0) {
                if ((cell.perimeter & SIDE_LEFT) != 0) {
                    //go up and down by left side continuesly and calc solid left side length
                    var x = cell.x;
                    var y = cell.y;
                    var nextCell = cell;
                    while ((nextCell.perimeter & SIDE_LEFT) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_LEFT;
                        }
                        y = y + 1;
                        if (y > map.length - 1) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                    x = cell.x;
                    y = cell.y;
                    nextCell = cell;
                    while ((nextCell.perimeter & SIDE_LEFT) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_LEFT;
                        }
                        y = y - 1;
                        if (y < 0) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                }

                if ((cell.perimeter & SIDE_RIGHT) != 0) {
                    //go up and down by left side continuesly and calc solid left side length
                    var x = cell.x;
                    var y = cell.y;
                    var nextCell = cell;
                    while ((nextCell.perimeter & SIDE_RIGHT) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_RIGHT;
                        }
                        y = y + 1;
                        if (y > map.length - 1) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                    x = cell.x;
                    y = cell.y;
                    nextCell = cell;
                    while ((nextCell.perimeter & SIDE_RIGHT) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_RIGHT;
                        }
                        y = y - 1;
                        if (y < 0) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                }

                if ((cell.perimeter & SIDE_UP) != 0) {
                    //go up and down by left side continuesly and calc solid left side length
                    var x = cell.x;
                    var y = cell.y;
                    var nextCell = cell;
                    while ((nextCell.perimeter & SIDE_UP) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_UP;
                        }
                        x = x + 1;
                        if (x > map[0].length - 1) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                    x = cell.x;
                    y = cell.y;
                    nextCell = cell;
                    while ((nextCell.perimeter & SIDE_UP) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_UP;
                        }
                        x = x - 1;
                        if (x < 0) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                }
                if ((cell.perimeter & SIDE_DOWN) != 0) {
                    //go up and down by left side continuesly and calc solid left side length
                    var x = cell.x;
                    var y = cell.y;
                    var nextCell = cell;
                    while ((nextCell.perimeter & SIDE_DOWN) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_DOWN;
                        }
                        x = x + 1;
                        if (x > map[0].length - 1) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                    x = cell.x;
                    y = cell.y;
                    nextCell = cell;
                    while ((nextCell.perimeter & SIDE_DOWN) != 0) {
                        if (nextCell != cell) {
                            nextCell.perimeter = nextCell.perimeter & CLEAR_SIDE_DOWN;
                        }
                        x = x - 1;
                        if (x < 0) {
                            break;
                        }
                        if (!isInArea(x, y, area)) {
                            break;
                        }
                        nextCell = findInArea(x, y, area);
                    }
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < area.size(); i++) {
            Cell cell = area.get(i);
            if (cell.perimeter != 0) {
                if ((cell.perimeter & SIDE_LEFT) != 0) {
                    sum++;
                }
                if ((cell.perimeter & SIDE_UP) != 0) {
                    sum++;
                }
                if ((cell.perimeter & SIDE_RIGHT) != 0) {
                    sum++;
                }
                if ((cell.perimeter & SIDE_DOWN) != 0) {
                    sum++;
                }
            }
        }


        return sum;
    }

    private static long getAreaSquare(List<Cell> area) {
        return area.size();
    }

    private static boolean isInAreas(List<List<Cell>> areas, int x, int y) {
        for (int i = 0; i < areas.size(); i++) {
            List<Cell> area = areas.get(i);
            if (isInArea(x, y, area)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInArea(int x, int y, List<Cell> area) {
        for (int j = 0; j < area.size(); j++) {
            Cell cell = area.get(j);
            if (cell.x == x && cell.y == y) {
                return true;
            }
        }
        return false;
    }

    private static Cell findInArea(int x, int y, List<Cell> area) {
        for (int j = 0; j < area.size(); j++) {
            Cell cell = area.get(j);
            if (cell.x == x && cell.y == y) {
                return cell;
            }
        }
        return null;
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
                perimeter = perimeter | SIDE_LEFT;
            }
        } else {
            perimeter = perimeter | SIDE_LEFT;
        }
        if (x < map[0].length - 1) {
            char s = map[y][x + 1];
            if (s != symbol) {
                perimeter = perimeter | SIDE_RIGHT;
            }
        } else {
            perimeter = perimeter | SIDE_RIGHT;
        }
        if (y > 0) {
            char s = map[y - 1][x];
            if (s != symbol) {
                perimeter = perimeter | SIDE_UP;
            }
        } else {
            perimeter = perimeter | SIDE_UP;
        }
        if (y < map.length - 1) {
            char s = map[y + 1][x];
            if (s != symbol) {
                perimeter = perimeter | SIDE_DOWN;
            }
        } else {
            perimeter = perimeter | SIDE_DOWN;
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

    static final class Cell {
        private final int x;
        private final int y;
        private final char symbol;
        private int perimeter;

        Cell(int x, int y, char symbol, int perimeter) {
            this.x = x;
            this.y = y;
            this.symbol = symbol;
            this.perimeter = perimeter;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public char symbol() {
            return symbol;
        }

        public int perimeter() {
            return perimeter;
        }


        @Override
        public String toString() {
            return "Cell[" +
                    "x=" + x + ", " +
                    "y=" + y + ", " +
                    "symbol=" + symbol + ", " +
                    "perimeter=" + perimeter + ']';
        }

    }

}
