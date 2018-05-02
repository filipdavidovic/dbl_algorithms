/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Random;


/**
 *
 * @author dianaepureanu
 */
public class DrawGrid extends JFrame {
    
    //private int[][] grid;
    private JFrame window = new JFrame("Packing Visualisation");
    private Color[] colors = new Color[7];
    Random rand = new Random();
        
    
    public DrawGrid(Rectangle[] layout) {
        //this.grid = grid;
        //define colors
        colors[0] = new Color(160,157,215);
        colors[1] = new Color(214,95,92);
        colors[2] = new Color(165,146,144);
        colors[3] = new Color(75,38,34);
        colors[4] = new Color(250,128,114);
        colors[5] = new Color(130,152,154);
        colors[6] = new Color(154,132,130);
        
        //window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridBagLayout());
        fillLayout(window.getContentPane(), layout); 
        window.pack(); 
        window.setVisible(true);
 
    }
    
    private void fillLayout(Container pane, Rectangle[] layout){
        
        JLabel label;
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE; //or none?   
        
        for (int i = 0; i< layout.length; i++){
            
            label = new JLabel(String.valueOf(i));
            c.fill = GridBagConstraints.NONE;
            c.weightx = (layout[i].width)/100; 
            c.weighty = (1/layout.length); 
            c.gridx = layout[i].blx + layout[i].height;
            c.gridy = layout[i].bly;
            c.gridwidth = layout[i].width;
            c.gridheight = layout[i].height;
           // c.anchor = GridBagConstraints.FIRST_LINE_START; //top left
            //choose random color from our list
            int  n = rand.nextInt(7);
            label.setBackground(colors[n]);
            label.setOpaque(true);
            label.setVisible(true);
            pane.add(label, c);
        }
        
        
        
        
        
        
    }
    
}
