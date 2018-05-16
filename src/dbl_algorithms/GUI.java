/*
 *
 */

package dbl_algorithms;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author dianaepureanu
 */
public class GUI {
    
    //change to false if you want to keep the height X width ratio, 
    //otherwise the visualisation is stretched to the screen.
    boolean adjustRatio = false;
    boolean displayPackage = true;

    private State state;
    
    GUI(State state) {

        this.state = state;
    }
    
    public void run() {
        JFrame window = new JFrame("Packing Visualisation");
        window.setSize(new Dimension(600, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(new DrawGrid(state, adjustRatio, displayPackage));
        String fillRate = String.valueOf(state.fillRate);
        String innerArea = String.valueOf(state.innerArea);
        String outerArea = String.valueOf(state.getArea());
        String width = String.valueOf(state.layoutWidth);
        String height = String.valueOf(state.layoutHeight);
        
        System.out.println("Fill rate: "+fillRate);
        System.out.println("Inner Area: "+innerArea);
        System.out.println("Outer Area: "+outerArea);
        System.out.println("Width: "+width);
        System.out.println("Height: "+height);
      
        
    }
    
}
