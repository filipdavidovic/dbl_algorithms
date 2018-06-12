package dbl_algorithms;

import java.util.ArrayList;
import java.util.List;

public class RotationPermutation implements AbstractPermutation {

    List<Rectangle[]> permutations;
    Rectangle[] rectangles;
    int containerHeight;

    RotationPermutation(Rectangle[] rectangles, int containerHeight) {
        this.rectangles = rectangles;
        this.permutations = new ArrayList<>();
        this.containerHeight = containerHeight;
    }

    @Override
    public List<Rectangle[]> permute(boolean rotate) {
        rotationPermutation(rectangles, rectangles.length - 1);
        return permutations;
    }

    private void rotationPermutation(Rectangle[] permutation, int n) {
        // if current node is a leaf add its two permutations to the list
        if(n == 0) {
            // add left child
            Rectangle[] clone1 = getClone(permutation);
            permutations.add(clone1);
            // add rotated right child
            if(permutation[n].width <= containerHeight) {
                Rectangle[] clone2 = getClone(permutation);
                clone2[n].rotate();
                permutations.add(clone2);
            }
        } else {
            // create left child
            Rectangle[] clone1 = getClone(permutation);
            rotationPermutation(clone1, n - 1);
            // create rotated right child
            Rectangle[] clone2 = getClone(permutation);
            if(permutation[n].width <= containerHeight) {
                clone2[n].rotate();
            }
            rotationPermutation(clone2, n - 1);
        }
    }

    private Rectangle[] getClone(Rectangle[] original) {
        Rectangle[] clone = new Rectangle[original.length];
        for(int i = 0; i < clone.length; i++) {
            clone[i] = original[i].clone();
        }
        return clone;
    }
}
