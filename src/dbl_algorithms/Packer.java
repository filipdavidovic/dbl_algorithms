/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Tea
 */
public class Packer {

    private Rectangle root;
    private int containerHeight;
  
    public Packer(int h) {
        root = new Rectangle(0, h, 0, 0);
        containerHeight = h;
       
    }

    public void fit(ArrayList<Rectangle> rectangles) {
        Rectangle r;
        Rectangle rectangle;

        if (rectangles.isEmpty()) {
            root = new Rectangle(0, 0, 0, 0);
        } else {
            if(containerHeight != -1){
            root = new Rectangle(rectangles.get(0).width, containerHeight, 0, 0);
            } else {
                root = new Rectangle(rectangles.get(0).width, rectangles.get(0).height,0,0);
            }
        }

        Iterator<Rectangle> rectangleItr = rectangles.iterator();
        while (rectangleItr.hasNext()) {
            rectangle = rectangleItr.next();
            if ((r = findRectangle(root, rectangle.width, rectangle.height)) != null) {
                rectangle.fit = splitRectangle(r, rectangle.width, rectangle.height);
            } else {

                rectangle.fit = growRectangle(rectangle.width, rectangle.height);

            }
        }

    }

    public Rectangle findRectangle(Rectangle root, int w, int h) {
        if (root.used) {
            Rectangle right = findRectangle(root.right, w, h);
            if (right != null) {
                return right;
            } else {
                return findRectangle(root.down, w, h);
            }
        } else if ((w <= root.width) && (h <= root.height)) {
            return root;
        } else {
            return null;
        }
    }

    public Rectangle splitRectangle(Rectangle rectangle, int w, int h) {
        rectangle.used = true;
        rectangle.down = new Rectangle(rectangle.width, rectangle.height - h, rectangle.blx, rectangle.bly + h);
        rectangle.right = new Rectangle(rectangle.width - w, h, rectangle.blx + w, rectangle.bly);
        return rectangle;
    }

    private Rectangle growRectangle(int width, int height) {
        boolean canGrowDown = (width <= root.width);
        boolean canGrowRight = (height <= root.height);
        if(containerHeight!=-1){
            canGrowDown = canGrowDown&&(root.height+height<containerHeight);
        }
        boolean shouldGrowRight = (canGrowRight && (root.height >= (root.width + width)));
        boolean shouldGrowDown = (canGrowDown && (root.width >= (root.height + height)));

        if (shouldGrowRight) {
            return growRight(width, height);
        } else if (shouldGrowDown) {
            return growDown(width, height);
        } else if (canGrowRight) {
            return growRight(width, height);
        } else if (canGrowDown) {
            return growDown(width, height);
        } else {
            return null;
        }
    }

    private Rectangle growRight(int width, int height) {
        Rectangle r;
        Rectangle d = root;
        Rectangle ri = new Rectangle(width, root.height, root.width, 0);
        root = new Rectangle(root.width + width, root.height, 0, 0, true, ri, d);

        if ((r = findRectangle(root, width, height)) != null) {
            return splitRectangle(r, width, height);
        } else {
            return null;
        }
    }

    private Rectangle growDown(int width, int height) {
        Rectangle r;
        Rectangle d = new Rectangle(root.width, height, 0, root.height);
        Rectangle ri = root;
        root = new Rectangle(root.width, root.height + height, 0, 0, true, ri, d);

        if ((r = findRectangle(root, width, height)) != null) {
            return splitRectangle(r, width, height);
        } else {
            return null;
        }
    }
}
