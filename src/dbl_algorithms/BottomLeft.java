package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * LeftBottom
 */
public class BottomLeft extends PackingStrategy {

    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    boolean sort;

    BottomLeft(int containerHeight, boolean rotationsAllowed,
            Rectangle[] rectangles, boolean sort) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.sort = sort;
    }


    @Override
    protected State pack() throws IOException, FileNotFoundException {
        
        if(this.sort) {
            // Sort the rectangles in descending order
            QuickSort instance = new QuickSort();
            rectangles = instance.sort(rectangles);
        }

        
        State s = new State(rectangles.length, containerHeight); // State to be returned

        int width = 0;  // Initial width

        for(int i = 0; i < rectangles.length; i++) {
            // Iterate through all the rectangles
            
            int sy = containerHeight - rectangles[i].height; 
            // The starting y is equal to the height of the container minus the
            // the height of the rectangle

            int maxWidth = 0;
            
            // Check to the left of the rectangle
            for(int j = 0; j < i; j++) {
                // Iterate through the rectangles that are already placed
                // to determine the max x (maxWidth) 
                // where the rectangle can be placed
                if(rectangles[j].bly + rectangles[j].height > sy && 
                        rectangles[j].blx + rectangles[j].width > maxWidth) {
                    // The top left coordinate of the rectangle is greater
                    // than sy  and bottom right coordinate is greater than
                    // the max width
                    maxWidth = rectangles[j].blx + rectangles[j].width;
                    // Increase the maxWidth
                }
            }

            int maxHeight = 0;
            
            // Check to the bottom of the rectangle
            for(int j = 0; j < i; j++) {
                // Iterate through the rectangles that are already placed
                // to determine the max y (maxHeigth) 
                // where the rectangle can be placed
                if(rectangles[j].blx < maxWidth + rectangles[i].width &&
                        rectangles[j].blx + rectangles[j].width > maxWidth &&
                        rectangles[j].bly + rectangles[j].height > maxHeight) {
                    // The bottom left coordinate is smaller than the max width
                    // AND the bottom right coordinate is greater than
                    // the max width AND the top left coordinate is grater
                    // than sy
                    maxHeight = rectangles[j].bly + rectangles[j].height;
                }
            }

            // Place the rectangle
            rectangles[i].setPosition(maxWidth, maxHeight);

            // Add the rectangle to the state
            s.addRectangle(rectangles[i]);

            // Update the width of the frame
            if(maxWidth + rectangles[i].width > width) {
                width = maxWidth + rectangles[i].width;
            }
        }

        return s;
    }
     
}
