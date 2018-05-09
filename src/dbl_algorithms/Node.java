/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *
 * @author Tea
 */
public class Node {
    public boolean isroot = false;
    public String name;
    public double x;
    public double y;
    public double w;
    public double h;
    public boolean used = false;
    public Node right = null;
    public Node down = null;
    public Node fit = null;

    public Node(String name, double w, double h) {
        this.name = name;
        this.w = w;
        this.h = h;
    }

    public Node(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        if(x == 0 && y == 0){
            this.isroot = true;//this is only necessary for me to print 'Pack Starts Here' in the example code
        }
    }

}