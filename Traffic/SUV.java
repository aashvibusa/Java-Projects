import java.awt.*;
/*
 * This contains the variables for every SUV
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class SUV extends Vehicle {

    // Create a SUV object
    public SUV(int newX, int newY) {
        super(newX, newY);
        width = 60;
        height = 30;
        speed = 8;
    }

    // Draw the vehicle
    public void paintMe(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x,y,width,height);
    }
}
