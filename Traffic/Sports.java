import java.awt.*;
/*
 * This contains the variables for every sports car
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class Sports extends Vehicle {

    // Create a sports object
    public Sports(int newX, int newY) {
        super(newX, newY);
        width = 40;
        height = 20;
        speed = 12;
    }

    // Draw the vehicle
    public void paintMe(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x,y,width,height);
    }
}
