/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *
 * insertion sort on array of rectangles in decreasing width
 */
public class InsertionSort implements AbstractSort {

    @Override
    public Rectangle[] sort(Rectangle[] rectangles) throws CloneNotSupportedException {
        int n = rectangles.length;
        Rectangle[] result = new Rectangle[n];
        result[0] = (Rectangle) rectangles[0].clone();

        for (int i = 1; i < n; i++) {
            Rectangle key = (Rectangle) rectangles[i].clone();

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
