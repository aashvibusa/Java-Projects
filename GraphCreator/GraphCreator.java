import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
/*
 * This program allows the user to create a graph with nodes and edges using the GUI.
 * The user may check if nodes are connected and find the "cheapest" path between nodes.
 * Author: Aashvi Busa
 * Date: 5/17/21
 */
public class GraphCreator implements ActionListener, MouseListener {

    // Initialize objects and variables
    JFrame frame = new JFrame();
    GraphPanel panel = new GraphPanel();
    JButton nodeB = new JButton("Node");
    JButton edgeB = new JButton("Edge");
    JButton connectedB = new JButton("Test Connected");
    JButton salesmanB = new JButton("Shortest Path");
    JTextField labelsTF = new JTextField("A");
    JTextField firstNode = new JTextField("First");
    JTextField secondNode = new JTextField("Second");
    JTextField salesmanStartTF = new JTextField("A");
    Container west = new Container();
    Container east = new Container();
    Container south = new Container();
    final int NODE_CREATE = 0;
    final int EDGE_FIRST = 1;
    final int EDGE_SECOND = 2;
    int state;
    Node first = null;
    ArrayList<ArrayList<Node>> completed = new ArrayList<ArrayList<Node>>();
    ArrayList<Integer> costs = new ArrayList<Integer>();
    int costAddition = 0;
    String tempLabel = "";

    // Constructor for GraphCreator
    public GraphCreator() {
        frame.setSize(800,400);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        // West container
        west.setLayout(new GridLayout(3,1));
        west.add(nodeB);
        nodeB.addActionListener(this);
        nodeB.setBackground(Color.GREEN);
        west.add(edgeB);
        edgeB.addActionListener(this);
        edgeB.setBackground(Color.LIGHT_GRAY);
        west.add(labelsTF);
        frame.add(west, BorderLayout.WEST);

        // East Container
        east.setLayout(new GridLayout(3,1));
        east.add(firstNode);
        east.add(secondNode);
        east.add(connectedB);
        connectedB.addActionListener(this);
        frame.add(east, BorderLayout.EAST);

        // South container
        south.setLayout(new GridLayout(1,2));
        south.add(salesmanStartTF);
        south.add(salesmanB);
        salesmanB.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Method for travelling salesman
    public void travelling(Node n, ArrayList<Node> path, int total) {
        costs.clear();
        if (path.size() == panel.getNodeList().size()) {
            if (path.get(0).getLabel().equals(tempLabel)) {
                costAddition = total;
                completed.add(path);
                costs.add(costAddition);
                costAddition = 0;
            }
        } else {
            if (!path.contains(n)) {
                path.add(n);
                tempLabel = n.getLabel();
            }
            for (int i = 0; i < panel.getEdgeList().size(); i++) {
                Edge e = panel.getEdgeList().get(i);
                if (e.getOtherEnd(n) != null) {
                    if (!path.contains(e.getOtherEnd(n))) {
                        path.add(e.getOtherEnd(n));
                        travelling(e.getOtherEnd(n), path, total + Integer.parseInt(e.getLabel()));
                    }
                }
            }
        }
    }

    // Allows for user interaction with the GUI
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nodeB)) { // If the node button is pressed
            nodeB.setBackground(Color.GREEN);
            edgeB.setBackground(Color.LIGHT_GRAY);
            state = NODE_CREATE;
        }
        if (e.getSource().equals(edgeB)) { // If the edge button is pressed
            edgeB.setBackground(Color.GREEN);
            nodeB.setBackground(Color.LIGHT_GRAY);
            state = EDGE_FIRST;
            panel.stopHighlighting();
            frame.repaint();
        }
        if (e.getSource().equals(connectedB)) { // If the connected button is pressed
            if (!panel.nodeExists(firstNode.getText())) {
                JOptionPane.showMessageDialog(frame, "First node is not in your graph.");
            } else if (!panel.nodeExists(secondNode.getText())) {
                JOptionPane.showMessageDialog(frame, "Second node is not in your graph.");
            } else {
                Queue queue = new Queue();
                ArrayList<String> connectedList = new ArrayList<String>();
                connectedList.add(panel.getNode(firstNode.getText()).getLabel());
                ArrayList<String> edges = panel.getConnectedLabels(firstNode.getText());
                for (int i = 0; i < edges.size(); i++) {
                    queue.enqueue(edges.get(i));
                }
                while (!queue.isEmpty()) {
                    String currentNode = queue.dequeue();
                    if (!connectedList.contains(currentNode)) {
                        connectedList.add(currentNode);
                    }
                    edges = panel.getConnectedLabels(currentNode);
                    for (int i = 0; i < edges.size(); i++) {
                        if (!connectedList.contains(edges.get(i))) {
                            queue.enqueue(edges.get(i));
                        }
                    }
                }
                if (connectedList.contains(secondNode.getText())) {
                    JOptionPane.showMessageDialog(frame, "Connected!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Not Connected.");
                }
            }
        }
        if (e.getSource().equals(salesmanB)) { // If the salesman button is pressed
            if (panel.getNode(salesmanStartTF.getText()) != null) {
                if (completed != null) {
                    travelling(panel.getNode(salesmanStartTF.getText()), new ArrayList<Node>(), 0);
                    int least = 0;
                    int index = 0;
                    String s = "";
                    for (int i = 0; i < costs.size(); i++) {
                        if (i == 0) {
                            least = costs.get(i);
                        } else if (least > costs.get(i)) {
                            least = costs.get(i);
                            index = i;
                        }
                    }
                    for (int i = 0; i < completed.get(index).size(); i++) {
                        s += completed.get(index).get(i).getLabel() + ", ";
                    }
                    JOptionPane.showMessageDialog(frame, "Nodes in the path are: " + s + "\r\n The shortest path costs: " + least);
                    costs.clear();
                }
            } else {
                JOptionPane.showMessageDialog(frame,"Not a valid starting node!");
            }
        }
    }

    // Allows the user to interact with mouse inputs
    @Override
    public void mouseReleased(MouseEvent e) {
       if (state == NODE_CREATE) { // If the node button has been pressed
           panel.addNode(e.getX(), e.getY(), labelsTF.getText());
       } else if (state == EDGE_FIRST) { // If the edge button has been pressed
           Node n = panel.getNode(e.getX(), e.getY());
           if (n != null) {
               first = n;
               state = EDGE_SECOND;
               n.setHighlighted(true);
           }
       } else if (state == EDGE_SECOND) { // If one node has already been selected
           Node n = panel.getNode(e.getX(), e.getY());
           if (n != null && !first.equals(n)) {
               String s = labelsTF.getText();
               boolean valid = true;
               for (int i = 0; i < s.length(); i++) {
                   if (!Character.isDigit(s.charAt(i))) {
                       valid = false;
                   }
               }
               if (valid) {
                   first.setHighlighted(false);
                   panel.addEdge(first, n, labelsTF.getText());
                   first = null;
                   state = EDGE_FIRST;
               } else {
                   JOptionPane.showMessageDialog(frame, "You can only have digits in edge labels.");
               }
           }
       }
        frame.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    // Main method to start the program
    public static void main(String[] args) { new GraphCreator(); }
}
