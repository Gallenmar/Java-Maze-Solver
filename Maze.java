import java.util.Scanner;

import Tools.*;

public class Maze {
    public static void printPath(int[][] path, int[][] maze) {
        System.out.print("Length of the path is: ");
        System.out.println(path.length);
        System.out.println("The path:");
        for (int[] line : path)
            System.out.print("(" + line[0] + "," + line[1] + ") ");

        System.out.println();
        for (int i = 0; i <= maze.length + 1; i++) {
            System.out.print("███");
        }
        System.out.println();
        boolean contains = false;
        int k;
        for (int i = 0; i < maze.length; i++) {
            System.out.print("███");
            for (int j = 0; j < maze[0].length; j++) {
                contains = false;
                // System.out.printf("%d\t",el);
                if (maze[i][j] == 0) {

                    for (k = 0; k < path.length; k++) {
                        if ((i == path[k][0]) & (j == path[k][1])) {
                            contains = true;
                            break;
                        }
                    }
                    if (contains) {
                        System.out.print(" " + '\u2022' + " ");
                    } else {
                        System.out.print("   ");
                    }
                } else if (maze[i][j] == 1) {
                    System.out.print("███");
                }
            }
            System.out.print("███");
            System.out.println();
        }
        for (int i = 0; i <= maze.length + 1; i++) {
            System.out.print("███");
        }
        System.out.println();
    }

    public static void main(String[] arg) {
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
            for (i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    System.out.print("[" + i + "][" + j + "]: ");
                    maze[i][j] = sc.nextInt();
                }
            }
        } else {
            // automātiski aizpildam masīvu un izvadam to ekrānā
            int[] dims = { row, col };
            maze = GenMaze.deapthFisrt(dims);
            for (i = 0; i <= maze.length + 1; i++) {
                System.out.print("███");
            }
            System.out.println();
            for (int[] line : maze) {
                System.out.print("███");
                for (int el : line) {
                    // System.out.printf("%d\t",el);
                    if (el == 0) {
                        System.out.print("   ");
                    } else if (el == 1) {
                        System.out.print("███");
                    }
                }
                System.out.print("███");
                System.out.println();
            }
            for (i = 0; i <= maze.length + 1; i++) {
                System.out.print("███");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Choose a method:");
        System.out.println("  1 - Keep to the left side.");
        System.out.println("  2 - Flood fill.");
        System.out.print("Method number (1-2): ");
        mode = sc.nextInt();

        System.out.println();
        char rep;
        switch (mode) {
            case 1: // left side algorythm
                path = FirstRoute.leftSide(maze);
                printPath(path, maze);
                sc.nextLine();
                System.out.print("Compare it to the other method (y/n)? ");
                rep = sc.nextLine().charAt(0);
                if (rep == 'y') {
                    path = SecondRoute.floodFill(maze, row, col);
                    printPath(path, maze);
                }
                break;
            case 2: // flood fill algorythm
                path = SecondRoute.floodFill(maze, row, col);
                printPath(path, maze);
                sc.nextLine();
                System.out.print("Compare it to the other method (y/n)? ");
                rep = sc.nextLine().charAt(0);
                if (rep == 'y') {
                    path = FirstRoute.leftSide(maze);
                    printPath(path, maze);
                }
                break;
            default:
                System.out.println("Input Error: Method does not exist.");
        }
        sc.close();

    }
}