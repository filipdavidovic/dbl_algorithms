package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BruteForce extends PackingStrategy {

    private int containerHeight;
    private boolean rotationsAllowed;
    private Rectangle[] rectangles;
    private List<int[]> permutations = new ArrayList<>();

    BruteForce(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }

    private void heapPermutation(int a[], int size, int n) {
        // if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
            int[] arr = new int[a.length];
            for(int i = 0; i < a.length; i++) {
                arr[i] = a[i];
            }
            permutations.add(arr);
        }

        for (int i=0; i < size; i++)
        {
            heapPermutation(a, size-1, n);

            // if size is odd, swap first and last
            // element
            if (size % 2 == 1)
            {
                int temp = a[0];
                a[0] = a[size-1];
                a[size-1] = temp;
            }
            // If size is even, swap ith and last
            // element
            else
            {
                int temp = a[i];
                a[i] = a[size-1];
                a[size-1] = temp;
            }
        }
    }

    @Override
    protected State pack() throws IOException, FileNotFoundException {

        State state = null;

        int[] a = new int[rectangles.length];
        for(int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        heapPermutation(a, a.length, a.length);

        if(containerHeight > 0) {
            for(int[] b : permutations) {
                Rectangle[] permutation = new Rectangle[rectangles.length];
                for(int i = 0; i < permutation.length; i++) {
                    permutation[i] = rectangles[b[i]];
                }

                BottomLeft bl = new BottomLeft(containerHeight, rotationsAllowed, permutation, false);

                State s = bl.pack();

                if(state == null || s.getArea() < state.getArea()) {
                    state = s;
                }
            }
        } else {
            for(int[] b : permutations) {
                Rectangle[] permutation = new Rectangle[rectangles.length];
                for(int i = 0; i < permutation.length; i++) {
                    permutation[i] = rectangles[b[i]].clone();
                }

                State s = bru(permutation);

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
        states.add(new State(permutation.length));
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
}
