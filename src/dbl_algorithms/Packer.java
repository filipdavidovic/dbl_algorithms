/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Tea
 */
public class Packer {

     private final ArrayList<Rectangle> root = new ArrayList();

    public Packer(int w, int h) {
            this.root.add(new Rectangle(w, h, 0, 0));
    }
    

    public void fit(ArrayList<Rectangle> blocks) {
        Rectangle node;
        Rectangle block;
        Iterator<Rectangle> blockItr = blocks.iterator();
        int n=0;
        while (blockItr.hasNext()) {
            block = blockItr.next();
            if ((node = this.findRectangle(this.root.get(n), block.width, block.height))!=null) {
                block.fit = this.splitRectangle(node, block.width, block.height);
                
            }else{
                n++;
            }
        }
    }

    public Rectangle findRectangle(Rectangle root, int w, int h) {
        if (root.used) {
            Rectangle right = findRectangle(root.right, w, h);
            return (right != null ? right : findRectangle(root.down, w, h));
        } else if ((w <= root.width) && (h <= root.height)) {
            return root;
        } else {
            return null;
        }
    }

    public Rectangle splitRectangle(Rectangle node, int w, int h) {
        node.used = true;
        node.down = new Rectangle( node.width, node.height - h, node.blx, node.bly + h);
        node.right = new Rectangle(node.width - w, h, node.blx + w, node.bly);
        return node;
    }

}