import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.html.HTMLDocument.BlockElement;

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

    private static class Branch {
        public static ArrayList<Integer> coreY = new ArrayList<>();
        public static ArrayList<Integer> coreX = new ArrayList<>();

        private int[][] mazeCore;

        public Branch(int[][] a) {
            this.mazeCore = a;

        }

        public int[][] expandCore() {
            int n = calculateBranching();
            int[][] editedMaze = mazeCore;
            while (n != 0) {
                System.out.println(n);
                int[] position = getBranchPos();
                int y = position[0];
                int x = position[1];
                boolean left = true;
                boolean right = true;
                boolean up = true;
                boolean down = true;
                isTurn verifyTurn = new isTurn(editedMaze, x, y);
                while (up && down && left && right) {
                    switch (Direction.fork()) {
                        case up:
                            if (!verifyTurn.Up()) {
                                up = false;
                                break;
                            }
                            y--;

                            break;

                        case down:
                            if (!verifyTurn.Down()) {
                                down = false;
                                break;
                            }
                            y++;
                            break;

                        case left:
                            if (!verifyTurn.Left()) {
                                left = false;
                                break;
                            }

                            x--;
                            break;

                        case right:
                            if (!verifyTurn.Right()) {
                                right = false;
                                break;
                            }
                            x++;
                            break;
                    }
                    editedMaze[y][x] = 1;
                }
                n--;
            }
            return editedMaze;
        }

        private int calculateBranching() {
            int freeSpace = 0;
            for (int i = 0; i < mazeCore.length; i++) {
                for (int j = 0; j < mazeCore[i].length; j++) {
                    if (mazeCore[i][j] == 0)
                        freeSpace++;
                }
            }
            double branchCount = (mazeCore.length * mazeCore[0].length * 0.25); // may be change
            return (int) branchCount;
        }

        private static int[] getBranchPos() {
            Random rnd = new Random();
            int pos = rnd.nextInt(coreX.size());
            int[] coord = { coreY.get(pos), coreX.get(pos) };
            return coord;
        }

    }

    public static void main(String[] arg) throws IOException { // delete after debug "throws IOException"
        System.out.println(java.time.Clock.systemUTC().instant());
        PrintWriter result = new PrintWriter(new FileWriter("log.txt"));
        int[][] maze;
        int rows = 50;
        int columns = 50;
        while (true) {
            maze = new int[rows][columns];
            maze[0][0] = 1;
            maze = createCore(maze, 0, 0);
            if (maze[0][0] == 20) {
                maze = new int[rows][columns];
                maze = createCore(maze, 0, 0);
            } else
                break;
        }

        //Branch branch = new Branch(maze);

        //maze = branch.expandCore();

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
                    xPoint--;
                    break;

                case right:
                    if (!verifyTurn.Right())
                        break;
                    xPoint++;
                    break;
            }
            Array[yPoint][xPoint] = 1;
            Branch.coreY.add(yPoint);
            Branch.coreX.add(xPoint);
            if (n > Math.pow(1.5 * (xMax + yMax) / 2, 2)) {
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
