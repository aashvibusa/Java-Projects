import java.awt.*;
/*
 * This contains the variables for every vehicle
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class Vehicle {

    // Initialize variables
    int x;
    int y;
    int width;
    int height;
    int speed;

    // Create new vehicle object
    public Vehicle(int newX, int newY) {
        x = newX;
        y = newY;
    }

    // Where each vehicle will be drawn in their respective classes
    public void paintMe(Graphics g) {

    }

    // Initialize getters + setters
    public int getX() {
        return x;
    }
    public void setX(int newX) {
        x = newX;
    }
    public int getY() { return y; }
    public void setY(int newY) { y = newY; }
    public int getSpeed() { return speed; }
    public int getWidth() { return width; }
}
