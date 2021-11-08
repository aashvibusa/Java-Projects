import java.util.Scanner;
/*
 * This game will have the user guess a number between 0 and 50
 * Author: Aashvi Busa
 * Date: 2/11/2021
 */
public class GuessingGame {

    // Constructor for GuessingGame
    public GuessingGame() {
        boolean stillPlaying = true;

        // Runs while the user is still playing
        while (stillPlaying) {
            int random = (int)(Math.random() * 51);
            int guess = -1;
            int guessNumber = 0;

            System.out.println("Welcome to the Guessing Game!");
            System.out.println("Guess a number between 0 and 50.");

            // Runs until the user guesses correctly
            while(guess != random) {
                System.out.print("\r\n" + "Enter a number: ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();

                // Checks if the user input is an integer
                try {
                    guess = Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid guess. Try guessing a number between 0 and 50.");
                    continue;
                }

                // Compares the user's guess to the random number
                if ((guess < 0) || (guess > 50)) {
                    System.out.println("Invalid guess. Try guessing a number between 0 and 50.");
                } else if (guess < random) {
                    System.out.println("Too low!");
                    guessNumber++;
                } else if (guess > random) {
                    System.out.println("Too high!");
                    guessNumber++;
                } else {
                    System.out.println("Congratulations! You guessed the number.");
                    guessNumber++;
                    if (guessNumber == 1) {
                        System.out.println("It took you 1 guess.");
                    } else {
                        System.out.println("It took you " + guessNumber + " guesses.");
                    }
                    break;
                }
            }

            boolean validInput = false;

            // Checks if the user wants to continue playing
            while (!validInput) {
                System.out.println("\r\n" + "Do you want to play again?");
                System.out.print("Enter yes or no: ");
                Scanner scanner = new Scanner(System.in);
                String playAgain = scanner.nextLine();

                if (playAgain.equals("yes") || playAgain.equals("Yes")) {
                    stillPlaying = true;
                    validInput = true;
                    System.out.println(" ");
                } else if (playAgain.equals("no") || playAgain.equals("No")) {
                    stillPlaying = false;
                    validInput = true;
                    System.out.println("\r\n" + "Thanks for playing!");
                } else {
                    validInput = false;
                    System.out.println("Invalid answer. Try entering yes or no.");
                }
            }
        }
    }

    // Main method to start the program
    public static void main(String[] args) { new GuessingGame(); }
}
