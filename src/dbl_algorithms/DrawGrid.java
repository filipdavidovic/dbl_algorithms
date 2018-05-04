/*
 */
package dbl_algorithms;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 */
public class DrawGrid extends JPanel {

    //private JFrame window = new JFrame("Packing Visualisation");
    private Color[] colors = new Color[7];
    Random rand = new Random();
    State state;
    int sheight = 5; //these will be in the state, hardcoded for now for 05_01_h7
    int swidth= 56;
    
    public DrawGrid(State s) {
        this.state = s;
        colors[0] = new Color(160,157,215);
        colors[1] = new Color(214,95,92);
        colors[2] = new Color(165,146,144);
        colors[3] = new Color(75,38,34);
        colors[4] = new Color(250,128,114);
        colors[5] = new Color(130,152,154);
        colors[6] = new Color(154,132,130);     
    }
    
    public void paint(Graphics g) {
        
        Rectangle[] layout = state.getLayout();
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = super.getSize();
        //proportions based on the current dimensions
        //of the window so that everything is always visible
        int xunit = (int)((int)dim.width / (int)swidth);
        int yunit = (int)((int)dim.height / (int)sheight);
       
        
        for (int i = 0; i< layout.length; i++){
            //select random color from the array
            int  r = rand.nextInt(7);
            g2.setColor(colors[r]);
            
            //compute dimensions
            int ulx = (layout[i].blx)*xunit;
            int uly = (( sheight - layout[i].bly )- layout[i].height)*yunit;
            int width = layout[i].width * xunit;
            int height = layout[i].height * yunit;

            g2.fillRect(ulx, uly, width , height);   
        }
        
    }
    

    
}
