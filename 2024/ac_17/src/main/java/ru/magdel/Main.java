package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

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
        System.out.println("Hello, ac17!");
        String input = Files.readString(Path.of("input.txt"));
        var mapInput = input.replace("\r", "");

        var gamesInput = input.split(": ");
        var progInput = gamesInput[1];
        var prog = progInput.split(",");
        var initState = new State(61657405, 0, 0, 0);

        System.out.println("ProgInput=" + progInput);
        System.out.print("Prog=");
        Stream.of(prog).forEach(v -> System.out.print(v));
        System.out.println();


        var state = initState;
        while (isPointerAllowed(prog, state)) {
            var command = prog[state.ip];
            switch (command) {
                case "0":
                    long combo0 = getComboOp(prog, state);
                    long val0 = state.A / (1 << combo0);
                    state = new State(val0, state.B, state.C, state.ip + 2);
                    break;
                case "1":
                    var lit1 = Long.parseLong(prog[state.ip + 1]);
                    var val1 = (state.B ^ lit1);
                    state = new State(state.A, val1, state.C, state.ip + 2);
                    break;
                case "2":
                    var combo2 = getComboOp(prog, state);
                    var val2 = combo2 & 0b111;
                    state = new State(state.A, val2, state.C, state.ip + 2);
                    break;
                case "3":
                    if (state.A != 0) {
                        var lit3 = Integer.parseInt(prog[state.ip + 1]);
                        state = new State(state.A, state.B, state.C, lit3);
                    } else {
                        state = new State(state.A, state.B, state.C, state.ip + 2);
                    }
                    break;
                case "4":
                    var val4 = (state.B ^ state.C);
                    state = new State(state.A, val4, state.C, state.ip + 2);
                    break;
                case "5":
                    var combo5 = getComboOp(prog, state) & 0b111;
                    System.out.print(combo5);
                    System.out.print(",");
                    state = new State(state.A, state.B, state.C, state.ip + 2);
                    break;
                case "6":
                    long combo6 = getComboOp(prog, state);
                    long val6 = state.A / (1 << combo6);
                    state = new State(state.A, val6, state.C, state.ip + 2);
                    break;
                case "7":
                    long combo7 = getComboOp(prog, state);
                    long val7 = state.A / (1 << combo7);
                    state = new State(state.A, state.B, val7, state.ip + 2);
                    break;
            }
            //System.out.println(state);
        }

        System.out.println();
        System.out.println();
        System.out.println("END");
        System.out.println(state);


    }

    private static long getComboOp(String[] prog, State state) {
        var comboCode = prog[state.ip + 1];
        return switch (comboCode) {
            case "0", "1", "2", "3" -> Long.parseLong(comboCode);
            case "4" -> state.A;
            case "5" -> state.B;
            case "6" -> state.C;
            default -> throw new RuntimeException();
        };
    }

    private static boolean isPointerAllowed(String[] prog, State state) {
        return state.ip < prog.length;
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
        if (isPathEnd(nextDirectCell)) {
            //сделать лучшей, если лучше
            if (bestPathes.isEmpty()) {
                var bestPath = (ArrayList<Cell>) path.clone();
                bestPathes.add(bestPath);
                fillCostMap(bestPath);
                System.out.println(bestPath.getLast());
            } else {
                var lastBestCell = bestPathes.get(0).getLast();
                if (lastBestCell.accCost == nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    System.out.println(bestPath.getLast());
                    path.removeLast();
                } else if (lastBestCell.accCost > nextDirectCell.accCost) {
                    path.add(nextDirectCell);
                    var bestPath = (ArrayList<Cell>) path.clone();
                    bestPathes.clear();
                    bestPathes.add(bestPath);
                    fillCostMap(bestPath);
                    System.out.println(bestPath.getLast());
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

    private static Cell nextLeftCell(Cell lastCell, char[][] map, ArrayList<Cell> path) {
        if (lastCell.rotateLeft == 0) {
            return null;
        }
        int dir = lastCell.dir + 1;
        dir = dir % 4;
        var nextLeftCell = new Cell(lastCell.x, lastCell.y, lastCell.move, dir, lastCell.rot + 1, lastCell.accCost + 1000, lastCell.rotateLeft - 1);
        return nextLeftCell;
    }

    private static Cell nextRightCell(Cell lastCell, char[][] map, ArrayList<Cell> path) {
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

    record State(long A, long B, long C, int ip) {

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
