/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tea
 */
public class QuickSortTest {
    Rectangle[] rectangles = new Rectangle[6];
    public QuickSortTest() {
    }
    protected void makeTester(){
        

        Rectangle r1 = new Rectangle(3,3);
        Rectangle r2 = new Rectangle(1,1);
        Rectangle r3 = new Rectangle(6,6);
        Rectangle r4 = new Rectangle(2,2);
        Rectangle r5 = new Rectangle(7,7);
        Rectangle r6 = new Rectangle(4,4);
        rectangles[0] = r1;
        rectangles[1] = r2;
        rectangles[2] = r3;
        rectangles[3] = r4;
        rectangles[4] = r5;
        rectangles[5] = r6;
    }
    
    private void printRectangleWidth(Rectangle[] r){
        System.out.println(r[0].width);
        System.out.println(r[1].width);
        System.out.println(r[2].width);
        System.out.println(r[3].width);
        System.out.println(r[4].width);
        System.out.println(r[5].width);
    }

    /**
     * Test of sort method, of class QuickSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");
        makeTester();
        printRectangleWidth(rectangles);
        System.out.println("--------------------");
        QuickSort instance = new QuickSort();
        Rectangle[] result;
        result = instance.sort(rectangles);
        printRectangleWidth(result);
        assertEquals(7, result[0].width);
        assertEquals(6, result[1].width);
        assertEquals(4, result[2].width);
        assertEquals(3, result[3].width);
        assertEquals(2, result[4].width);
        assertEquals(1, result[5].width);
        
    }

    /**
     * Test of swap method, of class QuickSort.
     */
    @Test
    public void testSwap() {
        System.out.println("swap");
        Rectangle r1 = new Rectangle(3,3);
        Rectangle r2 = new Rectangle(1,1);
        
        QuickSort instance = new QuickSort();
        instance.swap(r1, r2);
        // TODO review the generated test code and remove the default call to fail.
        System.out.println("r1 "+r1.width);
        System.out.println("r2 "+r2.width);
         
        assertEquals(1, r1.width);
        assertEquals(3, r2.width);
        
        
    }

    /**
     * Test of partition method, of class QuickSort.
     */
    @Test
    public void testPartition() {
        System.out.println("partition");
        Rectangle[] rectangles = new Rectangle[6];
        Rectangle r1 = new Rectangle(3,3);
        Rectangle r2 = new Rectangle(1,1);
        Rectangle r3 = new Rectangle(6,6);
        Rectangle r4 = new Rectangle(2,2);
        Rectangle r5 = new Rectangle(7,7);
        Rectangle r6 = new Rectangle(4,4);
        rectangles[0] = r1;
        rectangles[1] = r2;
        rectangles[2] = r3;
        rectangles[3] = r4;
        rectangles[4] = r5;
        rectangles[5] = r6;
        int hi = 5;
        int lo = 0;
        QuickSort instance = new QuickSort();
        int expResult = 0;
        int result = instance.partition(rectangles, lo, hi);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
