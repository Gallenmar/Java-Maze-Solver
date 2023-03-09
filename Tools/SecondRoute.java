package Tools;
//package dip107;

import java.util.LinkedList;

public class SecondRoute {

    public static int[][] floodFill(int[][] mazeOrig,int row,int col) {
        // figure out the length of path (x)
        // initialize path
        //int[][] path = new int[x][2];
        int[][] maze = new int[row][col];
        for(int i = 0; i<row; i++){
            for(int j = 0; j<col; j++){
                maze[i][j] = mazeOrig[i][j];
            }
        }
        
        boolean[][] marked = new boolean[row][col];
        LinkedList<Integer> rowList = new LinkedList<Integer>();
        LinkedList<Integer> colList = new LinkedList<Integer>();
        LinkedList<Integer> howFarList = new LinkedList<Integer>();
        int howFar = 1;// 0 and 1 are reserver values
        //                so we start from 2
        row = row-1;
        col = col-1;
        rowList.add(row);
        colList.add(col);
        howFar++;
        howFarList.add(howFar);
        
        int curRow;
        int curCol;
        int curFar;
        // running water thru maze
        while (rowList.size()>0){
            curRow = rowList.poll();
            curCol = colList.poll();
            curFar = howFarList.poll();

            
            if (!(marked[curRow][curCol])){
                maze[curRow][curCol]=curFar;
                marked[curRow][curCol] = true;
                
                // Adding all the neighbors to the queue

                if ((curRow+1)<=row){
                    if (maze[curRow+1][curCol]==0){
                        if (!(marked[curRow+1][curCol])){
                            rowList.add(curRow+1);
                            colList.add(curCol);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curRow-1)>=0){
                    if (maze[curRow-1][curCol]==0){
                        if (!(marked[curRow-1][curCol])){
                            rowList.add(curRow-1);
                            colList.add(curCol);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curCol+1)<=col){
                    if (maze[curRow][curCol+1]==0){
                        if (!(marked[curRow][curCol+1])){
                            rowList.add(curRow);
                            colList.add(curCol+1);
                            howFarList.add(curFar+1);

                        }
                    }
                }
                if ((curCol-1)>=0){
                    if (maze[curRow][curCol-1]==0){
                        if (!(marked[curRow][curCol-1])){
                            rowList.add(curRow);
                            colList.add(curCol-1);
                            howFarList.add(curFar+1);

                        }
                    }
                }
            }

        }
        // for(int[] line : maze){
        //     for(int el : line){
        //         System.out.printf("%d\t",el);
        //     }
        //     System.out.println();
        // }

        boolean keepGoing = true;
        int element;
        curRow = 0;
        curCol = 0;
        while (keepGoing){
            element = maze[curRow][curCol];
            if (element==2){
                keepGoing =false;
            }
            rowList.add(curRow);
            colList.add(curCol);
            if ((curRow+1)<=row){
                if (maze[curRow+1][curCol]==(element-1)){
                    curRow++;
                }
            }
            if ((curRow-1)>=0){
                if (maze[curRow-1][curCol]==(element-1)){
                    curRow--;
                }
            }
            if ((curCol+1)<=col){
                if (maze[curRow][curCol+1]==(element-1)){
                    curCol++;
                }
            }
            if ((curCol-1)>=0){
                if (maze[curRow][curCol-1]==(element-1)){
                    curCol--;
                }
            }
        }
        // for(int i = 0; i<rowList.size();i++){
        //     System.out.print(rowList.get(i));
        //     System.out.println(colList.get(i));
        // }

        int[][] path = new int[rowList.size()][2];
        for(int i = 0; i<rowList.size();i++){
            path[i][0]=rowList.get(i);
            path[i][1]=colList.get(i);
        }
        return path;
    }
}
