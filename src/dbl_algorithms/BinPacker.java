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
        State state = new State(rectangles.length);
        Packer packer;
        if (containerHeight != -1) {
            packer = new Packer(2000*rectangles.length , containerHeight*300);
        } else {
            packer = new Packer(2000*rectangles.length , rectangles.length );
        }

        ArrayList<Rectangle> result = new ArrayList<>();
        for (int i = 0; i < rectangles.length; i++) {
            Rectangle r = new Rectangle(rectangles[i].width, rectangles[i].height);
            result.add(r);
        }
        packer.fit(result);
        for (int i = 0; i < rectangles.length; i++) {
            // Rectangle r = new Rectangle(result.get(i).fit.blx,result.get(i).fit.bly , result.get(i).width, result.get(i).height);
            //result.add(r);
            result.get(i).setPosition(result.get(i).fit.blx, result.get(i).fit.bly);
        }

        for (int i = 0; i < rectangles.length; i++) {
            state.addRectangle(result.get(i));
        }
        return state;
    }
}
