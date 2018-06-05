package dbl_algorithms;

import java.util.List;
import static dbl_algorithms.InsertionSortListStripe.*;

/**
 *
 * @author Thanh-dat and Tijana Klimovic
 */
public class Stripe {

    //never changed in stripe adjustment
    final int x;
    final int width;
    int y;
    int height;

    /**
     * Stripe constructor
     * 
     * @param x x coordinate of the lower left corner of this stripe
     * @param y y coordinate of the lower left corner of this stripe
     * @param width width of stripe
     * @param height height of stripe
     */
    Stripe(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * checks if (@code r) can be fit in this stripe
     * @param r rectangle to be checked
     * @return true if rectangle fits else false    
    */
    private boolean check(Rectangle r) {
        if (r.height <= height && r.width <= width) {
            return true;
        }
        return false;
    }

    /**
     * adds r to this stripe in list if r can fit in this stripe 
     * removes this stripe from the list
     * inserts a modified stripe in list if the residual vertical space upon placing r 
     * in this stripe is greater than the minimal stripe
     * inserts a remaining stripe in list if the residual horizontal space upon placing r 
     * in this stripe is greater than the minimal stripe where:
     * 
     * modified stripe - residual vertical space upon placing r in this stripe
     * remaining stripe - residual horizontal space upon placing r in this stripe
     * 
     * @param r rectangle to be fit in this stripe
     * @param list list of stripes to which r will be added
     * @param minWidth width of minimum stripe
     * @param minHeight height of minimum stripe 
     * @modifies list
     * @return true if r has been added to this stripe , else false
     */
    
    public boolean add(Rectangle r, List<Stripe> list, int minWidth, int minHeight) {
        //check if r fits in this stripe
        if (check(r)) {
            //if it does place align the rectangle s.t. the lower left corner of r 
            //is the same as the lower left corner of this stripe
            r.setPosition(x, y);

            
            //if the remaining stripe is greater than or equal to the minimal stripe, where 
            //the minimal stripe is the space with dimensions of minheight and minWidth
            //then construct a remaining stripe and add it to the list 
            //in a sorted manner (by increasing area)
            if (width - r.width >= minWidth || r.height >= minHeight) {
                Stripe stripe = new Stripe(x + r.width, y, width - r.width, r.height);
                insertSort(stripe, list);
            }

            //remove this stripe from list
            list.remove(this);
            //add the modified stripe in a sorted manner (by increasing area)
            if (height - r.height >= minHeight) {
                y += r.height;
                height -= r.height;
                insertSort(this, list);
            }
            return true;
        }
        return false;
    }

    /*
    posibly deleted
    add rectangle to the stripe and adjust available stripe
    
     */
    public void add(Rectangle r, List<Stripe> list) {
        //width and x coord unchanged 
        //stripe is only adjusted vertically
        r.setPosition(x, y);
        list.remove(this);
        y += r.height;
        height -= r.height;
        list.remove(this);
        insertSort(this, list);
    }
}
