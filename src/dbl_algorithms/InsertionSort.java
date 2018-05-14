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
 * insertion sort on array of rectangles in decreasing width
 */
public class InsertionSort implements AbstractSort {

    private int minWidth = 0;
    private int minHeight = 0;

    @Override
    public Rectangle[] sort(Rectangle[] rectangles) throws CloneNotSupportedException {
        int n = rectangles.length;
        Rectangle[] result = new Rectangle[n];
        result[0] = (Rectangle) rectangles[0].clone();

        for (int i = 1; i < n; i++) {
            Rectangle key = (Rectangle) rectangles[i].clone();
            /*Rectangle key = new Rectangle(rectangles[i].width, rectangles[i].height);
            if (rectangles[i].rotated) {
                key.setRotated();
            }*/
            int j = i - 1;

            while (j >= 0 && result[j].width < key.width) {
                result[j + 1] = result[j];
                j = j - 1;
            }

            result[j + 1] = key;
        }
        setMin(rectangles);
        return result;
    }

    // set minimum height and width for threshold strip
    private void setMin(Rectangle[] r) {
        minWidth = r[r.length - 1].width;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < r.length; i++) {
            if (r[i].height < min) {
                min = r[i].height;
            }
        }
        minHeight = min;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

}
