package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BottomLeft extends PackingStrategy {

    String containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;

    BottomLeft(String containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }


    @Override
    protected void pack() throws IOException, FileNotFoundException {
        // TODO: sort rectangles descending order

        final int frameWidth = 100;
        int height = 0;

        for(int i = 0; i < rectangles.length; i++) {
            int sx = height + rectangles[i].height;
            int sy = frameWidth - rectangles[i].width;

            int maxHeight = 0;
            for(int j = 0; j < i; j++) {
                if(rectangles[j].blx <= sx && rectangles[j].blx + rectangles[j].width > sx) {
                    maxHeight = rectangles[j].bly + rectangles[j].height;
                }
            }

            int maxWidth = 0;
            for(int j = 0; j < i; j++) {
                if(rectangles[j].bly <= sy && rectangles[j].bly + rectangles[j].height > sy) {
                    maxWidth = rectangles[j].blx + rectangles[j].width;
                }
            }

            rectangles[i].setPosition(maxWidth, maxHeight);
        }
    }
}
