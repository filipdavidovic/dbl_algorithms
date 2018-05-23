/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.List;

/**
 *
 * @author thanh
 */
public final class InsertionSortListStripe {

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