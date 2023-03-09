package Tools;
//package dip107;

public class FirstRoute {
    public static int[][] leftSide(int[][] maze){
        final long startTime = System.nanoTime();
        // figure out the length of path (x)
        // initialize path
        //int[][] path = new int[x][2];
        
        //junk code to fill the path temporarily
        int[][] path = new int[2][2];







        final long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1_000_000.0;
        time = Math.ceil(time * 10_000) / 10_000;
        System.out.println("Total execution time: " + time+"ms.");
        return path;
    }
}
