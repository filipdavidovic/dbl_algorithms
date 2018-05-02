package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BottomLeft extends PackingStrategy {

    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;

    BottomLeft(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }


    @Override
    protected State pack() throws IOException, FileNotFoundException {
        // TODO: sort rectangles descending order

        State s = new State(rectangles.length);

        final int frameWidth = 20;
        int height = 0;

        for(int i = 0; i < rectangles.length; i++) {
            int sx = frameWidth - rectangles[i].width;

            int maxHeight = 0;
            for(int j = 0; j < i; j++) {
                // check that the rectangle is below the current position and that its x is greater than the current maxHeight
                if(rectangles[j].blx < sx + rectangles[i].width && rectangles[j].blx + rectangles[j].width > sx && rectangles[j].bly + rectangles[j].height > maxHeight) {
                    maxHeight = rectangles[j].bly + rectangles[j].height;
                }
            }

            int maxWidth = 0;
            for(int j = 0; j < i; j++) {
                // check that the rectangle is to the left of the current position and that its y is greater than the current maxWidth
                if(rectangles[j].bly < maxHeight + rectangles[i].height && rectangles[j].bly + rectangles[j].height > maxHeight && rectangles[j].blx + rectangles[j].width > maxWidth) {
                    maxWidth = rectangles[j].blx + rectangles[j].width;
                }
            }

            rectangles[i].setPosition(maxWidth, maxHeight);

            s.addRectangle(rectangles[i]);

            // update the height of the frame
            if(maxHeight + rectangles[i].height > height) {
                height = maxHeight + rectangles[i].height;
            }
        }

        return s;
    }
}