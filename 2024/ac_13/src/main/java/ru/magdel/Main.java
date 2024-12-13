package ru.magdel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final char EMPTY = '0';
    private static final int COST_A = 3;
    private static final int COST_B = 1;
    public static final int MAX_BUTTON_PRESS_COUNT = 100;

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, ac12!");
        String input = Files.readString(Path.of("inputTest2.txt"));
        var gamesInput = input.replace("\r", "").split("\n\n");
        System.out.println("games, count=" + gamesInput.length);
        ArrayList<Game> games = new ArrayList<>();
        for (String gamesI : gamesInput) {
            var inputRows = gamesI.split("\n");
            var a = inputRows[0].split("X+");
            var butAx1 = a[1].split(",");
            var butAx = butAx1[0];
            var butAy = butAx1[1].split("Y+")[1];

            var b = inputRows[1].split("X+");
            var butBx1 = b[1].split(",");
            var butBx = butBx1[0];
            var butBy = butBx1[1].split("Y+")[1];

            var p = inputRows[2].split("X=");
            var pBx1 = p[1].split(",");
            var pBx = pBx1[0];
            var pBy = pBx1[1].split("Y=")[1];

            games.add(new Game(Integer.parseInt(butAx), Integer.parseInt(butAy),
                    Integer.parseInt(butBx), Integer.parseInt(butBy),
                    Integer.parseInt(pBx), Integer.parseInt(pBy)));
        }

        System.out.println("Games=" + games);

        long totalTokens = 0;
        for (Game game : games) {
            //findSolution
            Press best = findMinimumTokens(game, new ArrayList<>());
            if (best != null) {
                totalTokens += best.price();
                System.out.println("Best: " + game.best);
            } else{
                System.out.println("Best impossible " + game);
            }
        }

        System.out.println();
        System.out.println("Total: " + totalTokens);
    }

    private static Press findMinimumTokens(Game game, ArrayList<Press> solution) {
        if (isTooMuchTries(solution)) {
            return null;
        }
        if (isSumTooMuch(game, solution)) {
            return null;
        }
        if (isFoundSolution(game, solution)) {
            game.updateBestSolution(solution);
            return solution.getLast();
        }

        Press bestA = null;
        if (solution.size() == 0) {
            solution.add(new Press(1, 0, 1, 0, COST_A, 0, game.ax, game.ay));
            bestA = findMinimumTokens(game, solution);
            solution.removeLast();
        } else {
            var lastSolution = solution.getLast();
            solution.add(new Press(1, 0, lastSolution.ac + 1, lastSolution.bc, lastSolution.costA + COST_A, lastSolution.costB,
                    lastSolution.accX + game.ax, lastSolution.accY + game.ay));
            bestA = findMinimumTokens(game, solution);
            solution.removeLast();
        }
        if (bestA!= null) return bestA;

        Press bestB = null;
        if (solution.size() == 0) {
            solution.add(new Press(0, 1, 0, 1, 0, COST_B, game.bx, game.by));
            bestB = findMinimumTokens(game, solution);
            solution.removeLast();
        } else {
            var lastSolution = solution.getLast();
            solution.add(new Press(0, 1, lastSolution.ac, lastSolution.bc + 1, lastSolution.costA, lastSolution.costB + COST_B,
                    lastSolution.accX + game.bx, lastSolution.accY + game.by));
            bestB = findMinimumTokens(game, solution);
            solution.removeLast();
        }
        if (bestB!= null) return bestB;


        return null;
    }

    private static boolean isFoundSolution(Game game, ArrayList<Press> solution) {
        if (solution.size() == 0) {
            return false;
        }
        var lastSolution = solution.getLast();
        if (lastSolution.accX != game.x) {
            return false;
        }
        if (lastSolution.accY != game.y) {
            return false;
        }
        return true;
    }

    private static boolean isSumTooMuch(Game game, ArrayList<Press> solution) {
        if (solution.size() == 0) {
            return false;
        }
        var lastSolution = solution.getLast();
        if (lastSolution.accX > game.x) {
            return true;
        }
        if (lastSolution.accY > game.y) {
            return true;
        }
        if (game.best != null) {
            if (game.best.price() < lastSolution.price()) {
                return true;
            }
        }
        long difX = game.x - lastSolution.accX;
        long difY = game.y - lastSolution.accY;
        long minXaC = difX / game.ax;
        long minYaC = difY / game.ay;
        long minAC = minXaC<minYaC? minXaC: minYaC;
        long minXbC = difX / game.bx;
        long minYbC = difY / game.by;
        long minBC = minXbC<minYbC? minXbC: minYbC;

        if (lastSolution.ac + minAC > MAX_BUTTON_PRESS_COUNT) {
            return true;
        }
        if (lastSolution.bc + minBC > MAX_BUTTON_PRESS_COUNT) {
            return true;
        }


        return false;
    }

    private static boolean isTooMuchTries(ArrayList<Press> solution) {
        if (solution.size() == 0) {
            return false;
        }
        var lastSolution = solution.getLast();
        if (lastSolution.ac > MAX_BUTTON_PRESS_COUNT) {
            return true;
        }
        if (lastSolution.bc > MAX_BUTTON_PRESS_COUNT) {
            return true;
        }
        return false;
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

    record Press(int a, int b, int ac, int bc, long costA, long costB, long accX, long accY) {
        public long price() {
            return costA + costB;
        }
    }

    static final class Game {
        private final int ax;
        private final int ay;
        private final int bx;
        private final int by;
        private final int x;
        private final int y;
        private Press best;

        Game(int ax, int ay, int bx, int by, int x, int y) {
            this.ax = ax;
            this.ay = ay;
            this.bx = bx;
            this.by = by;
            this.x = x;
            this.y = y;
        }

        public int ax() {
            return ax;
        }

        public int ay() {
            return ay;
        }

        public int bx() {
            return bx;
        }

        public int by() {
            return by;
        }

        public int x() {
            return x;
        }

        public int y() {
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
