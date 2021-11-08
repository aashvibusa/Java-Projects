import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

/*
 * Implementation of Arimaa for two players via the GUI
 * Author: Sitara and Aashvi
 * Date: 6/1/2021
 */

public class Arimaa implements ActionListener {

    //Global variables & objects

    JFrame frame = new JFrame();

    //Containers
    Container north = new Container();
    Container south = new Container();
    Container east = new Container();
    Container west = new Container();
    Container center = new Container();

    JButton[][] gridButtons = new JButton[8][8];
    int[][] gridBoard = new int[8][8];
    int[][] pieceStates = new int[8][8];

    JButton currentButton = new JButton();
    int currentRow;
    int currentColumn;
    int currentPieceValue;
    JButton strongButton = new JButton();
    int strongRow;
    int strongColumn;
    JButton weakButton = new JButton();
    int weakRow;
    int weakColumn;
    JButton previousButton = new JButton();
    int previousRow;
    int previousColumn;
    JButton initialButton = new JButton();
    int initalRow;
    int initialColumn;

    final int G_ELEPHANT = 6;
    final int G_CAMEL = 5;
    final int G_HORSE = 4;
    final int G_DOG = 3;
    final int G_CAT = 2;
    final int G_RABBIT = 1;

    final int BLANK = 0;

    final int S_ELEPHANT = 12;
    final int S_CAMEL = 11;
    final int S_HORSE = 10;
    final int S_DOG = 9;
    final int S_CAT = 8;
    final int S_RABBIT = 7;

    int aShift, bShift; //the gold pieces (valued 1-6) and silver pieces (valued 7-12), so we need a shift when comparing the pieces

    ArrayList<Integer> goldPieceList = new ArrayList<Integer>(Arrays.asList(G_ELEPHANT, G_CAMEL, G_HORSE, G_HORSE, G_DOG, G_DOG, G_CAT, G_CAT, G_RABBIT, G_RABBIT, G_RABBIT, G_RABBIT, G_RABBIT, G_RABBIT, G_RABBIT, G_RABBIT));
    ArrayList<Integer> silverPieceList = new ArrayList<Integer>(Arrays.asList(S_ELEPHANT, S_CAMEL, S_HORSE, S_HORSE, S_DOG, S_DOG, S_CAT, S_CAT, S_RABBIT, S_RABBIT, S_RABBIT, S_RABBIT, S_RABBIT, S_RABBIT, S_RABBIT, S_RABBIT));

    JButton push = new JButton("Push");
    JButton pull = new JButton("Pull");
    JButton step = new JButton("Step");
    JPanel moveButtons = new JPanel();
    JButton turnDone = new JButton("Turn Done");
    JButton reset = new JButton("Reset");
    JButton start = new JButton("Start");

    //Labels
    JLabel goldLabel = new JLabel("GOLD", SwingConstants.CENTER);
    JLabel silverLabel = new JLabel("SILVER", SwingConstants.CENTER);
    JLabel piecesLabel = new JLabel("<html><p><center>PIECE</center></p></html>", SwingConstants.CENTER);
    JLabel winsLabel = new JLabel("WINS", SwingConstants.CENTER);
    JLabel goldWinsLabel = new JLabel("Gold: 0");
    JLabel silverWinsLabel = new JLabel("Silver: 0");
    JLabel blankLabel = new JLabel(" ", SwingConstants.CENTER);
    JLabel setupLabel = new JLabel("<html><p><center>SET-UP</center></p></html>", SwingConstants.CENTER);
    JLabel movesLabel = new JLabel("MOVES", SwingConstants.CENTER);

    JLabel instructionsLabel = new JLabel("<html><p><center>Instructions:</center><br> "
            + " Click the grid square <br> "
            + " that you want to place <br> "
            + " your piece on once you <br> "
            + " have selected an animal <br></p></html>");

    int goldWins = 0;
    int silverWins = 0;

    //Radio buttons
    JRadioButton elephantButton = new JRadioButton("Elephant");
    boolean elephant = false;
    int eCounter = 1;
    JRadioButton camelButton = new JRadioButton("Camel");
    boolean camel = false;
    int cCounter = 1;
    JRadioButton horseButton = new JRadioButton("Horses");
    boolean horse = false;
    int hCounter = 2;
    JRadioButton dogButton = new JRadioButton("Dogs");
    boolean dog = false;
    int dCounter = 2;
    JRadioButton catButton = new JRadioButton("Cats");
    boolean cat = false;
    int catCounter = 2;
    JRadioButton rabbitButton = new JRadioButton("Rabbits");
    boolean rabbit = false;
    int rCounter = 8;

    ImageIcon[] imageGold = new ImageIcon[6];
    ImageIcon[] imageSilver = new ImageIcon[6];

    int turn;
    final int GOLD_TURN = 0, SILVER_TURN = 1;
    int gameState;
    final int SET_UP = 0, PLAY = 1, FINISH = 2;
    final int STEP = 1, PUSH = 2, PULL_ONE = 3, PULL_TWO = 4, TEMP = 5;
    int moveState = TEMP;

    final int PART_ONE = 0, PART_TWO = 1;
    int pushPart = PART_ONE;

    int stepCounter = 4;

