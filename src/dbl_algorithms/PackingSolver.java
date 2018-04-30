/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author dianaepureanu
 */
/** CONTEXT. */
public class PackingSolver {
    
    PackingStrategy strategy;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * read first 3 lines of input and select the strategy
     * currently it always chooses PackingSolver by default
     */
    public PackingSolver () {
//        this.strategy = new DefaultStrategy();
    }

    private void run() {
        try {
            /* input */
            // container height: {free, fixed}
            String containerHeight = br.readLine().split("container height: ")[1]; // {free, fixed}

            // rotations allowed: {yes, no}
            boolean rotationsAllowed = br.readLine().split("rotations allowed: ")[1].equals("yes"); // {yes, no}

            // number of rectangles: n
            int numberOfRectangles = Integer.parseInt(br.readLine().split("number of rectangles: ")[1]); // n
            // rectangle input
            Rectangle[] rectangles = new Rectangle [numberOfRectangles];

            for(int i = 0; i < numberOfRectangles; i++) {
                String[] input = br.readLine().split(" ");
                rectangles[i] = new Rectangle(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            }

            // TODO: make the selection for the proper algorithm
            strategy = new DefaultStrategy(containerHeight, rotationsAllowed, rectangles);

            strategy.pack();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        new PackingSolver().run();
    } 
}
