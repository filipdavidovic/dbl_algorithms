/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author Tea
 */
public class BinPacker extends PackingStrategy{

    
    int containerHeight;
    boolean rotationsAllowed;
    Rectangle[] rectangles;
    
    BinPacker(int containerHeight, boolean rotationsAllowed, Rectangle[] rectangles) {
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
    }
    
    protected State pack() throws IOException, FileNotFoundException{
        QuickSort instance = new QuickSort();
        rectangles = instance.sort(rectangles);
        State s = new State(rectangles.length);
        
        return s;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Packer packer = new Packer(2, 600, 800);// 2 available packets to fill, 600X800 each
        ArrayList<Node> blocks = new ArrayList();

        blocks.add(new Node("Figure1", 300, 400));
        blocks.add(new Node("Figure2", 300, 400));
        blocks.add(new Node("Figure3", 300, 400));
        blocks.add(new Node("Figure4", 300, 400));
        blocks.add(new Node("Figure5", 300, 400));
        blocks.add(new Node("Figure6", 300, 400));
        blocks.add(new Node("Figure7", 300, 400));
        blocks.add(new Node("Figure8", 300, 400));
        blocks.add(new Node("Figure9", 300, 400));

        Collections.sort(blocks, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {

                return (Double.compare(b.w, a.w)); //doing the sort based on the width, you can change it accordingly to your needs.
            }
        });

        packer.fit(blocks);
        Iterator<Node> blocksItr = blocks.iterator();
        while (blocksItr.hasNext()) {
            Node block = blocksItr.next();
            if (block.fit != null) {
                if (block.fit.isroot) {
                    System.out.format("%32s", "Pack Starts Here");
                    System.out.println("");
                    System.out.format("%32s%24s%16s%16s%16s", "Display name", "x", "y", "w", "h");
                    System.out.println("");
                }
                System.out.format("%32s%24s%16s%16s%16s", block.name, block.fit.x, block.fit.y, block.w, block.h);
                System.out.println("");
            }
        }

        System.out.println("");
    }

}