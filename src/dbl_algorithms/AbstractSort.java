package dbl_algorithms;

/**
 * abstract class for sorting an array of rectangles by decreasing width/height
 *
 */
public interface AbstractSort {

    /*
    * 
     */
    abstract Rectangle[] sort(Rectangle[] rectangles) throws CloneNotSupportedException;
}
