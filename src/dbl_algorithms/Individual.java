/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

/**
 *Individuals are elements of one population;
 * Each individual has its corresponding permutation and the fitness value the
 * permutation has.
 * @author dianaepureanu
 */
public class Individual {
    
    private Rectangle[] permutation;
    /**
     * Variable which represents the amount of empty space left after the 
     * placement of the rectangles in the container; the bigger this value is,
     * the better the state is.
     */
    private double fitnessValue;
    
    public Individual (Rectangle[] permutation, double fitnessValue) {
        this.permutation = permutation;
        this.fitnessValue = fitnessValue;
    }

    public Rectangle[] getPermutation() {
        return permutation;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }
    
}
