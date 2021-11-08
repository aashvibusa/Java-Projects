import java.awt.*;
/*
 * This contains the variables for every semi
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class Semi extends Vehicle {

    // Create a semi object
    public Semi(int newX, int newY) {
        super(newX, newY);
        width = 100;
        height = 40;
        speed = 5;
    }

    // Draw the vehicle
    public void paintMe(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x,y,width,height);
    }

}
