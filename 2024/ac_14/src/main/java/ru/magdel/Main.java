package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final int WIDTH_T = 101;
    private static final int HEIGHT_T = 103;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac12!");
        String input = Files.readString(Path.of("input.txt"));
        var robotInput = input.replace("\r", "").split("\n");
        System.out.println("robots, count=" + robotInput.length);
        ArrayList<Robot> robots = new ArrayList<>();
        for (String gamesI : robotInput) {
            var pv = gamesI.split(" ");
            var xy = pv[0].split("=")[1];
            var x = xy.split(",")[0];
            var y = xy.split(",")[1];
            var vxvy = pv[1].split("=")[1];
            var vx = vxvy.split(",")[0];
            var vy = vxvy.split(",")[1];


            robots.add(new Robot(
                    Integer.parseInt(x),
                    Integer.parseInt(y),
                    Integer.parseInt(vx),
                    Integer.parseInt(vy)
            ));
        }

        System.out.println("Robots=" + robots);
        int iterations = 17001;

        long minFactor = Long.MAX_VALUE;
        for (int i = 0; i < iterations; i++) {
            char map[][] = new char[HEIGHT_T][WIDTH_T];
            ArrayList<Robot> movedList = new ArrayList<>();
            for (Robot robot : robots) {
                int newx = robot.x + robot.vx;
                int newy = robot.y + robot.vy;
                newx = newx < 0 ? newx + WIDTH_T : newx;
                newy = newy < 0 ? newy + HEIGHT_T : newy;
                newx = newx >= WIDTH_T ? newx - WIDTH_T : newx;
                newy = newy >= HEIGHT_T ? newy - HEIGHT_T : newy;

                var movedRobot = new Robot(
                        newx,
                        newy,
                        robot.vx, robot.vy
                );
                movedList.add(movedRobot);
                map[movedRobot.y][movedRobot.x] = 'X';
            }
            robots = movedList;
            long c1 = 0, c2 = 0, c3 = 0, c4 = 0;

            for (Robot robot : robots) {
                if (robot.x < WIDTH_T / 2 && robot.y < HEIGHT_T / 2) {
                    c1++;
                }
                if (robot.x > WIDTH_T / 2 && robot.y < HEIGHT_T / 2) {
                    c2++;
                }
                if (robot.x > WIDTH_T / 2 && robot.y > HEIGHT_T / 2) {
                    c3++;
                }
                if (robot.x < WIDTH_T / 2 && robot.y > HEIGHT_T / 2) {
                    c4++;
                }
            }
            long newMinFactor = c1 * c2 * c3 * c4;
            if (newMinFactor < minFactor) {
                System.out.println("I=" + i);
                for (int y = 0; y < HEIGHT_T; y++) {
                    for (int x = 0; x < WIDTH_T; x++) {
                        System.out.print(map[y][x] == 'X' ? 'X' : ' ');
                    }
                    System.out.println();
                }
                minFactor = newMinFactor;
            }
        }
        System.out.println("Robots=" + robots);


        System.out.println();
        //System.out.println("Total: " + (c1*c2*c3*c4));
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

    record Press(int a, int b, int ac, int bc, long costA, long costB, long accX, long accY) {
        public long price() {
            return costA + costB;
        }
    }

    record Robot(int x, int y, int vx, int vy) {
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
