/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *
 * @author TijanaKlimovic
 */
public class Rectangle {

    public int width;
    public int height;
    public int blx;    // bottom left x
    public int bly;    // bottom left y
    boolean rotated;    //
    public boolean used = false;
    public boolean placed = false; // used in the genetic algorithm
    public Rectangle right = null;
    public Rectangle down = null;
    public Rectangle fit = null;

    Rectangle(int width, int height) {
        //default
        this.blx = -1;
        this.bly = -1;
        this.rotated = false;
        this.height = height;
        this.width = width;
    }

    Rectangle(int width, int height, int x, int y) {
        //default
        this.blx = x;
        this.bly = y;
        this.rotated = false;
        this.height = height;
        this.width = width;
    }

    Rectangle(int width, int height, int x, int y, boolean used, Rectangle right, Rectangle down) {
        this.blx = x;
        this.bly = y;
        this.rotated = false;
        this.height = height;
        this.width = width;
        this.used = used;
        this.down = down;
        this.right = right;
    }

    public void setPosition(int x, int y) {
        this.blx = x;
        this.bly = y;
    }

    public void rotate() {
        int temp = width;
        width = height;
        height = temp;
        rotated = true;
        //System.out.println(rotated);
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
    
    @Override
    public Rectangle clone() {
        return new Rectangle(this.width, this.height, this.blx, this.bly);
    }
}
