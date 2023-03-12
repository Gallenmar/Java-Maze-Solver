import java.util.Scanner;

import Tools.*;

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
    public static void main(String[] arg){
        int[][] maze;
        int[][] path;
        int row, col, mode;
        char fil;
        Scanner sc = new Scanner(System.in);
        System.out.print("Row count: ");
        // row = sc.nextInt();
        row = keyInputInt(sc);

        System.out.print("Column count: ");
        col = keyInputInt(sc);
        sc.nextLine();
        System.out.print("Auto fill maze (y/n)? ");
        fil = sc.nextLine().charAt(0);
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
                path = FirstRoute.leftSide(maze);
                printPath(path,maze);
                sc.nextLine();
                System.out.print("Compare it to the other method to solve maze (y/n)? ");
                rep = sc.nextLine().charAt(0);
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
                rep = sc.nextLine().charAt(0);
                if (rep == 'y') {
                    path = FirstRoute.leftSide(maze);
                    printPath(path,maze);
                }
                break;
            default:
                System.out.println("Input Error: Method does not exist.");
        }
        sc.close();
        
    }
}
