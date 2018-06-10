package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BottomLeftSlide extends PackingStrategy{
    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    boolean sort;

    BottomLeftSlide(int containerHeight, boolean rotationsAllowed,
            Rectangle[] rectangles, boolean sort) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.sort = sort;
    }


    @Override
    protected State pack() throws IOException, FileNotFoundException {
        // Sort the rectangles in descending order
        if(sort) {
            QuickSort instance = new QuickSort();
            rectangles = instance.sort(rectangles);
        }

        State s = new State(rectangles.length); // State to be returned

        int width = 0;  // Initial width

        for(int i = 0; i < rectangles.length; i++) {
            // Iterate through all the rectangles
            int sx = width;
            // Starting x. It's equal to the width
            int sy = containerHeight - rectangles[i].height;
            // The starting y is equal to the height of the container minus the
            // the height of the rectangle
            boolean spin;

            // Final width and final height of the container
            int finalWidth = Integer.MAX_VALUE;
            int finalHeight = Integer.MAX_VALUE;

            do {
                spin = false;
                int maxWidth = 0;
                int maxHeight = 0;

                // Check to the left of the rectangle
                for(int j = 0; j < i; j++) {
                    // Iterate through the rectangles that are already placed
                    if(doesHOverlap(sy, rectangles[i].height, rectangles[j]) &&
                            isLeft(sx, rectangles[j]) && 
                            isRightMost(maxWidth, rectangles[j])) {
                        // the current rectangle "vertically overlaps", 
                        // is at left of sx and is the rightmost rectangle
                        if(!isWSame(sx, rectangles[j])) {
                            // the current rectangle is not the same as the one
                            // that caused a change in maxWidth before
                            maxWidth = rectangles[j].blx + rectangles[j].width;
                        } else {
                            maxWidth = sx;
                        }
                    }
                }
                
                if(finalWidth != maxWidth) {
                    // If the final width has changed, run the do loop again
                    spin = true;
                }
                finalWidth = maxWidth;
                sx = maxWidth;  // Update the starting x

                // Check to the bottom of the rectangle
                for(int j = 0; j < i; j++) {
                    // Iterate through the rectangles that are already placed
                    if(doesWOverlap(sx, rectangles[i].width, rectangles[j]) &&
                            isBottom(sy, rectangles[j]) && 
                            isTopMost(maxHeight, rectangles[j])) {
                        // the current rectangle "horizontally overlaps", 
                        // is at bottom of sy and is the topmost rectangle
                        if(!isHSame(sy, rectangles[j])) {
                            // the current rectangle is not the same as the one
                            // that caused  a change in maxHeight before
                            maxHeight = rectangles[j].bly + rectangles[j].height;
                        } else {
                            maxHeight = sy;
                        }
                    }
                }
                if(finalHeight != maxHeight) {
                    // If the final height has changed, run the do loop again
                    spin = true;
                }
                finalHeight = maxHeight;
                sy = maxHeight; // Update the starting y
            } while(spin);

            // Place the rectangle
            rectangles[i].setPosition(finalWidth, finalHeight);

            // Add the rectangle to the state
            s.addRectangle(rectangles[i]);

            // Update the width of the frame
            if(sx + rectangles[i].width > width) {
                width = sx + rectangles[i].width;
            }
        }

        return s;
    }

    // Returns true if Y is smaller than the top left coordinate of RECT
    // and Y plus H is greater than the bottom left coordinate of RECT
    private boolean doesHOverlap(int y, int h, Rectangle rect) {
        return y < rect.bly + rect.height && y + h > rect.bly;
    }

    // Returns true if X is smaller than the bottom right coordinate of RECT
    // and X plus W is greater than the bottom left coordinate of RECT
    private boolean doesWOverlap(int x, int w, Rectangle rect) {
        return x < rect.blx + rect.width && x + w > rect.blx;
    }

    // Return true if rect is at the left of x
    private boolean isLeft(int x, Rectangle rect) {
        return x >= rect.blx + rect.width;
    }

    // Return true if rect is below y
    private boolean isBottom(int y, Rectangle rect) {
        return y >= rect.bly + rect.height;
    }

    // Return true if rect is the right-most rectangle
    private boolean isRightMost(int maxWidth, Rectangle rect) {
        return rect.blx + rect.width > maxWidth;
    }

    // Return true if rect is the top-most rectangle
    private boolean isTopMost(int maxHeight, Rectangle rect) {
        return rect.bly + rect.height > maxHeight;
    }

    private boolean isHSame(int currentH, Rectangle rect) {
        return currentH == rect.bly + rect.height;
    }

    private boolean isWSame(int currentW, Rectangle rect) {
        return currentW == rect.blx + rect.width;
    }

}
