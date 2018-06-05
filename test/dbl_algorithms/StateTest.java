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
public class StateTest {
    
    public StateTest() {
    }

    
    /**
     * Test of getArea method, of class State.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        State instance = new State(3);
        Rectangle r1 = new Rectangle(1, 1);
        Rectangle r2 = new Rectangle(1, 2);
        Rectangle r3 = new Rectangle(3, 1);
        r1.setPosition(0,0);
        r2.setPosition(1,0);
        r3.setPosition(0,2);
        instance.addRectangle(r1);
        instance.addRectangle(r2);
        instance.addRectangle(r3);   
        
        int result = instance.getArea();
        assertEquals(9, result);
        
    }

    
    /**
     * Test of getLayoutWidth method, of class State.
     */
    @Test
    public void testGetLayoutWidth() {
        System.out.println("getLayoutWidth");
      
        State instance = new State(3);

        Rectangle r1 = new Rectangle(1, 1);
        Rectangle r2 = new Rectangle(1, 2);
        Rectangle r3 = new Rectangle(3, 1);
        r1.setPosition(0,0);
        r2.setPosition(1,0);
        r3.setPosition(0,2);
        instance.addRectangle(r1);
        instance.addRectangle(r2);
        instance.addRectangle(r3);   
        
        
        int result = instance.getLayoutWidth();
        assertEquals(3, result);
        
    }

    /**
     * Test of getLayoutHeight method, of class State.
     */
    @Test
    public void testGetLayoutHeight() {
        System.out.println("getLayoutHeight");
        State instance = new State(3);
        Rectangle r1 = new Rectangle(1, 1);
        Rectangle r2 = new Rectangle(1, 2);
        Rectangle r3 = new Rectangle(3, 1);
        r1.setPosition(0,0);
        r2.setPosition(1,0);
        r3.setPosition(0,2);
        instance.addRectangle(r1);
        instance.addRectangle(r2);
        instance.addRectangle(r3);   
        int result = instance.getLayoutHeight();
        assertEquals(3, result);
        
    }
    
}
