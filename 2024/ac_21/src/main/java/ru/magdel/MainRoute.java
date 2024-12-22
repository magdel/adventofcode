package ru.magdel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainRoute {

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
        System.out.println("Hello, ac18!");

        char[][] map = new char[4][3];
        costMap = new long[4][3];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = EMPTY;
            }
        }
        map[3][0] = WALL;
        Robot robot = null;
        robot = new Robot(0, 0);
        var path = new ArrayList<Cell>();
        printMap(map, robot, path);
        finalPos = new Robot(2, 3);

        long startTime = System.currentTimeMillis();

        path.add(new Cell(robot.x, robot.y, 0, 0, 0, 0, MAX_ROTATE_COUNT));
        //printMap(map, robot, path);
        //printCostMap(map, robot, path);

        costMap = new long[4][3];
        bestPathes.clear();
        deepSearchMapForEnd(path, map);

        List<ArrayList<Cell>> bestPathesOnlyMove = clearRotation(bestPathes);
        List<ArrayList<Cell>> shortPathes = shortestPathes(bestPathesOnlyMove);

        path.add(new Cell(robot.x, robot.y, 0, 3, 0, 0, MAX_ROTATE_COUNT));
        //printMap(map, robot, path);
        //printCostMap(map, robot, path);

        costMap = new long[4][3];
        bestPathes.clear();
        deepSearchMapForEnd(path, map);

        List<ArrayList<Cell>> bestPathesOnlyMove3 = clearRotation(bestPathes);
        List<ArrayList<Cell>> shortPathes3 = shortestPathes(bestPathesOnlyMove3);

        long coordSum = 0;

        printMap(map, null, bestPathes.get(0));

        System.out.println();
//        System.out.println("Result=" + bestPathes.get(0).getLast() + ", time=" + (System.currentTimeMillis() - startTime) + "ms");
        //    System.out.println("pathes=" + bestPathes.size());
        //System.out.println("calcBestTiles=" + calcBestTiles());


    }

    private static List<ArrayList<Cell>> shortestPathes(List<ArrayList<Cell>> pathes) {
        pathes.sort(Comparator.comparing(ArrayList::size));
        var shortSize = pathes.get(0).size();
        List<ArrayList<Cell>> result = new ArrayList<>();
        for (var path : pathes) {
            if (path.size() != shortSize) {
                break;
            }
            result.add(path);
        }
        return result;
    }

    private static List<ArrayList<Cell>> clearRotation(List<ArrayList<Cell>> bestPathes) {
        List<ArrayList<Cell>> result = new ArrayList<>();
        for (int i = 0; i < bestPathes.size(); i++) {
            var path = bestPathes.get(i);
            ArrayList<Cell> clearedPath = new ArrayList<>();
            int prevMove = path.get(0).move;
            for (int j = 1; j < path.size(); j++) {
                if (path.get(j).move != prevMove) {
                    prevMove = path.get(j).move;
                    clearedPath.add(path.get(j));
                }
            }
            result.add(clearedPath);
        }
        return result;
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
            //if (isBetterPathCost(nextDirectCell)) {
            //     costMap[nextDirectCell.y][nextDirectCell.x] = nextDirectCell.accCost;
            processNextCell(path, map, nextDirectCell);
            //}
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
        if (costMap[nextDirectCell.y][nextDirectCell.x] >= nextDirectCell.accCost) {
            return true;
        }
        return false;
    }

    private static void processNextCell(ArrayList<Cell> path, char[][] map, Cell nextDirectCell) {
        if (isInPathExceptLast(nextDirectCell.x, nextDirectCell.y, path)) {
            return;
        }
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
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    //System.out.println(bestPath.getLast());
                    path.removeLast();
                } else if (lastBestCell.accCost > nextDirectCell.accCost) {
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.clear();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    // System.out.println(bestPath.getLast());
                    path.removeLast();
                }
            }
            return;
        }
        deepSearchMapForEnd(path, map);
        path.removeLast();
    }

    private static boolean isOutMap(ArrayList<Cell> path, char[][] map) {
        return false;
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

    private static boolean isInPathExceptLast(int x, int y, ArrayList<Cell> path) {
        for (int i = 0; i < path.size()-1; i++) {
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
