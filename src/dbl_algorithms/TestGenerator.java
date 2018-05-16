/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *
 * @author dianaepureanu
 */
public class TestGenerator {
    private int containerHeight;
    private boolean rotationsAllowed;
    private int numberOfRectangles;
    private boolean sort;
    List<PackingStrategy> listOfStrategies;
    Random rand;
    //Settings for the intervals of randomness
    final int RANDOM_TEST_CASES = 1000;
    final int RANDOM_CONTAINER_HEIGHT = 201;
    final int RANDOM_NR_RECTANGLES = 201;
    final int RANDOM_WIDTH = 201;
    final int RANDOM_HEIGHT = 201;
    //constraints on rectangles shape
    final boolean SQUARE_SHAPE = false;
    // set lower bound on rectangles size
    final boolean BIG_SIZE = false;
    //make sure it is smaller than the max!!
    final int minDimensionSize = 100;

    public TestGenerator() {
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

    public boolean isSort() {
        return sort;
    }
  
    
    public void run() throws IOException, CloneNotSupportedException { 
        
        String COMMA_DELIMITER = ", ";
        String NEW_LINE_SEPARATOR = "\n";
        String FILE_HEADER = "#input, Strategy, Shape Filter, Container Height, "
                + "Rotations Allowed, Number of rectangles, Input sorted, "
                + "Fill rate";
        FileWriter fileWriter = new FileWriter("inputs.csv");
        fileWriter.append(FILE_HEADER);
        for (int count = 1; count <= RANDOM_TEST_CASES; count++){
            //generate random container height: free or fixed aka 0 or 1
            // if containerHeight  is 1 (fixed), then generate random height.
            listOfStrategies = new ArrayList<>();
            containerHeight = rand.nextInt(2);
            if (containerHeight == 0) {
                containerHeight = -1;
            } else if (containerHeight == 1) {
                if (BIG_SIZE) {
                    containerHeight = minDimensionSize + 
                            rand.nextInt(RANDOM_CONTAINER_HEIGHT - minDimensionSize);
                } else {
                //should be at least 2
                containerHeight = 2 + rand.nextInt(RANDOM_CONTAINER_HEIGHT - 2);
                }
            }
            //generate random boolean values for rotations
            rotationsAllowed = rand.nextBoolean();
            //generate random number of rectangles
            // set to be al least 5
            numberOfRectangles = 5 + rand.nextInt(RANDOM_NR_RECTANGLES - 5);
            //create array of rectangles to store all of them
            Rectangle[] rectangles = new Rectangle [numberOfRectangles];
            // generate n random widths and heights for the rectangles
            for (int i = 0; i < numberOfRectangles; i++) {
                if (containerHeight != -1) { 
                    // container height is fixed
                    // set max random dimenion size to container height
                    if (SQUARE_SHAPE){ 
                        int width = rand.nextInt(containerHeight);
                        int height = rand.nextInt(containerHeight);
                        rectangles[i] = new Rectangle(width, height);
                    } else if (BIG_SIZE) {
                        int width = minDimensionSize + rand.nextInt(containerHeight - minDimensionSize);
                        int height = minDimensionSize + rand.nextInt(containerHeight- minDimensionSize);
                        rectangles[i] = new Rectangle(width, height);
                    } else {
                        int width = rand.nextInt(containerHeight);
                        int height = rand.nextInt(containerHeight);
                        rectangles[i] = new Rectangle(width, height);
                    }

                } else { // container height not fixed 
                    // assign random values
                    if (SQUARE_SHAPE) {    
                        int width = 1 + rand.nextInt(RANDOM_WIDTH - 1);
                        int height = width;
                        rectangles[i] = new Rectangle(width, height);
                    } else if (BIG_SIZE) {
                        int width = minDimensionSize - rand.nextInt(RANDOM_WIDTH - minDimensionSize);
                        int height = minDimensionSize - rand.nextInt(RANDOM_HEIGHT - minDimensionSize);
                        rectangles[i] = new Rectangle(width, height);
                    } else {
                        int width = 1 + rand.nextInt(RANDOM_WIDTH - 1);
                        int height = 1 + rand.nextInt(RANDOM_HEIGHT - 1);
                        rectangles[i] = new Rectangle(width, height);
                    }
                    }
            }  
            //generate random boolean for 'sort' parameter -- check constructor of
            // BottomLeft if in doubt.
            sort = rand.nextBoolean();
            //pass the generated input to all the strategies
            //
            // note: i did not put all strategies in an arraylist and then loop through
            //them since they need to be instantiated with different parameters
            // so i could not generalise it without restructuring the constructors
            // of all strategies individually;

            //add BottomLeft
            PackingStrategy strategy_1 = new BottomLeft(this.getContainerHeight(), 
                    this.isRotationsAllowed(),  rectangles, this.isSort());
            listOfStrategies.add(strategy_1);
            //add DefaultStripe
            PackingStrategy strategy_2 = new DefaultStripe(this.getContainerHeight(),
            this.isRotationsAllowed(), rectangles);
            listOfStrategies.add(strategy_2);
            //write csv file
            for (PackingStrategy strategy: listOfStrategies) {
                State s = strategy.pack();
                fileWriter.append(NEW_LINE_SEPARATOR);
                fileWriter.append(String.valueOf(count));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(strategy.getClass().getSimpleName());
                fileWriter.append(COMMA_DELIMITER);
                if (SQUARE_SHAPE){
                    fileWriter.append("Squares");
                } else if (BIG_SIZE) {
                    fileWriter.append("Big rectangles");
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
                if (strategy == strategy_1) {
                    fileWriter.append(String.valueOf(this.isSort()));
                } else {
                    fileWriter.append('-');
                }
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(s.getFillRate()));
                //fileWriter.append(COMMA_DELIMITER);
                
                //print same stuff -- for debugging
                System.out.println("Input number: " + count);
                System.out.println("Strategy: " + strategy.getClass().getSimpleName());
                if (SQUARE_SHAPE){
                    System.out.println("Shape filter: Squares");
                } else if (BIG_SIZE) {
                    System.out.println("Shape filter: Big rectangles");
                } else {
                    System.out.println("Shape filter: None");
                }
                
                System.out.println("Container height: " + this.getContainerHeight() );
                System.out.println("Rotations allowed: " + this.isRotationsAllowed());
                System.out.println("Number of rectangles:" + this.getNumberOfRectangles());
                if (strategy == strategy_1) {
                    System.out.println("Input sorted: " + this.isSort());
                } else {
                    System.out.println("Input sorted: -");
                }
                System.out.println("Fill rate: " + s.getFillRate());
                System.out.println();
            }
        }
        fileWriter.flush();
        fileWriter.close();

    }
    

    
//    public void testMain() throws Exception {
//        
//        String[] args = null;    
//        
//        int size =  25;
//        boolean rotations = false;
//        int fixed = 101; 
//        
//        String input = generateHeader(size, rotations, fixed);
//        input =  generateInput(size, input);
//        System.out.println(input);
//        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        //PackingSolver.main(args);
//      
//    }
//    
//    
//    private String generateInput(int size, String headers){
//        
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(headers);
//        Random rand = new Random();
//        
//        for (int i = 0; i < size; i++){
//            stringBuilder.append(System.lineSeparator());
//            
//            int height = rand.nextInt(100+1);
//            stringBuilder.append(height);
//            
//            stringBuilder.append(" ");
//            
//            int width = rand.nextInt(100+1);
//            stringBuilder.append(width);
//        }
//        
//        String finalString = stringBuilder.toString();
//        return finalString;
//    }
//    
//    private String generateHeader(int size, boolean rotations, int fixed){
//        StringBuilder stringBuilder = new StringBuilder();
//        
//        //write container height
//        //if fixed == -1 -> free
//        stringBuilder.append("container height: ");
//        if (fixed == -1){
//            stringBuilder.append("free");
//        } else {
//            stringBuilder.append("fixed "+fixed);
//        }
//        stringBuilder.append(System.lineSeparator());
//        
//        //write rotations
//        stringBuilder.append("rotations allowed: ");
//        if (rotations){
//            stringBuilder.append("yes");
//        } else {
//            stringBuilder.append("no");
//        }
//        stringBuilder.append(System.lineSeparator());
//        
//        //write number of rectangles
//        stringBuilder.append("number of rectangles: "+size);
//        
//        
//        String finalString = stringBuilder.toString();
//        return finalString;
//    }
}
