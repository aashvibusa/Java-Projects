import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
 * This class draws the nodes + edges and highlights them on the graph.
 * Author: Aashvi Busa
 * Date: 5/17/21
 */
public class GraphPanel extends JPanel {

    ArrayList<Node> nodeList = new ArrayList<Node>();
    ArrayList<Edge> edgeList = new ArrayList<Edge>();
    int circleRadius = 20;

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    ArrayList<ArrayList<Boolean>> adjacency = new ArrayList<ArrayList<Boolean>>();

    // Constructor for GraphPanel
    public GraphPanel() {
        super();
    }

    // Gets the labels of connected nodes
    public ArrayList<String> getConnectedLabels(String label) {
        int x = getIndex(label);
        ArrayList<String> toReturn = new ArrayList<String>();
        for (int i = 0; i < adjacency.size(); i++) {
            if ((adjacency.get(x).get(i)) && !(nodeList.get(i).getLabel().equals(label))) {
                toReturn.add(nodeList.get(i).getLabel());
            }
        }
        return toReturn;
    }

    // Prints the adjacency matrix
    public void printAdjacency() {
        System.out.println();
        for (int i = 0; i < adjacency.size(); i++) {
            for (int j = 0; j < adjacency.get(0).size(); j++) {
                System.out.print(adjacency.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    // Adds a node to the graph
    public void addNode(int newX, int newY, String newLabel) {
        nodeList.add(new Node(newX, newY, newLabel));
        adjacency.add(new ArrayList<Boolean>());
        for (int i = 0; i < adjacency.size(); i++) {
            adjacency.get(i).add(false);
        }
        for (int i = 0; i < adjacency.size(); i++) {
            adjacency.get(adjacency.size() - 1).add(false);
        }
        printAdjacency();
    }

    // Adds an edge to the graph
    public void addEdge(Node first, Node second, String newLabel) {
        edgeList.add(new Edge(first, second, newLabel));
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < nodeList.size(); i++) {
           if (first.equals(nodeList.get(i))) {
               firstIndex = i;
           }
            if (second.equals(nodeList.get(i))) {
                secondIndex = i;
            }
            adjacency.get(firstIndex).set(secondIndex, true);
            adjacency.get(secondIndex).set(firstIndex, true);
        }
        printAdjacency();
    }

    // Gets node from x and y positions
    public Node getNode(int x, int y) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            double radius = Math.sqrt(Math.pow(x-node.getX(), 2) + Math.pow(y-node.getY(), 2));
            if (radius < circleRadius) {
                return node;
            }
        }
        return null;
    }

    // Gets node from label
    public Node getNode(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            if (s.equals(node.getLabel())) {
                return node;
            }
        }
        return null;
    }

    // Gets the index of a node in nodeList
    public int getIndex(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            Node node = nodeList.get(i);
            if (s.equals(node.getLabel())) {
                return i;
            }
        }
        return -1;
    }

    // Checks if a certain node exists
    public boolean nodeExists(String s) {
        for (int i = 0; i < nodeList.size(); i++) {
            if (s.equals(nodeList.get(i).getLabel())) {
                return true;
            }
        }
        return false;
    }

    // Paints components on the interface
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < nodeList.size(); i++) {
            if (nodeList.get(i).getHighlighted()) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.drawOval(nodeList.get(i).getX() - circleRadius,
                    nodeList.get(i).getY() - circleRadius,
                    circleRadius * 2,
                    circleRadius * 2);
            g.drawString(nodeList.get(i).getLabel(), nodeList.get(i).getX(), nodeList.get(i).getY());
        }
        for (int i = 0; i < edgeList.size(); i++) {
            g.setColor(Color.BLACK);
            g.drawLine(edgeList.get(i).getFirst().getX(),
                    edgeList.get(i).getFirst().getY(),
                    edgeList.get(i).getSecond().getX(),
                    edgeList.get(i).getSecond().getY());
            int fx = edgeList.get(i).getFirst().getX();
            int fy = edgeList.get(i).getFirst().getY();
            int sx = edgeList.get(i).getSecond().getX();
            int sy = edgeList.get(i).getSecond().getY();
            g.drawString(edgeList.get(i).getLabel(),
                    Math.min(fx,sx) + (Math.abs(sx-fx) / 2),
                    Math.min(fy,sy) + (Math.abs(sy-fy) / 2));
        }
    }

    // Stops nodes from being highlighted
    public void stopHighlighting() {
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).setHighlighted(false);
        }
    }
}