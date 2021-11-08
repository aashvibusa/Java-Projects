import java.util.Scanner;
import java.io.*;
/*
 * This program will have the user input either a decimal or binary
 * number from the console or a file and choose to convert it to the other
 * Author: Aashvi Busa
 * Date: 2/19/2021
 */
public class BinaryTranslator {

    // Constructor for BinaryTranslator
    public BinaryTranslator() {
        System.out.println("Welcome to the Binary Translator!");
        System.out.print("\r\n" + "Please enter \"file\" to enter a file or \"input\" to use the console: ");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String numberInput = "";

        // Checks if user will input from a file or the console
        if (input.equals("file")) {
            try {
                System.out.print("Enter your file path and name: ");
                input = scanner.nextLine();
                Scanner fileScanner = new Scanner(new File(input));
                numberInput = fileScanner.nextLine();
            } catch (IOException ex) {
                System.out.println("File not found.");
                scanner.close();
                System.exit(1);
            }
        } else if (input.equals("input")) {
            System.out.print("Enter your number: ");
            numberInput = scanner.nextLine();
        } else {
            System.out.println("Invalid input.");
            scanner.close();
            System.exit(1);
        }

        System.out.println("\r\n" + "If you are translating from decimal to binary, type \"dtb\".");
        System.out.println("If you are translating from binary to decimal, type \"btd\".");
        System.out.print("Enter \"dtb\" or \"btd\": ");
        input = scanner.nextLine();

        // Checks if user wants to convert from decimal to binary or binary to decimal
        if (input.equals("dtb")) {
            int number = Integer.parseInt(numberInput);
            String answer = "";
            while (number > 0) {
                if (number % 2 == 1) {
                    answer = "1" + answer;
                } else if (number % 2 == 0) {
                    answer = "0" + answer;
                } else {
                    continue;
                }
                number = number / 2;
            }
            System.out.println("\r\n" + "Output = " + answer);
        } else if (input.equals("btd")) {
            int answer = 0;
            for (int i = numberInput.length() - 1; i >= 0; i--) {
                if (numberInput.charAt(i) == '1') {
                    answer = answer + (int)(Math.pow(2, numberInput.length() - (i + 1)));
                }
            }
            System.out.println("\r\n" + "Output = " + answer);
        } else {
            System.out.println("Invalid input.");
            scanner.close();
            System.exit(1);
        }

        scanner.close();
    }

    // Main method to start the program
    public static void main(String[] args) { new BinaryTranslator(); }
}
