package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 *  Solver for non fixed height that uses DefaultStripe by iterating over different container heights
 */
public class StripeNonFixed extends PackingStrategy {

    boolean rotationsAllowed;
    Rectangle[] rectangles;

    StripeNonFixed(boolean rotationsAllowed, Rectangle[] rectangles) {
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, CloneNotSupportedException {
        int minSide = getMinAreaSide(); //used to define the container height
        State bestState = new State(rectangles.length); //keep track of best encountered state
        for (int i = minSide; i > minSide / 2.5; i -= minSide/25) { //arbitrary choice of container heights
            PackingStrategy strategy = new DefaultStripe(i, rotationsAllowed, rectangles); 
            State newState = strategy.pack();
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
}
