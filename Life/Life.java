import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/*
 * This program is an implementation of Conway's Life simulation
 * Author: Aashvi Busa
 * Date: 3/10/21
 */
public class Life implements MouseListener, ActionListener, Runnable {

    // Define variables and objects
    boolean[][] cells = new boolean[10][10];
    JFrame frame = new JFrame("Life simulation");
    LifePanel panel = new LifePanel(cells);
    Container south = new Container();
    JButton step = new JButton("Step");
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    boolean running = false;

    // Constructor for Life
    public Life() {
        frame.setSize(600,600);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        panel.addMouseListener(this);

        // South container
        south.setLayout(new GridLayout(1,3));
        south.add(step);
        step.addActionListener(this);
        south.add(start);
        start.addActionListener(this);
        south.add(stop);
        stop.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    // Changes the cell value when the mouse is released
    @Override
    public void mouseReleased(MouseEvent event) {
        double width = (double) panel.getWidth() / cells[0].length;
        double height = (double) panel.getHeight() / cells.length;
        int row = Math.min(cells[0].length - 1,(int)(event.getY() / height));
        int column = Math.min(cells.length - 1,(int)(event.getX() / width));
        cells[row][column] = !cells[row][column];
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Performs actions depending on which button was clicked
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(step)) {
            step();
        }
        if (event.getSource().equals(start)) {
            if (!running) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (event.getSource().equals(stop)) {
            running = false;
        }
    }

    // Lets 'step' and 'actionPerformed' run simultaneously
    @Override
    public void run() {
        while (running) {
            step();
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Checks the amount of neighbors for each cell and acts accordingly
    public void step() {
        boolean[][] nextCells = new boolean[cells.length][cells[0].length];
        for (int row = 0; row < cells.length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                int neighborCount = 0;
                if ((row > 0) && (column > 0) && (cells[row-1][column-1])) { // Top left
                    neighborCount++;
                }
                if ((row > 0) && (cells[row-1][column])) { // Top
                    neighborCount++;
                }
                if ((row > 0) && (column < cells[0].length-1) && (cells[row-1][column+1])) { // Top right
                    neighborCount++;
                }
                if ((column > 0) && (cells[row][column-1])) { // Middle left
                    neighborCount++;
                }
                if ((column < cells[0].length-1) && (cells[row][column+1])) { // Middle right
                    neighborCount++;
                }
                if ((row < cells.length-1) && (column > 0) && (cells[row+1][column-1])) { // Bottom left
                    neighborCount++;
                }
                if ((row < cells.length-1) && (cells[row+1][column])) { // Bottom
                    neighborCount++;
                }
                if ((row < cells.length-1) && (column < cells[0].length-1) && (cells[row+1][column+1])) { // Bottom right
                    neighborCount++;
                }

                // Rules of life
                if (cells[row][column] == true) {
                    if ((neighborCount == 2) || (neighborCount == 3)) {
                        nextCells[row][column] = true;
                    } else {
                        nextCells[row][column] = false;
                    }
                } else {
                    if (neighborCount == 3) {
                        nextCells[row][column] = true;
                    } else {
                        nextCells[row][column] = false;
                    }
                }
            }
        }
        cells = nextCells;
        panel.setCells(nextCells);
        frame.repaint();
    }

    // Main method to start the program
    public static void main(String[] args) { new Life(); }
}
