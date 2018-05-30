/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author dianaepureanu
 */
public class GeneticAlgorithm extends PackingStrategy {
    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    BottomLeft bl; // placement algorithm to be used;
    Individual[] population; // a population is an array of individuals
    int t; // counter which keeps track of number of loops
    Random r; // random variable
    //OPTIMIZATION PARAMTERS
    int m; // number of individuals in the population;
    int MAX_LOOPS; // number of loops allowed until mutations are stopped;

    
    public GeneticAlgorithm(int containerHeight, boolean rotationsAllowed,
            Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.t = 1;
        this.r = new Random();
        this.m = 200;
        this.population = new Individual[m];
    }
    
     /**
      * This function generates a random permutation of a[].
      */
    public void randomize( Rectangle[] rectangles, int n) {     
        // Start from the last element and swap one by one. We don't
        // need to run for the first element that's why i > 0
        for (int i = n-1; i > 0; i--) {
             
            // Pick a random index from 0 to i
            int j = r.nextInt(i);
             
            // Swap arr[i] with the element at random index
            Rectangle temp = rectangles[i];
            rectangles[i] = rectangles[j];
            rectangles[j] = temp;
        }
    }

    public void createFirstPopulation() throws IOException {
        //first individual is a permutation where rectangles are sorted
        //by decreasing width and placed in the container with BottomLeft
        bl = new BottomLeft(containerHeight, rotationsAllowed, rectangles, true);
        State firstIndividual_state = bl.pack(); // state of the first individual
        double fitnessValue = 1.0 - firstIndividual_state.getFillRate();
        //create the first Individual instance
        population[0] = new Individual(rectangles, fitnessValue);
        //the rest of the individuals have as State a random permutation of the
        //rectangles; they are then placed in the container with BottomLeft
        for (int i = 1; i < m; i++) {
            this.randomize(rectangles, rectangles.length);
            bl = new BottomLeft(containerHeight, rotationsAllowed, rectangles,
                    false);
            State randomIndividual_state = bl.pack();
            double randomFitnessValue = 1.0 - randomIndividual_state.getFillRate();
            population[i] = new Individual(rectangles, randomFitnessValue);
        }     
    }
    /**
     * Method which generates a new individual given the elements of two 
     * distinct individuals. 
     * At the random position p the crossover copies q elements out of a to the 
     * beginning of the new permutation. (1<=p,q <=n)
     * Afterwards, the new permutation is filled up by the other elements of b,
     * in the same order
     * @param a first Individual to be used in creating the new Individual
     * @param b second Individual to be used in creating the new Individual
     * @return permutation consisting of elements from both a and b
     */
    public Rectangle[] crossOver(Individual a, Individual b) throws IOException { 
        Rectangle[] a_perm = a.getPermutation();
        Rectangle[] b_perm = b.getPermutation();
        Rectangle[] new_perm = new Rectangle[a_perm.length];
        int p = 1 + r.nextInt(rectangles.length - 1);
        int q = 1 + r.nextInt(rectangles.length - 1);
        for (int i = 0; i < q; i++) {
            new_perm[i] = a_perm[(p+i) % a_perm.length];
            a_perm[(p+i) % a_perm.length].setPlaced(true);
        }
        for(Rectangle rect : b_perm) {
            if (rect.isPlaced() == false) {
                new_perm[q] = rect;
                rect.setPlaced(true);
                q++;
            }
        }
        
        return new_perm;    
    }
    
    public void mutationNormal(Rectangle[] rectangles) {
        
    }
    
    public void mutation(Rectangle[] rectangles) {
        
    }
    
    @Override
    protected State pack() throws IOException, FileNotFoundException,
            CloneNotSupportedException {
        
        this.createFirstPopulation();
        while(t <= MAX_LOOPS) {
            int i = r.nextInt(m);
            int j = r.nextInt(m);
            while (i == j) {
                j = r.nextInt(m);
            }
            // choose 2 random Individuals from the population
            Individual ind_A = population[i]; 
            Individual ind_B = population[j];
            // apply 3 genetic operations on the offspring
            Rectangle[] new_perm = this.crossOver(ind_A, ind_B);
            mutationNormal(new_perm);
            mutation(new_perm);
            // convert the offspring into an Individual by first placing
            //the rectangles in the container using BottomLeft
            bl = new BottomLeft(containerHeight, rotationsAllowed, rectangles, 
                false);
            State new_state = bl.pack();
            // and then computing the respective fitness values
            double new_fitnessValue = 1.0 - new_state.getFillRate();
            //create the new Individual instance
            Individual newIndividual = new Individual(new_perm, new_fitnessValue);

            t++;  
        }
        return null;
    }
}
