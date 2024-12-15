package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    private static final char EMPTY = '.';
    private static final char BOX = 'O';
    private static final char WALL = '#';
    private static final int COST_A = 3;
    private static final int COST_B = 1;
    public static final int MAX_BUTTON_PRESS_COUNT = 100;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac15!");
        String input = Files.readString(Path.of("input.txt"));
        var gamesInput = input.replace("\r", "").split("\n\n");
        var mapInput = gamesInput[0];
        var moveInput = gamesInput[1];

        var mapLinesArray = mapInput.split("\n");
        char[][] map = new char[mapLinesArray.length - 2][mapLinesArray[0].length() - 2];
        Robot robot = null;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = mapLinesArray[y + 1].charAt(x + 1);
                if (map[y][x] == '@') {
                    robot = new Robot(x, y);
                    map[y][x] = EMPTY;
                }
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);
        System.out.println("Robot " + robot);

        printMap(map, robot);

        System.out.println();
        System.out.println("Moving");

        for (int i = 0; i < moveInput.length(); i++) {
            char move = moveInput.charAt(i);
            robot = tryMove(map, move, robot);
            //printMap(map, robot);
            //System.out.println();
        }

        long coordSum = 0;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == BOX) {
                    coordSum += (y + 1) * 100 + (x + 1);
                }
            }
        }

        System.out.println(coordSum);
    }

    private static void printMap(char[][] map, Robot robot) {
        for (int y = 0; y < map.length; y++) {
            System.out.println();
            for (int x = 0; x < map[0].length; x++) {
                if (robot.x == x && robot.y == y) {
                    System.out.print('@');
                } else {
                    System.out.print(map[y][x]);
                }
            }
        }
    }

    private static Robot tryMove(char[][] map, char move, Robot robot) {
        switch (move) {
            case '>': {
                //найти первую свободную ячейку справа
                Cell freeCell = null;
                for (int x = robot.x + 1; x < map[0].length; x++) {
                    if (map[robot.y][x] == WALL) {
                        break;
                    }
                    if (map[robot.y][x] == EMPTY) {
                        freeCell = new Cell(x, robot.y);
                        break;
                    }
                }
                if (freeCell != null) {
                    //move everything to the right till this cell
                    for (int x = freeCell.x; x > robot.x; x--) {
                        map[robot.y][x] = map[robot.y][x - 1];
                    }
                    robot = new Robot(robot.x + 1, robot.y);
                }
                break;
            } //move end
            case '<': {
                //найти первую свободную ячейку справа
                Cell freeCell = null;
                for (int x = robot.x - 1; x >= 0; x--) {
                    if (map[robot.y][x] == WALL) {
                        break;
                    }
                    if (map[robot.y][x] == EMPTY) {
                        freeCell = new Cell(x, robot.y);
                        break;
                    }
                }
                if (freeCell != null) {
                    //move everything to the right till this cell
                    for (int x = freeCell.x; x < robot.x; x++) {
                        map[robot.y][x] = map[robot.y][x + 1];
                    }
                    robot = new Robot(robot.x - 1, robot.y);
                }
                break;
            } //move end
            case 'v': {
                //найти первую свободную ячейку справа
                Cell freeCell = null;
                for (int y = robot.y + 1; y < map.length; y++) {
                    if (map[y][robot.x] == WALL) {
                        break;
                    }
                    if (map[y][robot.x] == EMPTY) {
                        freeCell = new Cell(robot.x, y);
                        break;
                    }
                }
                if (freeCell != null) {
                    //move everything to the right till this cell
                    for (int y = freeCell.y; y > robot.y; y--) {
                        map[y][robot.x] = map[y - 1][robot.x];
                    }
                    robot = new Robot(robot.x, robot.y + 1);
                }
                break;
            } //move end
            case '^': {
                //найти первую свободную ячейку справа
                Cell freeCell = null;
                for (int y = robot.y - 1; y >= 0; y--) {
                    if (map[y][robot.x] == WALL) {
                        break;
                    }
                    if (map[y][robot.x] == EMPTY) {
                        freeCell = new Cell(robot.x, y);
                        break;
                    }
                }
                if (freeCell != null) {
                    //move everything to the top till this cell
                    for (int y = freeCell.y; y < robot.y; y++) {
                        map[y][robot.x] = map[y + 1][robot.x];
                    }
                    robot = new Robot(robot.x, robot.y - 1);
                }
                break;
            } //move end
        }

        return robot;
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


    record Robot(int x, int y) {
    }

    record Cell(int x, int y) {
    }

    record Press(int a, int b, int ac, int bc, long costA, long costB, long accX, long accY) {
        public long price() {
            return costA + costB;
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
