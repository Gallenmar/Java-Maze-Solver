import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class linass {

    private static class isTurn {
        private int[][] base;
        private int y;
        private int x;
        private int xMax;
        private int yMax;

        public isTurn(int[][] arr, int y, int x) {
            this.base = arr.clone();
            this.y = y;
            this.x = x;
            this.yMax = (arr.length - 1);
            this.xMax = (arr[0].length - 1);
        }

        public boolean Up() {
            if (y - 1 < 0 || (y == yMax && x - 1 == xMax) || x + 2 > xMax || x - 2 < 0)
                return false;
            try {
                if (base[y - 1][x - 1] == 1 || base[y - 1][x + 1] == 1 || base[y - 2][x] == 1
                        || (base[y - 1][x - 2] == 1) && (base[y - 1][x + 2] == 1))
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
                if (base[y + 1][x - 1] == 1 || base[y + 1][x + 1] == 1 || base[y + 2][x] == 1
                        || base[y][x - 3] == 1 && base[y + 1][x - 3] == 1)
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
                if (base[y - 1][x - 1] == 1 || base[y + 1][x - 1] == 1 || base[y][x - 2] == 1)
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
                if (base[y - 1][x + 1] == 1 || base[y + 1][x + 1] == 1 || base[y][x + 2] == 1 || base[y][x + 3] == 1)
                    return false;
            } catch (Exception e) {
                return true;
            }
            return true;
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
        
        for (int[] line : maze) {
            for (int i : line) {
                if (i == 1)
                    result.print(" " + "'");
                else
                    result.print(" " + "H");
            }
            result.println();
        }
        result.println();
        result.println();

        Random rand = new Random();
        for( int i = 1 ; i < maze.length - 1 ; i ++){
            for( int j = maze[i].length - 2; j > 1; j-- ){
                if(rand.nextInt(4) == 1){
                    if(maze[i - 1][j - 1] == 0 && maze[i - 1][j + 1] == 0 && maze[i + 1][j + 1] == 0 && maze[i + 1][j - 1] == 0 ){
                        maze[i][j] = 1;
                    }
                }
            }
        }

        for( int i = 1; i + 1 < maze.length; i ++){
            for( int j = 1; j + 1 < maze[i].length; j++ ){
                if(maze[i - 1][j - 1] == 1 && maze[i - 1][j + 1] == 0 && maze[i + 1][j + 1] == 0 && maze[i + 1][j - 1] == 0 ){
                    maze[i][j] = 1;
                }
                if(maze[i - 1][j - 1] == 0 && maze[i - 1][j + 1] == 1 && maze[i + 1][j + 1] == 0 && maze[i + 1][j - 1] == 0 ){
                    maze[i][j] = 1;
                }
                if(maze[i - 1][j - 1] == 0 && maze[i - 1][j + 1] == 0 && maze[i + 1][j + 1] == 1 && maze[i + 1][j - 1] == 0 ){
                    maze[i][j] = 1;
                }
                if(maze[i - 1][j - 1] == 0 && maze[i - 1][j + 1] == 0 && maze[i + 1][j + 1] == 0 && maze[i + 1][j - 1] == 1 ){
                    maze[i][j] = 1;
                }
            }
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

    private static int[][] createCore(int[][] Array, int y, int x) {
        int yMax = (Array.length - 1);
        int xMax = (Array[0].length - 1);
        while (true) {
            if (Array[yMax][xMax] == 1) {
                return Array;
            }
            boolean left = true, right = true, up = true, down = true, nextMove = false;
            while (!nextMove) {
                if ((!left && !right && !down && !up)) {
                    Array[0][0] = 20;
                    return Array;
                }
                isTurn verifyTurn = new isTurn(Array, y, x);
                switch (Direction.fork()) {
                    case up:
                        if (!verifyTurn.Up()) {
                            up = false;
                            break;
                        } else {
                            y--;
                            Array[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case down:
                        if (!verifyTurn.Down()) {
                            down = false;
                            break;
                        } else {
                            y++;
                            Array[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case left:
                        if (!verifyTurn.Left()) {
                            left = false;
                            break;
                        } else {
                            x--;
                            Array[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case right:
                        if (!verifyTurn.Right()) {
                            right = false;
                            break;
                        } else {
                            x++;
                            Array[y][x] = 1;
                            nextMove = true;
                        }
                        break;
                }

            }
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
