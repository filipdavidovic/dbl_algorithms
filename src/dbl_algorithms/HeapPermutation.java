package dbl_algorithms;

import java.util.ArrayList;
import java.util.List;

public class HeapPermutation implements AbstractPermutation {

    private List<Rectangle[]> permutations;
    Rectangle[] rectangles;

    HeapPermutation(Rectangle[] rectangles) {
        this.rectangles = rectangles;
        this.permutations = new ArrayList<>();
    }

    @Override
    public List<Rectangle[]> permute(boolean rotate) {
        heapPermutation(rectangles.length);

        return this.permutations;
    }

    private void heapPermutation(int n) {
        if(n == 1) {
            permutations.add(rectangles.clone());
            return;
        }

        for(int i = 0; i < n - 1; i++) {
            heapPermutation(n - 1);

            if(n % 2 == 0) {
                Rectangle temp = rectangles[i];
                rectangles[i] = rectangles[n - 1];
                rectangles[n - 1] = temp;
            } else {
                Rectangle temp = rectangles[0];
                rectangles[0] = rectangles[n - 1];
                rectangles[n - 1] = temp;
            }
        }
    }
}
