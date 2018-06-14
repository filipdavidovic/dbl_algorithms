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
 * Best-Fit Decreasing Width Stripe Packing algorithm Used when: inputSize =
 * 5000 containerHeight = fixed rotationsAllowed = true/false
 *
 * @author Tijana Klimovic and Thanh-Dat
 */
public class DefaultStripe extends PackingStrategy {

    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    int llx;  //lower left x coordinate 

    /**
     * DefaultStripe constructor
     * @param containerHeight fixed height of the container given at input
     * @param rotationsAllowed true if rotations of rectangles are allowed, else false
     * @param rectangles list of rectangles given at input
     */
    DefaultStripe(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, UnsupportedOperationException,
            CloneNotSupportedException {
        //special value (-1) used to indicate if no fixed height is given at input
        if (containerHeight == -1) {
            throw new UnsupportedOperationException("not fixed height");
        }
        llx = 0;
        //if rotations are allowed, rotate each rectangle s.t. 
        //the biggest dimension is parallel to the 'x axis'(width)
        if (rotationsAllowed) {
            for (Rectangle r : rectangles) {
                if (r.height > r.width) {
                    r.rotate();
                }
            }
        }

        //sort the rectangles in decreasing order by horizonal dimension (width)
        QuickSort instance = new QuickSort();
        rectangles = instance.sort(rectangles);

        // initiate the state and the list of stripes
        State state = new State(rectangles.length);
        state.setFixedHeight(containerHeight);
        List<Stripe> list = new LinkedList<>();

        //get smallest width and smallest height (note dimensions may not belong to same rectangle)
        int minWidth = getMinWidth(rectangles);
        int minHeight = getMinHeight(rectangles);

        //iterate through all rectangles 
        for (Rectangle r : rectangles) {
            //at the start of the iteration the list is empty so we add to it a stripe with 
            //hegiht= containerHeight and width = width of widest rectangle
            //(rectangle at the begining of the sorted rectangles list)
            if (list.isEmpty()) {
                createNewStripe(llx, r, state, list, minWidth, minHeight);

                //if the list of stripes is not empty we check if we can fit the rectangle 
                //in any of the existing stripes. 
                //If the rectangle can fit into more than one stripe then
                //we add it to a stripe to which it would fit best
                //best fit implies that in the case of the rectangle being able to fit 
                //in many stripes the stripe with smallest area is chosen for its placement
                //the state is updated with the placement of this r
            } else {
                boolean added = false;
                for (Stripe s : list) {
                    if (s.add(r, list, minWidth, minHeight)) {
                        state.addRectangle(r);
                        added = true;
                        break;
                    }
                }
                //if the rectangle didn't fit in any of the stripes in list 
                //then create a new stripe with width = r.width and hegiht= containerHeight
                if (!added) {
                    createNewStripe(llx, r, state, list, minWidth, minHeight);
                }
            }

        }

        return state;
    }

    /**
     * creates a new stripe at the bottom of maximum height
     *
     * @param llx - x coordinate of stripe's lower left corner
     * @param r - rectangle to be added to stripe
     * @param state - state of rectangle placement
     * @param list - list of stripes
     * @param minWidth width of minimum stripe
     * @param minHeight height of minimum stripe
     * @modifies list, state, r
     */
    void createNewStripe(int llx, Rectangle r, State state, List<Stripe> list, int minWidth,
            int minHeight) {
        //create new stripe 
        Stripe stripe = new Stripe(llx, 0, r.width, containerHeight);
        //add the stripe to the list
        stripe.add(r, list, minWidth, minHeight);
        this.llx += r.width;
        state.addRectangle(r);

    }

}
