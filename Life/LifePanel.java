import javax.swing.*;
import java.awt.*;
/*
 * Panel for Conway's Life simulation, this draws the grid and cells
 * Author: Aashvi Busa
 * Date: 3/10/21
 */
public class LifePanel extends JPanel {

    boolean[][] cells;
    double width;
    double height;

    // Constructor for LifePanel
    public LifePanel(boolean[][] in) {
        cells = in;
    }

    // Updates cells
    public void setCells(boolean[][] newCells) {
        cells = newCells;
    }

    // Draws the grid
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        width = (double) this.getWidth() / cells[0].length;
        height = (double) this.getHeight() / cells.length;

        // Draws the cells
        g.setColor(Color.BLACK);
        for (int row = 0; row < cells[0].length; row++) {
            for (int column = 0; column < cells[0].length; column++) {
                if (cells[row][column] == true) {
                    g.fillRect((int)Math.round(column * width),
                            (int)Math.round(row * height),
                            (int)(width+1), (int)(height+1));
                }
            }
        }

        // Draws the grid lines
        g.setColor(Color.BLACK);
        for (int x = 0; x < cells[0].length + 1; x++) {
            g.drawLine((int)Math.round(x*width),0,(int)Math.round(x*width),this.getHeight());
        }
        for (int y = 0; y < cells.length + 1; y++) {
            g.drawLine(0,(int)Math.round(y*height),this.getWidth(),(int)Math.round(y*height));
        }
    }
}
