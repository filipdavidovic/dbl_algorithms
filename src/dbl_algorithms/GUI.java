/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *
 * @author dianaepureanu
 */
public class GUI {

//    private int[][] placement;
//    private int numberOfRectangles;
//    private int[][] rectangles;
//    private boolean rotated;
    private Rectangle[] layout;
    private int[][] grid;    
    
    GUI(Rectangle[] layout) {
//        this.placement = placement;
//        this.numberOfRectangles = numberOfRectangles;
//        this.rectangles = rectangles;
 //       this.grid = new int[70][70];
        this.layout = layout;
    }
    
    public void run() {
//        for (int i = 0; i < layout.length; i++) {
//            if (!rotated) {
//                // area = (x, x+w)*(y, y+h)
//                for ( int width = placement[i][0]; width < placement[i][0]+ rectangles[i][0]; width ++){
//                    for (int height = placement[i][1]; height < placement[i][1] + rectangles[i][1]; height++){
//                        grid[width][height] = i+1;
//                    }
//                }
//            } else {
//                // area = (x, x+h)*(y, y+w)
//            } 
//        }
//        for (int x = 0; x < 70 ; x++) {
//            for (int j = 0; j < 70; j ++) {
//                System.out.print(grid[x][j]+ " ");
//            }
//            System.out.println();
//         }  
        DrawGrid draw = new DrawGrid(layout);
    }
 
    
}
