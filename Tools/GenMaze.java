package Tools;
//package dip107;
import java.util.Random;

public class GenMaze {
    private static class IsTurn {
        private int[][] base;
        private int y;
        private int x;
        private int xMax;
        private int yMax;

        public IsTurn(int[][] arr, int y, int x) {
            this.base = arr.clone();
            this.y = y;
            this.x = x;
            this.yMax = (arr.length - 1);
            this.xMax = (arr[0].length - 1);
        }

        public boolean Up() {
            try {
                if (y - 1 < 0 || (y == yMax && x - 1 == xMax) || x + 2 > xMax || x - 2 < 0)
                    return false;

                if (base[y - 1][x - 1] == 1 || base[y - 1][x + 1] == 1 || base[y - 2][x] == 1
                        || (base[y - 1][x - 2] == 1) && (base[y - 1][x + 2] == 1))
                    return false;

            } catch (IndexOutOfBoundsException e) {
                return true;
            }
            return true;
        }

        public boolean Down() {
            try {
                if (y + 1 > yMax)
                    return false;

                if (base[y + 1][x - 1] == 1 || base[y + 1][x + 1] == 1 || base[y + 2][x] == 1
                        || base[y][x - 3] == 1 && base[y + 1][x - 3] == 1)
                    return false;

            } catch (IndexOutOfBoundsException e) {
                return true;
            }
            return true;
        }

        public boolean Left() {
            try {
                if (x - 1 < 0 || ((x == xMax) && (y + 1 == yMax)) || y + 2 > yMax || y - 2 < 0)
                    return false;

                if (base[y - 1][x - 1] == 1 || base[y + 1][x - 1] == 1 || base[y][x - 2] == 1)
                    return false;

            } catch (IndexOutOfBoundsException e) {
                return true;
            }
            return true;
        }

        public boolean Right() {
            try {
                if (x + 1 > xMax)
                    return false;

                if (base[y - 1][x + 1] == 1 || base[y + 1][x + 1] == 1 || base[y][x + 2] == 1 || base[y][x + 3] == 1)
                    return false;

            } catch (IndexOutOfBoundsException e) {
                return true;
            }
            return true;
        }
    }

    public static int[][] deapthFisrt(int[] dims) {
        final long startTime = System.nanoTime();
        int[][] maze;
        while (true) {
            maze = new int[dims[0]][dims[1]];
            maze[0][0] = 1;
            maze = createCore(maze, 0, 0);
            if (maze[0][0] == 20) {
                maze = new int[dims[0]][dims[1]];
                maze = createCore(maze, 0, 0);
            } else
                break;
        }

        maze = expandCore(maze);

        for(int[] line : maze){
            for(int i : line) {
                if (i == 1) {
                    i = 0;
                } else {
                    i = 1;
                }
            }
        }

        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time + "ms.");
        return maze;
    }

    private static int[][] expandCore(int[][] core) {
        Random rand = new Random();
        for (int i = 1; i < core.length - 1; i++) {
            for (int j = core[i].length - 2; j > 1; j--) {
                if (rand.nextInt(4) == 1) {
                    if (core[i - 1][j - 1] == 0 && core[i - 1][j + 1] == 0 && core[i + 1][j + 1] == 0
                            && core[i + 1][j - 1] == 0) {
                        core[i][j] = 1;
                    }
                }
            }
        }

        for (int i = 1; i + 1 < core.length; i++) {
            for (int j = 1; j + 1 < core[i].length; j++) {
                if (core[i - 1][j - 1] == 1 && core[i - 1][j + 1] == 0 && core[i + 1][j + 1] == 0
                        && core[i + 1][j - 1] == 0) {
                    core[i][j] = 1;
                }

                if (core[i - 1][j - 1] == 0 && core[i - 1][j + 1] == 1 && core[i + 1][j + 1] == 0
                        && core[i + 1][j - 1] == 0) {
                    core[i][j] = 1;
                }

                if (core[i - 1][j - 1] == 0 && core[i - 1][j + 1] == 0 && core[i + 1][j + 1] == 1
                        && core[i + 1][j - 1] == 0) {
                    core[i][j] = 1;
                }

                if (core[i - 1][j - 1] == 0 && core[i - 1][j + 1] == 0 && core[i + 1][j + 1] == 0
                        && core[i + 1][j - 1] == 1) {
                    core[i][j] = 1;
                }

            }
        }
        return core;
    }

    private static int[][] createCore(int[][] base, int y, int x) {
        int yMax = (base.length - 1);
        int xMax = (base[0].length - 1);
        while (true) {
            if (base[yMax][xMax] == 1) {
                return base;
            }
            boolean left = true, right = true, up = true, down = true, nextMove = false;
            while (!nextMove) {
                if ((!left && !right && !down && !up)) {
                    base[0][0] = 20;
                    return base;
                }
                IsTurn verifyTurn = new IsTurn(base, y, x);
                switch (Direction.fork()) {
                    case up:
                        if (!verifyTurn.Up()) {
                            up = false;
                            break;
                        } else {
                            y--;
                            base[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case down:
                        if (!verifyTurn.Down()) {
                            down = false;
                            break;
                        } else {
                            y++;
                            base[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case left:
                        if (!verifyTurn.Left()) {
                            left = false;
                            break;
                        } else {
                            x--;
                            base[y][x] = 1;
                            nextMove = true;
                        }
                        break;

                    case right:
                        if (!verifyTurn.Right()) {
                            right = false;
                            break;
                        } else {
                            x++;
                            base[y][x] = 1;
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
