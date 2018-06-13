/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author dianaepureanu
 */
public class GeneticAlgorithm extends PackingStrategy {
    // to be made local in crossOver used for debug

    
    
    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    BottomLeftSlide bl; // placement algorithm to be used;
    Individual[] population; // a population is an array of individuals
    int t; // counter which keeps track of number of loops
    Random r; // random variable
    double bestFillRate;
    double worstFillRate;
    Individual bestIndividual;
    Individual worstIndividual;
    State bestState;
    Rectangle[] new_perm;
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
  
    /**
     * GeneticAlgorithm constructor 
     * 
     * @param containerHeight fixed height of the container given at input
     * @param rotationsAllowed true if rotations of rectangles are allowed, else false
     * @param rectangles list of rectangles given at input
     * @param m number of permutations
     * @param MAX_LOOPS number of offspring generated
     * @param p_m mutation rate
     */
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
             
            // Pick a random index from 0 to n
            int j = r.nextInt(n);                                                    // i think the randomness can be improved because the probabilities of swaps decrement as we decrease i also i is at max equal to n-1, which means the maximum swap interval considered is [1,n-1) although it should include n-1?
             
            // Swap arr[i] with the element at random index
            Rectangle temp = rectangles[i];                                         //new order of rectangles is overwritting the original order? need it maintained for the printing
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
        //by decreasing width and placed in the container with BottomLeftSlide
        bl = new BottomLeftSlide(containerHeight, rotationsAllowed, rectangles, true);
        State firstIndividual_state = bl.pack(); // state of the first individual
        //remember the best state seen so far
        bestState = firstIndividual_state;
        double fitnessValue = 1.0 - firstIndividual_state.getFillRate();
        //initialize the best and the worst permutations
        bestFillRate = fitnessValue;                                                    //consider m=1
        worstFillRate = fitnessValue;
        //create the first Individual instance
        
        Rectangle[] permutation = new Rectangle[rectangles.length];
        for(int i = 0 ; i < rectangles.length ; i++){
            permutation[i] = rectangles[i];
        }
        
