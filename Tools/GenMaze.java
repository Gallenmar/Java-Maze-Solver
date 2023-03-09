package Tools;
//package dip107;

public class GenMaze {
    public static int[][] deapthFisrt(int[] dims){
        final long startTime = System.nanoTime();


        //int[][] maze = new int[dims[0]][dims[1]];
        int[][] maze = {{0,1,1,1,1,1,1},
                        {0,0,0,0,0,0,0},
                        {1,1,1,0,1,1,1},
                        {1,0,0,0,0,0,1},
                        {0,0,1,1,1,0,1},
                        {1,0,0,0,0,0,0},
                        {1,1,1,1,1,1,0},
                    };
        
        
        
        
        
        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return maze;
    }
}
