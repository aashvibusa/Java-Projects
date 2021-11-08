import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * This program allows the user to interact with a traffic simulation using the GUI
 * Author: Aashvi Busa
 * Date: 5/13/21
 */
public class Traffic implements ActionListener, Runnable {

    // Initialize objects and variables
    JFrame frame = new JFrame("Traffic Simulation");
    Road road = new Road();
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JLabel throughput = new JLabel("Throughput: 0");
    Container south = new Container();
    JButton semi = new JButton("Add Semi");
    JButton SUV = new JButton("Add SUV");
    JButton sports = new JButton("Add Sports");
    Container west = new Container();
    boolean running = false;
    int carCount = 0;
    long startTime = 0;

    // Constructor for Traffic
    public Traffic() {
        frame.setSize(1000, 550);
        frame.setLayout(new BorderLayout());
        frame.add(road, BorderLayout.CENTER);

        // South container
        south.setLayout(new GridLayout(1,3));
        south.add(start);
        start.addActionListener(this);
        south.add(stop);
        stop.addActionListener(this);
        south.add(throughput);
        frame.add(south, BorderLayout.SOUTH);

        // West container
        west.setLayout(new GridLayout(3,1));
        west.add(semi);
        semi.addActionListener(this);
        west.add(SUV);
        SUV.addActionListener(this);
        west.add(sports);
        sports.addActionListener(this);
        frame.add(west, BorderLayout.WEST);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.repaint();
    }

    // Allows for user interaction
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource().equals(start)) { // When start is pressed, play the simulation
            if (running == false) {
                running = true;
                road.resetCarCount();
                startTime = System.currentTimeMillis();
                Thread t = new Thread(this);
                t.start();
            }
        }
        if (event.getSource().equals(stop)) { // When stop is pressed, pause the simulation
            running = false;
        }
        if (event.getSource().equals(semi)) { // When semi is pressed, add a semi to the road
            Semi semi = new Semi(0, 30);
            road.addCar(semi);
            for (int x = 0; x < road.ROAD_WIDTH; x+=20) {
                for (int y = 40; y < 480; y+=120) {
                    semi.setX(x);
                    semi.setY(y);
                    if (!road.collision(x,y,semi)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (event.getSource().equals(SUV)) { // When SUV is pressed, add a SUV to the road
            SUV suv = new SUV(0, 30);
            road.addCar(suv);
            for (int x = 0; x < road.ROAD_WIDTH; x+=20) {
                for (int y = 40; y < 480; y+=120) {
                    suv.setX(x);
                    suv.setY(y);
                    if (!road.collision(x,y,suv)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
        if (event.getSource().equals(sports)) { // When sports is pressed, add a sports car to the road
            Sports sports = null;
            sports = new Sports(0, 30);
            road.addCar(sports);
            for (int x = 0; x < road.ROAD_WIDTH; x+=20) {
                for (int y = 40; y < 480; y+=120) {
                    sports.setX(x);
                    sports.setY(y);
                    if (!road.collision(x,y,sports)) {
                        frame.repaint();
                        return;
                    }
                }
            }
        }
    }

    // Lets the simulation and "actionPerformed" run simultaneously
    @Override
    public void run() {
        while (running) {
            road.step();
            carCount = road.getCarCount();
            double throughputCalc = carCount / (1000 * (double)(System.currentTimeMillis() - startTime));
            throughput.setText("Throughput: " + throughputCalc);
            frame.repaint();
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // Main method to start the program
    public static void main(String[] args) { new Traffic(); }
}