    // Constructor for Arimaa
    public Arimaa() {
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        // Icons for gold
        imageGold[0] = new ImageIcon("./images/eGold.png");
        imageGold[1] = new ImageIcon("./images/cGold.png");
        imageGold[2] = new ImageIcon("./images/hGold.png");
        imageGold[3] = new ImageIcon("./images/dGold.png");
        imageGold[4] = new ImageIcon("./images/catGold.png");
        imageGold[5] = new ImageIcon("./images/rGold.png");

        // Icons for silver
        imageSilver[0] = new ImageIcon("./images/eSilver.png");
        imageSilver[1] = new ImageIcon("./images/cSilver.png");
        imageSilver[2] = new ImageIcon("./images/hSilver.png");
        imageSilver[3] = new ImageIcon("./images/dSilver.png");
        imageSilver[4] = new ImageIcon("./images/catSilver.png");
        imageSilver[5] = new ImageIcon("./images/rSilver.png");

        //North container: silver
        north.setLayout(new GridLayout(1, 1));
        north.add(silverLabel);
        frame.add(north, BorderLayout.NORTH);

        //South container: gold
        south.setLayout(new GridLayout(1, 1));
        south.add(goldLabel);
        frame.add(south, BorderLayout.SOUTH);

        //Center container: grid
        center.setLayout(new GridLayout(8, 8));
        //creating the buttons in the grid
        for (int a = 0; a < gridButtons.length; a++) {
            for (int b = 0; b < gridButtons[0].length; b++) {
                gridButtons[a][b] = new JButton();
                gridButtons[a][b].addActionListener(this);
                gridButtons[a][b].setEnabled(false);
                center.add(gridButtons[a][b]);
                gridBoard[a][b] = BLANK;
            }
        }
        setBackgrounds();
        frame.add(center, BorderLayout.CENTER);

        //East container: move buttons
        east.setLayout(new GridLayout(11,1));
        east.add(movesLabel);
        east.add(blankLabel);
        moveButtons.setLayout(new GridLayout(1,3));
        moveButtons.add(push);
        push.setEnabled(false);
        push.addActionListener(this);
        moveButtons.add(pull);
        pull.setEnabled(false);
        pull.addActionListener(this);
        moveButtons.add(step);
        step.setEnabled(false);
        step.addActionListener(this);
        east.add(moveButtons);
        east.add(turnDone);
        turnDone.setEnabled(false);
        turnDone.addActionListener(this);
        east.add(blankLabel);
        east.add(winsLabel);
        east.add(goldWinsLabel);
        east.add(silverWinsLabel);
        east.add(blankLabel);
        east.add(reset);
        reset.setEnabled(false);
        reset.addActionListener(this);
        east.add(start);
        start.addActionListener(this);
        frame.add(east, BorderLayout.EAST);

        //West container: setup GUI
        BoxLayout boxlayout = new BoxLayout(west, BoxLayout.Y_AXIS);
        west.setLayout(boxlayout);
        west.add(setupLabel);
        west.add(blankLabel);
        west.add(piecesLabel);
        west.add(blankLabel);

        // Radiobuttons
        elephantButton.setMnemonic(KeyEvent.VK_B);
        elephantButton.setActionCommand("Elephant");
        west.add(elephantButton);
        elephantButton.addActionListener(this);
        elephantButton.setEnabled(false);
        camelButton.setMnemonic(KeyEvent.VK_C);
        camelButton.setActionCommand("Camel");
        west.add(camelButton);
        camelButton.addActionListener(this);
        camelButton.setEnabled(false);
        horseButton.setMnemonic(KeyEvent.VK_C);
        horseButton.setActionCommand("Horse");
        west.add(horseButton);
        horseButton.addActionListener(this);
        horseButton.setEnabled(false);
        dogButton.setMnemonic(KeyEvent.VK_C);
        dogButton.setActionCommand("Dog");
        west.add(dogButton);
        dogButton.addActionListener(this);
        dogButton.setEnabled(false);
        catButton.setMnemonic(KeyEvent.VK_C);
        catButton.setActionCommand("Cat");
        west.add(catButton);
        catButton.addActionListener(this);
        catButton.setEnabled(false);
        rabbitButton.setMnemonic(KeyEvent.VK_C);
        rabbitButton.setActionCommand("Rabbit");
        west.add(rabbitButton);
        rabbitButton.addActionListener(this);
        rabbitButton.setEnabled(false);

        west.add(blankLabel);
        west.add(instructionsLabel);

        frame.add(west, BorderLayout.WEST);

        //Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(elephantButton);
        group.add(camelButton);
        group.add(horseButton);
        group.add(dogButton);
        group.add(catButton);
        group.add(rabbitButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Main method to start the program
    public static void main(String[] args) {
        /*
         * I copied this code from stackoverflow.com
         * URL: https://stackoverflow.com/questions/1065691/how-to-set-the-background-color-of-a-jbutton-on-the-mac-os#:~:text=Normally%20with%20Java%20Swing%20you,RED)%3B
         * This section makes Apple Mac's GUI components match Windows
         */
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Arimaa();
    }

    //Method to check if someone has won (by goal, elimination, or immobilization)
    public void checkWin() {

        boolean gWin = false;
        boolean sWin = false;
        //checks if win is by elimination
        if (!goldPieceList.contains(G_RABBIT)) {
            sWin = true;
        } else if (!silverPieceList.contains(S_RABBIT)) {
            gWin = true;
        }

        //checks if win is by immobilization
        int gCount = 0;
        int sCount = 0;
        for (int a = 0; a < gridButtons.length; a++) {
            for (int b = 0; b < gridButtons[0].length; b++) {
                currentRow = a;
                currentColumn = b;
                if (gridBoard[a][b] >= 1 && gridBoard[a][b] <= 6 && checkFrozen()) {
                    gCount++;
                    if (gCount == goldPieceList.size()) {
                        sWin = true;
                    }
                } else if (gridBoard[a][b] >= 7 && gridBoard[a][b] <= 12 && checkFrozen()) {
                    sCount++;
                    if (sCount == silverPieceList.size()) {
                        sWin = true;
                    }
                }
            }
        }

        //checks if win is by goal
        for (int i = 0; i < gridButtons.length; i++) {
            if (gridBoard[0][i] == G_RABBIT) {
                gWin = true;
            } else if (gridBoard[7][i] == S_RABBIT) {
                sWin = true;
            }
        }

        //If there is a win: update label and only enable reset button
        if (gWin) {
            goldWins++;
            goldWinsLabel.setText("Gold: " + goldWins);
            JOptionPane.showMessageDialog(frame, "Gold wins!");
            for (int a = 0; a < gridButtons.length; a++) {
                for (int b = 0; b < gridButtons[0].length; b++) {
                    gridButtons[a][b].setEnabled(false);
                }
            }
            reset.setEnabled(true);
            start.setEnabled(false);
            push.setEnabled(false);
            pull.setEnabled(false);
            step.setEnabled(false);
            turnDone.setEnabled(false);
        } else if (sWin) {
            silverWins++;
            silverWinsLabel.setText("Silver: " + silverWins);
            JOptionPane.showMessageDialog(frame, "Silver wins!");
            for (int a = 0; a < gridButtons.length; a++) {
                for (int b = 0; b < gridButtons[0].length; b++) {
                    gridButtons[a][b].setEnabled(false);
                }
            }
            reset.setEnabled(true);
            start.setEnabled(false);
            push.setEnabled(false);
            pull.setEnabled(false);
            step.setEnabled(false);
            turnDone.setEnabled(false);
        }
    }

    //Method to completely reset the GUI to start again
    public void resetBoard() {
        turn = GOLD_TURN;
        gameState = SET_UP;
        setBackgrounds();
        for (int a = 0; a < gridButtons.length; a++) {
            for (int b = 0; b < gridButtons[0].length; b++) {
                gridButtons[a][b].setEnabled(false);
                gridButtons[a][b].setIcon(null);
                gridBoard[a][b] = BLANK;
            }
        }
        push.setEnabled(false);
        pull.setEnabled(false);
        step.setEnabled(false);
        turnDone.setEnabled(false);
        start.setEnabled(true);
        reset.setEnabled(false);

        eCounter = 1;
        cCounter = 1;
        hCounter = 2;
        dCounter = 2;
        catCounter = 2;
        rCounter = 8;
        stepCounter = 4;
    }

    //Method to check if a piece on the board is frozen
    public boolean checkFrozen() {

        //Shift for comparing gold pieces (valued 1-6) and silver pieces (valued 7-12) hierarchy
        if(currentPieceValue <= G_ELEPHANT) {
            aShift = 0;
            bShift = 6;
        } else {
            aShift = 6;
            bShift = 0;
        }

        //Checking the current piece's above, left, right, and down squares for if they can freeze the current piece

        if((currentRow > 0) && (gridBoard[currentRow-1][currentColumn] != BLANK)) { //above
            if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                    && gridBoard[currentRow-1][currentColumn] <= G_ELEPHANT) {//if there is a friendly piece
                return false;
            } else if((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                    && gridBoard[currentRow-1][currentColumn] >= S_RABBIT){//if there is a friendly piece
                return false;
            }
        }
        if((currentColumn > 0) && (gridBoard[currentRow][currentColumn-1] != BLANK)) { //left
            if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                    && gridBoard[currentRow][currentColumn-1] <= G_ELEPHANT) {//if there is a friendly piece
                return false;
            } else if((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                    && gridBoard[currentRow][currentColumn-1] >= S_RABBIT){//if there is a friendly piece
                return false;
            }
        }
        if((currentColumn < gridButtons[0].length - 1) && (gridBoard[currentRow][currentColumn+1] != BLANK)) { //right
            if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                    && gridBoard[currentRow][currentColumn+1] <= G_ELEPHANT) {//if there is a friendly piece
                return false;
            } else if((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                    && gridBoard[currentRow][currentColumn+1] >= S_RABBIT){//if there is a friendly piece
                return false;
            }
        }
        if((currentRow < gridButtons.length - 1) && (gridBoard[currentRow+1][currentColumn] != BLANK)) { //down
            if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                    && gridBoard[currentRow+1][currentColumn] <= G_ELEPHANT) {//if there is a friendly piece
                return false;
            } else if((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                    && gridBoard[currentRow+1][currentColumn] >= S_RABBIT){//if there is a friendly piece
                return false;
            }
        }

        if((currentRow > 0) && (gridBoard[currentRow-1][currentColumn] != BLANK)) { //above
            if((gridBoard[currentRow-1][currentColumn]-bShift) > currentPieceValue-aShift) {//if there's a stronger piece
                if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                        && gridBoard[currentRow-1][currentColumn] >= S_RABBIT) {//if it's an opposing piece
                    return true;
                } else if ((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                        && gridBoard[currentRow-1][currentColumn] <= G_ELEPHANT) {//if it's an opposing piece
                    return true;
                } else {
                    return false;
                }
            }
        }
        if((currentColumn > 0) && (gridBoard[currentRow][currentColumn-1] != BLANK)) { //left
            if((gridBoard[currentRow][currentColumn-1]-bShift) > currentPieceValue-aShift) {//if there's a stronger piece
                if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                        && gridBoard[currentRow][currentColumn-1] >= S_RABBIT) {//if it's an opposing piece
                    return true;
                } else if ((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                        && gridBoard[currentRow][currentColumn-1] <= G_ELEPHANT) {//if it's an opposing piece
                    return true;
                } else {
                    return false;
                }
            }
        }
        if((currentColumn < gridButtons[0].length - 1) && (gridBoard[currentRow][currentColumn+1] != BLANK)) { //right
            if((gridBoard[currentRow][currentColumn+1]-bShift) > currentPieceValue-aShift) {//if there's a stronger piece
                if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                        && gridBoard[currentRow][currentColumn+1] >= S_RABBIT) {//if it's an opposing piece
                    return true;
                } else if ((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                        && gridBoard[currentRow][currentColumn+1] <= G_ELEPHANT) {//if it's an opposing piece
                    return true;
                } else {
                    return false;
                }
            }
        }
        if((currentRow < gridButtons.length - 1) && (gridBoard[currentRow+1][currentColumn] != BLANK)) { //down
            if((gridBoard[currentRow+1][currentColumn]-bShift) > currentPieceValue-aShift) {//if there's a stronger piece
                if((gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT)
                        && gridBoard[currentRow+1][currentColumn] >= S_RABBIT) {//if it's an opposing piece
                    return true;
                } else if ((gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT)
                        && gridBoard[currentRow+1][currentColumn] <= G_ELEPHANT) {//if it's an opposing piece
                    return true;
                } else {
                    return false;
                }
            }
        }


        return false;
    }

    //Method to check if a piece in a trap square has been 'captured' (no adjacent friendly pieces)
    public void checkCaptured() {

        setBackgrounds();

        //for loop to check each of the four trap squares
        for(int i = 0; i < 4; i++) {
            if(i == 0) {
                currentRow = 2;
                currentColumn = 2;
            } else if(i == 1) {
                currentRow = 2;
                currentColumn = 5;
            } else if (i == 2) {
                currentRow = 5;
                currentColumn = 2;
            } else if (i == 3){
                currentRow = 5;
                currentColumn = 5;
            }

            //Checking if the silver or gold piece has no adjacent friendly pieces, and if so, removing it from the game
            if (gridBoard[currentRow][currentColumn] <= G_ELEPHANT && gridBoard[currentRow][currentColumn] >= G_RABBIT) {
                if (!(gridBoard[currentRow - 1][currentColumn] <= G_ELEPHANT && gridBoard[currentRow - 1][currentColumn] >= G_RABBIT)
                        && !(gridBoard[currentRow + 1][currentColumn] <= G_ELEPHANT && gridBoard[currentRow + 1][currentColumn] >= G_RABBIT)
                        && !(gridBoard[currentRow][currentColumn - 1] <= G_ELEPHANT && gridBoard[currentRow][currentColumn - 1] >= G_RABBIT)
                        && !(gridBoard[currentRow][currentColumn + 1] <= G_ELEPHANT && gridBoard[currentRow][currentColumn + 1] >= G_RABBIT)) {

                    goldPieceList.remove(goldPieceList.indexOf(gridBoard[currentRow][currentColumn]));
                    gridButtons[currentRow][currentColumn].setIcon(null);
                    gridBoard[currentRow][currentColumn] = BLANK;

                }
            } else if (gridBoard[currentRow][currentColumn] <= S_ELEPHANT && gridBoard[currentRow][currentColumn] >= S_RABBIT) {
                if (!(gridBoard[currentRow - 1][currentColumn] >= S_RABBIT)
                        && !(gridBoard[currentRow + 1][currentColumn] >= S_RABBIT)
                        && !(gridBoard[currentRow][currentColumn - 1] >= S_RABBIT)
                        && !(gridBoard[currentRow][currentColumn + 1] >= S_RABBIT)) {

                    silverPieceList.remove(silverPieceList.indexOf(gridBoard[currentRow][currentColumn]));
                    gridButtons[currentRow][currentColumn].setIcon(null);
                    gridBoard[currentRow][currentColumn] = BLANK;
                }
            }
        }
    }

    //Method for checking if a piece can push another adjacent piece
    public boolean checkPush() {

        //shifting down silver to compare with gold depending on if it's silver pushing OR getting pushed
        if(currentPieceValue <= G_ELEPHANT) {
            aShift = 0;
            bShift = 6;
        } else {
            aShift = 6;
            bShift = 0;
        }

        if(stepCounter < 2) {
            return false;
        } else {
            //A push is possible if an adjacent square houses an opponenent's weaker piece
            if((currentRow > 0) && (gridBoard[currentRow-1][currentColumn] != BLANK) && ((gridBoard[currentRow-1][currentColumn]-bShift) < currentPieceValue-aShift)) { //above
                if(turn == GOLD_TURN && gridBoard[currentRow-1][currentColumn] >= S_RABBIT) {
                    return true;
                } else if (turn == SILVER_TURN && gridBoard[currentRow-1][currentColumn] <= G_ELEPHANT) {
                    return true;
                } else {
                    return false;
                }
            }
            if((currentColumn > 0) && (gridBoard[currentRow][currentColumn-1] != BLANK) && ((gridBoard[currentRow][currentColumn-1]-bShift) < currentPieceValue-aShift)) { //left
                if(turn == GOLD_TURN && gridBoard[currentRow][currentColumn-1] >= S_RABBIT) {
                    return true;
                } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn-1] <= G_ELEPHANT) {
                    return true;
                } else {
                    return false;
                }
            }
            if((currentColumn < gridButtons[0].length - 1) && (gridBoard[currentRow][currentColumn+1] != BLANK) && ((gridBoard[currentRow][currentColumn+1]-bShift) < currentPieceValue-aShift)) { //right
                if(turn == GOLD_TURN && gridBoard[currentRow][currentColumn+1] >= S_RABBIT) {
                    return true;
                } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn+1] <= G_ELEPHANT) {
                    return true;
                } else {
                    return false;
                }
            }
            if((currentRow < gridButtons.length - 1) && (gridBoard[currentRow+1][currentColumn] != BLANK) && ((gridBoard[currentRow+1][currentColumn]-bShift) < currentPieceValue-aShift)) { //down
                if(turn == GOLD_TURN && gridBoard[currentRow+1][currentColumn] >= S_RABBIT) {
                    return true;
                } else if (turn == SILVER_TURN && gridBoard[currentRow+1][currentColumn] <= G_ELEPHANT) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    //method for checking if a piece can pull
    public boolean checkPull() {

        //Shift for comparing gold pieces (valued 1-6) and silver pieces (valued 7-12) hierarchy
        if (currentPieceValue <= G_ELEPHANT) {
            aShift = 0;
            bShift = 6;
        } else {
            aShift = 6;
            bShift = 0;
        }

        boolean conditionOne = false;
        boolean conditionTwo = false;

        //Checking if a pull is possible if an adjacent square houses an opponenent's weaker piece

        if ((currentRow > 0) && (gridBoard[currentRow - 1][currentColumn] - bShift) < currentPieceValue - aShift) { //above
            if (gridBoard[currentRow - 1][currentColumn] == BLANK) {
                conditionOne = true;
            } else if (turn == GOLD_TURN && gridBoard[currentRow - 1][currentColumn] >= S_RABBIT) {
                conditionTwo = true;
            } else if (turn == SILVER_TURN && gridBoard[currentRow - 1][currentColumn] <= G_ELEPHANT) {
                conditionTwo = true;
            }
        }
        if ((currentColumn > 0) && (gridBoard[currentRow][currentColumn - 1] - bShift) < currentPieceValue - aShift) { //left
            if (gridBoard[currentRow][currentColumn - 1] == BLANK) {
                conditionOne = true;
            } else if (turn == GOLD_TURN && gridBoard[currentRow][currentColumn - 1] >= S_RABBIT) {
                conditionTwo = true;
            } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn - 1] <= G_ELEPHANT) {
                conditionTwo = true;
            }
        }
        if ((currentColumn < gridButtons[0].length - 1) && (gridBoard[currentRow][currentColumn + 1] - bShift) < currentPieceValue - aShift) { //right
            if (gridBoard[currentRow][currentColumn + 1] == BLANK) {
                conditionOne = true;
            } else if (turn == GOLD_TURN && gridBoard[currentRow][currentColumn + 1] >= S_RABBIT) {
                conditionTwo = true;
            } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn + 1] <= G_ELEPHANT) {
                conditionTwo = true;
            }
        }
        if ((currentRow < gridButtons.length - 1) && (gridBoard[currentRow + 1][currentColumn] - bShift) < currentPieceValue - aShift) { //down
            if (gridBoard[currentRow + 1][currentColumn] == BLANK) {
                conditionOne = true;
            } else if (turn == GOLD_TURN && gridBoard[currentRow + 1][currentColumn] >= S_RABBIT) {
                conditionTwo = true;
            } else if (turn == SILVER_TURN && gridBoard[currentRow + 1][currentColumn] <= G_ELEPHANT) {
                conditionTwo = true;
            }
        }
        if (conditionOne && conditionTwo) {
            return true;
        }
        return false;
    }

    //method for letting a piece push or pull
    public void pushOrPull() {

        //disabling all grid buttons first so they can't be selected for a move
        for (int a = 0; a < gridButtons.length; a++) {
            for (int b = 0; b < gridButtons[0].length; b++) {
                gridButtons[a][b].setEnabled(false);
            }
        }

        //shifting down silver to compare with gold depending on if it's silver pushing OR getting pushed
        if(currentPieceValue <= G_ELEPHANT) {
            aShift = 0;
            bShift = 6;
        } else {
            aShift = 6;
            bShift = 0;
        }

        //enabling and highlighting the spaces with opponent's weaker pieces to push or pull
        if((currentRow > 0) && (gridBoard[currentRow-1][currentColumn] != BLANK) && ((gridBoard[currentRow-1][currentColumn]-bShift) < currentPieceValue-aShift)) { //above
            if(turn == GOLD_TURN && gridBoard[currentRow-1][currentColumn] >= S_RABBIT) {
                gridButtons[currentRow-1][currentColumn].setEnabled(true);
                gridButtons[currentRow-1][currentColumn].setBackground(Color.GREEN);
            } else if (turn == SILVER_TURN && gridBoard[currentRow-1][currentColumn] <= G_ELEPHANT) {
                gridButtons[currentRow-1][currentColumn].setEnabled(true);
                gridButtons[currentRow-1][currentColumn].setBackground(Color.GREEN);
            }
        }
        if((currentColumn > 0) && (gridBoard[currentRow][currentColumn-1] != BLANK) && ((gridBoard[currentRow][currentColumn-1]-bShift) < currentPieceValue-aShift)) { //left
            if(turn == GOLD_TURN && gridBoard[currentRow][currentColumn-1] >= S_RABBIT) {
                gridButtons[currentRow][currentColumn-1].setEnabled(true);
                gridButtons[currentRow][currentColumn-1].setBackground(Color.GREEN);
            } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn-1] <= G_ELEPHANT) {
                gridButtons[currentRow][currentColumn-1].setEnabled(true);
                gridButtons[currentRow][currentColumn-1].setBackground(Color.GREEN);
            }
        }
        if((currentColumn < gridBoard.length - 1) && (gridBoard[currentRow][currentColumn+1] != BLANK) && ((gridBoard[currentRow][currentColumn+1]-bShift) < currentPieceValue-aShift)) { //right
            if(turn == GOLD_TURN && gridBoard[currentRow][currentColumn+1] >= S_RABBIT) {
                gridButtons[currentRow][currentColumn+1].setEnabled(true);
                gridButtons[currentRow][currentColumn+1].setBackground(Color.GREEN);
            } else if (turn == SILVER_TURN && gridBoard[currentRow][currentColumn+1] <= G_ELEPHANT) {
                gridButtons[currentRow][currentColumn+1].setEnabled(true);
                gridButtons[currentRow][currentColumn+1].setBackground(Color.GREEN);
            }
        }
        if((currentRow < gridBoard.length - 1) && (gridBoard[currentRow+1][currentColumn] != BLANK) && ((gridBoard[currentRow+1][currentColumn]-bShift) < currentPieceValue-aShift)) { //down
            if(turn == GOLD_TURN && gridBoard[currentRow+1][currentColumn] >= S_RABBIT) {
                gridButtons[currentRow+1][currentColumn].setEnabled(true);
                gridButtons[currentRow+1][currentColumn].setBackground(Color.GREEN);
            } else if (turn == SILVER_TURN && gridBoard[currentRow+1][currentColumn] <= G_ELEPHANT) {
                gridButtons[currentRow+1][currentColumn].setEnabled(true);
                gridButtons[currentRow+1][currentColumn].setBackground(Color.GREEN);
            }
        }
    }

    //method for checking if a piece can step
    public boolean checkStep() {
        if (currentRow > 0 && gridBoard[currentRow - 1][currentColumn] == BLANK) { //above
            return true;
        }
        if (currentColumn > 0 && gridBoard[currentRow][currentColumn - 1] == BLANK) { //left
            return true;
        }
        if (currentColumn < gridBoard[0].length - 1 && gridBoard[currentRow][currentColumn + 1] == BLANK) { //right
            return true;
        }
        if (currentRow < gridBoard.length - 1 && gridBoard[currentRow + 1][currentColumn] == BLANK) { //down
            return true;
        }
        return false;
    }

    //method for letting a piece step
    public void step() {

        for (int a = 0; a < gridButtons.length; a++) {
            for (int b = 0; b < gridButtons[0].length; b++) {
                gridButtons[a][b].setEnabled(false);
            }
        }

        if (currentRow > 0 && gridBoard[currentRow - 1][currentColumn] == BLANK
                && gridBoard[currentRow][currentColumn] != S_RABBIT) { //above
            gridButtons[currentRow - 1][currentColumn].setEnabled(true);
            gridButtons[currentRow - 1][currentColumn].setBackground(Color.GREEN);
        }
        if (currentColumn > 0 && gridBoard[currentRow][currentColumn - 1] == BLANK) { //left
            gridButtons[currentRow][currentColumn - 1].setEnabled(true);
            gridButtons[currentRow][currentColumn - 1].setBackground(Color.GREEN);
        }
        if (currentColumn < gridBoard[0].length - 1 && gridBoard[currentRow][currentColumn + 1] == BLANK) { //right
            gridButtons[currentRow][currentColumn + 1].setEnabled(true);
            gridButtons[currentRow][currentColumn + 1].setBackground(Color.GREEN);
        }
        if (currentRow < gridBoard.length - 1 && gridBoard[currentRow + 1][currentColumn] == BLANK
                && gridBoard[currentRow][currentColumn] != G_RABBIT) { //down
            gridButtons[currentRow + 1][currentColumn].setEnabled(true);
            gridButtons[currentRow + 1][currentColumn].setBackground(Color.GREEN);
        }
    }

    //enable pieces for making next move
    public void enablePieces() {
        if (turn == GOLD_TURN) {
            for (int c = 0; c < gridButtons.length; c++) {
                for (int d = 0; d < gridButtons[0].length; d++) {
                    if (gridBoard[c][d] >= 1 && gridBoard[c][d] <= 6) {
                        gridButtons[c][d].setEnabled(true);
                    } else {
                        gridButtons[c][d].setEnabled(false);
                    }
                }
            }
        } else {
            for (int c = 0; c < gridButtons.length; c++) {
                for (int d = 0; d < gridButtons[0].length; d++) {
                    if (gridBoard[c][d] >= 7 && gridBoard[c][d] <= 12) {
                        gridButtons[c][d].setEnabled(true);
                    } else {
                        gridButtons[c][d].setEnabled(false);
                    }
                }
            }
        }
    }

    // makes all backgrounds black and trap squares red
    public void setBackgrounds() {
        for (int i = 0; i < gridButtons.length; i++) {
            for (int j = 0; j < gridButtons[0].length; j++) {
                gridButtons[i][j].setBackground(Color.BLACK);
            }
        }
        gridButtons[2][2].setBackground(Color.RED);
        gridButtons[2][5].setBackground(Color.RED);
        gridButtons[5][2].setBackground(Color.RED);
        gridButtons[5][5].setBackground(Color.RED);
    }

    // Creates an action event for user interaction
    @Override
    public void actionPerformed(ActionEvent event) {

        // when reset is pressed, call resetBoard
        if (event.getSource().equals(reset)) {
            resetBoard();
        }

        // when turn done is pressed, switch turn
        if (event.getSource().equals(turnDone)) {
            turnDone.setEnabled(false);
            setBackgrounds();
            push.setEnabled(false);
            pull.setEnabled(false);
            step.setEnabled(false);
            stepCounter = 4;
            if (turn == SILVER_TURN) {
                turn = GOLD_TURN;
                for (int c = 0; c < gridButtons.length; c++) {
                    for (int d = 0; d < gridButtons[0].length; d++) {
                        if (gridBoard[c][d] >= 1 && gridBoard[c][d] <= 6) {
                            gridButtons[c][d].setEnabled(true);
                        } else {
                            gridButtons[c][d].setEnabled(false);
                        }
                    }
                }
            } else {
                turn = SILVER_TURN;
                for (int c = 0; c < gridButtons.length; c++) {
                    for (int d = 0; d < gridButtons[0].length; d++) {
                        if (gridBoard[c][d] >= 7 && gridBoard[c][d] <= 12) {
                            gridButtons[c][d].setEnabled(true);
                        } else {
                            gridButtons[c][d].setEnabled(false);
                        }
                    }
                }
            }
        }

        //when game state is play allow move buttons to be used
        if (gameState == PLAY) {
            if(stepCounter < 4) {
                turnDone.setEnabled(true);
            }
            if (stepCounter > 0) {
                //highlighting the piece a user wants to move and checking if they can step, push or pull
                for (int a = 0; a < gridButtons.length; a++) {
                    for (int b = 0; b < gridButtons[0].length; b++) {
                        if (moveState == TEMP) {
                            if (event.getSource().equals(gridButtons[a][b])) {

                                gridButtons[a][b].setBackground(Color.BLUE);
                                currentButton = gridButtons[a][b];
                                currentRow = a;
                                currentColumn = b;
                                currentPieceValue = gridBoard[currentRow][currentColumn];
                                turnDone.setEnabled(false);

                                for (int c = 0; c < gridButtons.length; c++) {
                                    for (int d = 0; d < gridButtons[0].length; d++) {
                                        gridButtons[c][d].setEnabled(false);
                                    }
                                }
                                gridButtons[a][b].setEnabled(true);

                                // Enabled buttons if piece isn't frozen and if action is possible
                                if (!checkFrozen()) {
                                    if (checkPush() && stepCounter >= 2) {
                                        push.setEnabled(true);
                                    }
                                    if (checkPull() && stepCounter >= 2) {
                                        pull.setEnabled(true);
                                    }
                                    if (checkStep()) {
                                        step.setEnabled(true);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Piece is frozen, choose another!");
                                    if (stepCounter <= 3) {
                                        turnDone.setEnabled(true);
                                    }
                                    setBackgrounds();
                                    enablePieces();
                                }
                            }
                        }
                    }
                }

                // when step is pressed, call step
                if (event.getSource().equals(step)) {
                    step.setEnabled(false);
                    pull.setEnabled(false);
                    push.setEnabled(false);
                    turnDone.setEnabled(false);
                    moveState = STEP;
                    step();
                }

                boolean stepDone = false;
                //if the user made a step move to another square, change the current square data to that square for the 'step'
                if (moveState == STEP) {
                    for (int a = 0; a < gridButtons.length; a++) {
                        for (int b = 0; b < gridButtons[0].length; b++) {
                            if (event.getSource().equals(gridButtons[a][b])) {
                                gridButtons[a][b].setIcon(currentButton.getIcon());
                                gridBoard[a][b] = gridBoard[currentRow][currentColumn];
                                currentButton.setIcon(null);
                                currentButton.setBackground(Color.BLACK);
                                setBackgrounds();
                                gridBoard[currentRow][currentColumn] = BLANK;
                                stepDone = true;
                            }
                        }
                    }
                    if (stepDone) {
                        enablePieces();
                        checkCaptured();
                        checkWin();
                        turnDone.setEnabled(true);
                        stepCounter--;
                        if (stepCounter == 0) {
                            for (int c = 0; c < gridButtons.length; c++) {
                                for (int d = 0; d < gridButtons[0].length; d++) {
                                    gridButtons[c][d].setEnabled(false);
                                }
                            }
                            if (turn == GOLD_TURN) {
                                JOptionPane.showMessageDialog(frame, "It's silver's turn! Click turn done.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "It's gold's turn! Click turn done.");
                            }
                        }
                        moveState = TEMP;
                    }
                }

                // when push is pressed, call push
                if (event.getSource().equals(push)) {
                    push.setEnabled(false);
                    pull.setEnabled(false);
                    step.setEnabled(false);
                    turnDone.setEnabled(false);
                    moveState = PUSH;
                    pushOrPull();
                }

                boolean pushDone = false;
                boolean pushAllow = false;
                //if the user made a step move to another square, change the current square data to that square for the 'step'
                if (moveState == PUSH) {
                    if (pushPart == PART_ONE) {
                        for (int a = 0; a < gridButtons.length; a++) {
                            for (int b = 0; b < gridButtons[0].length; b++) {
                                if (event.getSource().equals(gridButtons[a][b])) {

                                    initalRow = currentRow;
                                    initialColumn = currentColumn;
                                    initialButton = gridButtons[currentRow][currentColumn];

                                    //user clicked green weaker piece, which turns blue, and all other green returns to black
                                    gridButtons[a][b].setBackground(Color.BLUE);
                                    gridButtons[currentRow][currentColumn].setBackground(Color.BLACK);
                                    if (currentRow > 0 && gridButtons[currentRow - 1][currentColumn] != gridButtons[a][b]) { //above
                                        gridButtons[currentRow - 1][currentColumn].setBackground(Color.BLACK);
                                    }
                                    if (currentColumn > 0 && gridButtons[currentRow][currentColumn - 1] != gridButtons[a][b]) { //left
                                        gridButtons[currentRow][currentColumn - 1].setBackground(Color.BLACK);
                                    }
                                    if (currentColumn < gridBoard.length - 1 && gridButtons[currentRow][currentColumn + 1] != gridButtons[a][b]) { //right
                                        gridButtons[currentRow][currentColumn + 1].setBackground(Color.BLACK);
                                    }
                                    if (currentRow < gridBoard.length - 1 && gridButtons[currentRow + 1][currentColumn] != gridButtons[a][b]) { //down
                                        gridButtons[currentRow + 1][currentColumn].setBackground(Color.BLACK);
                                    }

                                    //options for where the blue weaker piece could be pushed to highlighted green/enabled and all other grid buttons disabled
                                    currentRow = a;
                                    currentColumn = b;
                                    currentButton = gridButtons[a][b];


                                    for (int c = 0; c < gridButtons.length; c++) {
                                        for (int d = 0; d < gridButtons[0].length; d++) {
                                            gridButtons[c][d].setEnabled(false);
                                        }
                                    }

                                    JOptionPane.showMessageDialog(frame, "Choose where you would like to push to");

                                    if (currentRow > 0 && gridBoard[currentRow - 1][currentColumn] == BLANK) { //above
                                        gridButtons[currentRow - 1][currentColumn].setEnabled(true);
                                        gridButtons[currentRow - 1][currentColumn].setBackground(Color.GREEN);
                                        pushAllow = true;
                                    }
                                    if (currentColumn > 0 && gridBoard[currentRow][currentColumn - 1] == BLANK) { //left
                                        gridButtons[currentRow][currentColumn - 1].setEnabled(true);
                                        gridButtons[currentRow][currentColumn - 1].setBackground(Color.GREEN);
                                        pushAllow = true;
                                    }
                                    if (currentColumn < gridBoard[0].length - 1 && gridBoard[currentRow][currentColumn + 1] == BLANK) { //right
                                        gridButtons[currentRow][currentColumn + 1].setEnabled(true);
                                        gridButtons[currentRow][currentColumn + 1].setBackground(Color.GREEN);
                                        pushAllow = true;
                                    }
                                    if (currentRow < gridBoard.length - 1 && gridBoard[currentRow + 1][currentColumn] == BLANK) { //down
                                        gridButtons[currentRow + 1][currentColumn].setEnabled(true);
                                        gridButtons[currentRow + 1][currentColumn].setBackground(Color.GREEN);
                                        pushAllow = true;
                                    }

                                    if (!pushAllow) {
                                        gridButtons[a][b].setBackground(Color.BLACK);
                                        pushPart = PART_ONE;
                                        pushDone = true;
                                        JOptionPane.showMessageDialog(frame, "Push not possible, please make a different move!");
                                    } else {
                                        previousRow = currentRow;
                                        previousColumn = currentColumn;
                                        previousButton = currentButton;
                                        pushPart = PART_TWO;
                                    }
                                }
                            }
                        }
                    }

                    if (pushPart == PART_TWO) {

                        pushAllow = true;

                        if (previousRow > 0 && event.getSource().equals(gridButtons[previousRow - 1][previousColumn])) {

                            gridButtons[previousRow - 1][previousColumn].setIcon(previousButton.getIcon());
                            gridBoard[previousRow - 1][previousColumn] = gridBoard[previousRow][previousColumn];

                            gridButtons[previousRow][previousColumn].setIcon(initialButton.getIcon());
                            gridBoard[previousRow][previousColumn] = gridBoard[initalRow][initialColumn];

                            gridButtons[initalRow][initialColumn].setIcon(null);
                            gridBoard[initalRow][initialColumn] = BLANK;

                        } else if (previousColumn > 0 && event.getSource().equals(gridButtons[previousRow][previousColumn - 1])) {

                            gridButtons[previousRow][previousColumn - 1].setIcon(previousButton.getIcon());
                            gridBoard[previousRow][previousColumn - 1] = gridBoard[previousRow][previousColumn];

                            gridButtons[previousRow][previousColumn].setIcon(initialButton.getIcon());
                            gridBoard[previousRow][previousColumn] = gridBoard[initalRow][initialColumn];

                            gridButtons[initalRow][initialColumn].setIcon(null);
                            gridBoard[initalRow][initialColumn] = BLANK;
                        } else if (previousColumn < gridBoard[0].length - 1 && event.getSource().equals(gridButtons[previousRow][previousColumn + 1])) {

                            gridButtons[previousRow][previousColumn + 1].setIcon(previousButton.getIcon());
                            gridBoard[previousRow][previousColumn + 1] = gridBoard[previousRow][previousColumn];

                            gridButtons[previousRow][previousColumn].setIcon(initialButton.getIcon());
                            gridBoard[previousRow][previousColumn] = gridBoard[initalRow][initialColumn];

                            gridButtons[initalRow][initialColumn].setIcon(null);
                            gridBoard[initalRow][initialColumn] = BLANK;
                        } else if (previousRow < gridBoard.length - 1 && event.getSource().equals(gridButtons[previousRow + 1][previousColumn])) {

                            gridButtons[previousRow + 1][previousColumn].setIcon(previousButton.getIcon());
                            gridBoard[previousRow + 1][previousColumn] = gridBoard[previousRow][previousColumn];

                            gridButtons[previousRow][previousColumn].setIcon(initialButton.getIcon());
                            gridBoard[previousRow][previousColumn] = gridBoard[initalRow][initialColumn];

                            gridButtons[initalRow][initialColumn].setIcon(null);
                            gridBoard[initalRow][initialColumn] = BLANK;
                        }

                        if (gridBoard[initalRow][initialColumn] == BLANK) {

                            setBackgrounds();

                            gridButtons[previousRow][previousColumn].setEnabled(false);

                            if (previousRow > 0) {
                                gridButtons[previousRow - 1][previousColumn].setEnabled(false);
                            }
                            if (previousColumn > 0) {
                                gridButtons[previousRow][previousColumn - 1].setEnabled(false);
                            }
                            if (previousColumn < gridBoard[0].length - 1) {
                                gridButtons[previousRow][previousColumn + 1].setEnabled(false);
                            }
                            if (previousRow < gridBoard.length - 1) {
                                gridButtons[previousRow + 1][previousColumn].setEnabled(false);
                            }

                            pushPart = PART_ONE;
                            pushDone = true;
                        }
                    }

                    if (pushDone) {
                        push.setEnabled(false);
                        enablePieces();
                        checkCaptured();
                        checkWin();
                        turnDone.setEnabled(true);
                        if (pushAllow) {
                            stepCounter = stepCounter - 2;
                        }
                        setBackgrounds();
                        if (stepCounter == 0) {
                            for (int c = 0; c < gridButtons.length; c++) {
                                for (int d = 0; d < gridButtons[0].length; d++) {
                                    gridButtons[c][d].setEnabled(false);
                                }
                            }
                            if (turn == GOLD_TURN) {
                                JOptionPane.showMessageDialog(frame, "It's silver's turn! Click turn done.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "It's gold's turn! Click turn done.");
                            }
                        }
                        moveState = TEMP;
                    }
                }

                // when pull is pressed, call pull
                if (event.getSource().equals(pull)) {
                    moveState = PULL_ONE;
                    pushOrPull();
                    pull.setEnabled(false);
                    push.setEnabled(false);
                    step.setEnabled(false);
                    turnDone.setEnabled(false);
                }

                boolean pullDone = false;
                if (moveState == PULL_ONE) {
                    for (int a = 0; a < gridButtons.length; a++) {
                        for (int b = 0; b < gridButtons[0].length; b++) {
                            if (event.getSource().equals(gridButtons[a][b])) {

                                //user clicked green weaker piece, which turns blue, and all other green returns to black
                                setBackgrounds();
                                gridButtons[a][b].setBackground(Color.BLUE);
                                gridButtons[currentRow][currentColumn].setBackground(Color.BLUE);

                                JOptionPane.showMessageDialog(frame, "Choose where you would like to move to");

                                strongRow = currentRow;
                                strongColumn = currentColumn;
                                strongButton = currentButton;
                                weakRow = a;
                                weakColumn = b;
                                weakButton = gridButtons[a][b];

                                for (int c = 0; c < gridButtons.length; c++) {
                                    for (int d = 0; d < gridButtons[0].length; d++) {
                                        gridButtons[c][d].setEnabled(false);
                                    }
                                }

                                if (strongRow > 0 && gridBoard[strongRow - 1][strongColumn] == BLANK) { //above
                                    gridButtons[strongRow - 1][strongColumn].setEnabled(true);
                                    gridButtons[strongRow - 1][strongColumn].setBackground(Color.GREEN);
                                }
                                if (strongColumn > 0 && gridBoard[strongRow][strongColumn - 1] == BLANK) { //left
                                    gridButtons[strongRow][strongColumn - 1].setEnabled(true);
                                    gridButtons[strongRow][strongColumn - 1].setBackground(Color.GREEN);
                                }
                                if (strongColumn < gridBoard[0].length && gridBoard[strongRow][strongColumn + 1] == BLANK) { //right
                                    gridButtons[strongRow][strongColumn + 1].setEnabled(true);
                                    gridButtons[strongRow][strongColumn + 1].setBackground(Color.GREEN);
                                }
                                if (strongRow < gridBoard.length && gridBoard[strongRow + 1][strongColumn] == BLANK) { //down
                                    gridButtons[strongRow + 1][strongColumn].setEnabled(true);
                                    gridButtons[strongRow + 1][strongColumn].setBackground(Color.GREEN);
                                }
                                moveState = PULL_TWO;
                            }
                        }
                    }
                }

                if (moveState == PULL_TWO) {

                    if (strongRow > 0 && gridBoard[strongRow - 1][strongColumn] == BLANK
                            && event.getSource().equals((gridButtons[strongRow - 1][strongColumn]))) { //above
                        gridButtons[strongRow - 1][strongColumn].setIcon(strongButton.getIcon());
                        gridBoard[strongRow - 1][strongColumn] = gridBoard[strongRow][strongColumn];
                        strongButton.setIcon(weakButton.getIcon());
                        weakButton.setIcon(null);
                        gridBoard[strongRow][strongColumn] = gridBoard[weakRow][weakColumn];
                        gridBoard[weakRow][weakColumn] = BLANK;
                        pullDone = true;
                    }
                    if (strongColumn > 0 && gridBoard[strongRow][strongColumn - 1] == BLANK
                            && event.getSource().equals((gridButtons[strongRow][strongColumn - 1]))) { //left
                        gridButtons[strongRow][strongColumn - 1].setIcon(strongButton.getIcon());
                        gridBoard[strongRow][strongColumn - 1] = gridBoard[strongRow][strongColumn];
                        strongButton.setIcon(weakButton.getIcon());
                        weakButton.setIcon(null);
                        gridBoard[strongRow][strongColumn] = gridBoard[weakRow][weakColumn];
                        gridBoard[weakRow][weakColumn] = BLANK;
                        pullDone = true;
                    }
                    if (strongColumn < gridBoard[0].length && gridBoard[strongRow][strongColumn + 1] == BLANK
                            && event.getSource().equals((gridButtons[strongRow][strongColumn + 1]))) { //right
                        gridButtons[strongRow][strongColumn + 1].setIcon(strongButton.getIcon());
                        gridBoard[strongRow][strongColumn + 1] = gridBoard[strongRow][strongColumn];
                        strongButton.setIcon(weakButton.getIcon());
                        weakButton.setIcon(null);
                        gridBoard[strongRow][strongColumn] = gridBoard[weakRow][weakColumn];
                        gridBoard[weakRow][weakColumn] = BLANK;
                        pullDone = true;
                    }
                    if (strongRow < gridBoard.length && gridBoard[strongRow + 1][strongColumn] == BLANK
                            && event.getSource().equals((gridButtons[strongRow + 1][strongColumn]))) { //down
                        gridButtons[strongRow + 1][strongColumn].setIcon(strongButton.getIcon());
                        gridBoard[strongRow + 1][strongColumn] = gridBoard[strongRow][strongColumn];
                        strongButton.setIcon(weakButton.getIcon());
                        weakButton.setIcon(null);
                        gridBoard[strongRow][strongColumn] = gridBoard[weakRow][weakColumn];
                        gridBoard[weakRow][weakColumn] = BLANK;
                        pullDone = true;
                    }

                    if (pullDone) {
                        setBackgrounds();
                        checkCaptured();
                        checkWin();
                        turnDone.setEnabled(true);
                        enablePieces();
                        stepCounter = stepCounter - 2;
                        if (stepCounter == 0) {
                            for (int c = 0; c < gridButtons.length; c++) {
                                for (int d = 0; d < gridButtons[0].length; d++) {
                                    gridButtons[c][d].setEnabled(false);
                                }
                            }
                            if (turn == GOLD_TURN) {
                                JOptionPane.showMessageDialog(frame, "It's silver's turn! Click turn done.");
                            } else {
                                JOptionPane.showMessageDialog(frame, "It's gold's turn! Click turn done.");
                            }
                        }
                        moveState = TEMP;
                    }
                }
            }
        }

        //when start is pressed, begin set-up
        if (event.getSource().equals(start)) {
            JOptionPane.showMessageDialog(frame, "Gold, place your pieces below!");
            gameState = SET_UP;
            turn = GOLD_TURN;
            start.setEnabled(false);
            turnDone.setEnabled(false);
            elephantButton.setEnabled(true);
            camelButton.setEnabled(true);
            horseButton.setEnabled(true);
            dogButton.setEnabled(true);
            catButton.setEnabled(true);
            rabbitButton.setEnabled(true);
            //enabling the bottom two rows of buttons for gold to place their pieces
            for (int a = 0; a < gridButtons.length; a++) {
                for (int b = 0; b < gridButtons[0].length; b++) {
                    if ((a == gridButtons.length - 1) || (a == gridButtons.length - 2)) {
                        gridButtons[a][b].setEnabled(true);
                    }
                }
            }
        }

        //When a radio button is pressed, set the respective boolean to true and all other to false
        if (event.getSource().equals(elephantButton)) {
            elephant = true;
            camel = false;
            horse = false;
            dog = false;
            cat = false;
            rabbit = false;
        }
        if (event.getSource().equals(camelButton)) {
            elephant = false;
            camel = true;
            horse = false;
            dog = false;
            cat = false;
            rabbit = false;
        }
        if (event.getSource().equals(horseButton)) {
            elephant = false;
            camel = false;
            horse = true;
            dog = false;
            cat = false;
            rabbit = false;
        }
        if (event.getSource().equals(dogButton)) {
            elephant = false;
            camel = false;
            horse = false;
            dog = true;
            cat = false;
            rabbit = false;
        }
        if (event.getSource().equals(catButton)) {
            elephant = false;
            camel = false;
            horse = false;
            dog = false;
            cat = true;
            rabbit = false;
        }
        if (event.getSource().equals(rabbitButton)) {
            elephant = false;
            camel = false;
            horse = false;
            dog = false;
            cat = false;
            rabbit = true;
        }
        if (gameState == SET_UP) {
            // Add piece to grid depending on the radio button pressed
            for (int a = 0; a < gridButtons.length; a++) {
                for (int b = 0; b < gridButtons[0].length; b++) {
                    if (event.getSource().equals(gridButtons[a][b])) {
                        if (elephant) {
                            eCounter--;
                            if (elephantButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[0]);
                                    gridBoard[a][b] = G_ELEPHANT;
                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[0]);
                                    gridBoard[a][b] = S_ELEPHANT;
                                }
                            }
                            if (eCounter == 0) {
                                elephantButton.setEnabled(false);
                            }
                        } else if (camel) {
                            cCounter--;
                            if (camelButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[1]);
                                    gridBoard[a][b] = G_CAMEL;
                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[1]);
                                    gridBoard[a][b] = S_CAMEL;
                                }
                            }
                            if (cCounter == 0) {
                                camelButton.setEnabled(false);
                            }
                        } else if (horse) {
                            hCounter--;
                            if (horseButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[2]);
                                    gridBoard[a][b] = G_HORSE;

                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[2]);
                                    gridBoard[a][b] = S_HORSE;
                                }
                            }
                            if (hCounter == 0) {
                                horseButton.setEnabled(false);
                            }
                        } else if (dog) {
                            dCounter--;
                            if (dogButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[3]);
                                    gridBoard[a][b] = G_DOG;
                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[3]);
                                    gridBoard[a][b] = S_DOG;
                                }
                            }
                            if (dCounter == 0) {
                                dogButton.setEnabled(false);
                            }
                        } else if (cat) {
                            catCounter--;
                            if (catButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[4]);
                                    gridBoard[a][b] = G_CAT;
                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[4]);
                                    gridBoard[a][b] = S_CAT;
                                }
                            }
                            if (catCounter == 0) {
                                catButton.setEnabled(false);
                            }
                        } else if (rabbit) {
                            rCounter--;
                            if (rabbitButton.isEnabled()) {
                                gridButtons[a][b].setEnabled(false);
                                if (turn == GOLD_TURN) {
                                    gridButtons[a][b].setIcon(imageGold[5]);
                                    gridBoard[a][b] = G_RABBIT;
                                } else {
                                    gridButtons[a][b].setIcon(imageSilver[5]);
                                    gridBoard[a][b] = S_RABBIT;
                                }
                            }
                            if (rCounter == 0) {
                                rabbitButton.setEnabled(false);
                            }
                        }
                        if (turn == GOLD_TURN && !elephantButton.isEnabled() && !camelButton.isEnabled() && !horseButton.isEnabled() && !dogButton.isEnabled() && !catButton.isEnabled() && !rabbitButton.isEnabled()) {
                            //once gold finishes placing their pieces, it's silver's turn (resets counters and radio buttons)
                            turn = SILVER_TURN;
                            JOptionPane.showMessageDialog(frame, "Silver, place your pieces above!");
                            elephantButton.setEnabled(true);
                            camelButton.setEnabled(true);
                            horseButton.setEnabled(true);
                            dogButton.setEnabled(true);
                            catButton.setEnabled(true);
                            rabbitButton.setEnabled(true);
                            eCounter = 1;
                            cCounter = 1;
                            hCounter = 2;
                            dCounter = 2;
                            catCounter = 2;
                            rCounter = 8;
                            //enabling the top two rows for silver to place pieces
                            for (int c = 0; c < gridButtons.length; c++) {
                                for (int d = 0; d < gridButtons[0].length; d++) {
                                    if ((c == gridButtons.length - 7) || (c == gridButtons.length - 8)) {
                                        gridButtons[c][d].setEnabled(true);
                                    }
                                }
                            }
                        } else if (turn == SILVER_TURN && !elephantButton.isEnabled() && !camelButton.isEnabled() && !horseButton.isEnabled() && !dogButton.isEnabled() && !catButton.isEnabled() && !rabbitButton.isEnabled()) {
                            //after silver finishes placing their pieces, it's time to begin gameplay (enable all other buttons)
                            JOptionPane.showMessageDialog(frame, "Time to begin gameplay!");
                            gameState = PLAY;
                            turn = GOLD_TURN;
                            reset.setEnabled(true);
                            start.setEnabled(false);
                            turnDone.setEnabled(false);
                            for (int c = 0; c < gridButtons.length; c++) {
                                for (int d = 0; d < gridButtons[0].length; d++) {
                                    //gridButtons[c][d].setEnabled(true);
                                    if (gridBoard[c][d] >= 1 && gridBoard[c][d] <= 6) {
                                        gridButtons[c][d].setEnabled(true);
                                    } else {
                                        gridButtons[c][d].setEnabled(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}