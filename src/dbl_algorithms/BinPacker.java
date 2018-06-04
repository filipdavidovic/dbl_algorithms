/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Iterator;

/**
 *
 * @author Tea
 */
public class BinPacker extends PackingStrategy {

    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;

    BinPacker(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    protected State pack() throws IOException, FileNotFoundException {
        QuickSort instance = new QuickSort();
        InsertionSort instance2 = new InsertionSort();
        if (rotationsAllowed) {
            for (Rectangle r : rectangles) {
                if (r.height > r.width) {
                    r.rotate();
                }
            }
        }

       
        rectangles = instance.sort(rectangles);
        for (Rectangle r : rectangles) {
            System.out.println(r.width + " " + r.height);

        }
        State state = new State(rectangles.length);
        State initial = new State(rectangles.length);
        for (int i = 0; i < rectangles.length; i++) {
            initial.addRectangle(rectangles[i]);
        }
        boolean growingBox;
        int area = initial.innerArea;
        Packer packer;
        if (containerHeight != -1) {
            System.out.print("FIXED HEIGHT NOT SUPPORTED");
            packer = null;
            growingBox = false;
        } else {
            int hardcodex = (int) Math.sqrt(area * 100 / 99);
            int hardcodey = (int) Math.sqrt(area * 100 / 99) * 2;
            growingBox = true;
            System.out.println("Area " + area + " hardcode " + hardcodex * hardcodey);
            packer = new Packer(0, 0);
        }

        ArrayList<Rectangle> result = new ArrayList<>();

        for (int i = 0; i < rectangles.length; i++) {
            Rectangle r = rectangles[i].clone();
            result.add(r);
        }

        packer.fit(result, growingBox);
        int maxY = 0;
        int almostMaxY = 0;
        for (int i = 0; i < rectangles.length; i++) {
            if (result.get(i).fit.blx == 0 ) {
                if(result.get(i).fit.bly > maxY){
                maxY = result.get(i).fit.bly;
                } else {
                    if(result.get(i).fit.bly>almostMaxY){
                        almostMaxY = result.get(i).fit.bly;
                    }
                }
            } 
           
        }

        for (int i = 0; i < rectangles.length; i++) {
            result.get(i).setPosition(result.get(i).fit.blx, result.get(i).fit.bly);
        }

        Rectangle[] topRectangles = new Rectangle[10000];
        Rectangle[] almostTopRectangles = new Rectangle[10000];
        

        for (int i = 0; i < rectangles.length; i++) {
            state.addRectangle(result.get(i));
        }
         for (int i = 0; i < rectangles.length; i++) {
            if (result.get(i).bly == maxY && result.get(i).blx < state.getLayoutWidth()/2) {
                topRectangles[i] = result.get(i).clone();
            } else {
                if(result.get(i).bly == almostMaxY && result.get(i).blx < state.getLayoutWidth()/2){
                    almostTopRectangles[i] = result.get(i).clone();
                }
            }
        }
        

        return state;
    }
}
