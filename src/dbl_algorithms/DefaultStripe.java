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
                createNewStripe(llx, r, state, list, minWidth, minHeight);
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
                    createNewStripe(llx, r, state, list, minWidth, minHeight);
                }
            }

        }

        for (Rectangle r1 : rectangles) {
            for (Rectangle r2 : rectangles) {
                if (checkOverlap(r1, r2) && r1 != r2) {
                    System.out.println("Overlap! " + r1.blx + " " + r1.bly + " " + r1.width + " " + r1.height + " " + r2.blx + " " + r2.bly + " " + r2.width + " " + r2.height);
                }
            }
        }
        return state;
    }

    /*
    creates a new stripe at the bottom of maximum height
     */
    void createNewStripe(int llx, Rectangle r, State state, List<Stripe> list, int minWidth, int minHeight) {
        Stripe stripe = new Stripe(llx, 0, r.width, containerHeight);
        stripe.add(r, list);
        this.llx += r.width;
        state.addRectangle(r);

    }

    boolean checkOverlap(Rectangle r1, Rectangle r2) {
        if (r1.blx >= r2.blx + r2.width || r2.blx >= r1.blx + r1.width) {
            return false;
        }

        // If one rectangle is above other
        if (r1.bly + r1.height <= r2.bly || r2.bly + r2.height <= r1.bly) {
            return false;
        }

        return true;
    }

}
