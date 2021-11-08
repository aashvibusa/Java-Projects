import java.util.ArrayList;
/*
 * This class queues objects for GraphCreator
 * Author: Aashvi Busa
 * Date: 5/19/21
 */
public class Queue {

    ArrayList<String> queue = new ArrayList<String>();

    // Adds a string to the queue
    public void enqueue(String s) {
        queue.add(s);
    }

    // Returns the first element in the queue
    public String dequeue() {
        String s = queue.get(0);
        queue.remove(0);
        return s;
    }

    // Checks if queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
