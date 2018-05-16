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
    boolean adjustRatio = true;

    private State state;
    
    GUI(State state) {

        this.state = state;
    }
    
    public void run() {
        JFrame window = new JFrame("Packing Visualisation");
        window.setSize(new Dimension(600, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(new DrawGrid(state, adjustRatio));
        String fillRate = String.valueOf(state.fillRate);
        System.out.println(fillRate);
    }
    
}
