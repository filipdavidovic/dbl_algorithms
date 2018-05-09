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

    private State state;
    
    GUI(State state) {

        this.state = state;
    }
    
    public void run() {
        JFrame window = new JFrame("Packing Visualisation");
        window.setSize(new Dimension(600, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(new DrawGrid(state));
        String fillRate = String.valueOf(state.fillRate);
      //      g2.drawString(fillRate, 20, 20);
//        JLabel label = new JLabel();
//        label.setText(fillRate);
//        label.setVisible(true);
//        window.add(label);
    }
    
}
