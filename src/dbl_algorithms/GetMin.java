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
public final class GetMin {
    
    public static int getMinWidth(Rectangle[] r) {
        return r[r.length - 1].width;
    }
    
    public static int getMinHeight(Rectangle[] r) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < r.length; i++) {
            if (r[i].height < min) {
                min = r[i].height;
            }
        }
        return min;
    }
}
