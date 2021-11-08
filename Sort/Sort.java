import java.io.*;
import java.util.Scanner;
/*
 * This game will allow the user to input a file with a list of numbers
 * This list will then be sorted by a method of the user's choice and outputted to a file
 * Author: Aashvi Busa
 * Date: 4/4/2021
 */
public class Sort {

    Scanner consoleInput = new Scanner(System.in);
    String input;
    Scanner fileInput;
    int[] inputArray;
    long startTime;

    // Constructor for Sort
    public Sort() {
        System.out.println("Welcome to Sort!");
        System.out.println("1: input1.txt, 2: input2.txt, 3: input3.txt, 4: input4.txt");
        System.out.print("Enter a number for the input file: ");
        input = consoleInput.nextLine();

        // Check user input
        if ((input.length() != 1) && (input.charAt(0) != '1') && (input.charAt(1) != '2')
                && (input.charAt(0) != '3') && (input.charAt(0) != '4')) {
            System.out.println("Enter 1, 2, 3, or 4");
            while ((input.length() != 1) && (input.charAt(0) != '1') && (input.charAt(1) != '2')
                    && (input.charAt(0) != '3') && (input.charAt(0) != '4')) {
                input = consoleInput.nextLine();
            }
        }

        // Read in desired file according to input
        try {
            fileInput = new Scanner(new File("input" + input.charAt(0) + ".txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(0);
        }

        // Put file contents into an array
        String infile = fileInput.nextLine();
        String[] inputStringArray = infile.split(",");
        inputArray = new int[inputStringArray.length];
        // Loops through and assigns inputStringArray to inputArray
        for (int i = 0; i < inputStringArray.length; i++) {
            inputArray[i] = Integer.parseInt(inputStringArray[i]);
        }

        System.out.println("\r\n" + "1: Bubble, 2: Selection, 3: Quicksort, 4: Table");
        System.out.print("Enter a number for the type of sort you wish to use: ");
        input = consoleInput.nextLine();

        // Check user input
        if ((input.length() != 1) && (input.charAt(0) != '1') && (input.charAt(1) != '2')
                && (input.charAt(0) != '3') && (input.charAt(0) != '4')) {
            System.out.println("Enter 1, 2, 3, or 4");
            while ((input.length() != 1) && (input.charAt(0) != '1') && (input.charAt(1) != '2')
                    && (input.charAt(0) != '3') && (input.charAt(0) != '4')) {
                input = consoleInput.nextLine();
            }
        }

        // Begin the time count
        startTime = System.currentTimeMillis();

        // Implement bubble sort
        if (input.equals("1")) {
            inputArray = bubbleSort(inputArray);
        }

        // Implement selection sort
        if (input.equals("2")) {
            inputArray = selectionSort(inputArray);
        }

        // Implement quick sort
        if (input.equals("3")) {
            inputArray = quickSort(inputArray, 0, inputArray.length-1);
        }

        // Implement table sort
        if (input.equals("4")) {
            inputArray = tableSort(inputArray);
        }

        // Print out time taken for sorting
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Total time: " + totalTime);
        PrintWriter pw;

        try {
            pw = new PrintWriter(new FileWriter(new File("output.txt")));
            String output = "";
            for (int i = 0; i < inputArray.length; i++) {
                output += inputArray[i] + ",";
                //System.out.println(inputArray[i]);
            }
            output += "\nTotal Time: " + totalTime;
            pw.write(output);
            pw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    // Compare each pair of numbers and move the larger to the right
    int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        return array;
    }

    // Find the smallest and move it to the front
    int[] selectionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int smallestNumber = array[i];
            int smallestIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < smallestNumber) {
                    smallestNumber = array[j];
                    smallestIndex = j;
                }
            }
            int temp = array[smallestIndex];
            array[smallestIndex] = array[i];
            array[i] = temp;
        }
        return array;
    }

    // Use recursions to create partitions less than or greater than a certain pivot point
    int[] quickSort(int[] array, int low, int high) {
        if (low >= high) {
            return array;
        }
        // Set the pivot point for the middle of the array
        int pivot = array[low + (high - low) / 2];
        int x = low;
        int y = high;
        // Check for values less than or greater than pivot
        while (x <= y) {
            while (array[x] < pivot) {
                x++;
            }
            while (array[y] > pivot) {
                y--;
            }
            if (x <= y) {
                int temp = array[x];
                array[x] = array[y];
                array[y] = temp;
                x++;
                y--;
            }
        }
        // Use recursion to implement quick sort for the partitions
        if (low < y) {
            quickSort(array, low, y);
        }
        if (high > x) {
            quickSort(array, x, high);
        }
        return array;
    }

    // Tally how often you see each number, print out that number of times
    int[] tableSort(int[] array) {
        int[] tally = new int[1001];
        for (int i = 0; i < array.length; i++) {
            tally[array[i]]++;
        }
        int count = 0;
        // 'i' keeps track of the actual number
        for (int i = 0; i < tally.length; i++) {
            // 'j' keeps track of how many times we've seen that number
            for (int j = 0; j < tally[i]; j++) {
                array[count] = i;
                count++;
            }
        }
        return array;
    }

    // Main method to start the program
    public static void main(String[] args) {
        new Sort();
    }
}
