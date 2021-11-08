import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
 * This implements the JPanel for the traffic simulation and draws the road
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class Road extends JPanel {

    // Initialize variables
    final int LANE_HEIGHT = 120;
    final int ROAD_WIDTH = 800;
    ArrayList<Vehicle> cars = new ArrayList<Vehicle>();
    int carCount = 0;

    // Constructor for Road
    public Road() {
        super();
    }

    // Adds a car to the ArrayList "cars"
    public void addCar(Vehicle v) {
        cars.add(v);
    }

    // Draws the road
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(0,0,300,500);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(), getHeight());
        g.setColor(Color.WHITE);
        for (int i = LANE_HEIGHT; i < LANE_HEIGHT * 4; i+=LANE_HEIGHT) {
            for (int j = 0; j < getWidth(); j+=40) {
                g.fillRect(j, i, 30, 5);
            }
        }
        for (int i = 0; i < cars.size(); i++) {
            cars.get(i).paintMe(g);

        }
    }

    // Allows the vehicles to move according to their position
    public void step() {
        for (int i = 0; i < cars.size(); i++) {
            Vehicle v = cars.get(i);
            if (!collision(v.getX() + v.getSpeed(), v.getY(), v)) {
                v.setX(v.getX() + v.getSpeed());
                if (v.getX() > ROAD_WIDTH) {
                    if (!collision(v.getX() + v.getSpeed(), v.getY(), v)) {
                        v.setX(0);
                        carCount++;
                    }
                }
            } else {
                if ((v.getY() > 40) && (!collision(v.getX(),v.getY()-LANE_HEIGHT,v))) {
                        v.setY(v.getY() - LANE_HEIGHT);
                } else if ((v.getY() < 40 + 3*LANE_HEIGHT) && (!collision(v.getX(),v.getY()+LANE_HEIGHT,v))) {
                    v.setY(v.getY() + LANE_HEIGHT);
                }
            }
        }
    }

    // Checks for a collision
    public boolean collision(int x, int y, Vehicle v) {
        for (int i = 0; i < cars.size(); i++) {
            Vehicle u = cars.get(i);
            if (y == u.getY()) {
                if (!u.equals(v)) {
                    if (x < u.getX() + u.getWidth() && x + v.getWidth() > u.getX()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Gets the number of cars
    public int getCarCount() {
        return carCount;
    }

    // Resets the car count
    public void resetCarCount() {
        carCount = 0;
    }
}
