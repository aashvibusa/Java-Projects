/*
 * This class holds methods and variables for Edges.
 * Author: Aashvi Busa
 * Date: 5/18/21
 */
public class Edge {

    Node first;
    Node second;
    String label;

    // Constructor for Edge
    public Edge(Node newFirst, Node newSecond, String newLabel) {
        first = newFirst;
        second = newSecond;
        label = newLabel;
    }

    // Finds the node on the opposite end of the edge
    public Node getOtherEnd(Node n) {
        if (first.equals(n)) {
            return second;
        } else if (second.equals(n)) {
            return first;
        } else {
            return null;
        }
    }

    // Gets first node connected to the edge
    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    // Gets second node connected to the edge
    public Node getSecond() {
        return second;
    }

    public void setSecond(Node second) {
        this.second = second;
    }

    // Gets the label corresponding to the edge
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
