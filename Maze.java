import java.util.Scanner;
import java.util.Random;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

class SecondRoute {

    public static int[][] floodFill(int[][] mazeOrig,int row,int col) {
        final long startTime = System.nanoTime();
        // figure out the length of path (x)
        // initialize path
        //int[][] path = new int[x][2];
        int[][] maze = new int[row][col];
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                maze[i][j] = mazeOrig[i][j];
            }
        }
        
        boolean[][] marked = new boolean[row][col];
        LinkedList<Integer> rowList = new LinkedList<Integer>();
        LinkedList<Integer> colList = new LinkedList<Integer>();
        LinkedList<Integer> howFarList = new LinkedList<Integer>();
        int howFar = 1;// 0 and 1 are reserver values
        //                so we start from 2
        row = row-1;
        col = col-1;
        rowList.add(row);
        colList.add(col);
        howFar++;
        howFarList.add(howFar);
        
        int curRow;
        int curCol;
        int curFar;
        // running water thru maze
        while (rowList.size()>0){
            curRow = rowList.poll();
            curCol = colList.poll();
            curFar = howFarList.poll();

            
            if (!(marked[curRow][curCol])){
                maze[curRow][curCol]=curFar;
                marked[curRow][curCol] = true;
                
                // Adding all the neighbors to the queue

                if ((curRow+1)<=row){
                    if (maze[curRow+1][curCol]==0){
                        if (!(marked[curRow+1][curCol])){
                            rowList.add(curRow+1);
                            colList.add(curCol);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curRow-1)>=0){
                    if (maze[curRow-1][curCol]==0){
                        if (!(marked[curRow-1][curCol])){
                            rowList.add(curRow-1);
                            colList.add(curCol);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curCol+1)<=col){
                    if (maze[curRow][curCol+1]==0){
                        if (!(marked[curRow][curCol+1])){
                            rowList.add(curRow);
                            colList.add(curCol+1);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curCol-1)>=0){
                    if (maze[curRow][curCol-1]==0){
                        if (!(marked[curRow][curCol-1])){
                            rowList.add(curRow);
                            colList.add(curCol-1);
                            howFarList.add(curFar+1);

                        }
                    }
                }
            }

        }
        // for(int[] line : maze){
        //     for(int el : line){
        //         System.out.printf("%d\t",el);
        //     }
        //     System.out.println();
        // }

        boolean keepGoing = true;
        int element;
        curRow = 0;
        curCol = 0;
        while (keepGoing){
            element = maze[curRow][curCol];
            if (element==2){
                keepGoing =false;
            }
            rowList.add(curRow);
            colList.add(curCol);
            if ((curRow+1)<=row){
                if (maze[curRow+1][curCol]==(element-1)){
                    curRow++;
                }
            }
            if ((curRow-1)>=0){
                if (maze[curRow-1][curCol]==(element-1)){
                    curRow--;
                }
            }
            if ((curCol+1)<=col){
                if (maze[curRow][curCol+1]==(element-1)){
                    curCol++;
                }
            }
            if ((curCol-1)>=0){
                if (maze[curRow][curCol-1]==(element-1)){
                    curCol--;
                }
            }
        }
        // for(int i = 0; i<rowList.size();i++){
        //     System.out.print(rowList.get(i));
        //     System.out.println(colList.get(i));
        // }

        int[][] path = new int[rowList.size()][2];
        for(int i = 0; i<rowList.size();i++){
            path[i][0]=rowList.get(i);
            path[i][1]=colList.get(i);
        }
        final long endTime = System.nanoTime();
        //Double time = (double)(endTime - startTime)/1000000;  
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return path;
    }
}


class FirstRoute {
    // lists for storing visited positions
    static List<Integer> rowList = new ArrayList<>();
    static List<Integer> colList = new ArrayList<>();
    
    public static int[][] rightSide(int[][] mazeOrig){
        final long startTime = System.nanoTime();
        // copy of the original maze, so that original one won't be modified
        int[][] maze = new int[mazeOrig.length][mazeOrig[0].length];
        for(int i = 0; i<mazeOrig.length; i++){
            for(int j = 0; j<mazeOrig[0].length; j++){
                maze[i][j] = mazeOrig[i][j];
            }
        }

        int x = 0; 
        int y = 0;
        int x_next = 0;
        int y_next = 0;
         
        // starting position   
        rowList.add(x);
        colList.add(y);

    
        while (x != maze.length - 1 || y != maze[0].length - 1) {

            boolean moved = false;
            
            // check if it's possible to move right
            if (y + 1 < maze[0].length && maze[x][y + 1] == 0 && !checkVisited(x, y + 1)) {
                x_next = x;
                y_next = y + 1;
                moved = true;
            // check if it's possible to move down
            } else if (x + 1 < maze.length && maze[x + 1][y] == 0 && !checkVisited(x + 1, y)) {
                x_next = x + 1;
                y_next = y;
                moved = true;
            // check if it's possible to move left
            } else if (y - 1 >= 0 && maze[x][y - 1] == 0 && !checkVisited(x, y - 1)) {
                x_next = x;
                y_next = y - 1;
                moved = true;
            // check if it's possible to move up
            } else if (x - 1 >= 0 && maze[x - 1][y] == 0 && !checkVisited(x - 1, y)) {
                x_next = x - 1;
                y_next = y;
                moved = true;
            }
            // every time we move, we add the position in the lists
            if (moved) {
                rowList.add(x_next);
                colList.add(y_next);
            } else {
                // if we couldn't move, we are at the dead end
                int last = rowList.size() - 1;
                x_next = rowList.get(last - 1);
                y_next = colList.get(last - 1);
                //delete list elements after the last unvisited cell
                rowList.subList(last, rowList.size()).clear();
                colList.subList(last, colList.size()).clear();
            }
            // update position
            x = x_next;
            y = y_next;
            // mark visited cell
            maze[x][y] = 2;
        }
        
        // create path
        int[][] path = new int[rowList.size()][2];
        for(int i = 0; i<rowList.size();i++){
            path[i][0]=rowList.get(i);
            path[i][1]=colList.get(i);
        }
            
        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return path;
 
    }
    // check if the position was visited
    static boolean checkVisited(int x, int y) {
        for (int i = 0; i < rowList.size(); i++) {
            if (rowList.get(i) == x && colList.get(i) == y) {
                return true;
            }
        }
        return false;
    }

}
 


class SecondGenMaze {
    public static boolean exists(int[][] maze, int r, int c){
        int maxRow = maze.length;
        int maxCol = maze[0].length;
        if ((r<maxRow)&&(r>=0)){
            if ((c<maxCol)&&(c>=0)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public static boolean isWall(int[][] maze, int r, int c){
        if (maze[r][c]==1){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean goodNeighbor(int[][] maze, int r, int c){
        if (exists(maze,r,c)){
            if (isWall(maze,r,c)){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean isViableNeigbor(int[][] maze, int r, int c){
        boolean s = true;
        boolean w = true;
        boolean e = true;
        boolean n = true;
        int count = 0;
        Random rand = new Random();
        int num = rand.nextInt(2);
        // num =1;                     //uncomment this code to get real deapth first (its gonna have less path variations) 
        int threshhold = 2 +num;
        if (exists(maze,r,c)){
            s = goodNeighbor(maze, r+1, c);
            w = goodNeighbor(maze, r, c-1);
            e = goodNeighbor(maze, r, c+1);
            n = goodNeighbor(maze, r-1, c);

            if (s) count++;
            if (w) count++;
            if (e) count++;
            if (n) count++;

            if (count>=threshhold){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isViable(int[][] maze, int r, int c, char direction){
        boolean legitCore = false;
        boolean legitLEar = false;
        boolean legitREar = false;

        switch (direction){
            case 'n':
                legitCore = isViableNeigbor(maze, r-1, c);
                legitLEar = goodNeighbor(maze,r-2,c+1);
                legitREar = goodNeighbor(maze, r-2, c-1);
                if (legitCore && legitLEar && legitREar){
                    return true;
                } else {
                    return false;
                }
            case 's':
                legitCore = isViableNeigbor(maze, r+1, c);
                legitLEar = goodNeighbor(maze,r+2,c+1);
                legitREar = goodNeighbor(maze, r+2, c-1);
                if (legitCore && legitLEar && legitREar){
                    return true;
                } else {
                    return false;
                }
            case 'w':
                legitCore = isViableNeigbor(maze, r, c-1);
                legitLEar = goodNeighbor(maze,r+1,c-2);
                legitREar = goodNeighbor(maze, r-1, c-2);
                if (legitCore && legitLEar && legitREar){
                    return true;
                } else {
                    return false;
                }
            case 'e':
                legitCore = isViableNeigbor(maze, r, c+1);
                legitLEar = goodNeighbor(maze,r-1,c+2);
                legitREar = goodNeighbor(maze, r+1, c+2);
                if (legitCore && legitLEar && legitREar){
                    return true;
                } else {
                    return false;
                }
            default:
                return false;

        }


    }    

    public static void dfs(int[][] maze, int r,int c){
        maze[r][c]=0;

        LinkedList<Integer> rowList = new LinkedList<Integer>();
        LinkedList<Integer> colList = new LinkedList<Integer>();

        if (isViable(maze, r+1, c, 's')){
            rowList.add(r+1);
            colList.add(c);
        }
        if (isViable(maze, r-1, c, 'n')){
            rowList.add(r-1);
            colList.add(c);
        }
        if (isViable(maze, r, c+1, 'e')){
            rowList.add(r);
            colList.add(c+1);
        }
        if (isViable(maze, r, c-1, 'w')){
            rowList.add(r);
            colList.add(c-1);
        }

        Random rand = new Random();
        // char[] dirs = new char[4];
        LinkedList<Character> dirs = new LinkedList<Character>();
        dirs.add('n');
        dirs.add('s');
        dirs.add('w');
        dirs.add('e');
        while (true){
            if (dirs.size()>0){
                
                int num = rand.nextInt(dirs.size());
                // int nextRow = rowList.get(num);
                // int nextCol = colList.get(num);
                char nextDir = dirs.get(num);
                dirs.remove(num);
                // rowList.remove(num);
                // colList.remove(num);
                
                if(isViable(maze, r, c, nextDir)){
                    switch (nextDir){
                        case 'n':
                            dfs(maze, r-1, c);
                            break;
                        case 's':
                            dfs(maze,  r+1, c);
                            break;
                        case 'w':
                            dfs(maze, r, c-1);
                            break;
                        case 'e':
                            dfs(maze, r, c+1);
                            break;
            
                    }
                    

                }
            } else {
                break;
            }
        }
    }

    public static int[][] deapthFirst(int[] dims){
        final long startTime = System.nanoTime();


        //int[][] maze = new int[dims[0]][dims[1]];
        /*
        int[][] maze = {{0,1,1,1,1,1,1},
                        {0,0,0,0,0,0,0},
                        {1,1,1,0,1,1,1},
                        {1,0,0,0,0,0,1},
                        {0,0,1,1,1,0,1},
                        {1,0,0,0,0,0,0},
                        {1,1,1,1,1,1,0},
                    };
         */
        int[][] maze = new int[dims[0]][dims[1]];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 1;
            }
        }
        
        dfs(maze, 0,dims[1]-1);


        maze[0][0]=0;
        maze[dims[0]-1][dims[1]-1] = 0;
        /*
        Random rand = new Random();
        int numx, numy;
        for (int i =0;i<(dims[0]*dims[1]/100); i++){
            numx = rand.nextInt(dims[0]);
            numy = rand.nextInt(dims[1]);
            maze[numx][numy]=0;
        }
         */

        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return maze;
    }
}


class GenMaze {
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

    public static int[][] deapthFirst(int[] dims) {
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

        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[i].length; j ++) {
                if(maze[i][j] == 1){
                    maze[i][j] = 0;
                } else {
                    maze[i][j] = 1;
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


public class Maze {
    public static void drawPath(int[][] path,int[][] maze){
        System.out.println();
        for(int i = 0; i<=maze[0].length+1; i++){
            System.out.print("█");
        }
        System.out.println();
        boolean contains = false;
        int k;
        for(int i=0; i<maze.length; i++){
            System.out.print("█");
            for(int j=0; j<maze[0].length; j++){
                
                //System.out.printf("%d\t",el);
                if (maze[i][j]==0){
                    if (!(path==null)){
                        contains = false;
                        for(k = 0; k<path.length;k++){
                            if ((i==path[k][0])&(j==path[k][1])){
                                contains=true;
                                break;
                            }
                        }
                        if (contains){
                            System.out.print("@");
                        } else {
                            System.out.print(" ");
                        }
                    } else {
                        System.out.print(" ");
                    }
                } else if (maze[i][j]==1){
                    System.out.print("█");
                }
            }
            System.out.print("█");
            System.out.println();
        }
        for(int i = 0; i<=maze[0].length+1; i++){
            System.out.print("█");
        }
        System.out.println();
    }

    public static void printPath(int[][] path,int[][] maze){
        System.out.print("Length of the path is: ");
        System.out.println(path.length);
        System.out.println("The path:");
        for (int[] line : path )
            System.out.print("(" + line[0] + "," + line[1] + ") ");

        drawPath(path, maze);
        // sc.nextInt();
    }

    public static int keyInputInt(Scanner sc){
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                number = sc.nextInt();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // consume the invalid input
            }
        }
        return number;
    }

    public static int keyInput10(Scanner sc){
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                number = sc.nextInt();
                if ((number>1)||(number<0)){
                    System.out.println("Maze can have only 1 for a wall or 0 for a path. Please enter a valid entry.");
                } else {
                    validInput = true;
                }
            } catch (Exception e) {
                System.out.println("Maze can have only 1 for a wall or 0 for a path. Please enter a valid entry.");
                sc.nextLine(); // consume the invalid input
            }
        }
        return number;
    }

    public static char keyInputYN(Scanner sc){
        boolean validInput = false;
        char fil = 'y';

        while (!validInput) {
            fil = sc.nextLine().charAt(0);
            if ((fil=='y') || (fil=='n')){
                validInput = true;
            } else {
                System.out.println("Input y or n charecter.");
                // sc.nextLine(); // consume the invalid input
            }
        }
        return fil;
    }
    public static void main(String[] arg){
        int[][] maze;
        int[][] path;
        int row, col, mode;
        char fil;
        Scanner sc = new Scanner(System.in);
        System.out.println("Input maze dimentions:");
        System.out.println("(It's gonna look better if collumn count is bigger then row count)");
        System.out.print("Row count: ");
        // row = sc.nextInt();
        row = keyInputInt(sc);

        System.out.print("Column count: ");
        col = keyInputInt(sc);
        sc.nextLine();
        System.out.print("Auto fill maze (y/n)? ");
        // fil = sc.nextLine().charAt(0);
        fil = keyInputYN(sc);
        int i;
        char rep;
        int tmp;
        if (!(fil == 'y')) {
            maze = new int[row][col];
            for (i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    System.out.print("["+i+"]["+j+"]: ");
                    maze[i][j] = keyInput10(sc);
                }
            }
        } else {
            System.out.println();
            System.out.println("Choose a method to generate a maze:");
            System.out.println("  1 - First method.");
            System.out.println("  2 - Second method.");
            System.out.print("Method number (1-2): ");
            mode = keyInputInt(sc);
            int[] dims={row,col};
            maze = null;
            switch (mode) {
                case 1: // Linass' algorithm
                    maze = GenMaze.deapthFirst(dims);
                    /*
                    for (i = 0; i < maze.length; i++) {
                        for (int j = 0; j < maze[i].length; j++) {
                            if (maze[i][j]==1){
                                maze[i][j]=0;
                            } else {
                                maze[i][j]=1;
                            }
                        }
                    }
                     */
                    break;
                case 2: // Nikita's Algorithm
                    maze = SecondGenMaze.deapthFirst(dims);
                    break;
                default:
                    System.out.println("Input Error: Method does not exist.");
            }
            path = null;
            drawPath(path,maze);

        }
        System.out.println();
        System.out.println("Choose a method to solve this maze:");
        System.out.println("  1 - Keep to the left side.");
        System.out.println("  2 - Flood fill.");
        System.out.print("Method number (1-2): ");
        mode = keyInputInt(sc);
        
        System.out.println();
        
        switch (mode) {
            case 1: // left side algorythm
                path = FirstRoute.rightSide(maze);
                printPath(path,maze);
                sc.nextLine();
                System.out.print("Compare it to the other method to solve maze (y/n)? ");
                rep = keyInputYN(sc);
                if (rep == 'y') {
                    path = SecondRoute.floodFill(maze,row,col);
                    printPath(path,maze);
                }
                break;
            case 2: // flood fill algorythm
                path = SecondRoute.floodFill(maze,row,col);
                printPath(path,maze);
                sc.nextLine();
                System.out.print("Compare it to the other method to solve maze (y/n)? ");
                rep = keyInputYN(sc);
                if (rep == 'y') {
                    path = FirstRoute.rightSide(maze);
                    printPath(path,maze);
                }
                break;
            default:
                System.out.println("Input Error: Method does not exist.");
        }
        sc.close();
        
    }
}
