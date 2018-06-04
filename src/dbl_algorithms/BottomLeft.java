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

    BottomLeft(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles, boolean sort) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.sort = sort;
    }


    @Override
    protected State pack() throws IOException, FileNotFoundException {
        // TODO: sort rectangles descending order
        if(this.sort) {
            QuickSort instance = new QuickSort();
            rectangles = instance.sort(rectangles);
        }

        State s = new State(rectangles.length);

        int width = 0;

        for(int i = 0; i < rectangles.length; i++) {
            int sy = containerHeight - rectangles[i].height;

            int maxWidth = 0;
            for(int j = 0; j < i; j++) {
                if(rectangles[j].bly + rectangles[j].height > sy && rectangles[j].blx + rectangles[j].width > maxWidth) {
                    maxWidth = rectangles[j].blx + rectangles[j].width;
                }
            }

            int maxHeight = 0;
            for(int j = 0; j < i; j++) {
                if(rectangles[j].blx < maxWidth + rectangles[i].width && rectangles[j].blx + rectangles[j].width > maxWidth && rectangles[j].bly + rectangles[j].height > maxHeight) {
                    maxHeight = rectangles[j].bly + rectangles[j].height;
                }
            }

            rectangles[i].setPosition(maxWidth, maxHeight);

            s.addRectangle(rectangles[i]);

            // update the width of the frame
            if(maxWidth + rectangles[i].width > width) {
                width = maxWidth + rectangles[i].width;
            }
        }

        return s;
    }
     
}
