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
 * quick sort on array of rectangles in decreasing width
 */
public class QuickSort implements AbstractSort {

    @Override
    public Rectangle[] sort(Rectangle[] rectangles) {

        int size = rectangles.length;
        quickSort(rectangles, 0, size - 1);

        return rectangles;
    }

    protected void quickSort(Rectangle[] rectangles, int lo, int hi) {
        int part;
        
        if (lo < hi) {
            part = partition(rectangles, lo, hi);
            quickSort(rectangles, lo, part - 1);
            quickSort(rectangles, part + 1, hi);
        }
    }

    protected int partition(Rectangle[] rectangles, int lo, int hi) {
        int pivot = rectangles[hi].width;
        int index = lo - 1;

        for (int i = lo; i < hi; i++) {

            if (rectangles[i].width > pivot) {
                index++;
                swap(rectangles[index], rectangles[i]);
            }
        }
        swap(rectangles[index + 1], rectangles[hi]);
        return index + 1;
    }

    protected void swap(Rectangle r1, Rectangle r2) {
        Rectangle helper;

        helper = new Rectangle(r1.width, r1.height);
        helper.setPosition(r1.blx, r1.bly);
        helper.rotated = r1.rotated;
        helper.placed = r1.placed;
        helper.used = r1.used;
        helper.setInitialposition(r1.getInitialposition());
        
        r1.blx = r2.blx;
        r1.bly = r2.bly;
        r1.height = r2.height;
        r1.rotated = r2.rotated;
        r1.width = r2.width;
        r1.placed = r2.placed;
        r1.used = r2.used;
        r1.setInitialposition(r2.getInitialposition());

        r2.blx = helper.blx;
        r2.bly = helper.bly;
        r2.height = helper.height;
        r2.rotated = helper.rotated;
        r2.width = helper.width;
        r2.placed = helper.placed;
        r2.used = helper.used;
        r2.setInitialposition(helper.getInitialposition());
        

    }

    public void printArray(Rectangle[] r) {
        for (Rectangle r1 : r) {
            System.out.println(r1.width);
        }
    }
}
