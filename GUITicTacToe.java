import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
 * This game will allow two users to play Tic-Tac-Toe by using the GUI
 * Author: Aashvi Busa
 * Date: 3/1/2021
 */
public class GUITicTacToe implements ActionListener {

    JFrame frame = new JFrame();
    JButton[][] button = new JButton[3][3];
    JButton xChangeName = new JButton("Change X's name");
    JButton oChangeName = new JButton("Change O's name");
    Container center = new Container();
    Container north = new Container();
    JLabel xLabel = new JLabel("X wins: 0");
    JLabel oLabel = new JLabel("O wins: 0");
    JTextField xChangeField = new JTextField();
    JTextField oChangeField = new JTextField();

    int[][] board = new int[3][3];
    final int BLANK = 0;
    final int X_MOVE = 1;
    final int O_MOVE = 2;
    final int X_TURN = 0;
    final int O_TURN = 1;
    int turn = X_TURN;
    String xPlayerName = "X";
    String oPlayerName = "O";
    int xWins = 0;
    int oWins = 0;

    // Constructor for GUITicTacToe
    public GUITicTacToe() {
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        center.setLayout(new GridLayout(3,3));

        // Creates a button for every grid box
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {
                button[j][i] = new JButton();
                center.add(button[j][i]);
                button[j][i].addActionListener(this);
            }
        }
        frame.add(center, BorderLayout.CENTER);

        north.setLayout(new GridLayout(3,2));
        north.add(xLabel);
        north.add(oLabel);
        north.add(xChangeName);
        xChangeName.addActionListener(this);
        north.add(oChangeName);
        oChangeName.addActionListener(this);
        north.add(xChangeField);
        north.add(oChangeField);
        frame.add(north, BorderLayout.NORTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Creates an action event for user interaction
    @Override
    public void actionPerformed(ActionEvent event) {
        JButton current;
        boolean gridButton = false;

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[0].length; j++) {

                // Disables grid boxes once clicked
                if (event.getSource().equals(button[j][i])) {
                    gridButton = true;
                    current = button[j][i];
                    current.setEnabled(false);

                    // Sets the grid boxes to 'X' or 'O' depending on the player turn
                    if (board[j][i] == BLANK) {
                        if (turn == X_TURN) {
                            current.setText("X");
                            board[j][i] = X_MOVE;
                            turn = O_TURN;
                        } else {
                            current.setText("O");
                            board[j][i] = O_MOVE;
                            turn = X_TURN;
                        }

                        // Checks if either 'X' or 'O' has won or if the game is tied
                        if (checkWin(X_MOVE) == true) {
                            xWins++;
                            xLabel.setText(xPlayerName + " wins: " + xWins);
                            resetBoard();
                        } else if (checkWin(O_MOVE) == true) {
                            oWins++;
                            oLabel.setText(oPlayerName + " wins: " + oWins);
                            resetBoard();
                        } else if (checkTie() == true) {
                            resetBoard();
                        } else {
                            continue;
                        }
                    }
                }
            }
        }

        // Lets users change names
        if (!gridButton) {
            if (event.getSource().equals(xChangeName)) {
                if (xChangeField.getText().equals("")) {
                    xLabel.setText(xPlayerName + " wins: " + xWins);
                } else {
                    xPlayerName = xChangeField.getText();
                    xLabel.setText(xPlayerName + " wins: " + xWins);
                }
            } else if (event.getSource().equals(oChangeName)) {
                if (oChangeField.getText().equals("")) {
                    oLabel.setText(oPlayerName + " wins: " + oWins);
                } else {
                    oPlayerName = oChangeField.getText();
                    oLabel.setText(oPlayerName + " wins: " + oWins);
                }
            }
        }
    }

    // Resets the Tic-Tac-Toe board
    public void resetBoard() {
        JButton reset;
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                reset = button[row][column];
                reset.setEnabled(true);
                board[row][column] = BLANK;
                button[row][column].setText("");
            }
        }
        turn = X_TURN;
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
    public static void main (String[] args) { new GUITicTacToe(); }
}
