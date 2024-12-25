package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        System.out.println("Hello, ac16!");
        String input = Files.readString(Path.of("input.txt"));
        var mapInput = input.replace("\r", "");

        var mapLinesArray = mapInput.split("\n");
        char[][] map = new char[mapLinesArray.length - 2][mapLinesArray[0].length() - 2];
        costMap = new long[mapLinesArray.length - 2][mapLinesArray[0].length() - 2];
        for (int x = 0; x < costMap[0].length; x++) {
            for (int y = 0; y < costMap.length; y++) {
                costMap[y][x] = Long.MAX_VALUE / 2;
            }
        }

        Robot robot = null;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = mapLinesArray[y + 1].charAt(x + 1);
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

        //471 too low
        //503 wrong?
        var path = new ArrayList<Cell>();
        path.add(new Cell(robot.x, robot.y, 0, 0, 0, 0, MAX_ROTATE_COUNT));
        //printMap(map, robot, path);
        //printCostMap(map, robot, path);

        deepSearchMapForEnd(path, map);

        printMapPathes(map, null, bestPathes);
        //printMap(map, null, bestPathes.get(0));

        Set<Robot> uniqPos = new HashSet<>();
        for (var bestPath : bestPathes) {
            for (var cell : bestPath) {
                uniqPos.add(new Robot(cell.x, cell.y));
            }
        }
        System.out.println("Result=" + bestPathes.get(0).getLast() + ", time=" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("pathes=" + bestPathes.size());
        System.out.println("calcBestTiles=" + uniqPos.size());

        path = new ArrayList<Cell>();
        path.add(new Cell(robot.x, robot.y, 0, 0, 0, 0, MAX_ROTATE_COUNT));
        bestPathes.clear();
        /*for (int x = 0; x < costMap[0].length; x++) {
            for (int y = 0; y < costMap.length; y++) {
                costMap[y][x] = Long.MAX_VALUE / 2;
            }
        }*/
        deepSearchMapForEnd(path, map);

        //for (var bestPath : bestPathes) {
        //    printMap(map, null, bestPath);
        //}
        //printMap(map, null, bestPathes.get(0));
        printMapPathes(map, null, bestPathes);
        //printMap(map, null, bestPathes.get(1));

        System.out.println();
        System.out.println("Result=" + bestPathes.get(0).getLast() + ", time=" + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("pathes=" + bestPathes.size());

        //System.out.println("calcBestTiles=" + calcBestTiles());
        uniqPos.clear();
        for (var bestPath : bestPathes) {
            for (var cell : bestPath) {
                uniqPos.add(new Robot(cell.x, cell.y));
            }
        }
        System.out.println("calcBestTiles=" + uniqPos.size());


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
            //if (!isInPath(nextDirectCell.x, nextDirectCell.y, path)) {
            if (!worsePathCost(nextDirectCell)) {
                if (path.size() > 1) {
                    var prevCellInPath = path.get(path.size() - 2);
                   // if (prevCellInPath.move < nextDirectCell.move) {
                        costMap[prevCellInPath.y][prevCellInPath.x] = prevCellInPath.accCost;
                    //}
                }
                //costMap[nextDirectCell.y][nextDirectCell.x] = nextDirectCell.accCost;
                processNextCell(path, map, nextDirectCell);
            }
            // }
        }
        //if no, turn left
        Cell nextLeftCell = nextLeftCell(lastCell);
        if (nextLeftCell != null) {
           /* if (!worsePathCost(nextLeftCell)) {
                costMap[nextDirectCell.y][nextDirectCell.x] = nextLeftCell.accCost;
                processNextCell(path, map, nextLeftCell);
            }*/
            processNextCell(path, map, nextLeftCell);
        }
        //or right
        Cell nextRightCell = nextRightCell(lastCell);
        if (nextRightCell != null) {
            /*if (!worsePathCost(nextRightCell)) {
                costMap[nextRightCell.y][nextRightCell.x] = nextRightCell.accCost;
                processNextCell(path, map, nextRightCell);
            }*/
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

    private static boolean worsePathCost(Cell nextDirectCell) {
        /*if (costMap[nextDirectCell.y][nextDirectCell.x] == 0) {
            return false;
        }*/
        if (costMap[nextDirectCell.y][nextDirectCell.x] < nextDirectCell.accCost) {
            return true;
        }
        return false;
    }

    private static void processNextCell(ArrayList<Cell> path, char[][] map, Cell nextDirectCell) {
        if (isPathEnd(nextDirectCell)) {
            //сделать лучшей, если лучше
            if (bestPathes.isEmpty()) {
                path.add(nextDirectCell);
                var bestPath = new ArrayList<>(path);
                bestPathes.add(bestPath);
                //fillCostMap(bestPath);
                path.removeLast();
                //System.out.println("First:" + bestPath.getLast());
            } else {
                var lastBestCell = bestPathes.getLast().getLast();
                if (lastBestCell.accCost == nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = new ArrayList<>(path);
                    bestPathes.add(bestPath);
                    //fillCostMap(bestPath);
                    //System.out.println(bestPath.getLast());
                    path.removeLast();
                } else if (lastBestCell.accCost > nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = new ArrayList<>(path);
                    bestPathes.clear();
                    bestPathes.add(bestPath);
                    //fillCostMap(bestPath);
                    //System.out.println(bestPath.getLast());
                    path.removeLast();
                }
            }
            return;
        }
        path.add(nextDirectCell);
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

    private static Cell nextLeftCell(Cell lastCell) {
        if (lastCell.rotateLeft == 0) {
            return null;
        }
        int dir = lastCell.dir + 1;
        dir = dir % 4;
        var nextLeftCell = new Cell(lastCell.x, lastCell.y, lastCell.move, dir, lastCell.rot + 1, lastCell.accCost + 1000, lastCell.rotateLeft - 1);
        return nextLeftCell;
    }

    private static Cell nextRightCell(Cell lastCell) {
        if (lastCell.rotateLeft == 0) {
            return null;
        }
        int dir = lastCell.dir - 1;
        dir += 4;
        dir = dir % 4;
        var nextRightCell = new Cell(lastCell.x, lastCell.y, lastCell.move, dir, lastCell.rot + 1, lastCell.accCost + 1000, lastCell.rotateLeft - 1);
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
                    System.out.print('O');
                } else if ((robot != null) && (robot.x == x && robot.y == y)) {
                    System.out.print('@');
                } else {
                    System.out.print(map[y][x]);
                }
            }
        }
    }

    private static void printMapPathes(char[][] map, Robot robot, List<ArrayList<Cell>> pathes) {
        System.out.println();
        for (int y = 0; y < map.length; y++) {
            System.out.println();
            for (int x = 0; x < map[0].length; x++) {
                if (isInPathes(x, y, pathes)) {
                    System.out.print('O');
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

    private static boolean isInPath(int x, int y, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++) {
            Cell cell = path.get(i);
            if (cell.x == x && cell.y == y) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInPathes(int x, int y, List<ArrayList<Cell>> pathes) {
        for (int j = 0; j < pathes.size(); j++) {
            var path = pathes.get(j);
            for (int i = 0; i < path.size(); i++) {
                Cell cell = path.get(i);
                if (cell.x == x && cell.y == y) {
                    return true;
                }
            }
        }
        return false;
    }

    record Robot(int x, int y) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Robot robot = (Robot) o;
            return x == robot.x && y == robot.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

    //dir - 0 -east, 1 - north, 2- west, 3 - south
    record Cell(int x, int y, int move, int dir, int rot, long accCost, int rotateLeft) {
    }

    record Cost(long minCost) {

    }

}
