import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class linass {
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
                if (mazeArray[y + 2][x] == 1)
                    return false;
                if (mazeArray[y][x - 3] == 1 && mazeArray[y + 1][x - 3] == 1)
                    return false;
            } catch (Exception e) {
                return true;
            }
            return true;
        }

        public boolean Left() {
            if (x - 1 < 0 || ((x == xMax) || (y + 1 == yMax)) || y + 2 > yMax || y - 2 < 0)
                return true;
            try {
                if (mazeArray[y - 1][x - 1] == 1)
                    return false;
                if (mazeArray[y + 1][x - 1] == 1)
                    return false;
                if (mazeArray[y][x - 2] == 1)
                    return false;
                if (mazeArray[y - 3][x] == 1)
                    return false;
                if ((mazeArray[y][x - 3] == 1 && mazeArray[y - 1][x - 3] == 1)
                        || (mazeArray[y][x - 3] == 1 && mazeArray[y - 2][x - 3] == 1)) {
                    return false;
                }
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

    public static ArrayList<Integer> elderY = new ArrayList<>();
    public static ArrayList<Integer> elderX = new ArrayList<>();

    private static class Branch{
        private int[][] mazeCore;

        public Branch (int[][] a){
        this.mazeCore = a;
        }
        private static int[] getBranchPos() {
            Random rnd = new Random();
            int pos = rnd.nextInt(elderX.size());
            int[] coord = {elderY.get(pos), elderX.get(pos)};
            return coord;
        }


    }

    public static void main(String[] arg) throws IOException { // delete after debug "throws IOException"
        System.out.println(java.time.Clock.systemUTC().instant());
        PrintWriter result = new PrintWriter(new FileWriter("log.txt"));
        int[][] maze;
        int rows = 10;
        int columns = 10;
        while (true) {
            maze = new int[rows][columns];
            maze[0][0] = 1;
            maze[9][9] = 2;
            maze = createCore(maze, 0, 0);
            if (maze[0][0] == 20) {
                maze = new int[rows][columns];
                maze = createCore(maze, 0, 0);
            } else
                break;
        }

        for (int[] line : maze) {
            for (int i : line) {
                if (i == 1)
                    result.print(" " + "'");
                else
                    result.print(" " + "H");
            }
            result.println();
        }
        result.close();
        System.out.println(java.time.Clock.systemUTC().instant());
    }

    private static int[][] createCore(int[][] Array, int yPoint, int xPoint) {
        int yMax = (Array.length - 1);
        int xMax = (Array[0].length - 1);
        int n = 0;
        while (true) {
            isTurn verifyTurn = new isTurn(Array, yPoint, xPoint);
            if (Array[yMax][xMax] == 1) {
                return Array;
            }
            switch (Direction.fork()) {
                case up:
                    if (!verifyTurn.Up())
                        break;
                    yPoint--;
                    break;

                case down:
                    if (!verifyTurn.Down())
                        break;
                    yPoint++;
                    break;

                case left:
                    if (!verifyTurn.Left())
                        break;
                    break;

                case right:
                    if (!verifyTurn.Right())
                        break;
                    xPoint++;
                    break;
            }
            Array[yPoint][xPoint] = 1;
            elderY.add(yPoint);
            elderX.add(xPoint);
            if (n > 30 * xMax) {
                Array[0][0] = 20;
                return Array;
            }
            n++;
        }
    }

    private static enum Direction {
        up, left, right, down;

        private static final Random rnd = new Random();

        public static Direction fork() {
            Direction[] fork = values();
            return fork[rnd.nextInt(fork.length)];
        }
    }

}
