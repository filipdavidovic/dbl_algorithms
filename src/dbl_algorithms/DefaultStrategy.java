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
     * Method that places given rectangles one next to each other in order they
     * are provided. The width of this container will be the sum of all
     * rectangles' widths (x-coordinate).
     *
     * @param rectangles - rectangles to be ordered.
     * @return - lower x and lower y coordinates of each of the rectangles.
     */
    private State noSort(Rectangle[] rectangles) {
        State s;
        s = new State(rectangles.length);

        int sum = 0;

        for (int i = 0; i < rectangles.length; i++) {
            rectangles[i].setPosition(sum, 0);
            s.addRectangle(rectangles[i]);
            sum += rectangles[i].blx;
        }
        return s;
    }

    /**
     * Method to collect input and run appropriate an algorithm based on it.
     *
     * @throws IOException
     */
    @Override
    protected void pack() throws IOException {
        State placement = noSort(rectangles);

        /* output */
        System.out.println("container height: " + containerHeight);
        System.out.println("rotations allowed: " + rotationsAllowed);
        System.out.println("number of rectangles: " + rectangles.length);
        for (Rectangle rectangle : rectangles) {
            System.out.println(rectangle.width + " " + rectangle.height);
        }

        System.out.println("placement of rectangles");
        for (int i = 0; i < rectangles.length; i++) {
            System.out.println("no " + placement.getLayout()[i].blx + " " + placement.getLayout()[i].bly);
        }
              
//        drawing = new GUI(placement, numberOfRectangles, rectangles);
//        drawing.run();
    }
}
