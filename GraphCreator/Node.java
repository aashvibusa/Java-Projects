/*
 * This class holds methods and variables for Nodes.
 * Author: Aashvi Busa
 * Date: 5/18/21
 */
public class Node {

    int x;
    int y;
    String label;
    boolean highlighted;

    // Constructor for Node
    public Node(int newX, int newY, String newLabel) {
        x = newX;
        y = newY;
        label = newLabel;
        highlighted = false;
    }

    // Gets the x-value of the node
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // Gets the y-value of the node
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Gets the label corresponding to the node
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // Gets whether or not a node is highlighted
    public boolean getHighlighted() {
        return highlighted;
    }

    // Sets whether or not a node is highlighted
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
}
