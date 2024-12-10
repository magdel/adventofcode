package ru.magdel;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    private static final char GUARD = '^';
    private static final char OBSTACLE = '#';
    private static final char SPACE = '.';
    private static final char PATH = 'X';

    public static void main(String[] args) {
        String input2 = """
                ....#...................................#...............#.................#.......#...#...........................................
                .........................#.........#....................................#...........................#......##..............##...#.
                .....................................................................#.........................................#..#...............
                .............#........#...............................................................................#......................#....
                ....#.#.................................#......................................#...#..#..........#....#.....#.............#.......
                ....................#.......#..............#.......#.#.............................#......................................#.......
                ..............................................................................................#......#....................#.......
                .........#.#..#...............................#........#.#........................................................#..............#
                ....#...........#.......#.......#....#...#....#..........#..........#......#...#................#.........................#.......
                .......#...........................................................................................#.................#...........#
                ..................#.#....#..............................#....................#.................#....#..........#..........#.......
                ...#...............#...................................................#................#.........................................
                .................#..#.#.............#.#...................................#......................#....#..........#................
                .#..................#........................#..........#...........................#.......#......................#.#............
                ......#...........................................#.....#.....#...........#...........................#..............#............
                .........#...............................#.............................#.#.......................................................#
                ...#.........#.#........................#...#..............................................#...#...............#...#..#...........
                ..............................#.....................................#................#..........................#.........##......
                ..........#...........#.....#......#......#..............................#.....#......................................#...........
                ..........................#....#.........................................................#..................#...#.........#.......
                ......................................#...........#.........#...............................#.............#...............#.......
                .......................#........#.#....#...#...#................................................................#......#..........
                ......................#...............#................#..#.#...........#...#.......................#.........#.........#..#......
                ....#.................#..........#............#....#..............................................................#.#.............
                .........................................................................................................................#...##...
                .....................#.......#................................................##.............#........#........................#..
                ........................#..#.....#.#...............#.......#.................#.............................................#.#....
                ......................................................................................#..#.............................#..........
                ..#........................................................#..........................#..............#.#.....................#....
                ...#...................#............#...#.....#....#..................................#.......................#...................
                ...#...........#..............#............................................................#....#.....#.....................#.....
                ........................#..............#...................#.....#..#.............................................................
                .....#....................#....................##..........#..............#......#...............#..........#.....#..........##...
                ....#.......#..........................................................#....................................#.#..................#
                #......#.#.................................#........................................................................#.........#...
                ...............................#......#......................#....................................................................
                ..............#..........................#....................................#..........#.............#..........................
                .....#........#....#.........................#..#.................#.#.................#...........................................
                .................#..................#.#................................#..#..................................#......#.............
                ...........................#.......................................................#...................#............#.............
                .....................#.........#..#..........................#..................................#........#........................
                ..#................................................#.#.................................#.#..........#.....#......................#
                ..........................................................................#..................................................#.#..
                ..................................#..........................................#......................#.............................
                .......#...............#..............................................#......................................................#....
                ..........................................^....................#..................................................................
                .........#.........#.........................................................................................#......#....#........
                .........#.........#..............................................................................................................
                ........#....#......................#...............#...........#..................#...........................................##.
                ......#............#...........#................................................................#.......#.........................
                #.............................#..............................................................#..............#.....................
                ...................................#...........#...................................................#........#..........#..........
                ....#.....................................................................................................#...........#......#....
                ....................................................#....#...................................#....................................
                ............#........#............................#..........#.............................#.....#..#.#...........................
                ...........#.................................................................#.............#......................................
                ....#.........................................#.#.......#...............#...........................#............##...............
                ..................#..............#...#........................................................................#....#...........#..
                .....................#.....#...#.....#.............#..............#...#...................................#.......................
                .#...#.....#.............................................#...........#....#.......................................................
                .#.....#.............................#................#......................#........................#.........................#.
                ......................#.........................................#.............................................#.................#.
                ...#...#....................#.................................................................#............#....#.......#.........
                ...........#......#...............................#.......#......#........#..........#...........................................#
                ......................#....#..............##...........................................................#..........#...............
                .#......#.....................................................................#..#....#.....................#.....................
                ....##....#...#.........#.............................#.........#...................#.............#....#................#.........
                ..................................................................#...............................#...............................
                ....#.................#.#.........................................................................................................
                ........................#..........................#...........#...................................#.#.......#....................
                ...........#.....................................................................................#...............................#
                ...........................................#..................................#...................................................
                .....#.........#......#..........#.........#.................................................................................#....
                ....#.....................#....#..#.........................................................................#..#..................
                ........................##..#........................#...#.........#.................#......................#..#....#.............
                .............#................#.......#........#............................................................#......#..............
                #....#................................#.............#..........#........#.....#...#..........##.....................#.............
                ......#...#......#..........................................................................................#....#..#.........#...
                #.................#..............##....#........................#....#...........................................#..........#.#...
                ........................#.#..#........................................#.............................................##............
                ..........#.........#.........................................................................................##..................
                ....#....#...........#.................#......................#........................#...#....................................#.
                .......#..................................#..................#...................#...............................#.....#.....#....
                #................................#...........#................##...#............#.................................................
                .............................#...#....................................#...................................#...#..........#...#....
                .#......#.........#...#.......#.................##....#..................................................#...#....................
                ...........#........................................................................................................#.............
                ......................................#......#..........#.....#.....................#...................#.#.................#.....
                .....................................#............#................................................................##.............
                .......................................#....................#...........................................##..#.....................
                ..........................#.................................................................................#....................#
                ...........................................................#............#.........#..#....#.#............#.....#..................
                ....#.....................................#...........................................#........#.....................#............
                .............#.............#................#..........................#......................#..........................#........
                .........#.................#......#............#....#.#.............................#.......##......................#.#.......#...
                ..............................................................................................................#...................
                #...#...............................................#...................#........................#...............#................
                .......#...#.......#..#..#..#..........#..........................................................................................
                ......#.##....................#......#........#............#................#.............#...................#............#......
                .....#..#......#.......................#..............................#...............................#...........................
                .#.................................#......#..............................................#........................................
                ....#...........................#..#........#...............#..#.......................................#......##.#.........#......
                ..................................#..................#......................#.....................#.......#.....................#.
                .........#....................#....#...............................................#...................................#..........
                .........................................................#...#...............##..#...............................................#
                .#..#.............#.............#....................................#....................#.....#..#..........................#...
                .................#.........................#..#........................................#.................................#........
                .....................#....#................................#......#...............................................................
                ...............#..........................#...........#.............................................................#.........#...
                ...........##.......#................#..............................#..........................#....##.....#...................#..
                ......................#........#........#.........#..................................................##..#........................
                ....#........................#......................#.#..................#....#..................#.....................#..........
                .................#......#...#...................................#...............#....#.........#............................#.....
                #.................#...............................#......#..#..............................................###...#.......#........
                ..........#...........#..........................................................................................................#
                .......................#......................................#.................#.......#........#................................
                ..#.......#..#.............................................................................................................#......
                #.........#......#........................#.#..............................................#...................##.................
                ...#....#.....................................#...................................................................................
                .....................#.....................................................................#........................#.....#.......
                .....#..#...............................................................##......#.#......#..#.#.......#........................#..
                .......#..........#....................................#.........#.................#.................#.....................#......
                ..............##.................................................#................#.........#.#............#........#............#
                .....................#.............#....................#...#.................#.........#........#................................
                .............#...........................................#........................................................................
                .....#...#.............#..............#..............#..................................................................#......#..
                .....##...................................##.....#.....#..#.........#...............#..........#.....#............#.#...........#.
                ....#..................#.........#....................#....................#...#....#..............##............#..............#.
                ..................#..........#.#.....................................#....#..............#.......#.....#...............#......#..#
                ..............#.........#.............................................................................................#...........
                """;
        String input1 =
                "....#.....\n" +
                        ".........#\n" +
                        "..........\n" +
                        "..#.......\n" +
                        ".......#..\n" +
                        "..........\n" +
                        ".#..^.....\n" +
                        "........#.\n" +
                        "#.........\n" +
                        "......#...";
        var input = input2;
        var lines = input.split("\n");
        var colms = lines[0];
        char[][] map = new char[colms.length()][lines.length];
        for (int i = 0; i < lines.length; i++) {
            var line = lines[i];
            for (int j = 0; j < line.length(); j++) {
                map[j][i] = line.charAt(j);
            }
        }

        int distinctPositions = countDistinctPositions(map);
        System.out.println(distinctPositions);
    }

    private static int countDistinctPositions(char[][] map) {
        //find start
        int x = -1, y = -1, dir = 0; // starting position and direction

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[j][i] == GUARD) {
                    x = j;
                    y = i;
                    map[j][i] = SPACE;
                    break;
                }
            }
        }

        int rows = map.length;
        int cols = map[0].length;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {-1, 0, 1, 0};
        boolean[][] visited = new boolean[rows][cols];


        //make visited route to check over
        List<Cell> path = passRoute(map, visited, x, y, dx, dir, dy);
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (visited[j][i]) {
                    count++;
                }
            }
        }
        System.out.println("path: " + count + " pl:" + path.size());

        var obstacleWorked = new boolean[rows][cols];
        for (int i = 1; i < path.size(); i++) {
            var attemptVisited = new boolean[rows][cols];
            var c = path.get(i);
            map[c.x][c.y] = OBSTACLE;
            if (!attemptPassRoute(map, attemptVisited, x, y, dx, dir, dy)) {
                obstacleWorked[c.y][c.x] = true;
            }
            map[c.x][c.y] = SPACE;
        }
