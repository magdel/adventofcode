package ru.magdel;

import java.util.ArrayList;

public class Main2 {

    static String input =
            """
                    654329854329876510123231016987212010510218565101234510343
                    789218765010945678754122987878900123424309654345679623490
                    543201698541232789669003456987210874535698701234388701581
                    650165467432871694578712012076321965549785698323897632672
                    569870346501960543209873453125409876678012587610432543543
                    678761250123451632114562164534012761237703476526501105412
                    879610167245893101023470079643443450349812345457432236903
                    965673298436734345652981188712565621056789400348901247894
                    434789567823321238761892199801874872341019511289187656785
                    323458478910210679850743056765923985432678521898096105016
                    012367320154301589012656145678910676563565430787145234127
                    121032110263469874322347238765876545612346541256230873298
                    678545021378954365411038389054985434501487632344321964343
                    569956985489565243506789872123010123676598701015423459652
                    654877676671278152105698143232131010989687772356910598701
                    123468964510189067834521032143328325678567887447827654323
                    003457853031656508921432541089419834509413996530988921013
                    012346322142347210560210654076508543210302105421243219034
                    329855412456898723874345789123467658723213489439850108123
                    410765001387129634987606932345358169654654398701763237654
                    567892187298034545674517801776549015456765217652304565456
                    103011296106234589503623410887232102367896900343215694347
                    234520345787105678212896556992185601298787871298344787298
                    309639876496221218923287647881096520109696678987650120156
                    018743212345430307832100238941087412368543567676543210347
                    329651008758745476542321107632101309454322430125210321298
                    478010129669876589401498210543291298765011321212345696212
                    566723234554745654320567641874980787012780876503856787101
                    345814656765634765210198532965673236323498903454945671012
                    210905549876589894321983449452360145324567612367834589323
                    356876632365565923015892158301450765410212143256521985434
                    547893421245674310234765067201221896784301032103410676965
                    432012560634789212105604300104334985895212349874306565876
                    101765676543434301789812215419455854326710458213215678945
                    569858987612343890176543216328766733210824567303764549432
                    478945890502345789265674307435432145601939665432876732981
                    323832781981256776328989438976965032789348778901905891070
                    210501632870125865410676521089874321011657345877814560565
                    343412541065434934541541078789289010150069256766923471234
                    454323401298743327601232369632103210541178129845812984321
                    565216543345652418796543454543432325652298087234701005630
                    074307232387651009187452330346501478743367896100565216787
                    189898101997843218012341021257562569895456545321074323896
                    234798001876960107345987012368876521080303434538989010143
                    145667010165456256296456543879989437871212343245698763230
                    021054323234387340187898534987654306965105650156787654321
                    430032104101298712345657659654789215456016781235673212452
                    545145235310132101076740348743690104312167098767654101963
                    696236996232145098889821289634587431203458129868910017876
                    587347887145096567986798678723876520104239234776521149887
                    678956981076787057872107509018987011015145445689432234796
                    454965438987789142963456410123070187676076323432011005645
                    567870127695654231254784321212165296589889916501123010434
                    458765476554514340345697654301254345432127807867834121125
                    389654389453001234434398941210345452345076321958985034016
                    210323430302112965525289030367676321456985430843476985107
                    323014321210227871012100123458983210167890121652105876898
                    """;

    static String inputTest =
            """
                    0123
                    1234
                    8765
                    9876
                    """;


    public static void main(String[] args) {
        System.out.println("Hello, ac10!");
        var linesArray = input.split("\n");
        int[][] map = new int[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                map[y][x] = Integer.parseInt("" + linesArray[y].charAt(x));
            }
        }
        System.out.println("Map size, x=" + map[0].length + ":y=" + map.length);

        // need to scan every cell for start and then make deep search

        int[][] trailHeadScores = new int[linesArray.length][linesArray[0].length()];
        for (int x = 0; x < map[0].length; x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[y][x] != 0) {
                    continue;
                }
                boolean[][] reachedHeights = new boolean[linesArray.length][linesArray[0].length()];
                var path = new ArrayList<Cell>();
                path.add(new Cell(x, y, map[y][x]));
                var rate = deepSearchMapForScores(path, map, reachedHeights, 0);
                //var score = getReachedHeightsCount(reachedHeights);
                trailHeadScores[y][x] = rate;
            }
        }

        System.out.println();
        int resultCount = getScoresSums(trailHeadScores);


        System.out.println("Scores sum: " + resultCount);


    }

    private static int getScoresSums(int[][] trailHeadScores) {
        int resultCount = 0;
        for (int y = 0; y < trailHeadScores.length; y++) {
            for (int x = 0; x < trailHeadScores[0].length; x++) {
                //System.out.print(reachedHeights[y][x] ? 'X' : '.');
                resultCount += trailHeadScores[y][x];
            }
            //System.out.println();
        }
        return resultCount;
    }

    private static int getReachedHeightsCount(boolean[][] reachedHeights) {
        int resultCount = 0;
        for (int y = 0; y < reachedHeights.length; y++) {
            for (int x = 0; x < reachedHeights[0].length; x++) {
                //System.out.print(reachedHeights[y][x] ? 'X' : '.');
                if (reachedHeights[y][x]) {
                    resultCount++;
                }
            }
            //System.out.println();
        }
        return resultCount;
    }

    private static int deepSearchMapForScores(ArrayList<Cell> path, int[][] map, boolean[][] reachedHeights, int rate) {
        if (!isCorrectSlope(path)) {
            return rate;
        }
        var lastCell = path.get(path.size() - 1);
        if (path.size() ==10 && lastCell.symbol == 9) {
            reachedHeights[lastCell.y][lastCell.x] = true;
            return rate+1;
        }
        //берем ячейки вокруг и проверяем, подходят ли они нам.
        //если подходят - переходим на нее
        if (lastCell.x > 0) {
            Cell cell = new Cell(lastCell.x - 1, lastCell.y, map[lastCell.y][lastCell.x - 1]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                   rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.x < map[0].length - 1) {
            Cell cell = new Cell(lastCell.x + 1, lastCell.y, map[lastCell.y][lastCell.x + 1]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.y > 0) {
            Cell cell = new Cell(lastCell.x, lastCell.y - 1, map[lastCell.y - 1][lastCell.x]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        if (lastCell.y < map.length - 1) {
            Cell cell = new Cell(lastCell.x, lastCell.y + 1, map[lastCell.y + 1][lastCell.x]);
            if (notInPath(cell, path)) {
                path.add(cell);
                if (isCorrectSlope(path)) {
                    rate= deepSearchMapForScores(path, map, reachedHeights, rate);
                }
                path.removeLast();
            }
        }
        return rate;
    }

    private static boolean notInPath(Cell cell, ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++)
            if (path.get(i).x == cell.x && path.get(i).y == cell.y) {
                return false;
            }
        return true;
    }

    private static boolean isCorrectSlope(ArrayList<Cell> path) {
        for (int i = 0; i < path.size(); i++)
            if (path.get(i).symbol != i) {
                return false;
            }
        return true;
    }

    record Cell(int x, int y, int symbol) {
    }

}
