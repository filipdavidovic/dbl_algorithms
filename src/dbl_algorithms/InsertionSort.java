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
    public void sort(Rectangle[] rectangles) {
        int n = rectangles.length;
        for (int i = 1; i < n; i++) {
            int key = rectangles[i].width;
            int j = i - 1;
            while (j >= 0 && rectangles[j].width > key) {
                rectangles[j + 1] = rectangles[j];
                j = j - 1;
            }
            rectangles[j + 1].width = key;
        }
    }
}
