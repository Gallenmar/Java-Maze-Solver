package Tools;


import java.util.LinkedList;
import java.util.Random;

public class SecondGenMaze {
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
        if (exists(maze,r,c)){
            s = goodNeighbor(maze, r+1, c);
            w = goodNeighbor(maze, r, c-1);
            e = goodNeighbor(maze, r, c+1);
            n = goodNeighbor(maze, r-1, c);

            if (s) count++;
            if (w) count++;
            if (e) count++;
            if (n) count++;

            if (count>=3){
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
        
        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return maze;
    }
}
