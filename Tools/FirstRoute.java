package Tools;
//package dip107;

import java.util.ArrayList;
import java.util.List;

public class FirstRoute {
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
 