       // population[0] = new Individual(rectangles, fitnessValue);
        population[0] = new Individual(permutation, fitnessValue);
        //initialize worst and best individuals;
        worstIndividual = new Individual(rectangles, fitnessValue);
        bestIndividual = new Individual(rectangles, fitnessValue);
        //the rest of the individuals have as State a random permutation of the
        //rectangles; they are then placed in the container with BottomLeft
        for (int i = 1; i < m; i++) {
            this.randomize(rectangles, rectangles.length);
            bl = new BottomLeftSlide(containerHeight, rotationsAllowed, rectangles,
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
            Rectangle[] instance = new Rectangle[rectangles.length];
            for(int j = 0 ; j < rectangles.length; j++){
                instance[j] = rectangles[j];                                    //removed clone
            }
            population[i] = new Individual(instance, randomFitnessValue);
        }
        //get the best and the worst Individuals from the population                    //why is this dynamically not taken care of? namely when saving the worstCase save the index of that individual
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
    public void crossOver(Individual a, Individual b) throws IOException {          //try using a and b directly
        //create deep copy of the first individual 
//        Rectangle[] a_perm = new Rectangle[rectangles.length];
//        //a_perm = new Rectangle[rectangles.length];
//        for (int k = 0; k < a_perm.length; k++) {
//            a_perm[k] = a.getPermutation()[k].clone();
//            //ensure that a_perm has its placed fields false
//            if (a_perm[k].placed == true){
//                a_perm[k].placed = false;
//            }
//        }
//        //create deep copy of the second individual
//        Rectangle[] b_perm = b.getPermutation();
//         //b_perm = b.getPermutation();
//        for (int k = 0; k < b_perm.length; k++) {
//            b_perm[k] = b.getPermutation()[k].clone();
//            //ensure that b_perm has its placed fields false
//            if (b_perm[k].placed == true){
//                b_perm[k].placed = false;
//            }
//        }
//        
//        new_perm = new Rectangle[a_perm.length];
//         int p =  r.nextInt(rectangles.length );       //why 1 + length - 1 why not just   r.nextInt(rectangles.length) ? also it would then involve [1,length-1) when it should include [0,length-1]
//         int q =  r.nextInt(rectangles.length );
//         //ensures when setPlaced information needs to be copied from a_perm to b_perm it's executed on only one element , this covers the case of multiple rectangles with the same shape
//         int t = 0 ;
//        for (int i = 0; i < q; i++) {
//            new_perm[i] = a_perm[(p+i) % a_perm.length].clone();                    //remove clone
//            //mark as already placed in permutation
//            //a_perm[(p+i) % a_perm.length].setPlaced(true);
//            t = 0;
//            //copy this marking to the same number in the b_perm
//            for(int j = 0 ; j < b_perm.length ; j++){
//                if(a_perm[(p+i) % a_perm.length].height == b_perm[j].height && a_perm[(p+i) % a_perm.length].width == b_perm[j].width && a_perm[(p+i) % a_perm.length].rotated == b_perm[j].rotated && t==0){
//                    b_perm[j].setPlaced(true);
//                    t = 1;
//                }
//            }
//        }
//        for(Rectangle rect : b_perm) {
//            //all unmarked rectangles place in unaltered order in the remaining slots in permuatation
//            if (rect.isPlaced() == false) {
//                new_perm[q] = rect.clone();                                                     //if original rectangles used may cause trouble in next population iteratons
//                rect.setPlaced(true);
//                if(q == rectangles.length - 1){
//                    break;
//                }
//                q++;
//            }
//        }
              
        Rectangle[] a_perm = a.getPermutation();
        Rectangle[] b_perm = b.getPermutation();
        
        for(int i = 0 ; i < rectangles.length ; i++){
        //ensure all rectangles havent been marked
        a_perm[i].placed = false;
        b_perm[i].placed = false;
        }
        
        new_perm = new Rectangle[a_perm.length];
         int p =  r.nextInt(rectangles.length );       
         int q =  r.nextInt(rectangles.length );
        
        for (int i = 0; i < q; i++) {
            new_perm[i] = a_perm[(p+i) % a_perm.length];
            a_perm[(p+i) % a_perm.length].setPlaced(true);
        }
        
                for(Rectangle rect : b_perm) {
            //all unmarked rectangles place in unaltered order in the remaining slots in permuatation
            if (rect.isPlaced() == false) {
                new_perm[q] = rect;                                                     //if original rectangles used may cause trouble in next population iteratons
                rect.setPlaced(true);
                if(q == rectangles.length - 1){
                    break;
                }
                q++;
            }
        }
        
        
    }
    /**
     * This method swaps 2 random rectangles in a permutation.
     * @param rectangles elements of the permutation
     */
    public void mutationNormal(Rectangle[] rectangles) {                                //maybe this mutation can be better, more elements exchanged
        int i = r.nextInt(rectangles.length);           
        int j = r.nextInt(rectangles.length);
        while (i == j) {
            j = r.nextInt(rectangles.length);
        }
        try{       
            Rectangle aux = rectangles[i];                                          //removed clone
            rectangles[i] = rectangles[j];
            rectangles[j] = aux;
        }catch(NullPointerException e){
            System.out.println(i);
        }
     
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
                if (Math.random() < p_m && rectangles[i].width <= containerHeight) {
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
            // make a deep copy of the rectangles array   
            this.crossOver(ind_A, ind_B);
            mutationNormal(new_perm);
            mutation(new_perm);
            // convert the offspring into an Individual by first placing
            //the rectangles in the container using BottomLeft
            bl = new BottomLeftSlide(containerHeight, rotationsAllowed, new_perm,        //important mistake bl was done over rectangles not over new_perm
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
