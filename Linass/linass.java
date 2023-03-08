package Linass;

import java.util.Random;

public class linass {
    private class isTurn {
        
        public static boolean Up(int[][] arr, int y, int x) {
            int xMax = (arr[0].length - 1);
            if ((y - 1 < 0) || (x - 2 < 0) || (x + 2 > xMax))
                return false;
            try {
                if ((arr[y - 1][x - 1] == 1) || (arr[y - 1][x + 1] == 1) || (arr[y - 2][x] == 1)) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public static boolean Down(int[][] arr, int y, int x) {
            int yMax = (arr.length - 1);
            if (y + 1 > yMax)
                return false;
            if (arr[y + 1][x] == 1)
                return false;
            if (x - 1 >= 0) {
                if (arr[y + 1][x - 1] == 1)
                    return false;
            }
            if (y + 2 <= yMax) {
                if (arr[y + 2][x] == 1)
                    return false;
            }
            return true;
        }

        public static boolean Left(int[][] arr, int y, int x) {
            int yMax = (arr.length - 1);
            if (x - 1 < 0)
                return false;
            if (arr[y][x - 1] == 1)
                return false;
            if (x - 2 >= 0) {
                if (arr[y][x - 2] == 1)
                    return false;
            }
            if (y - 1 >= 0 && x - 1 >= 0) {
                if (arr[y - 1][x - 1] == 1)
                    return false;
            }
            if (y + 2 > yMax)
                return false;
            return true;
        }

        public static boolean Right(int[][] arr, int y, int x) {
            int xMax = (arr[0].length - 1);
            if (x + 1 > xMax)
                return false;
            if (arr[y][x + 1] == 1)
                return false;
            if (y - 1 >= 0) {
                if (arr[y - 1][x + 1] == 1)
                    return false;
            }
            if (x + 2 <= xMax) {
                if (arr[y][x + 2] == 1)
                    return false;
            }
            return true;
        }
    }

    public static void main(String[] arg) {
        int rows = 10;
        int columns = 10;
        int[][] maze = new int[rows][columns];
        maze[0][0] = 1;
        maze[9][9] = 2;
        maze = elderway(maze, 0, 0);
        for (int[] line : maze) {
            for (int el : line) {
                if (el == 1)
                    System.out.print(" " + "*");
                else
                    System.out.print(" " + el);
            }
            System.out.println();
        }
    }

    public static int[][] elderway(int[][] a, int b, int c) {
        int[][] arr = a;
        int yMax = (arr.length - 1);
        int xMax = (arr[0].length - 1);
        int y = b;
        int x = c;
        while (true) {
            System.out.println(y + "|" + x);
            switch (Direction.fork()) {
                case up:
                    if (!isTurn.Up(arr, y, x))
                        break;
                    y--;
                    arr[y][x] = 1;
                    break;

                case down:
                    if (!isTurn.Down(arr, y, x))
                        break;
                    y++;
                    arr[y][x] = 1;
                    break;

                case left:
                    if (!isTurn.Left(arr, y, x))
                        break;
                    x--;
                    arr[y][x] = 1;
                    break;

                case right:
                    if (!isTurn.Right(arr, y, x))
                        break;
                    x++;
                    arr[y][x] = 1;
                    break;
            }
            if (arr[yMax][xMax] == 1) {
                return arr;
            }
            for (int[] line : arr) {
                for (int el : line) {
                    if (el == 1)
                        System.out.print(" " + "*");
                    else
                        System.out.print(" " + el);
                }
                System.out.println();
            }
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
