/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *
 * @author thanh
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

    /*
    add rectangle to the stripe and adjust available stripe
    
     */
    public boolean add(Rectangle r) {
        //width and x coord unchanged 
        //stipe is only adjusted vertically
        if (check(r)) {
            r.setPosition(x, y);
            y += r.height;
            height -= r.height;
            return true;
        }
        return false;
    }
}
