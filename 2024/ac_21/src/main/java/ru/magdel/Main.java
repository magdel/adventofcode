package ru.magdel;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    private static final char EMPTY = '.';
    private static final char WALL = '#';
    private static final int COST_A = 3;
    private static final int COST_B = 1;
    public static final int MAX_BUTTON_PRESS_COUNT = 100;
    public static final int MAX_ROTATE_COUNT = 1;

    private static Cost bestCost;
    private static List<ArrayList<Cell>> bestPathes = new ArrayList<>();
    private static Robot finalPos;
    private static long[][] costMap;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac20!");

        Map<ButtonPath, String> arrToNumbuttonPaths = new HashMap<>();
        arrToNumbuttonPaths.put(new ButtonPath('A', 'A'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '0'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '3'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '2'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '1'), "^<<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '6'), "^^A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '5'), "^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '4'), "^^<<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '9'), "^^^A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '8'), "^^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('A', '7'), "^^^<<A");

        arrToNumbuttonPaths.put(new ButtonPath('0', 'A'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '0'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '1'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '2'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '3'), "^>A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '4'), "^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '5'), "^^A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '6'), "^^>A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '7'), "^^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '8'), "^^^A");
        arrToNumbuttonPaths.put(new ButtonPath('0', '9'), "^^^>A");

        arrToNumbuttonPaths.put(new ButtonPath('1', 'A'), ">>vA");
        arrToNumbuttonPaths.put(new ButtonPath('1', '0'), ">vA");
        arrToNumbuttonPaths.put(new ButtonPath('1', '1'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '2'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '3'), ">>A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '4'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '5'), "^>A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '6'), "^>>A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '7'), "^^A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '8'), "^^>A");
        arrToNumbuttonPaths.put(new ButtonPath('1', '9'), "^^>>A");

        arrToNumbuttonPaths.put(new ButtonPath('2', 'A'), ">vA");
        arrToNumbuttonPaths.put(new ButtonPath('2', '0'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('2', '1'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '2'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '3'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '4'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '5'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '6'), "^>A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '7'), "^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '8'), "^^A");
        arrToNumbuttonPaths.put(new ButtonPath('2', '9'), "^^>A");

        arrToNumbuttonPaths.put(new ButtonPath('3', 'A'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('3', '0'), "v<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '1'), "<<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '2'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '3'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '4'), "^<<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '5'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '6'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '7'), "^^<<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '8'), "^^<A");
        arrToNumbuttonPaths.put(new ButtonPath('3', '9'), "^^A");

        arrToNumbuttonPaths.put(new ButtonPath('4', 'A'), ">>vvA");
        arrToNumbuttonPaths.put(new ButtonPath('4', '0'), ">vvA");
        arrToNumbuttonPaths.put(new ButtonPath('4', '1'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('4', '2'), "v>A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '3'), "v>>A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '4'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '5'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '6'), ">>A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '7'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '8'), "^>A");
        arrToNumbuttonPaths.put(new ButtonPath('4', '9'), "^>>A");

        arrToNumbuttonPaths.put(new ButtonPath('5', 'A'), ">vvA");
        arrToNumbuttonPaths.put(new ButtonPath('5', '0'), "vvA");
        arrToNumbuttonPaths.put(new ButtonPath('5', '1'), "<vA");
        arrToNumbuttonPaths.put(new ButtonPath('5', '2'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('5', '3'), "v>A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '4'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '5'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '6'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '7'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '8'), "^A");
        arrToNumbuttonPaths.put(new ButtonPath('5', '9'), "^>A");

        arrToNumbuttonPaths.put(new ButtonPath('6', 'A'), "vvA");
        arrToNumbuttonPaths.put(new ButtonPath('6', '0'), "vv<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '1'), "v<<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '2'), "v<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '3'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('6', '4'), "<<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '5'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '6'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '7'), "^<<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '8'), "^<A");
        arrToNumbuttonPaths.put(new ButtonPath('6', '9'), "^A");

        arrToNumbuttonPaths.put(new ButtonPath('7', 'A'), ">>vvvA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '0'), ">vvvA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '1'), "vvA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '2'), ">vvA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '3'), ">>vvA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '4'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '5'), ">vA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '6'), ">>vA");
        arrToNumbuttonPaths.put(new ButtonPath('7', '7'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('7', '8'), ">A");
        arrToNumbuttonPaths.put(new ButtonPath('7', '9'), ">>A");

        arrToNumbuttonPaths.put(new ButtonPath('8', 'A'), ">vvvA");
        arrToNumbuttonPaths.put(new ButtonPath('8', '0'), "vvvA");
        arrToNumbuttonPaths.put(new ButtonPath('8', '1'), "vv<A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '2'), "vvA");
        arrToNumbuttonPaths.put(new ButtonPath('8', '3'), "vv>A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '4'), "v<A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '5'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('8', '6'), "v>A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '7'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '8'), "A");
        arrToNumbuttonPaths.put(new ButtonPath('8', '9'), ">A");

        arrToNumbuttonPaths.put(new ButtonPath('9', 'A'), "vvvA");
        arrToNumbuttonPaths.put(new ButtonPath('9', '0'), "vvv<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '1'), "vv<<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '2'), "vv<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '3'), "vvA");
        arrToNumbuttonPaths.put(new ButtonPath('9', '4'), "v<<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '5'), "v<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '6'), "vA");
        arrToNumbuttonPaths.put(new ButtonPath('9', '7'), "<<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '8'), "<A");
        arrToNumbuttonPaths.put(new ButtonPath('9', '9'), "A");

        Map<ButtonPath, String> arrToArrButtonPaths = new HashMap<>();
        arrToArrButtonPaths.put(new ButtonPath('A', 'A'), "A");
        arrToArrButtonPaths.put(new ButtonPath('A', '^'), "<A");
        arrToArrButtonPaths.put(new ButtonPath('A', '<'), "v<<A");
        arrToArrButtonPaths.put(new ButtonPath('A', 'v'), "v<A");
        arrToArrButtonPaths.put(new ButtonPath('A', '>'), "vA");

        arrToArrButtonPaths.put(new ButtonPath('^', 'A'), ">A");
        arrToArrButtonPaths.put(new ButtonPath('^', '^'), "A");
        arrToArrButtonPaths.put(new ButtonPath('^', '<'), "v<A");
        arrToArrButtonPaths.put(new ButtonPath('^', 'v'), "vA");
        arrToArrButtonPaths.put(new ButtonPath('^', '>'), "v>A");

        arrToArrButtonPaths.put(new ButtonPath('<', 'A'), ">>^A");
        arrToArrButtonPaths.put(new ButtonPath('<', '^'), ">^A");
        arrToArrButtonPaths.put(new ButtonPath('<', '<'), "A");
        arrToArrButtonPaths.put(new ButtonPath('<', 'v'), ">A");
        arrToArrButtonPaths.put(new ButtonPath('<', '>'), ">>A");

        arrToArrButtonPaths.put(new ButtonPath('v', 'A'), ">^A");
        arrToArrButtonPaths.put(new ButtonPath('v', '^'), "^A");
        arrToArrButtonPaths.put(new ButtonPath('v', '<'), "<A");
        arrToArrButtonPaths.put(new ButtonPath('v', 'v'), "A");
        arrToArrButtonPaths.put(new ButtonPath('v', '>'), ">A");

        arrToArrButtonPaths.put(new ButtonPath('>', 'A'), "^A");
        arrToArrButtonPaths.put(new ButtonPath('>', '^'), "^<A");
        arrToArrButtonPaths.put(new ButtonPath('>', '<'), "<<A");
        arrToArrButtonPaths.put(new ButtonPath('>', 'v'), "<A");
        arrToArrButtonPaths.put(new ButtonPath('>', '>'), "A");


        long comp = getComplexity("029A", arrToNumbuttonPaths, arrToArrButtonPaths);
        comp += getComplexity("980A", arrToNumbuttonPaths, arrToArrButtonPaths);
        comp += getComplexity("179A", arrToNumbuttonPaths, arrToArrButtonPaths);
        comp += getComplexity("456A", arrToNumbuttonPaths, arrToArrButtonPaths);
        comp += getComplexity("379A", arrToNumbuttonPaths, arrToArrButtonPaths);

        System.out.println(comp);

        System.exit(0);

        String input = Files.readString(Path.of("input.txt"));
        var mapInput = input.replace("\r", "");

        var mapLinesArray = mapInput.split("\n");
        char[][] map = new char[mapLinesArray.length - 2][mapLinesArray[0].length() - 2];
        Robot robot = null;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = mapLinesArray[y + 1].charAt(x + 1);
                if (map[y][x] == 'S') {
                    robot = new Robot(x, y);
                    map[y][x] = EMPTY;
                }
                if (map[y][x] == 'S') {
                    robot = new Robot(x, y);
                    map[y][x] = EMPTY;
                }
                if (map[y][x] == 'E') {
                    finalPos = new Robot(x, y);
                    map[y][x] = EMPTY;
                }
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);
        System.out.println("Robot " + robot);

        System.out.println();
        System.out.println("Moving");
        long startTime = System.currentTimeMillis();

        long maxCost = findCost(robot, mapLinesArray, map);
        ArrayList<Long> costs = new ArrayList<>();

        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == EMPTY) {
                    continue;
                }
                char[][] newMap = SerializationUtils.clone(map);
                newMap[y][x] = EMPTY;
                long newCost = findCost(robot, mapLinesArray, newMap);
                if (newCost < maxCost) {
                    long diffCost = maxCost - newCost;
                    costs.add(diffCost);
                    //if (diffCost==72){
                    //    printMap(newMap, null, bestPathes.get(0));
                    // }
                }
            }
            System.out.println("X=" + x + " cs=" + costs.size());
        }

        costs.sort(Comparator.reverseOrder());
        long incC = 0;
        long c = 0;
        for (var cost : costs) {
            if (cost >= 100) {
                c++;
            } else {
                break;
            }
        }
        System.out.println("C=" + c);
        System.out.println("incC=" + incC);

        printMap(map, null, bestPathes.get(0));

        System.out.println();
        System.out.println("Result=" + bestPathes.get(0).getLast() + ", time=" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("pathes=" + bestPathes.size());
        System.out.println("pathes s=" + bestPathes.get(0).size());
        System.out.println("pathes cost=" + maxCost);
        //System.out.println("calcBestTiles=" + calcBestTiles());


    }

    private static long getComplexity(String code, Map<ButtonPath, String> arrToNumbuttonPaths, Map<ButtonPath, String> arrToArrButtonPaths) {
        char startC = 'A';
        String path1 = "";
        for (int i = 0; i< code.length(); i++) {
            char next = code.charAt(i);
            path1 += arrToNumbuttonPaths.get(new ButtonPath(startC, next));
            startC = next;
        }

        System.out.println(path1);

        String path2 = "";
        for (int i=0; i<path1.length();i++) {
            char next = path1.charAt(i);
            path2 += arrToArrButtonPaths.get(new ButtonPath(startC, next));
            startC = next;
        }

        System.out.println(path2);

        String path3 = "";
        for (int i=0; i<path2.length();i++) {
            char next = path2.charAt(i);
            path3 += arrToArrButtonPaths.get(new ButtonPath(startC, next));
            startC = next;
        }

        System.out.println(path3);

        int codeNumPart = Integer.parseInt(code.substring(0, code.length()-1));
        int lengthCode = path3.length();
        System.out.println(codeNumPart);
        System.out.println(lengthCode);
        long comp = codeNumPart* lengthCode;
        return comp;
    }

    private static long findCost(Robot robot, String[] mapLinesArray, char[][] map) {
        var path = new ArrayList<Cell>();
        path.add(new Cell(robot.x, robot.y, 0, 0, 0, 0, MAX_ROTATE_COUNT));
        //printMap(map, robot, path);
        bestPathes.clear();
        costMap = new long[mapLinesArray.length - 2][mapLinesArray[0].length() - 2];
        deepSearchMapForEnd(path, map);
        long cost = bestPathes.get(0).getLast().accCost;
        return cost;
    }

    /*private static long calcBestTiles() {
        var bestCost = bestPath.getLast().accCost;
        long count = 0;
        for (int y = 0; y < costMap.length; y++) {
            //System.out.println();
            for (int x = 0; x < costMap[0].length; x++) {
                if (costMap[y][x] == bestCost) {
                    count++;
                }
            }
        }
        return count;
    }*/

    private static void deepSearchMapForEnd(ArrayList<Cell> path, char[][] map) {
        //printMap(map, null, path);
        var lastCell = path.getLast();
        //go direct
        Cell nextDirectCell = nextDirectCell(lastCell, map, path);
        if (nextDirectCell != null) {
            if (isBetterPathCost(nextDirectCell)) {
                costMap[nextDirectCell.y][nextDirectCell.x] = nextDirectCell.accCost;
                processNextCell(path, map, nextDirectCell);
            }
        }
        //if no, turn left
        Cell nextLeftCell = nextLeftCell(lastCell, map, path);
        if (nextLeftCell != null) {
            processNextCell(path, map, nextLeftCell);
        }
        Cell nextRightCell = nextRightCell(lastCell, map, path);
        if (nextRightCell != null) {
            processNextCell(path, map, nextRightCell);
        }
    }

    private static boolean isBetterPathCost(Cell nextDirectCell) {
        if (costMap[nextDirectCell.y][nextDirectCell.x] == 0) {
            return true;
        }
        if (costMap[nextDirectCell.y][nextDirectCell.x] > nextDirectCell.accCost) {
            return true;
        }
        return false;
    }

    private static void processNextCell(ArrayList<Cell> path, char[][] map, Cell nextDirectCell) {
        path.add(nextDirectCell);
        if (isPathEnd(nextDirectCell)) {
            //сделать лучшей, если лучше
            if (bestPathes.isEmpty()) {
                var bestPath = (ArrayList<Cell>) path.clone();
                bestPathes.add(bestPath);
                fillCostMap(bestPath);
                //System.out.println(bestPath.getLast());
            } else {
                var lastBestCell = bestPathes.get(0).getLast();
                if (lastBestCell.accCost == nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    //System.out.println(bestPath.getLast());
                    path.removeLast();
                } else if (lastBestCell.accCost > nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.clear();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    //System.out.println(bestPath.getLast());
                    path.removeLast();
                }
            }
            path.removeLast();
            return;
        }
        deepSearchMapForEnd(path, map);
        path.removeLast();
    }

    private static void fillCostMap(ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            var cell = path.get(i);
            costMap[cell.y][cell.x] = cell.accCost;
        }
    }

    private static boolean isPathEnd(Cell nextDirectCell) {
        return nextDirectCell.x == finalPos.x && nextDirectCell.y == finalPos.y;
    }

    private static Cell nextLeftCell(Cell lastCell, char[][] map, ArrayList<Cell> path) {
        if (lastCell.rotateLeft == 0) {
            return null;
        }
        int dir = lastCell.dir + 1;
        dir = dir % 4;
        var nextLeftCell = new Cell(lastCell.x, lastCell.y, lastCell.move, dir, lastCell.rot + 1, lastCell.accCost + 0, lastCell.rotateLeft - 1);
        return nextLeftCell;
    }

    private static Cell nextRightCell(Cell lastCell, char[][] map, ArrayList<Cell> path) {
        if (lastCell.rotateLeft == 0) {
            return null;
        }
        int dir = lastCell.dir - 1;
        dir += 4;
        dir = dir % 4;
        var nextRightCell = new Cell(lastCell.x, lastCell.y, lastCell.move, dir, lastCell.rot + 1, lastCell.accCost + 0, lastCell.rotateLeft - 1);
        return nextRightCell;
    }


    private static Cell nextDirectCell(Cell lastCell, char[][] map, ArrayList<Cell> path) {
        var nextDirectCell = tryDirectCell(lastCell, map);
        if (nextDirectCell == null) {
            return null;
        }
        if (map[nextDirectCell.y][nextDirectCell.x] != EMPTY) {
            return null;
        }
        if (isInPath(nextDirectCell.x, nextDirectCell.y, path)) {
            return null;
        }
        return nextDirectCell;
    }

    private static Cell tryDirectCell(Cell lastCell, char[][] map) {
        switch (lastCell.dir) {
            case 0:
                if (lastCell.x < map[0].length - 1) {
                    return new Cell(lastCell.x + 1, lastCell.y, lastCell.move + 1, 0, lastCell.rot, lastCell.accCost + 1, MAX_ROTATE_COUNT);
                } else {
                    return null;
                }
            case 1:
                if (lastCell.y > 0) {
                    return new Cell(lastCell.x, lastCell.y - 1, lastCell.move + 1, 1, lastCell.rot, lastCell.accCost + 1, MAX_ROTATE_COUNT);
                } else {
                    return null;
                }
            case 2:
                if (lastCell.x > 0) {
                    return new Cell(lastCell.x - 1, lastCell.y, lastCell.move + 1, 2, lastCell.rot, lastCell.accCost + 1, MAX_ROTATE_COUNT);
                } else {
                    return null;
                }
            case 3:
                if (lastCell.y < map.length - 1) {
                    return new Cell(lastCell.x, lastCell.y + 1, lastCell.move + 1, 3, lastCell.rot, lastCell.accCost + 1, MAX_ROTATE_COUNT);
                } else {
                    return null;
                }
        }

        throw new RuntimeException();
    }

    private static void printMap(char[][] map, Robot robot, ArrayList<Cell> path) {
        System.out.println();
        for (int y = 0; y < map.length; y++) {
            System.out.println();
            for (int x = 0; x < map[0].length; x++) {
                if (isInPath(x, y, path)) {
                    System.out.print('X');
                } else if ((robot != null) && (robot.x == x && robot.y == y)) {
                    System.out.print('@');
                } else {
                    System.out.print(map[y][x]);
                }
            }
        }
    }

    private static void printCostMap(long[][] map, Robot robot, ArrayList<Cell> path) {
        System.out.println();
        for (int y = 0; y < map.length; y++) {
            System.out.println();
            for (int x = 0; x < map[0].length; x++) {
                if (isInPath(x, y, path)) {
                    System.out.print('X');
                } else if ((robot != null) && (robot.x == x && robot.y == y)) {
                    System.out.print('@');
                } else {
                    System.out.print(map[y][x]);
                }
            }
        }
    }


    private static long findMinimumTokens(Game game) {
        long minPrice = Long.MAX_VALUE;
        for (int a = 0; a < MAX_BUTTON_PRESS_COUNT; a++) {
            for (int b = 0; b < MAX_BUTTON_PRESS_COUNT; b++) {
                long x = a * game.ax + b * game.bx;
                long y = a * game.ay + b * game.by;

                if (x == game.x && y == game.y) {
                    var price = a * COST_A + b * COST_B;
                    minPrice = Math.min(minPrice, price);
                }
            }
        }
        return minPrice;
    }

    private static boolean isInPath(int x, int y, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).x == x && path.get(i).y == y) {
                return true;
            }
        }
        return false;
    }

    private static boolean notInPath(Cell cell, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            if (path.get(i).x == cell.x && path.get(i).y == cell.y) {
                return false;
            }
        }
        return true;
    }

    record Robot(int x, int y) {
    }

    //dir - 0 -east, 1 - north, 2- west, 3 - south
    record Cell(int x, int y, int move, int dir, int rot, long accCost, int rotateLeft) {
    }

    record Press(int a, int b, int ac, int bc, long costA, long costB, long accX, long accY) {
        public long price() {
            return costA + costB;
        }
    }

    record Cost(long minCost) {

    }

    record ButtonPath(char from, char to) {

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ButtonPath that = (ButtonPath) o;
            return to == that.to && from == that.from;
        }

        @Override
        public int hashCode() {
            int result = from;
            result = 31 * result + to;
            return result;
        }
    }

    static final class Game {
        private final long ax;
        private final long ay;
        private final long bx;
        private final long by;
        private final long x;
        private final long y;
        private Press best;

        Game(long ax, long ay, long bx, long by, long x, long y) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.x = x;
            this.y = y;
        }

        public long ax() {
            return ax;
        }

        public long ay() {
            return ay;
        }

        public long bx() {
            return bx;
        }

        public long by() {
            return by;
        }

        public long x() {
            return x;
        }

        public long y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            var that = (Game) obj;
            return this.ax == that.ax &&
                    this.ay == that.ay &&
                    this.bx == that.bx &&
                    this.by == that.by &&
                    this.x == that.x &&
                    this.y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ax, ay, bx, by, x, y);
        }

        @Override
        public String toString() {
            return "Game[" +
                    "ax=" + ax + ", " +
                    "ay=" + ay + ", " +
                    "bx=" + bx + ", " +
                    "by=" + by + ", " +
                    "x=" + x + ", " +
                    "y=" + y + ']';
        }

        public void updateBestSolution(ArrayList<Press> solution) {
            var last = solution.getLast();
            if (best == null) {
                best = last;
                return;
            }
            if (best.price() >= last.price()) {
                best = last;
            }
        }
    }

}
