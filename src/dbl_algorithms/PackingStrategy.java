/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbl_algorithms;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author dianaepureanu
 */
public abstract class PackingStrategy {
    
    protected abstract void pack() throws IOException, FileNotFoundException;
}
