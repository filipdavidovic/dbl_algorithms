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
public class InsertionSort implements AbstractSort{
    @Override
    public Rectangle[] sort(Rectangle[] rectangles) {
        int n = rectangles.length;
        Rectangle[] result = new Rectangle[n];
        result[0] = new Rectangle(rectangles[0].width, rectangles[0].height);
        for (int i = 1; i < n; i++) {
            Rectangle key = new Rectangle(rectangles[i].width, rectangles[i].height);
            int j = i - 1;
            while (j >= 0 && result[j].width < key.width) {
                result[j + 1] = result[j];
                j = j - 1;
            }
            result[j + 1] = key;
        }
        return result;
    }
}
