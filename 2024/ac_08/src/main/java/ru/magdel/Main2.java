package ru.magdel;

import java.util.ArrayList;

public class Main2 {

    static String input =
            """
                    .....E........................s.......n.........g.
                    .............................c............4.......
                    ........................................4.........
                    ....................................5.....e.......
                    ...............p...........c......................
                    ....h................s...................e........
                    h..................1...........s..Ke............C.
                    .......................1...............g.....KC...
                    ........8.......B.....p..kc................K..e.X.
                    ...b.........pI...k..................r.........C.X
                    ...........................5.n............R.r.....
                    j......Z....tApE..............c....5..g.X.........
                    ............E..L......5............X..............
                    b...................D...................K.....R..4
                    ..k..D.....h..A...........L.1.....................
                    .j...........h......B.......A.....................
                    .........I......b..................4.......r....0.
                    .................B.n..........G...................
                    ..........9.I...............U...................2.
                    .........Doy........s...............U....R........
                    ..........................G.....V............R....
                    ...z.o.......I..E....t.....G..n....3..............
                    .Z.........Aj..................W.......M.U........
                    ..Z......k......O....W.....U........M.......0.....
                    .....z......o.O..........a....ZG..................
                    ........L..........Y............a.................
                    ......D8t...S.......WO............................
                    ......1P..........WO.9..F.w........Q..d....0......
                    ..........y............................x..........
                    ............z..........w.........J................
                    .o...t..P.........w..B......F....v........x....2..
                    y..8...........v.......M.................x.......2
                    .....y..........z..N...H.......6........a.........
                    ....N.S............H...................a..........
                    N........S..........v........m....................
                    ......8...........H........7x....6.l..............
                    .............q.P...............w..m...............
                    .....S......................7.6.......T...........
                    ...............................0....3.6....J......
                    ...N..........v.................m.......3.l.J.....
                    ...........................F..d....7.3............
                    ...............u..................................
                    .V....Y..u..........H.......J.............T.......
                    .......V...q...................d..fF.............T
                    ..u................................f.....T......l.
                    ..................................i...............
                    ...Y......M.........................7.............
                    ............Y...........9............f2..m..Q.....
                    .....................i.9........fd.......l....Q...
                    V.........q................i......................
                    """;

    static String inputTest =
            """
                    ............
                    ........0...
                    .....0......
                    .......0....
                    ....0.......
                    ......A.....
                    ............
                    ............
                    ........A...
                    .........A..
                    ............
                    ............
                    """;


    public static void main(String[] args) {
        System.out.println("Hello, ac08!");
        var linesArray = input.split("\n");
        char[][] map = new char[linesArray.length][linesArray[0].length()];
        boolean[][] antinodes = new boolean[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = linesArray[y].charAt(x);
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);
        // need to scan every cell. When find symbol - deep search for other same symbol and placing antinodes for all already found same symbols.

        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] == '.') {
                    continue;
                }
                var foundList = new ArrayList<Cell>();
                foundList.add(new Cell(x, y, map[y][x]));
                deepSearchAntinodesForSymbol(foundList, map, antinodes);
            }
        }

        System.out.println();
        int resultCount = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(antinodes[y][x] ? 'X' : '.');
                if (antinodes[y][x]) {
                    resultCount++;
                }
            }
            System.out.println();
        }


        System.out.println("Antinides: " + resultCount);


    }

    private static void deepSearchAntinodesForSymbol(ArrayList<Cell> foundList, char[][] map, boolean[][] antinodes) {
        char lookFor = foundList.get(0).symbol;
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] != lookFor) {
                    continue;
                }
                if (isPresentInList(x, y, foundList)) {
                    continue;
                }
                //found new same symbol, mark antinodes and go deeper
                foundList.add(new Cell(x, y, map[y][x]));
                markAntinodes(foundList, antinodes);
                deepSearchAntinodesForSymbol(foundList, map, antinodes);
                foundList.removeLast();
            }
        }
    }

    private static void markAntinodes(ArrayList<Cell> foundList, boolean[][] antinodes) {
        for (int a = 0; a < foundList.size() - 1; a++) {
            for (int b = a + 1; b < foundList.size(); b++) {
                var cell1 = foundList.get(a);
                var cell2 = foundList.get(b);
                antinodes[cell1.y][cell1.x] = true;
                antinodes[cell2.y][cell2.x] = true;
                var dx = cell1.x - cell2.x;
                var dy = cell1.y - cell2.y;

                boolean inside = true;
                var vx1 = cell1.x + dx;
                var vy1 = cell1.y + dy;
                while (inside) {
                    if (vx1 >= 0 && vx1 < antinodes[0].length) {
                        if (vy1 >= 0 && vy1 < antinodes.length) {
                            antinodes[vy1][vx1] = true;
                            vx1 = vx1 + dx;
                            vy1 = vy1 + dy;
                            continue;
                        }
                    }
                    inside = false;
                }

                inside = true;
                vx1 = cell2.x - dx;
                vy1 = cell2.y - dy;
                while (inside) {
                    if (vx1 >= 0 && vx1 < antinodes[0].length) {
                        if (vy1 >= 0 && vy1 < antinodes.length) {
                            antinodes[vy1][vx1] = true;
                            vx1 = vx1 - dx;
                            vy1 = vy1 - dy;
                            continue;
                        }
                    }
                    inside = false;
                }
            }
        }
    }

    private static boolean isPresentInList(int x, int y, ArrayList<Cell> foundList) {
        for (int i = 0; i < foundList.size(); i++) {
            if (foundList.get(i).x == x && foundList.get(i).y == y) {
                return true;
            }
        }
        return false;
    }


    record Cell(int x, int y, char symbol) {
    }

}


// not correct, low
