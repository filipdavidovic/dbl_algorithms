/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.List;

/**
 * Insertion sort modified for sorting stripes by increasing area 
 * @author Thanh-Dat and Tijana Klimovic
 */
public final class InsertionSortListStripe {

    /**
     * adds and sorts s into sorted list by increasing area
     * @param s stripe added to sorted list 
     * @param list list of stripes sorted by increasing area
     * @modifies list
     */
    public static void insertSort(Stripe s, List<Stripe> list) {
        int area = s.height * s.width;
        for (Stripe stripe : list) {
            int areaCheck = stripe.height * stripe.width;
            if (area < areaCheck) {
                list.add(list.indexOf(stripe), s);
                return;
            }
        }
        list.add(s);
    }
}