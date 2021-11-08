import java.util.*;
/*
 * This game will allow two users to play Tic-Tac-Toe
 * Author: Aashvi Busa
 * Date: 2/23/2021
 */
public class TicTacToe {

    int[][] board = new int[3][3];

    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;
    final int X_TURN = 0;
    final int O_TURN = 1;

    int xWins = 0;
    int oWins = 0;
    int ties = 0;

    Scanner scanner;
    String input = "";

    // Constructor for TicTacToe
    public TicTacToe() {
        boolean stillPlaying = true;

        // Runs while the user is still playing
        while (stillPlaying) {
            System.out.println("Welcome to Tic-Tac-Toe!");
            scanner = new Scanner(System.in);
            boolean gameEnd;
            int turn = X_TURN;

            // Runs till either 'X' or 'O' wins
            while (!checkWin(X_MOVE) && !checkWin(O_MOVE) && !checkTie()) {
                printBoard();
                if (turn == X_TURN) {
                    System.out.println("\r\n" + "It's X's turn.");
                } else {
                    System.out.println("\r\n" + "It's O's turn.");
                }
                System.out.print("Enter a coordinate: ");
                input = scanner.nextLine();

                // Checks if the coordinate entered is valid and puts it on the board
                if (input.length() != 2) {
                    System.out.println("Invalid input. Enter a letter followed by a number.");
                } else if (input.charAt(0) != 'a' &&
                        input.charAt(0) != 'b' &&
                        input.charAt(0) != 'c') {
                    System.out.println("Invalid input. Row must be an a, b, or c.");
                } else if (input.charAt(1) != '1' &&
                        input.charAt(1) != '2' &&
                        input.charAt(1) != '3') {
                    System.out.println("Invalid input. Column must be a 1, 2, or 3.");
                } else {
                    int row = input.charAt(0) - 'a';
                    int column = input.charAt(1) - '1';
                    if (board[row][column] == BLANK) {
                        if (turn == X_TURN) {
                            board[row][column] = X_MOVE;
                            turn = O_TURN;
                        } else {
                            board[row][column] = O_MOVE;
                            turn = X_TURN;
                        }
                    } else {
                        System.out.println("There is a piece there!");
                    }
                }
            }

            // Checks if either 'X' or 'O' has won or if the game is tied
            if (checkWin(X_MOVE)) {
                System.out.println("X wins!");
                xWins += 1;
                printBoard();
                clearBoard();
                gameEnd = true;
            } else if (checkWin(O_MOVE)) {
                System.out.println("O wins!");
                oWins += 1;
                printBoard();
                clearBoard();
                gameEnd = true;
            } else if (checkTie()) {
                System.out.println("It's a tie!");
                ties += 1;
                printBoard();
                clearBoard();
                gameEnd = true;
            } else {
                continue;
            }

            // Checks if the user wants to continue playing
            if (gameEnd) {
                System.out.println("\r\n" + "X-wins = " + xWins + ", O-wins = " + oWins + ", Ties = " + ties);
                boolean validInput = false;
                while (!validInput) {
                    System.out.print("Would you like to continue playing (Y or N): ");
                    input = scanner.nextLine();
                    if (input.equals("Y")) {
                        stillPlaying = true;
                        validInput = true;
                    } else if (input.equals("N")) {
                        System.out.println("Thanks for playing!");
                        stillPlaying = false;
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Try entering Y or N.");
                        validInput = false;
                    }
                }
            }
        }
    }

    // Prints the Tic-Tac-Toe board
    public void printBoard() {
        System.out.println("\r\n" + " \t1\t2\t3");
        for (int row = 0; row < board.length; row++) {
            String output = (char)('a' + row) + "\t";
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == BLANK) {
                    output += " \t";
                } else if (board[row][column] == X_MOVE) {
                    output += "X\t";
                } else if (board[row][column] == O_MOVE) {
                    output += "O\t";
                }
            }
            System.out.println(output);
        }
    }

    // Clears the Tic-Tac-Toe board
    public void clearBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                board[row][column] = BLANK;
            }
        }
    }

    // Checks if either 'X' or 'O' has won
    public boolean checkWin(int player) {
        if (board[0][0] == player && board[0][1] == player && board[0][2] == player) {
            return true;
        } else if (board[1][0] == player && board[1][1] == player && board[1][2] == player) {
            return true;
        } else if (board[2][0] == player && board[2][1] == player && board[2][2] == player) {
            return true;
        } else if (board[0][0] == player && board[1][0] == player && board[2][0] == player) {
            return true;
        } else if (board[0][1] == player && board[1][1] == player && board[2][1] == player) {
            return true;
        } else if (board[0][2] == player && board[1][2] == player && board[2][2] == player) {
            return true;
        } else if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        } else if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        } else {
            return false;
        }
    }

    // Checks if the game is tied
    public boolean checkTie() {
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == BLANK) {
                    return false;
                }
            }
        }
        return true;
    }

    // Main method to start the program
    public static void main(String[] args) { new TicTacToe(); }
}
