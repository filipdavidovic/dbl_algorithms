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
        int maxHeight = getMaxHeight(rectangles);
        int initialContainerHeight = 2 * Math.max(minSide, maxHeight);
        int stepSize = (initialContainerHeight - maxHeight)/25; 
        for (int i = initialContainerHeight; i >= maxHeight; i -= stepSize) { 
            PackingStrategy strategy = new DefaultStripe(i, rotationsAllowed, rectangles); 
            State newState = strategy.pack();
            //check if newState is better
            if (newState.layoutWidth < bestState.layoutWidth) {
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
