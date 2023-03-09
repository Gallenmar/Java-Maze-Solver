package Tools;
//package dip107;

public class GenMaze {
    public static int[][] deapthFisrt(int[] dims){
        //int[][] maze = new int[dims[0]][dims[1]];
        int[][] maze = {{0,1,1,1,1,1,1},
                        {0,0,0,0,0,0,0},
                        {1,1,1,0,1,1,1},
                        {1,0,0,0,0,0,1},
                        {0,0,1,1,1,0,1},
                        {1,0,0,0,0,0,0},
                        {1,1,1,1,1,1,0},
                    };

        return maze;
    }
}
