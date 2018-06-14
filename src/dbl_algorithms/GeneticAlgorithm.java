/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

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
    Rectangle[] new_perm;
    BottomLeftSlide bl; 
    State[] population; 
    State bestState;
    State worstState; 
    State new_state;
    int t; 
    Random r; 
    int m;
    int MAX_LOOPS;
    double p_m;
    State first;
    double bestFillRate;
    
       public GeneticAlgorithm(int containerHeight, boolean rotationsAllowed,
            Rectangle[] rectangles, int m, int MAX_LOOPS, double p_m) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.t = 1;
        this.r = new Random();
        this.population = new State[m];
        this.m = m;
        this.MAX_LOOPS = MAX_LOOPS;
        this.p_m = 0.4;
        this.bestState = new State(rectangles.length);
        this.worstState = new State(rectangles.length);
        }
       
        public void randomize( Rectangle[] rectangles, int n) {     
            for (int i = n-1; i > 0; i--) {
                int j = r.nextInt(n);
                while (i ==j) {
                    j = r.nextInt(n);
                }
                Rectangle temp = rectangles[i];                                         //new order of rectangles is overwritting the original order? need it maintained for the printing
                rectangles[i] = rectangles[j];
                rectangles[j] = temp;
            }
        }
        
        public void createFirstPopulation() throws IOException {
            //create first 
            if (rotationsAllowed) {
                for (Rectangle r : rectangles) {
                    if (r.height > r.width) {
                        r.rotate();
                    }
                }
            }
            bl = new BottomLeftSlide(containerHeight, rotationsAllowed, rectangles, true);
            first = bl.pack();
            population[0] = first;
            //remember bestState at another address;
            for (int i = 0; i < rectangles.length; i++) {
                //bestState.getLayout()[i] = first.getLayout()[i].clone();
                bestState.addRectangle(first.getLayout()[i].clone());
               
            } 
            //remember worstState at another address
            for (int i = 0; i < rectangles.length; i++) {
                worstState.addRectangle(first.getLayout()[i].clone());
            }
            //create the rest
            for (int j = 1; j < m; j++) {
                population[j] = new State(rectangles.length);
                this.randomize(rectangles, rectangles.length);
                BottomLeftSlide bl2 = new BottomLeftSlide(containerHeight, rotationsAllowed,
                        rectangles, false);
                State s = bl2.pack();  
                    for (int i = 0; i < rectangles.length; i++) {
                        population[j].addRectangle(s.getLayout()[i].clone());
                    }
                
            } 
            // compute best state again and make sure it's stored at another address
            for (State s: population) {
                if (s.getFillRate() > bestState.getFillRate()) {
                    bestState = s.clone();
                }
            }     
            //compute worst state
            for (State s: population) {
                if (s.getFillRate() < worstState.getFillRate()) {
                    worstState = s.clone();
                }
            }
            
        }
    /**
     * By now we have 20 States at 20 different locations.
     * There are also 2 states are 2 other addresses which store the 
     * best and the worst state. This way, when we modify the 20 states, we 
     * make sure we do not modify the best and/or the worst state.
     */
        
        
        public void crossOver(State a, State b) throws IOException {          
            Rectangle[] a_perm = a.getLayout();
            Rectangle[] b_perm = b.getLayout();
        
            for(int i = 0 ; i < rectangles.length ; i++){
                //ensure all rectangles havent been marked
                a_perm[i].placed = false;
                b_perm[i].placed = false;
            }
        
            new_perm = new Rectangle[rectangles.length];
            int p =  r.nextInt(rectangles.length );       
            int q =  r.nextInt(rectangles.length );
            int t = 0 ;
            for (int i = 0; i < q; i++) {
                new_perm[i] = a_perm[(p+i) % a_perm.length].clone();                    
                //mark as already placed in permutation
                a_perm[(p+i) % a_perm.length].setPlaced(true);
                t = 0;
                //copy this marking to the same number in the b_perm
                for(int j = 0 ; j < b_perm.length ; j++){
                    if(a_perm[(p+i) % a_perm.length].height == b_perm[j].height 
                        && a_perm[(p+i) % a_perm.length].width == b_perm[j].width 
                        && a_perm[(p+i) % a_perm.length].rotated == b_perm[j].rotated 
                        && t==0){
                    b_perm[j].setPlaced(true);
                    t = 1;
                    }
                }
            }
            for(Rectangle rect : b_perm) {
                if (rect.isPlaced() == false) {
                    new_perm[q] = rect.clone();                                                     
                    rect.setPlaced(true);
                    if(q == rectangles.length - 1){
                        break;
                    }
                    q++;
                }
            }
            //leave the states a and b unmodified.
            for(int i = 0 ; i < rectangles.length ; i++){
                //ensure all rectangles havent been marked
                a_perm[i].placed = false;
                b_perm[i].placed = false;
            }
            
        }
        
    public void mutationNormal(Rectangle[] rectangles) {                                
        int i = r.nextInt(rectangles.length);           
        int j = r.nextInt(rectangles.length);
        while (i == j) {
            j = r.nextInt(rectangles.length);
        }
        try{       
            Rectangle aux = rectangles[i];                                        
            rectangles[i] = rectangles[j];
            rectangles[j] = aux;
        } catch (NullPointerException e){
            System.out.println(i);
        }
     
    }
    
    public void mutation(Rectangle[] rectangles) {
        if (rotationsAllowed == true) {
            for (int i = 0; i < rectangles.length; i++) {
                if (Math.random() < p_m && rectangles[i].width <= containerHeight) {
                    rectangles[i].rotate();
                }
            }
        }
    }
    
    public State pack() throws IOException {
        this.createFirstPopulation();
        while(t <= MAX_LOOPS) {
            int i = r.nextInt(m);
            int j = r.nextInt(m);
            while (i == j) {
                j = r.nextInt(m);
            }
            // we use pointers because we do not modify a and b;
            State a = population[i];
            State b = population[j];
            crossOver(a,b);
            mutationNormal(new_perm);
            mutation(new_perm);
            BottomLeftSlide bl = new BottomLeftSlide(containerHeight, rotationsAllowed,
                    new_perm, false);
            new_state = bl.pack();
            
            //we need to find the worst state in the population
            // replace it only with a reference;
            for ( State s : population) {
                if (s.getFillRate() == worstState.getFillRate()) {
                    s = new_state;
                    break;
                }
            }  
            //compute the new worst state and store it at another address
            for (State s: population) {
                if (s.getFillRate() < worstState.getFillRate()) {
                     worstState = s.clone();
                }
            }
            //compute the new best state and store it at another address
            for (State s: population) {
                if (s.getFillRate() > bestState.getFillRate()) {
                    bestFillRate = s.getFillRate();
                    bestState = s.clone();
                }
            }
            t++; 
        }
    return bestState; 
    }
}


