/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Tea
 */
public class BinPackerHeightPicker extends PackingStrategy {

    boolean rotationsAllowed;
    Rectangle[] rectangles;
    int containerHeight;

    public BinPackerHeightPicker(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.containerHeight = containerHeight;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, CloneNotSupportedException {

        PackingStrategy strategy = new BinPacker(containerHeight, rotationsAllowed, rectangles);
        State s1 = strategy.pack();
        int maxHeight = 0;
        for (int i = 0; i < rectangles.length; i++) {
            if (maxHeight < rectangles[i].height) {
                maxHeight = rectangles[i].height;
            }

        }
        int height = s1.layoutHeight;
        double maxQuality = 0;
        State s = null;

        if (containerHeight == -1) {
            for (int i = Math.max(height - 200, maxHeight); i <= height + 200; i++) {
                strategy = new BinPacker(i, rotationsAllowed, rectangles);
                State sIntermediate = strategy.pack();

                if (sIntermediate.fillRate > maxQuality) {
                    maxQuality = sIntermediate.fillRate;
                    s = sIntermediate;
                }
            }
        } else {
//            for (int i = Math.max(height - 200, maxHeight); i <=height; i++) {
//                strategy = new BinPacker(i, rotationsAllowed, rectangles);
//                State sIntermediate = strategy.pack();
//
//                if (sIntermediate.fillRate > maxQuality && sIntermediate.layoutHeight <= containerHeight) {
//                    maxQuality = sIntermediate.fillRate;
//                    s = sIntermediate;
//                }
//            }
            int minWidth = Integer.MAX_VALUE;
            for (int i = Math.max(containerHeight - 100, maxHeight); i <= containerHeight; i++) {
                strategy = new BinPacker(i, rotationsAllowed, rectangles);
                State sIntermediate = strategy.pack();
                if (sIntermediate.layoutWidth < minWidth && sIntermediate.layoutHeight <= containerHeight) {
                    minWidth = sIntermediate.layoutWidth;
                    s = sIntermediate;
                }
            }

        }
        if (containerHeight != 0) {
            s.setFixedHeight(containerHeight);
            s.fillRate = s.getFillRate();
        }
        return s;

    }

}
