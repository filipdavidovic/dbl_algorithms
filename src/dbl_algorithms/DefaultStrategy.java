/**
 * Group 7 project for DBL Algorithms
 */
package dbl_algorithms;

import java.io.IOException;

/**
 * Main class of the program. Execute the program by running this class and
 * providing appropriate input.
 */
public class DefaultStrategy extends PackingStrategy {

    private GUI drawing;
    private Rectangle[] rectangles;
    private int containerHeight;
    private boolean rotationsAllowed;

    DefaultStrategy(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }


    /**
     * Method to collect input and run appropriate an algorithm based on it.
     *
     * Method that places given rectangles one next to each other in order they
     * are provided. The width of this container will be the sum of all
     * rectangles' widths (x-coordinate)
     * @return 
     * @throws IOException
     */
    @Override
    protected State pack() throws IOException {
        
        State s;
        s = new State(rectangles.length, containerHeight);

        int sum = 0;

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].setPosition(sum, 0);
            s.addRectangle(rectangles[i]);
            sum += rectangles[i].width;
        }
       
        return s;
        
    }
}
