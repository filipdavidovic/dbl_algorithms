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

    private final ArrayList<Node> root = new ArrayList();

    public Packer(int numofpackets, double w, double h) {
        for(int i = 0; i< numofpackets;i++){
            this.root.add(new Node(0, 0, w, h));
        }        
    }
    

    public void fit(ArrayList<Node> blocks) {
        Node node;
        Node block;
        Iterator<Node> blockItr = blocks.iterator();
        int n=0;
        while (blockItr.hasNext()) {
            block = blockItr.next();
            if ((node = this.findNode(this.root.get(n), block.w, block.h))!=null) {
                block.fit = this.splitNode(node, block.w, block.h);
                if(node.isroot){
                    block.fit.isroot = true;
                }
            }else{
                n++;
            }
        }
    }

    public Node findNode(Node root, double w, double h) {
        if (root.used) {
            Node right = findNode(root.right, w, h);
            return (right != null ? right : findNode(root.down, w, h));
        } else if ((w <= root.w) && (h <= root.h)) {
            return root;
        } else {
            return null;
        }
    }

    public Node splitNode(Node node, double w, double h) {
        node.used = true;
        node.down = new Node(node.x, node.y + h, node.w, node.h - h);
        node.right = new Node(node.x + w, node.y, node.w - w, h);
        return node;
    }

}