//package dip107;
import java.util.Scanner;
//import dip107.*;

public class Maze {
    public static void main(String[] arg){
        int[][] maze;
        //int[][] path = { { 0, 0 }, { 0, 1 }, { 0, 2 }, { 1, 2 } }; // šeit ir jābūt rezultātam
        int row, col, mode;
        char fil;
        Scanner sc = new Scanner(System.in);
        System.out.print("row count: ");
        row = sc.nextInt();
        System.out.print("column count: ");
        col = sc.nextInt();
        sc.nextLine();
        System.out.print("Auto fill maze (y/n)?");
        fil = sc.nextLine().charAt(0);
        int i;
        if (fil == 'n') {
            maze = new int[row][col];
            for (i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    System.out.print("["+i+"]["+j+"]: ");
                    maze[i][j] = sc.nextInt();
                }
            }
        } else {
            // automātiski aizpildam masīvu un izvadam to ekrānā
            //maze = new int[row][col];
            int[] dims={row,col};
            maze = GenMaze.deapthFisrt(dims);
            for(int[] line : maze){
                for(int el : line){
                    System.out.print(el);
                }
                System.out.println();
            }
        }
        
        System.out.println("1 - Keep to the left side.");
        System.out.println("2 - Flood fill.");
        System.out.print("method number (1-2):");
        mode = sc.nextInt();
        sc.close();

        int[][] path;
        
        switch (mode) {
            case 1: // 1. algoritms
                path = FirstRoute.leftSide(maze);

                // rezultātu izvade
                System.out.println("results:");
                for (int[] line : path )
                    System.out.print("(" + line[0] + "," + line[1] + ") ");
                break;
            case 2: // 2. algoritms
                path = SecondRoute.floodFill(maze);

                // rezultātu izvade
                System.out.println("results:");
                for (int[] line : path )
                    System.out.print("(" + line[0] + "," + line[1] + ") ");
                break;
            default:
                System.out.println("Input Error: Method does not exist.");
        }
        
    }
}
// Todo:
// update trello board
// move everything to the package (I just couldnt get them to work fsm)
// mb linked lisk for path
// sc not closed? (VScode points out a problem that sc is not closed, even tho i closed it)
// make path output a function mb