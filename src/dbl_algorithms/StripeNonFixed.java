package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *  Solver for non fixed height that uses DefaultStripe by iterating over different container heights
 */
public class StripeNonFixed extends PackingStrategy {

    boolean rotationsAllowed;
    Rectangle[] rectangles;

    StripeNonFixed(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, CloneNotSupportedException {
        int minSide = getMinAreaSide(); //used to define the container height
        State bestState = new State(rectangles.length); //keep track of best encountered state
        int stepSize = 25;
        //arbitrary choice of container heights
        for (int i = 2 * minSide; i >= getMaxHeight(rectangles); i -= minSide/stepSize) { 
            PackingStrategy strategy = new DefaultStripe(i, rotationsAllowed, rectangles); 
            State newState = strategy.pack();
            //System.out.println(i + " " + newState.fillRate);
            //check if newState is better
            if (newState.fillRate > bestState.fillRate) {
                bestState = newState;
            }
        }
                
        return bestState;
    }

    /*
    * Computes the area of all the rectangles, and returns the length of a side 
    * of a square with that same area
    */
    int getMinAreaSide() {
        int result = 0;
        for (Rectangle r : rectangles) {
            result += r.height * r.width;    
        }
        return (int) Math.sqrt(result);
    }
    
    /*
    * get max height for all the rectangles(for lowerbound of container height)
    */
    public int getMaxHeight(Rectangle[] r) {
        int max = 0;
        for (int i = 0; i < r.length; i++) {
            if (r[i].height > max) {
                max = r[i].height;
            }
        }
        return max;
    }
}
