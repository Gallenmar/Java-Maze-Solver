import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Linass {
    private static class isTurn {
        private int[][] mazeArray;
        private int y;
        private int x;
        private int xMax;
        private int yMax;

        public isTurn(int[][] arr, int y, int x) {
            this.mazeArray = arr;
            this.y = y;
            this.x = x;
            this.yMax = (arr.length - 1);
            this.xMax = (arr[0].length - 1);
        }

        public boolean Up() {
            if (y - 1 < 0 || (y == yMax && x - 1 == xMax) || x + 2 > xMax || x - 2 < 0)
                return false;
            try {
                if (mazeArray[y - 1][x - 1] == 1)
                    return false;

                if (mazeArray[y - 1][x + 1] == 1)
                    return false;

                if (mazeArray[y - 2][x] == 1)
                    return false;

                if ((mazeArray[y - 1][x - 2] == 1) && (mazeArray[y - 1][x + 2] == 1))
                    return false;

            } catch (Exception e) {
                return true;
            }
            return true;
        }

        public boolean Down() {
            if (y + 1 > yMax)
                return false;
            try {
                if (mazeArray[y + 1][x - 1] == 1)
                    return false;

                if (mazeArray[y + 1][x + 1] == 1)
                    return false;

                if ((mazeArray[y + 1][x - 2] == 1) && (mazeArray[y + 1][x + 2] == 1))
                    return false;

                if (mazeArray[y + 2][x] == 1)
                    return false;

                if (mazeArray[y][x - 3] == 1)
                    return false;

            } catch (Exception e) {
                return true;
            }
            return true;
        }

        public boolean Left() {
            if (x - 1 < 0 || ((x == xMax) && (y + 1 == yMax)) || y + 2 > yMax || y - 2 < 0)
                return false;

            try {
                if (mazeArray[y - 1][x - 1] == 1)
                    return false;

                if (mazeArray[y + 1][x - 1] == 1)
                    return false;

                if (mazeArray[y][x - 2] == 1)
                    return false;

                if (mazeArray[y - 3][x] == 1)
                    return false;

            } catch (Exception e) {
                return true;
            }
            return true;
        }

        public boolean Right() {
            if (x + 1 > xMax)
                return false;

            try {
                if (mazeArray[y - 1][x + 1] == 1)
                    return false;

                if (mazeArray[y + 1][x + 1] == 1)
                    return false;

                if (mazeArray[y][x + 2] == 1)
                    return false;

                if (mazeArray[y][x + 3] == 1)
                    return false;

                if (mazeArray[y - 3][x] == 1)
                    return false;
            } catch (Exception e) {
                return true;
            }
            return true;
        }
    }
    
    public static void main(String[] arg) throws IOException { // delete after debug "throws IOException"
        int rows = 20;
        int columns = 20;
        int n = 0;
        while (n < 100) {
            int[][] maze = new int[rows][columns];
            maze[0][0] = 1;
            maze[9][9] = 2;
            maze = elderway(maze, 0, 0);

            if (maze[0][0] == 20) {
                File log = new File("log.txt");
                String name = "log_" + n + "_failed.txt";
                File rename = new File(name);
                log.renameTo(rename);
            } else {
                File log = new File("log.txt");
                String name = "log_" + n + "_succes.txt";
                File rename = new File(name);
                log.renameTo(rename);
            }
            n++;
        }
    }

    public static int[][] elderway(int[][] Array, int yPoint, int xPoint) throws IOException { // delete after debug
                                                                                               // "throws IOException"
        int yMax = (Array.length - 1);
        int xMax = (Array[0].length - 1);
        PrintWriter log = new PrintWriter(new FileWriter("log.txt"));
        int n = 0;
        while (true) {
            isTurn verifyTurn = new isTurn(Array, yPoint, xPoint);
            System.out.println(yPoint + "|" + xPoint);
            log.println("\tRun " + n + " at [" + yPoint + "][" + xPoint + "]");
            log.println();
            switch (Direction.fork()) {
                case up:
                    if (!verifyTurn.Up())
                        break;
                    yPoint--;
                    Array[yPoint][xPoint] = 1;
                    break;

                case down:
                    if (!verifyTurn.Down())
                        break;
                    yPoint++;
                    Array[yPoint][xPoint] = 1;
                    break;

                case left:
                    if (!verifyTurn.Left())
                        break;
                    xPoint--;
                    Array[yPoint][xPoint] = 1;
                    break;

                case right:
                    if (!verifyTurn.Right())
                        break;
                    xPoint++;
                    Array[yPoint][xPoint] = 1;
                    break;
            }
            if (Array[yMax][xMax] == 1) {
                log.close();
                return Array;
            }
            if (n > 500) {
                log.println("Failure");
                Array[0][0] = 20;
                log.close();
                return Array;
            }

            for (int[] line : Array) {
                for (int el : line) {
                    if (el == 1) {
                        System.out.print(" " + "*");
                        log.print(" " + "'");
                    } else {
                        System.out.print(" " + el);
                        log.print(" " + "H");
                    }
                }
                System.out.println();
                log.println();
            }
            log.println();
            n++;
        }
    }

    private static enum Direction {
        up, down, left, right;

        private static final Random rnd = new Random();

        public static Direction fork() {
            Direction[] fork = values();
            return fork[rnd.nextInt(fork.length)];
        }
    }

}
