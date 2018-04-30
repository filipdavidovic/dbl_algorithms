/**
 * Group 7 project for DBL Algorithms
 */
package dbl_algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class of the program. Execute the program by running this class and providing appropriate input.
 */
public class DefaultStrategy extends PackingStrategy {

    private static BufferedReader br; 
    private GUI drawing;
    File file;

    /**
     * Method that places given rectangles one next to each other in order they are provided.
     * The width of this container will be the sum of all rectangles' widths (x-coordinate).
     *
     * @param rectangles - rectangles to be ordered.
     * @return - lower x and lower y coordinates of each of the rectangles.
     */
    private int[][] noSort(int[][] rectangles) {
        int[][] ret = new int[rectangles.length][2];

        int sum = 0;

        for(int i = 0; i < rectangles.length; i++) {
            ret[i][0] = sum;
            ret[i][1] = 0;
            sum += rectangles[i][0];
        }

        return ret;
    }

    /**
     * Method to collect input and run appropriate an algoritm based on it.
     *
     * @throws IOException
     */
    @Override
    protected void pack() throws IOException, FileNotFoundException {
        /* input */
        // container height: {free, fixed}
        try {
        file = new File("file.txt");    
        br =  new BufferedReader(new FileReader(file));
        String containerHeight = br.readLine().split("container height: ")[1]; // {free, fixed}

        // rotations allowed: {yes, no}
        String rotationsAllowed = br.readLine().split("rotations allowed: ")[1]; // {yes, no}

        // number of rectangles: n
        int numberOfRectangles = Integer.parseInt(br.readLine().split("number of rectangles: ")[1]); // n
        System.out.println(numberOfRectangles);
        // rectangle input
        int[][] rectangles = new int [numberOfRectangles][2];

        for(int i = 0; i < numberOfRectangles; i++) {
            String[] input = br.readLine().split(" ");
            rectangles[i][0] = Integer.parseInt(input[0]);
            System.out.print(rectangles[i][0] + " ");
            rectangles[i][1] = Integer.parseInt(input[1]);
            System.out.println(rectangles[i][1]);
            System.out.println("end of iteration iteration " + i);
            
        }

        /* output */
        System.out.println("container height: " + containerHeight);
        System.out.println("rotations allowed: " + rotationsAllowed);
        System.out.println("number of rectangles: " + numberOfRectangles);
        for(int i = 0; i < numberOfRectangles; i++) {
            System.out.println(rectangles[i][0] + " " + rectangles[i][1]);
        }
        System.out.println("placement of rectangles");
        int[][] placement = noSort(rectangles);
        for(int i = 0; i < numberOfRectangles; i++) {
            System.out.println("no " + placement[i][0] + " " + placement[i][1]);
        }
        drawing = new GUI(placement, numberOfRectangles, rectangles);
        drawing.run();
        } catch (FileNotFoundException e) {
            System.out.print(e);
            if (file.exists()) {
            System.out.println("I do!");
        }
        } catch (IOException e) {
            System.out.print(e);
        }
    }
}

