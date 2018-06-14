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
            State state = null;
            PackingStrategy s1, s2, s3, s4;
            State st1,st2,st3,st4;
            
            switch (numberOfRectangles) {

            case 3:
                s1 = new BruteForce(containerHeight, rotationsAllowed, rectangles);
                state = s1.pack();
                break;
            case 5:
                s1 = new BruteForce(containerHeight, rotationsAllowed, rectangles);
                state = s1.pack();
                break;
            case 10:
                if (containerHeight == -1) {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new StripeNonFixed(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.fillRate > st2.fillRate) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                    break;
                } else {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    s3 = new GeneticAlgorithm(containerHeight, rotationsAllowed, rectangles, 20, 2000, 0.4);
                    s4 = new SimulatedAnnealing(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    st3 = s3.pack();
                    st4 = s4.pack();
                    if (st1.layoutWidth < st2.layoutWidth && st1.layoutWidth < st3.layoutWidth && st1.layoutWidth < st4.layoutWidth) {
                        state = st1;
                    } else if (st2.layoutWidth < st3.layoutWidth && st2.layoutWidth < st4.layoutWidth) {
                        state = st2;
                    } else if (st3.layoutWidth < st4.layoutWidth) {
                        state = st3;
                    } else {
                        state = st4;
                    }
                    break;
                }
            case 25:
                if (containerHeight == -1) {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new StripeNonFixed(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.fillRate > st2.fillRate) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                    break;
                } else {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    s3 = new GeneticAlgorithm(containerHeight, rotationsAllowed, rectangles, 20, 2000, 0.4);
                    s4 = new SimulatedAnnealing(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    st3 = s3.pack();
                    st4 = s4.pack();
                    if (st1.layoutWidth < st2.layoutWidth && st1.layoutWidth < st3.layoutWidth && st1.layoutWidth < st4.layoutWidth) {
                        state = st1;
                    } else if (st2.layoutWidth < st3.layoutWidth && st2.layoutWidth < st4.layoutWidth) {
                        state = st2;
                    } else if (st3.layoutWidth < st4.layoutWidth) {
                        state = st3;
                    } else {
                        state = st4;
                    }
                    break;
                }
            case 5000:
                if (containerHeight == -1) {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new StripeNonFixed(containerHeight, rotationsAllowed, rectangles);
                    
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.layoutWidth < st2.layoutWidth) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                } else {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.layoutWidth < st2.layoutWidth) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                }
                break;
            case 10000:
                if (containerHeight == -1) {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new StripeNonFixed(containerHeight, rotationsAllowed, rectangles);
                    
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.layoutWidth < st2.layoutWidth) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                } else {
                    s1 = new BinPackerHeightPicker(containerHeight, rotationsAllowed, rectangles);
                    s2 = new DefaultStripe(containerHeight, rotationsAllowed, rectangles);
                    st1 = s1.pack();
                    st2 = s2.pack();
                    if (st1.layoutWidth < st2.layoutWidth) {
                        state = st1;
                    } else {
                        state = st2;
                    }
                }
                break;
            }

            state.reorder();
            printOutput(state.getLayout(), containerHeight, rotationsAllowed);

            drawing = new GUI(state);
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
            if (rectangle.rotated) {
                System.out.println(rectangle.height + " " + rectangle.width);
            } else {
                System.out.println(rectangle.width + " " + rectangle.height);
            }
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
        // new TestGenerator().run();
    }
}
