/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 *
 * @author dianaepureanu
 */
public class Context {
    
    PackingStrategy strategy;
    //read first 3 lines of input and select the strategy
    // currently it always chooses PackingSolver by default
    public Context () {
        this.strategy = new PackingSolver();
    }
    
    protected void run() {
        try{
        strategy.pack();
        }catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) throws IOException {
        new Context().run();
    } 
}