/*
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (x == i && j == y) {
                    continue;
                }
                if (visited[i][j]) {
                    map[i][j]=OBSTACLE;
                    if (attemptPassRoute(map, attemptVisited, x, y, dx, dir, dy)){
                        attemptVisited[i][j]=true;
                    }
                    map[i][j]=SPACE;
                }
            }
        }
*/

        count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (obstacleWorked[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private static List<Cell> passRoute(char[][] map, boolean[][] visited, int x, int y, int[] dx, int dir, int[] dy) {
        List<Cell> path = new ArrayList<>();
        while (true) {
            visited[x][y] = true;
            path.add(new Cell(x, y));
            var px = x + dx[dir];
            var py = y + dy[dir];
            if (px < 0 || px >= map.length) {
                break;
            }
            if (py < 0 || py >= map[x].length) {
                break;
            }
            if (map[px][py] == OBSTACLE) {
                dir = (dir + 1) % 4; // turn right
            } else {
                x += dx[dir];
                y += dy[dir];
            }
        }
        return path;
    }

    private static boolean attemptPassRoute(char[][] map, boolean[][] visited, int x, int y, int[] dx, int dir, int[] dy) {
        long checkLimit = 1000000;
        while (true) {
            visited[x][y] = true;
            var px = x + dx[dir];
            var py = y + dy[dir];
            if (px < 0 || px >= map.length) {
                break;
            }
            if (py < 0 || py >= map[x].length) {
                break;
            }
            if (map[px][py] == OBSTACLE) {
                dir = (dir + 1) % 4; // turn right
            } else {
                x += dx[dir];
                y += dy[dir];
            }
            checkLimit--;
            if (checkLimit < 0) {
                return false;
            }
        }
        return true;
    }

    record Cell(int x, int y) {
    }
}

//857 too low