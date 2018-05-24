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
        if (rotationsAllowed) {
            for (Rectangle r : rectangles) {
                if (r.height > r.width) {
                    r.rotate();
                }
            }
        }
        
       
        rectangles = instance.sort(rectangles);
         for (Rectangle r : rectangles) {
                System.out.println(r.width+" "+r.height);
                
            }
        State state = new State(rectangles.length);
        Packer packer;
        if (containerHeight != -1) {
            System.out.print("FIXED HEIGHT NOT SUPPORTED");
            packer = null;
        } else {
            packer = new Packer((rectangles.length+670)/18 , rectangles.length );
        }

        ArrayList<Rectangle> result = new ArrayList<>();
        for (int i = 0; i < rectangles.length; i++) {
            Rectangle r = new Rectangle(rectangles[i].width, rectangles[i].height);
            result.add(r);
        }
        packer.fit(result);
        for (int i = 0; i < rectangles.length; i++) {
            result.get(i).setPosition(result.get(i).fit.blx, result.get(i).fit.bly);
        }

        for (int i = 0; i < rectangles.length; i++) {
            state.addRectangle(result.get(i));
        }
        return state;
    }
}
