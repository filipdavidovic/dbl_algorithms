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
    double bestFillRate;
    double worstFillRate;
    Individual bestIndividual;
    Individual worstIndividual;
    State bestState;
    //OPTIMIZATION PARAMETERS
    //EXAMPLE INPUT FROM THE PAPER: input = 25; m = 20; MAX_LOOPS = 2000; p_m = 0.4;
    // number of individuals in the population;
    //range [2, 20] or larger. 20 is found in a paper
    int m;
    // number of loops allowed until mutations are stopped;
    //range [0, 2000] or larger. 2000 is found in a paper
    int MAX_LOOPS;
    // probability with which rectangles are rotated in a mutation
    //range: [0, 1)
    double p_m;
  
    public GeneticAlgorithm(int containerHeight, boolean rotationsAllowed,
            Rectangle[] rectangles, int m, int MAX_LOOPS, double p_m) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.t = 1;
        this.r = new Random();
        this.population = new Individual[m];
        this.m = 20;
        this.MAX_LOOPS = 2000;
        this.p_m = 0.4;
    }
    
     /**
      * This function generates a random permutation of the rectangles.
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
    /**
     * This method creates the first generation of individuals
     * @throws IOException 
     */
    public void createFirstPopulation() throws IOException {
        //first individual is a permutation where rectangles are sorted
        //by decreasing width and placed in the container with BottomLeft
        bl = new BottomLeft(containerHeight, rotationsAllowed, rectangles, true);
        State firstIndividual_state = bl.pack(); // state of the first individual
        //remember the best state seen so far
        bestState = firstIndividual_state;
        double fitnessValue = 1.0 - firstIndividual_state.getFillRate();
        //initialize the best and the worst permutations
        bestFillRate = fitnessValue;
        worstFillRate = fitnessValue;
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
            if (randomFitnessValue < worstFillRate) {
                worstFillRate = randomFitnessValue;
            }
            // remember the best state seen so far
            if (randomFitnessValue > bestFillRate) {
                bestFillRate = randomFitnessValue;
                bestState = randomIndividual_state;
            }
            population[i] = new Individual(rectangles, randomFitnessValue);
        }
        //get the best and the worst Individuals from the population
        for (Individual ind : population) {
            if (ind.getFitnessValue() == bestFillRate) {
                bestIndividual = ind; 
            } else if (ind.getFitnessValue() == worstFillRate) {
                worstIndividual = ind;
            }
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
    /**
     * This method swaps 2 random rectangles in a permutation.
     * @param rectangles elements of the permutation
     */
    public void mutationNormal(Rectangle[] rectangles) {
        int i = r.nextInt(rectangles.length);
        int j = r.nextInt(rectangles.length);
        while (i == j) {
            j = r.nextInt(rectangles.length);
        }
        Rectangle aux = rectangles[i];
        rectangles[i] = rectangles[j];
        rectangles[j] = aux;
    }
    
   /**
    * This method rotates rectangles with a probability lower than p_m under the
    * assumption that the rotations are allowed.It does not do anything if
    * rotations are now allowed.
    * @param rectangles elements of the permutation
    */
    public void mutation(Rectangle[] rectangles) {
        if (rotationsAllowed == true) {
            for (int i = 0; i < rectangles.length; i++) {
                if (Math.random() < p_m) {
                    rectangles[i].rotate();
                }
            }
        }
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
            // apply the 3 genetic operations on the offspring
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
            
            //if the new individual has a better fillRate than the worst
            //individual from the population, then replace the worst individual
            // with the new individual
            if (newIndividual.getFitnessValue() > 
                    worstIndividual.getFitnessValue()) {
                worstIndividual = newIndividual;
            }
            //if it is not the case, do not change any individual and continue
            //with the next iteration
            //recompute the best and the worst FillRate for this generation
            for (Individual ind: population) {
                if (ind.getFitnessValue() > bestFillRate) {
                    bestFillRate = ind.getFitnessValue();
                } else if (ind.getFitnessValue() < worstFillRate) {
                    worstFillRate = ind.getFitnessValue();
                }
            }
            //recompute the best and the worst individuals
            //for the current generation
            for (Individual ind : population) {
                if (ind.getFitnessValue() == bestFillRate) {
                    bestIndividual = ind; 
                } else if (ind.getFitnessValue() == worstFillRate) {
                    worstIndividual = ind;
                }
            }
            // increase the number of iterations   
            t++;  
        }
        return bestState;
    }
}
