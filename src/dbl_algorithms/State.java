/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.Arrays;

/**
 *
 * @author TijanaKlimovic
 */
public class State {

    private Rectangle[] layout;
    int index;
    int layoutWidth;
    int layoutHeight;
    int innerArea; //Sum of areas of rectangles that were placed already
    float fillRate;

    State(int size) {
        layout = new Rectangle[size];
        index = 0;
        innerArea = 0;
    }

    public float getFillRate() {
        return this.fillRate;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void addRectangle(Rectangle r) {
        if (index < layout.length) {
            layout[index] = r;
            index++;
        } else {
            System.out.println("Layout size exceeded!");
        }
        //update inner area and fillRate
        innerArea = innerArea + (r.height * r.width);
        System.out.println(innerArea);

        //update height and width of the state
        if (r.blx + r.width > layoutWidth) {
            layoutWidth = r.blx + r.width;
        }
        if (r.bly + r.height > layoutHeight) {
            layoutHeight = r.bly + r.height;
        }
        fillRate = (float) innerArea / (float) this.getArea();
        System.out.println(this.getArea());
    }

    public int getArea() {
        return this.layoutHeight * this.layoutWidth;
    }

    public boolean doesOverlap(Rectangle r) {
        for (int i = 0; i < index; i++) {
            if (layout[i].blx < r.blx + r.width && layout[i].blx + layout[i].width > r.blx && layout[i].bly < r.bly + r.height && layout[i].bly + layout[i].height > r.bly) {
                return true;
            }
        }

        return false;
    }

    public void setLayout(Rectangle[] rectangles) {
        this.layout = rectangles;
    }

    public Rectangle[] getLayout() {
        return layout;
    }

    public Rectangle[] getLayoutClone() {
        Rectangle[] ret = new Rectangle[this.layout.length];
        for (int i = 0; i < index; i++) {
            ret[i] = this.layout[i].clone();
        }
        return ret;
    }

    public void setLayoutWidth(int layoutWidth) {
        this.layoutWidth = layoutWidth;
    }

    public int getLayoutWidth() {
        return this.layoutWidth;
    }

    public void setLayoutHeight(int layoutHeight) {
        this.layoutHeight = layoutHeight;
    }

    public int getLayoutHeight() {
        return this.layoutHeight;
    }

    @Override
    public State clone() {
        State clone = new State(this.layout.length);

        clone.setLayout(getLayoutClone());
        clone.setIndex(this.index);
        clone.setLayoutHeight(this.layoutHeight);
        clone.setLayoutWidth(this.layoutWidth);

        return clone;
    }

}
