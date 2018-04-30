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
public class State {
    Rectangle[] layout;
    int index;
    
    State(int size){
    layout = new Rectangle[size];
    index = 0;
    }
    
    public void addRectangle(Rectangle r){
        if(index < layout.length){
            layout[index] = r;
            index ++;  
        }else{
        System.out.println("Layout size exceeded!");
        }
        
    }
    
}
