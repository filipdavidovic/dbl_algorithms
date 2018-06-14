package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteForce extends PackingStrategy {

    private int containerHeight;
    private boolean rotationsAllowed;
    private Rectangle[] rectangles;

    BruteForce(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException {

        State state = null;

        List<Rectangle[]> permutations = (new HeapPermutation(rectangles)).permute(this.rotationsAllowed);

        if(containerHeight > 0) {

            for(Rectangle[] b : permutations) {
                Rectangle[] permutation = new Rectangle[rectangles.length];
                for(int i = 0; i < permutation.length; i++) {
                    permutation[i] = b[i].clone();
                }

                if(rotationsAllowed) {
                    // get rotation permutations for the current rectangle permutation
                    AbstractPermutation rotationPermutation = new RotationPermutation(permutation, containerHeight);
                    List<Rectangle[]> rotationPermutations = rotationPermutation.permute(true);
                    // try each of them out
                    for(Rectangle[] c : rotationPermutations) {
                        BottomLeft bl = new BottomLeft(containerHeight, rotationsAllowed, c, false);
                        State s = bl.pack();
                        // select the best one
                        if(state == null || s.getArea() < state.getArea()) {
                            state = s;
                        }
                    }
                } else {
                    BottomLeft bl = new BottomLeft(containerHeight, rotationsAllowed, permutation, false);
                    State s = bl.pack();
                    if(state == null || s.getArea() < state.getArea()) {
                        state = s;
                    }
                }
            }
        } else {
            for(Rectangle[] b : permutations) {
                Rectangle[] permutation = new Rectangle[rectangles.length];
                for(int i = 0; i < permutation.length; i++) {
                    permutation[i] = b[i].clone();
                }

                State s = freeHeightPack(new State(rectangles.length, containerHeight), permutation, 0);

                if(state == null || s.getArea() < state.getArea()) {
                    state = s;
                }
            }
        }

        return state;
    }

    private State bru(Rectangle[] permutation) {
        State state = null;

        List<State> states = new ArrayList<>();
        states.add(new State(permutation.length, containerHeight));

        Rectangle temp = permutation[0].clone();
        temp.setPosition(0, 0);
        states.get(0).addRectangle(temp);

        List<State> newStates = new ArrayList<>();

        for(int rect = 1; rect < permutation.length; rect++) {
            Rectangle rectangle = permutation[rect];

            for(int st = 0; st < states.size(); st++) {
                Rectangle[] stateLayout = states.get(st).getLayout();
                for (int i = 0; i < states.get(st).getIndex(); i++) {
                    Rectangle stateRectangle = stateLayout[i];

                    State stateClone;
                    Rectangle rectangleClone;

                    // no-rotation
                    stateClone = states.get(st).clone();
                    rectangleClone = rectangle.clone();
                    rectangleClone.setPosition(stateRectangle.blx, stateRectangle.bly + stateRectangle.height);
                    if (!stateClone.doesOverlap(rectangleClone)) {
                        stateClone.addRectangle(rectangleClone);
                        newStates.add(stateClone);
                    }

                    stateClone = states.get(st).clone();
                    rectangleClone = rectangle.clone();
                    rectangleClone.setPosition(stateRectangle.blx + stateRectangle.width, stateRectangle.bly);
                    if (!stateClone.doesOverlap(rectangleClone)) {
                        stateClone.addRectangle(rectangleClone);
                        newStates.add(stateClone);
                    }

                    // rotation
                    if(rotationsAllowed) {
                        stateClone = states.get(st).clone();
                        rectangleClone = rectangle.clone();

                        rectangleClone.rotate();

                        rectangleClone.setPosition(stateRectangle.blx, stateRectangle.bly + stateRectangle.height);
                        if (!stateClone.doesOverlap(rectangleClone)) {
                            stateClone.addRectangle(rectangleClone);
                            newStates.add(stateClone);
                        }

                        stateClone = states.get(st).clone();
                        rectangleClone = rectangle.clone();
                        rectangleClone.setPosition(stateRectangle.blx + stateRectangle.width, stateRectangle.bly);
                        if (!stateClone.doesOverlap(rectangleClone)) {
                            stateClone.addRectangle(rectangleClone);
                            newStates.add(stateClone);
                        }
                    }
                }
            }
            states.clear();
            states.addAll(newStates);
            newStates.clear();
        }

        int minArea = Integer.MAX_VALUE;
        for(State s : states) {
            if(s.getIndex() == permutation.length && s.getArea() < minArea) {
                minArea = s.getArea();
                state = s;
            }
        }

        return state;
    }

    private State freeHeightPack(State state, Rectangle[] permutation, int n) {
        if(n == 0) {
            permutation[0].setPosition(0 ,0);
            state.addRectangle(permutation[0]);
            return freeHeightPack(state, permutation, n + 1);
        }

        if(n == rectangles.length) {
            return state.clone();
        }

        State best = null;

        int index = state.getIndex();

        Rectangle[] placedRectangles = state.getLayout();
        for(int j = 0; j < index; j++) { // placed rectangles loop

            State s = null;

            /* no-rotation */
            // top-left
            permutation[n].setPosition(placedRectangles[j].blx, placedRectangles[j].bly + placedRectangles[j].height);
            if(!state.doesOverlap(permutation[n])) {
                state.addRectangle(permutation[n]);
                s = freeHeightPack(state, permutation, n + 1);
                state.removeRectangle();
            }
            if(s != null && (best == null || s.getArea() < best.getArea())) {
                best = s;
            }

            // bottom-right
            permutation[n].setPosition(placedRectangles[j].blx + placedRectangles[j].width, placedRectangles[j].bly);
            if (!state.doesOverlap(permutation[n])) {
                state.addRectangle(permutation[n]);
                s = freeHeightPack(state, permutation, n + 1);
                state.removeRectangle();
            }
            if(s != null && (best == null || s.getArea() < best.getArea())) {
                best = s;
            }

            /* rotation */
            if(rotationsAllowed) {
                permutation[n].rotate();

                // top-left
                permutation[n].setPosition(placedRectangles[j].blx, placedRectangles[j].bly + placedRectangles[j].height);
                if (!state.doesOverlap(permutation[n])) {
                    state.addRectangle(permutation[n]);
                    s = freeHeightPack(state, permutation, n + 1);
                    state.removeRectangle();
                }
                if(s != null && (best == null || s.getArea() < best.getArea())) {
                    best = s;
                }

                // bottom-right
                permutation[n].setPosition(placedRectangles[j].blx + placedRectangles[j].width, placedRectangles[j].bly);
                if (!state.doesOverlap(permutation[n])) {
                    state.addRectangle(permutation[n]);
                    s = freeHeightPack(state, permutation, n + 1);
                    state.removeRectangle();
                }
                if(s != null && (best == null || s.getArea() < best.getArea())) {
                    best = s;
                }
            }
        }

        return best;
    }
}
