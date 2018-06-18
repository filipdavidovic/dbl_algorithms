/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author vcucu
 */
public class GATestGenerator {
    

    private GUI drawing;
    private int containerHeight;
    private boolean rotationsAllowed;
    private int numberOfRectangles;
    //private boolean sort;
    List<PackingStrategy> listOfStrategies;
    Random rand;
    //Settings for the intervals of randomness
    final int RANDOM_TEST_CASES = 100;
    final int RANDOM_CONTAINER_HEIGHT = 201;
    final int[] ARRAY_NR_RECTANGLES = new int[] {3,5,10,25,5000};
    final int RANDOM_WIDTH = 201;
    final int RANDOM_HEIGHT = 201;
    //constraints on rectangles shape
    final boolean SQUARE_SHAPE = false;
    // set lower bound on rectangles size
    final boolean BIG_SIZE = true;
    //make sure it is smaller than the max!!
    final int minDimensionSize = 20;

    public GATestGenerator() {
        this.rand = new Random();
    }
    //getters used in printing
    public int getContainerHeight() {
        return containerHeight;
    }

    public boolean isRotationsAllowed() {
        return rotationsAllowed;
    }

    public int getNumberOfRectangles() {
        return numberOfRectangles;
    }

    public void run() throws IOException, CloneNotSupportedException {

        String COMMA_DELIMITER = ", ";
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "#input, Strategy, Shape Filter, Container Height, "
                + "Rotations Allowed, Number of rectangles, "
                + "Fill rate, m, max_lopps, p_m";
        int randomizeFileName = rand.nextInt(50);
        FileWriter fileWriter = new FileWriter("inputsGA"+randomizeFileName+".csv");
        fileWriter.append(FILE_HEADER);
        for (int count = 1; count <= RANDOM_TEST_CASES; count++){
            //generate random container height: free or fixed aka 0 or 1
            //if containerHeight  is 1 (fixed), then generate random height.
            listOfStrategies = new ArrayList<>();
            //choose randomly whether its gonna be fixed or free height
            containerHeight = rand.nextInt(2);
            containerHeight = 1; //HARDCODED FOR TESTING
            if (containerHeight == 0) {
                containerHeight = -1;
            } else if (containerHeight == 1) {
                if (BIG_SIZE) {
                    containerHeight = minDimensionSize +
                            rand.nextInt(RANDOM_CONTAINER_HEIGHT);
                } else {
                    //should be at least 2
                    containerHeight = 2 + rand.nextInt(RANDOM_CONTAINER_HEIGHT);
                }
            }
            //generate random boolean values for rotations
            rotationsAllowed = rand.nextBoolean();
            //rotationsAllowed = false; //HARDCODED FOR TESTING
            //generate random number of rectangles
            // set to be al least 5
            int inputSize =  rand.nextInt(5);
            numberOfRectangles = ARRAY_NR_RECTANGLES[inputSize];
            numberOfRectangles = 25; //HARDCODED FOR TESTING
            //create array of rectangles to store all of them
            Rectangle[] rectangles = new Rectangle [numberOfRectangles];
            // generate n random widths and heights for the rectangles
            for (int i = 0; i < numberOfRectangles; i++) {
                if (containerHeight != -1) {
                    // container height is fixed
                    // set max random dimenion size to container height
                    if (SQUARE_SHAPE){
                        int width = rand.nextInt(containerHeight);
                        int height = width;
                        rectangles[i] = new Rectangle(width, height);
                    } else {
                        int width = rand.nextInt(containerHeight);
                        int height = rand.nextInt(containerHeight);
                        rectangles[i] = new Rectangle(width, height);
                    }

                } else { // container height not fixed, so equal to -1
                    // assign random values
                    if (SQUARE_SHAPE) {
                        int width = 1 + rand.nextInt(RANDOM_WIDTH - 1);
                        int height = width;
                        rectangles[i] = new Rectangle(width, height);
                    } else if (BIG_SIZE) {
                        int width = minDimensionSize + rand.nextInt(RANDOM_WIDTH);
                        int height = minDimensionSize + rand.nextInt(RANDOM_HEIGHT);
                        rectangles[i] = new Rectangle(width, height);
                    } else {
                        int width = 1 + rand.nextInt(RANDOM_WIDTH - 1);
                        int height = 1 + rand.nextInt(RANDOM_HEIGHT - 1);
                        rectangles[i] = new Rectangle(width, height);
                    }
                }
            }
            

            PackingStrategy strategy;
            int m;
            int max_loops;
            
            for (m = 10; m < 151; m+= 5){
                for (max_loops = 2000; max_loops < 2001; max_loops += 100){
                    double p_m = 0.9;
                    while (p_m < 1.0){
                        strategy = new GeneticAlgorithm(this.getContainerHeight(),
                                true, rectangles, m, max_loops, p_m);
                        State s = strategy.pack();
                        //drawing = new GUI(s);
                        //drawing.run();
                        fileWriter.append(NEW_LINE_SEPARATOR);
                        fileWriter.append(String.valueOf(count));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(strategy.getClass().getSimpleName());
                        fileWriter.append(COMMA_DELIMITER);
                        if (SQUARE_SHAPE){
                            fileWriter.append("Squares");
                        } else if (BIG_SIZE) {
                            fileWriter.append("Big size");
                        } else {
                            fileWriter.append("None");
                        }
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(this.getContainerHeight()));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(this.isRotationsAllowed()));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(this.getNumberOfRectangles()));

                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(s.getFillRate()));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(m));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(max_loops));
                        fileWriter.append(COMMA_DELIMITER);
                        fileWriter.append(String.valueOf(p_m));
                        //to see the progress
                        System.out.println(count);
                        //System.out.println(strategy.getClass());
                        
                        p_m += 0.2;
                    }
                }
            }
        }
        fileWriter.flush();
        fileWriter.close();

    }
    

}
