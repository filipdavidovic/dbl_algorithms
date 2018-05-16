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


    private static final Font monoFont = new Font("Monospaced", Font.BOLD
      | Font.ITALIC, 36);
    private Color[] colors = new Color[14];
    Random rand = new Random();
    State state;
    int sheight;
    int swidth;
    boolean adjustRatio;
    boolean displayPackage;
    
    public DrawGrid(State s, boolean adjustRatio, boolean displayPackage) {
        this.state = s;
        this.adjustRatio = adjustRatio;
        this.displayPackage =  displayPackage;
        if (adjustRatio) {
            this.sheight = s.layoutHeight;
            this.swidth = s.layoutWidth;
        } else {
            this.sheight = Math.max(s.layoutHeight, s.layoutWidth);
            this.swidth = Math.max(s.layoutHeight, s.layoutWidth);
        }
        
        colors[0] = new Color(90,90,93);
        colors[1] = new Color(113,127,114);
        colors[2] = new Color(201,99,204);
        colors[3] = new Color(230,210,235);
        colors[4] = new Color(149,255,176);
        colors[5] = new Color(40,153,132);
        colors[6] = new Color(99,204,185);
        colors[7] = new Color(90,90,93,150);
        colors[8] = new Color(113,127,114,170);
        colors[9] = new Color(180,79,184,170);
        colors[10] = new Color(150,130,184,170);
        colors[11] = new Color(109,210,126,170);
        colors[12] = new Color(40,153,132,170);
        colors[13] = new Color(66,170,155,170);  
    }
    
    @Override
    public void paint(Graphics g) {

        Rectangle[] layout = state.getLayout();
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = super.getSize();
        
       
         
        
        //proportions based on the current dimensions
        //of the window so that everything is always visible
        double xunit = ((double)dim.width / (double)swidth);
        double yunit = ((double)dim.height / (double)sheight);
        
        if (!adjustRatio) {
            int smallest = Math.min(dim.width, dim.height);
            xunit = ((double)smallest / (double)swidth);
            yunit = ((double)smallest / (double)sheight);
        }

        
         
        for (int i = 0; i< layout.length; i++){
            //select random color from the array
            int  r = rand.nextInt(colors.length);
            g2.setColor(colors[r]);
            
            //compute dimensions
            int ulx = (int)((layout[i].blx)*xunit);
            int uly = (int)((( sheight - layout[i].bly )- layout[i].height)*yunit);
            int width = (int)(layout[i].width * xunit);
            int height = (int)(layout[i].height * yunit);

            g2.fillRect(ulx, uly, width , height);              
        }
        
        if (displayPackage) {
            int alpha = 255; // 50% transparent
            Color myColour = new Color(255, 255, 255, alpha);
            g2.setColor(myColour);
            g2.fillRect(0, dim.height -(int)(state.getLayoutHeight()*yunit), 
                    (int)(state.getLayoutWidth()*xunit) , 
                    (int)(state.getLayoutHeight()*yunit));
        }
      
        String fillRate = String.valueOf(state.fillRate);
        g.setFont(monoFont);
        g.setColor(Color.BLACK);
        FontMetrics fm = g.getFontMetrics();
        int w = fm.stringWidth(fillRate);
        int h = fm.getAscent();
        g.drawString(fillRate, dim.width - (int)xunit -w, 4*(int)yunit+h);
        
        
        
    }
    

    
}
