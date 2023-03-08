package Tools;
//package dip107;

public class GenMaze {
    public static int[][] deapthFisrt(int[] dims){
        int[][] maze = new int[dims[0]][dims[1]];

        for(int i=0; i<dims[0]; i++){
            for(int j=0; j<dims[1]; j++){
                maze[i][j] =  (int) Math.round( Math.random() )  ;;
            }
        }

        return maze;
    }
}
