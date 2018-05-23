/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;
import static dbl_algorithms.GetMin.*;


/**
 * Implementation of First fit decreasing algorithm for Prototype 1 version 2
 *
 * @author TijanaKlimovic
 */
public class DefaultStripe extends PackingStrategy {

    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    int llx = 0;  //lower left x coordinate

    DefaultStripe(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, UnsupportedOperationException, CloneNotSupportedException {
        if (containerHeight == -1) {
            throw new UnsupportedOperationException("not fixed height");
        }
        
        if (rotationsAllowed) {
            for (Rectangle r : rectangles) {
                if (r.height > r.width) {
                    r.rotate();
                }
            }
        }

        QuickSort instance = new QuickSort();
        rectangles = instance.sort(rectangles);

        State state = new State(rectangles.length);
        List<Stripe> list = new LinkedList<>();
        int minWidth = getMinWidth(rectangles);
        int minHeight = getMinHeight(rectangles);

        for (Rectangle r : rectangles) {
            if (list.isEmpty()) {
                createNewStripe(llx, r, state, list);
            } else {
                boolean added = false;
                for (Stripe s : list) {
                    if (s.add(r, list, minWidth, minHeight)) {
                        state.addRectangle(r);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    createNewStripe(llx, r, state, list);
                }
            }

        }
        return state;
    }

    /*
    creates a new stripe at the bottom of maximum height
     */
    void createNewStripe(int llx, Rectangle r, State state, List<Stripe> list) {
        Stripe stripe = new Stripe(llx, 0, r.width, containerHeight);
        stripe.add(r, list);
        this.llx += r.width;
        state.addRectangle(r);     
    }

}
