/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.List;
import static dbl_algorithms.InsertionSortListStripe.*;

/**
 *
 * @author thanh-dat
 */
public class Stripe {

    //never changed in stripe adjustment
    final int x;
    final int width;
    int y;
    int height;

    Stripe(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    private boolean check(Rectangle r) {
        if (r.height <= height && r.width <= width) {
            return true;
        }
        return false;
    }

    public boolean add(Rectangle r, List<Stripe> list, int minWidth, int minHeight) {
        if (check(r)) {
            r.setPosition(x, y);

            if (width - r.width >= minWidth || r.height >= minHeight) {
                Stripe stripe = new Stripe(x + r.width, y, width - r.width, r.height);
                insertSort(stripe, list);
            }

            list.remove(this);
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
