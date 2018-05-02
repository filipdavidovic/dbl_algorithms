/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author thanh
 */
public class InsertionSortTest {
    
    public InsertionSortTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of sort method, of class InsertionSort.
     */
    @Test
    public void testSort() {
        System.out.println("sort");
        Rectangle[] rectangles = new Rectangle[3];
        Rectangle a = new Rectangle(3, 3);
        Rectangle b = new Rectangle(1, 3);
        Rectangle c = new Rectangle(2, 3);
        rectangles[0] = a;
        rectangles[1] = b;
        rectangles[2] = c;
        System.out.println(rectangles[0].width);
        System.out.println(rectangles[1].width);
        System.out.println(rectangles[2].width);
        InsertionSort instance = new InsertionSort();
        Rectangle[] result = new Rectangle[3];
        result = instance.sort(rectangles);
        System.out.println(result[0].width);
        System.out.println(result[1].width);
        System.out.println(result[2].width);
        assertEquals(result[0].width, 3);
        assertEquals(result[1].width, 2);
        assertEquals(result[2].width, 1);
        fail("The test case is a prototype.");
    }
    
}
