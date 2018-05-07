/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    protected State pack() throws IOException, FileNotFoundException {
        InsertionSort instance = new InsertionSort();
        if (rotationsAllowed) {
            for (Rectangle r : rectangles) {
                if (r.height > r.width) {
                    r.rotate();
                }
            }
        }
        rectangles = instance.sort(rectangles);
        State state = new State(rectangles.length);

        List<Stripe> list = new ArrayList<>();
        

        for (Rectangle r : rectangles) {
            if (list.isEmpty()) {
                createNewStripe(llx, r, state, list);
            } else {
                boolean added = false;
                for (Stripe s : list) {
                    if (s.add(r, list)) {
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
        //System.out.println(llx);
        Stripe stripe = new Stripe(llx, 0, r.width, containerHeight);
        stripe.add(r);
        state.addRectangle(r);
        list.add(stripe);
        this.llx += r.width;
    }

}
