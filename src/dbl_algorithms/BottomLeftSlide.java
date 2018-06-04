package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

public class BottomLeftSlide extends PackingStrategy{
    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    boolean sort;

    BottomLeftSlide(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles, boolean sort) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.sort = sort;
    }


    @Override
    protected State pack() throws IOException, FileNotFoundException {
        // TODO: sort rectangles descending order
        if(sort) {
            QuickSort instance = new QuickSort();
            rectangles = instance.sort(rectangles);
        }

        State s = new State(rectangles.length);

        int width = 0;

        for(int i = 0; i < rectangles.length; i++) {
            int sx = width;
            int sy = containerHeight - rectangles[i].height;
            boolean spin;

            int finalWidth = Integer.MAX_VALUE;
            int finalHeight = Integer.MAX_VALUE;

            do {
                spin = false;
                int maxWidth = 0;
                int maxHeight = 0;

                // check to left
                for(int j = 0; j < i; j++) {
                    if(doesHOverlap(sy, rectangles[i].height, rectangles[j]) && isLeft(sx, rectangles[j]) && isRightMost(maxWidth, rectangles[j])) {
                        if(!isWSame(sx, rectangles[j])) {
                            maxWidth = rectangles[j].blx + rectangles[j].width;
                        } else {
                            maxWidth = sx;
                        }
                    }
                }
                if(finalWidth != maxWidth) {
                    spin = true;
                }
                finalWidth = maxWidth;
                sx = maxWidth;

                // check to bottom
                for(int j = 0; j < i; j++) {
                    if(doesWOverlap(sx, rectangles[i].width, rectangles[j]) && isBottom(sy, rectangles[j]) && isTopMost(maxHeight, rectangles[j])) {
                        if(!isHSame(sy, rectangles[j])) {
                            maxHeight = rectangles[j].bly + rectangles[j].height;
                        } else {
                            maxHeight = sy;
                        }
                    }
                }
                if(finalHeight != maxHeight) {
                    spin = true;
                }
                finalHeight = maxHeight;
                sy = maxHeight;
            } while(spin);

            rectangles[i].setPosition(finalWidth, finalHeight);

            s.addRectangle(rectangles[i]);

            // update the width of the frame
            if(sx + rectangles[i].width > width) {
                width = sx + rectangles[i].width;
            }
        }

        return s;
    }

    private boolean doesHOverlap(int y, int h, Rectangle rect) {
        return y < rect.bly + rect.height && y + h > rect.bly;
    }

    private boolean doesWOverlap(int x, int w, Rectangle rect) {
        return x < rect.blx + rect.width && x + w > rect.blx;
    }

    private boolean isLeft(int x, Rectangle rect) {
        return x >= rect.blx + rect.width;
    }

    private boolean isBottom(int y, Rectangle rect) {
        return y >= rect.bly + rect.height;
    }

    private boolean isRightMost(int maxWidth, Rectangle rect) {
        return rect.blx + rect.width > maxWidth;
    }

    private boolean isTopMost(int maxHeight, Rectangle rect) {
        return rect.bly + rect.height > maxHeight;
    }

    private boolean isHSame(int currentH, Rectangle rect) {
        return currentH == rect.bly + rect.height;
    }

    private boolean isWSame(int currentW, Rectangle rect) {
        return currentW == rect.blx + rect.width;
    }

    // TODO: remove when done
    public static void main(String[] args) throws IOException {
        Rectangle[] rectangles = new Rectangle[8];
        rectangles[0] = new Rectangle(3, 2);
        rectangles[1] = new Rectangle(9, 4);
        rectangles[2] = new Rectangle(11, 2);
        rectangles[3] = new Rectangle(3, 1);
        rectangles[4] = new Rectangle(5, 1);
        rectangles[5] = new Rectangle(5, 4);
        rectangles[6] = new Rectangle(7, 3);
        rectangles[7] = new Rectangle(1, 1);


        BottomLeftSlide bl = new BottomLeftSlide(6, false, rectangles, false);
        State state = bl.pack();

        Rectangle[] placement = state.getLayout();
        for(Rectangle rectangle : placement) {
            System.out.println(rectangle.blx + " " + rectangle.bly);
        }

        GUI drawing = new GUI(state);
        drawing.run();
    }
}
