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
/**
 * CONTEXT.
 */
public class PackingSolver {

    PackingStrategy strategy;
    private GUI drawing;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * read first 3 lines of input and select the strategy currently it always
     * chooses PackingSolver by default
     */
    public PackingSolver() {
//        this.strategy = new DefaultStrategy();
    }

    private void run() throws CloneNotSupportedException {
        try {
            /* input */
            // container height: {free, fixed x}
            int containerHeight;
            String containerHeightString = br.readLine().split("container height: ")[1];
            if (containerHeightString.equals("free")) {
                containerHeight = -1;
            } else {
                containerHeight = Integer.parseInt(containerHeightString.split(" ")[1]);
            }

            // rotations allowed: {yes, no}
            boolean rotationsAllowed = br.readLine().split("rotations allowed: ")[1].equals("yes"); // {yes, no}

            // number of rectangles: n
            int numberOfRectangles = Integer.parseInt(br.readLine().split("number of rectangles: ")[1]); // n
            // rectangle input
            Rectangle[] rectangles = new Rectangle[numberOfRectangles];

            int maxWidth = 0;
            int minWidth = Integer.MAX_VALUE;
            int maxHeigth = 0;
            int minHeight = Integer.MAX_VALUE;
            for (int i = 0; i < numberOfRectangles; i++) {
                String[] input = br.readLine().split(" ");
                int width = Integer.parseInt(input[0]);
                int height = Integer.parseInt(input[1]);
                rectangles[i] = new Rectangle(width, height);
                rectangles[i].setInitialposition(i);
                // populate extreme value variables
                if (width > maxWidth) {
                    maxWidth = width;
                }
                if (width < minWidth) {
                    minWidth = width;
                }
                if (height > maxHeigth) {
                    maxHeigth = height;
                }
                if (height < minHeight) {
                    minHeight = height;
                }
            }

            // TODO: make the selection for the proper algorithm

            switch (numberOfRectangles) {
            case 3:
                strategy = new BruteForce(containerHeight, rotationsAllowed, rectangles);
                break;
            case 5:
                strategy = new BruteForce(containerHeight, rotationsAllowed, rectangles);
                break;
            case 10:
                if (containerHeight == -1) {
                    strategy = new DefaultStrategy(containerHeight, rotationsAllowed, rectangles);
                    break;
                } else {
                    strategy = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    break;
                }
            case 25:
                if (containerHeight == -1) {
                    strategy = new DefaultStrategy(containerHeight, rotationsAllowed, rectangles);
                    break;
                } else {
                    strategy = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    break;
                }
            case 5000:
                if (containerHeight == -1) {
                    strategy = new BinPacker(containerHeight, rotationsAllowed, rectangles);
                } else {
                    strategy = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                }
                break;
            case 10000: 
                if (containerHeight == -1) {
                    strategy = new BinPacker(containerHeight, rotationsAllowed, rectangles);
                } else {
                    strategy = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                }
                break;
            }


            //strategy = new BinPacker(containerHeight, rotationsAllowed, rectangles);
            State s = strategy.pack();
            //s.reorder();
            printOutput(s.getLayout(), containerHeight, rotationsAllowed);

            drawing = new GUI(s);
            drawing.run();

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private void printOutput(Rectangle[] layout, int containerHeight, boolean rotationsAllowed) {

        System.out.println("container height: " + containerHeight);
        System.out.println("rotations allowed: " + rotationsAllowed);
        System.out.println("number of rectangles: " + layout.length);
        for (Rectangle rectangle : layout) {
            System.out.println(rectangle.width + " " + rectangle.height);
        }

        System.out.println("placement of rectangles");
        for (int i = 0; i < layout.length; i++) {
            if (layout[i].rotated) {
                System.out.print("yes ");
            } else {
                System.out.print("no ");
            }
            System.out.println(layout[i].blx + " " + layout[i].bly);
        }
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        new PackingSolver().run();
        //new TestGenerator().run();
    }
}
