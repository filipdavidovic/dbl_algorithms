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
    int sheight;
    int swidth;
    
    public DrawGrid(State s) {
        this.state = s;
        this.sheight = s.layoutHeight;
        this.swidth = s.layoutWidth;
        colors[0] = new Color(90,90,93);
        colors[1] = new Color(113,127,114);
        colors[2] = new Color(201,99,204);
        colors[3] = new Color(230,210,235);
        colors[4] = new Color(149,255,176);
        colors[5] = new Color(40,153,132);
        colors[6] = new Color(99,204,185);     
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
        //System.out.println("height: "+ sheight+ " width:  "+ swidth);
        
        if (xunit < 1){
            xunit = 1;
        }
        if (yunit < 1){
            yunit = 1;
        }
        
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
