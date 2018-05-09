/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.ByteArrayInputStream;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Veronika
 */
public class PackingSolverTest {
    
    public PackingSolverTest() {
    }

    /**
     * Test of main method, of class PackingSolver.
     */
    @Test
    public void testMain() throws Exception {
        
        String[] args = null;    
        
        int size =  25;
        boolean rotations = false;
        int fixed = 101; 
        
        String input = generateHeader(size, rotations, fixed);
        input =  generateInput(size, input);
        System.out.println(input);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        PackingSolver.main(args);
      
    }
    
    
    private String generateInput(int size, String headers){
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(headers);
        Random rand = new Random();
        
        for (int i = 0; i < size; i++){
            stringBuilder.append(System.lineSeparator());
            
            int height = rand.nextInt(100+1);
            stringBuilder.append(height);
            
            stringBuilder.append(" ");
            
            int width = rand.nextInt(100+1);
            stringBuilder.append(width);
        }
        
        String finalString = stringBuilder.toString();
        return finalString;
    }
    
    private String generateHeader(int size, boolean rotations, int fixed){
        StringBuilder stringBuilder = new StringBuilder();
        
        //write container height
        //if fixed == -1 -> free
        stringBuilder.append("container height: ");
        if (fixed == -1){
            stringBuilder.append("free");
        } else {
            stringBuilder.append("fixed "+fixed);
        }
        stringBuilder.append(System.lineSeparator());
        
        //write rotations
        stringBuilder.append("rotations allowed: ");
        if (rotations){
            stringBuilder.append("yes");
        } else {
            stringBuilder.append("no");
        }
        stringBuilder.append(System.lineSeparator());
        
        //write number of rectangles
        stringBuilder.append("number of rectangles: "+size);
        
        
        String finalString = stringBuilder.toString();
        return finalString;
    }
    
}
