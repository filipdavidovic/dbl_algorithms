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
    private Rectangle[] layout;
    int index;
    int layoutWidth;
    int layoutHeight;
    
    State(int size){
        layout = new Rectangle[size];
        index = 0;
        
    }
    
//    State(Rectangle[] rectangles){
//        layout = rectangles;
//        index = layout.length;
//        
//    }
    
    public void addRectangle(Rectangle r){
        if(index < layout.length){
            layout[index] = r;
            index ++;  
        }else{
            System.out.println("Layout size exceeded!");
        }
        
        if(r.blx + r.width > layoutWidth){
            layoutWidth = r.blx + r.width;
        }
        if(r.bly + r.height > layoutHeight){
            layoutHeight = r.bly + r.height;
        }
        
    }
    
     public int getArea(){
        return this.layoutHeight*this.layoutWidth;
    }
    
    

    public Rectangle[] getLayout() {
        return layout;
    }
    public int getLayoutWidth() {
        return this.layoutWidth;
    }

    public int getLayoutHeight() {
        return this.layoutHeight;
    }
    
   
    
}
