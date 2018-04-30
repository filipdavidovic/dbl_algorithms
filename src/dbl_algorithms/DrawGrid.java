/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author dianaepureanu
 */
public class DrawGrid extends JFrame {
    
    private int[][] grid;
    private JFrame window = new JFrame();
    private Color[] colors;
    
    public DrawGrid(int[][] grid) {
        this.grid = grid;
        window.setSize(300,400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(grid.length, grid.length));
        window.setVisible(true);
        colors = new Color[7];
        colors[0] = new Color(160,157,215);
        colors[1] = new Color(214,95,92);
        colors[2] = new Color(165,146,144);
        colors[3] = new Color(75,38,34);
        colors[4] = new Color(250,128,114);
        colors[5] = new Color(130,152,154);
        colors[6] = new Color(154,132,130);
        for (int i = 0; i < grid.length; i++) {
            for ( int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0) {
                    JLabel jLabel = new JLabel();
                    jLabel.setBackground(colors[grid[i][j]%7]);
                    jLabel.setOpaque(true);

                    window.add(jLabel);
                } else {
                    JLabel jLabelx = new JLabel();
                    jLabelx.setBackground(new Color(227,221,220));
                    jLabelx.setOpaque(true);
                    window.add(jLabelx);
                }
            }
        }

    }
    
}
