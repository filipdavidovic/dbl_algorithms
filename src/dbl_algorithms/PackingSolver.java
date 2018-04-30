/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.IOException;

/**
 *
 * @author dianaepureanu
 */
/** CONTEXT. */
public class PackingSolver {
    
    PackingStrategy strategy;

    /**
     * read first 3 lines of input and select the strategy
     * currently it always chooses PackingSolver by default
     */
    public PackingSolver () {
        this.strategy = new DefaultStrategy();
    }
    
    private void run() {
        try {
            strategy.pack();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        new PackingSolver().run();
    } 
}
