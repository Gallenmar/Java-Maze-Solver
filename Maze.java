import java.util.Scanner;

import Tools.*;

public class Maze {
    public static void printPath(int[][] path){
        System.out.println("results:");
        for (int[] line : path )
            System.out.print("(" + line[0] + "," + line[1] + ") ");
    }
    public static void main(String[] arg){
        int[][] maze;
        int[][] path;
        int row, col, mode;
        char fil;
        Scanner sc = new Scanner(System.in);
        System.out.print("Row count: ");
        row = sc.nextInt();
        System.out.print("Column count: ");
        col = sc.nextInt();
        sc.nextLine();
        System.out.print("Auto fill maze (y/n)? ");
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
            int[] dims={row,col};
            maze = GenMaze.deapthFisrt(dims);
            for(int[] line : maze){
                for(int el : line){
                    System.out.print(el);
                }
                System.out.println();
            }
        }
        System.out.println("Choose a method:");
        System.out.println("  1 - Keep to the left side.");
        System.out.println("  2 - Flood fill.");
        System.out.print("Method number (1-2): ");
        mode = sc.nextInt();
        sc.close();
        
        switch (mode) {
            case 1: // left side algorythm
                path = FirstRoute.leftSide(maze);
                printPath(path);
                break;
            case 2: // flood fill algorythm
                path = SecondRoute.floodFill(maze);
                printPath(path);
                break;
            default:
                System.out.println("Input Error: Method does not exist.");
        }
        
    }
}