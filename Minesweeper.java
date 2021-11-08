import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * This game allows the user to play Minesweeper by using the GUI
 * Author: Aashvi Busa
 * Date: 4/29/2021
 */
public class Minesweeper implements ActionListener, MouseListener {
    // Initialize variables
    JFrame frame = new JFrame("Minesweeper");
    JButton reset = new JButton("Reset");
    JButton[][] buttons = new JButton[20][20];
    int[][] counts = new int[20][20];
    Container grid = new Container();
    final int MINE = 10;

    // Constructor for Minesweeper
    public Minesweeper() {
        // Set up frame
        frame.setSize(1000,700);
        frame.setLayout(new BorderLayout());
        frame.add(reset, BorderLayout.NORTH);
        //frame.addMouseListener(this);
        reset.addActionListener(this);

        // Set up button grid
        grid.setLayout(new GridLayout(20,20));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].addMouseListener(this);
                grid.add(buttons[i][j]);
            }
        }
        frame.add(grid, BorderLayout.CENTER);
        createRandomMines();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Create 30 random mines
    public void createRandomMines() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[0].length; j++) {
                list.add((i * 100) + j);
            }
        }
        // Reset counts, pick out 30 mines
        counts = new int[20][20];
        for (int i = 0; i < 30; i++) {
            int choice = (int)(Math.random() * list.size());
            counts[list.get(choice) / 100][list.get(choice) % 100] = MINE;
            list.remove(choice);
        }
        // Initialize neighbor counts
        for (int i= 0; i < counts.length; i++) {
            for (int j = 0; j < counts[0].length; j++) {
                int neighborCount = 0;
                if (counts[i][j] != MINE) {
                    if ((i > 0) && (j > 0) && (counts[i-1][j-1] == MINE)) { // Top left
                        neighborCount++;
                    }
                    if ((j > 0) && (counts[i][j-1] == MINE)) { // Top middle
                        neighborCount++;
                    }
                    if ((i < counts.length-1) && (j > 0) && (counts[i+1][j-1] == MINE)) { // Top right
                        neighborCount++;
                    }
                    if ((i > 0) && (counts[i-1][j] == MINE)) { // Middle left
                        neighborCount++;
                    }
                    if ((i < counts.length-1) && (counts[i+1][j] == MINE)) { // Middle Right
                        neighborCount++;
                    }
                    if ((i > 0) && (j < counts[0].length-1) && (counts[i-1][j+1] == MINE)) { // Bottom left
                        neighborCount++;
                    }
                    if ((j < counts[0].length-1) && (counts[i][j+1] == MINE)) { // Bottom middle
                        neighborCount++;
                    }
                    if ((i < counts.length-1) && (j < counts[0].length-1) && (counts[i+1][j+1] == MINE)) { // Bottom right
                        neighborCount++;
                    }
                    counts[i][j] = neighborCount;
                }
            }
        }
    }

    // Clear all zeroes touching a zero once clicked
    public void clearZeros(ArrayList<Integer> toClear) {
        if (toClear.size() == 0) {
            return;
        } else {
            int x = toClear.get(0) / 100;
            int y = toClear.get(0) % 100;
            toClear.remove(0);
            if (x > 0 && y > 0 && buttons[x-1][y-1].isEnabled()) { // Top left
                buttons[x-1][y-1].setText(counts[x-1][y-1]+"");
                buttons[x-1][y-1].setEnabled(false);
                if (counts[x-1][y-1] == 0) {
                    toClear.add((x-1) * 100 + (y-1));
                }
            }
            if (y > 0 && buttons[x][y-1].isEnabled()) { // Top middle
                buttons[x][y-1].setText(counts[x][y-1]+"");
                buttons[x][y-1].setEnabled(false);
                if (counts[x][y-1] == 0) {
                    toClear.add((x) * 100 + (y-1));
                }
            }
            if (x < counts.length - 1 && y > 0 && buttons[x+1][y-1].isEnabled()) { // Top right
                buttons[x+1][y-1].setText(counts[x+1][y-1]+"");
                buttons[x+1][y-1].setEnabled(false);
                if (counts[x+1][y-1] == 0) {
                    toClear.add((x+1) * 100 + (y-1));
                }
            }
            if (x > 0 && buttons[x-1][y].isEnabled()) { // Middle right
                buttons[x-1][y].setText(counts[x-1][y]+"");
                buttons[x-1][y].setEnabled(false);
                if (counts[x-1][y] == 0) {
                    toClear.add((x-1) * 100 + y);
                }
            }
            if (x < counts.length - 1 && buttons[x+1][y].isEnabled()) { // Middle right
                buttons[x+1][y].setText(counts[x+1][y]+"");
                buttons[x+1][y].setEnabled(false);
                if (counts[x+1][y] == 0) {
                    toClear.add((x+1) * 100 + y);
                }
            }
            if (x > 0 && y < counts[0].length - 1 && buttons[x-1][y+1].isEnabled()) { // Bottom left
                buttons[x-1][y+1].setText(counts[x-1][y+1]+"");
                buttons[x-1][y+1].setEnabled(false);
                if (counts[x-1][y+1] == 0) {
                    toClear.add((x-1) * 100 + (y+1));
                }
            }
            if (y < counts[0].length - 1 && buttons[x][y+1].isEnabled()) { // Bottom middle
                buttons[x][y+1].setText(counts[x][y+1]+"");
                buttons[x][y+1].setEnabled(false);
                if (counts[x][y+1] == 0) {
                    toClear.add((x) * 100 + (y+1));
                }
            }
            if (x < counts.length - 1 && y < counts[0].length - 1 && buttons[x+1][y+1].isEnabled()) { // Bottom right
                buttons[x+1][y+1].setText(counts[x+1][y+1]+"");
                buttons[x+1][y+1].setEnabled(false);
                if (counts[x+1][y+1] == 0) {
                    toClear.add((x+1) * 100 + (y+1));
                }
            }
            clearZeros(toClear);
        }
    }

    // Execute if the user touches a mine
    public void lostGame() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (buttons[i][j].isEnabled()) {
                    if (counts[i][j] != MINE) {
                        buttons[i][j].setText(counts[i][j] + "");
                        buttons[i][j].setEnabled(false);
                    } else {
                        buttons[i][j].setText("X");
                        buttons[i][j].setEnabled(false);
                    }
                }
            }
        }
    }

    // Check if the user has won
    public void checkWin() {
        boolean won = true;
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[0].length; j++) {
                if (counts[i][j] != MINE && buttons[i][j].isEnabled()) {
                    won = false;
                }
            }
        }
        if (won) {
            JOptionPane.showMessageDialog(frame, "You win!");
        }
    }

    // Get user action input from the GUI
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(reset)) {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[0].length; j++) {
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setText("");
                }
            }
            createRandomMines();
        } else {
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[0].length; j++) {
                    if (event.getSource() == buttons[i][j]) {
                        if (counts[i][j] == MINE) {
                            buttons[i][j].setForeground(Color.red);
                            buttons[i][j].setText("X");
                            lostGame();
                            JOptionPane.showMessageDialog(frame, "You lose!");
                        } else if (counts[i][j] == 0) {
                            buttons[i][j].setText(counts[i][j] + "");
                            buttons[i][j].setEnabled(false);
                            ArrayList<Integer> toClear = new ArrayList<Integer>();
                            toClear.add((i*100) + j);
                            clearZeros(toClear);
                            checkWin();
                        } else {
                            buttons[i][j].setText(counts[i][j] + "");
                            buttons[i][j].setEnabled(false);
                            checkWin();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    // If the user right clicks, display a flag on the button
    @Override
    public void mouseReleased(MouseEvent event) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (SwingUtilities.isRightMouseButton(event) && event.getSource() == buttons[i][j]
                        && buttons[i][j].getText() != "\u2691" && buttons[i][j].isEnabled()) {
                    buttons[i][j].setText("\u2691");
                } else if (SwingUtilities.isRightMouseButton(event) && event.getSource() == buttons[i][j]
                        && buttons[i][j].getText() == "\u2691" && buttons[i][j].isEnabled()) {
                    buttons[i][j].setText("");
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // Main method to start the program
    public static void main(String[] args) { new Minesweeper(); }
}
