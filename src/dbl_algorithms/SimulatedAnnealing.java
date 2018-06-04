package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealing extends PackingStrategy{

    private final int MAX_CONSECUTIVE_MUTATIONS;
    private final int MAX_SUCCESSFUL_MUTATIONS;

    private static Random rand = new Random();
    private int containerHeight;
    private boolean rotationsAllowed;
    private Rectangle[] rectangles;

    private PackingStrategy bl; // packing strategy
    private float temperature; // energy of the system, determines whether newly created states are good enough to be kept
    private int successfulMutations; // # of successful mutations with a single temperature value
    private int consecutiveMutations; // # of total mutations with a single temperature value
    private List<Integer> rotatable = new ArrayList<>();
    private State current; // current state that the strategy is in
    private State best;

    SimulatedAnnealing(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) throws IOException, CloneNotSupportedException {
        // set final variables according to the number of rectangles in the input
        this.MAX_CONSECUTIVE_MUTATIONS = 100 * rectangles.length;
        this.MAX_SUCCESSFUL_MUTATIONS = 10 * rectangles.length;

        // set parameter variables
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;

        // set variables used by this packing method
        this.temperature = 220f; // 220f
        this.bl = new BottomLeftSlide(containerHeight, false, rectangles, false);
        this.current = bl.pack();
        this.best = this.current;
        this.successfulMutations = 0;
        this.consecutiveMutations = 0;

        if(rotationsAllowed) {
            for(int i = 0; i < rectangles.length; i++) {
                if(rectangles[i].width <= containerHeight) {
                    rotatable.add(i);
                }
            }
        }
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException, CloneNotSupportedException {
        for(int i = 1; i <= 400; i++) {
            // mutate until the temperature decreases
            while(!checkDecreaseTemperature()) {
                mutate();
            }
        }
        return best;
    }

    /**
     * Method that decides whether the newly created state is good enough to be the current state.
     *
     * @param delta - difference in width between the current state and the newly created state.
     * @return - boolean value which is true if the new state is good enough, false otherwise.
     */
    private boolean keepMutation(int delta) {
        return delta < 0 || rand.nextFloat() < Math.exp((double) (-delta) / temperature);
    }

    /**
     * Method used to create a mutation, and if good enough replace the current state with the newly generated one.
     *
     * @throws IOException - required by the BottomLeft packing method.
     */
    private void mutate() throws IOException, CloneNotSupportedException {
        int currentWidth = current.getLayoutWidth();

        if((!rotationsAllowed || rand.nextBoolean()) && rotatable.size() == 0) {
            int i = rand.nextInt(rectangles.length - 1);
            int j = rand.nextInt(rectangles.length - 1);
            while(i == j) {
                j = rand.nextInt(rectangles.length - 1);
            }

            // swap rectangles in order to test the new permutation
            Rectangle temp = rectangles[i];
            rectangles[i] = rectangles[j];
            rectangles[j] = temp;

            State state = bl.pack();

            // TODO: might not be necessary
            if(best.getLayoutWidth() > state.getLayoutWidth()) {
                best = state;
            }

            int delta = currentWidth - state.getLayoutWidth();

            // if mutation is not good enough, return to the previous state
            // otherwise keep mutation
            if(!keepMutation(delta)) {
                temp = rectangles[i];
                rectangles[i] = rectangles[j];
                rectangles[j] = temp;
                // update counters
                consecutiveMutations++;
            } else {
                current = state.clone();
                // update counters
                consecutiveMutations++;
                successfulMutations++;
            }
        } else {
            int i = rotatable.get(rand.nextInt(rotatable.size()));

            // rotate the rectangle in order to test the new permutation
            rectangles[i].rotate();

            State state = bl.pack();

            // TODO: might not be necessary
            if(best.getLayoutWidth() > state.getLayoutWidth()) {
                best = state;
            }

            int delta = currentWidth - state.getLayoutWidth();

            // if mutation is not good enough, return to the previous state
            // otherwise keep mutation
            if(!keepMutation(delta)) {
                rectangles[i].rotate();
                // update counters
                consecutiveMutations++;
            } else {
                current = state.clone();
                // update counters
                consecutiveMutations++;
                successfulMutations++;
            }
        }
    }

    /**
     * Method which decides whether the temperature is to be decreased, and if so decreases it.
     *
     * @return - returns whether the temperature has changed.
     */
    private boolean checkDecreaseTemperature() {
        if(successfulMutations >= MAX_SUCCESSFUL_MUTATIONS || consecutiveMutations >= MAX_CONSECUTIVE_MUTATIONS) {
            temperature *= 0.9;
            successfulMutations = 0;
            consecutiveMutations = 0;

            return true;
        }

        return false;
    }
}
