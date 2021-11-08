import java.util.*;

public class BattleShipsGame {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in); //Init Scanner class

        char[][] oceanMap = new char[10][10]; //Init main array

        //Print out title sequence
        System.out.println("---Welcome to BattleShip---");
        System.out.println("Right now, the sea is empty");

        //Print out grid
        System.out.println("\r");
        System.out.println("  0123456789  ");
        int count1 = 0;
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if(j == 0) {
                    System.out.print(count1 + "|");
                }
                oceanMap[i][j] = ' ';
                System.out.print(oceanMap[i][j]);
                if(j == 9) {
                    System.out.print("|" + count1);
                }
            }
            System.out.println("\r");
            count1++;
        }
        System.out.println("  0123456789  ");
        System.out.println("\r");

        //Deploying player's ships
        System.out.println("---Deploy your ships---");
        while(true) {
            System.out.print("Enter X coordinate for ship 1: ");
            int x1 = input.nextInt();
            System.out.print("Enter Y coordinate for ship 1: ");
            int y1 = input.nextInt();
            if((x1 < 0) || (y1 < 0) || (x1 > 9) || (y1 > 9)) {
                System.out.println("Invalid coordinates");
            } else if(oceanMap[x1][y1] == '1') {
                System.out.println("Invalid coordinates");
            } else {
                oceanMap[x1][y1] = '1';
                break;
            }
        }
        while(true) {
            System.out.print("Enter X coordinate for ship 2: ");
            int x2 = input.nextInt();
            System.out.print("Enter Y coordinate for ship 2: ");
            int y2 = input.nextInt();
            if((x2 < 0) || (y2 < 0) || (x2 > 9) || (y2 > 9)) {
                System.out.println("Invalid coordinates");
            } else if(oceanMap[x2][y2] == '1') {
                System.out.println("Invalid coordinates");
            } else {
                oceanMap[x2][y2] = '1';
                break;
            }
        }
        while(true) {
            System.out.print("Enter X coordinate for ship 3: ");
            int x3 = input.nextInt();
            System.out.print("Enter Y coordinate for ship 3: ");
            int y3 = input.nextInt();
            if((x3 < 0) || (y3 < 0) || (x3 > 9) || (y3 > 9)) {
                System.out.println("Invalid coordinates");
            } else if(oceanMap[x3][y3] == '1') {
                System.out.println("Invalid coordinates");
            } else {
                oceanMap[x3][y3] = '1';
                break;
            }
        }
        while(true) {
            System.out.print("Enter X coordinate for ship 4: ");
            int x4 = input.nextInt();
            System.out.print("Enter Y coordinate for ship 4: ");
            int y4 = input.nextInt();
            if((x4 < 0) || (y4 < 0) || (x4 > 9) || (y4 > 9)) {
                System.out.println("Invalid coordinates");
            } else if(oceanMap[x4][y4] == '1') {
                System.out.println("Invalid coordinates");
            } else {
                oceanMap[x4][y4] = '1';
                break;
            }
        }
        while(true) {
            System.out.print("Enter X coordinate for ship 5: ");
            int x5 = input.nextInt();
            System.out.print("Enter Y coordinate for ship 5: ");
            int y5 = input.nextInt();
            if((x5 < 0) || (y5 < 0) || (x5 > 9) || (y5 > 9)) {
                System.out.println("Invalid coordinates");
            } else if(oceanMap[x5][y5] == '1') {
                System.out.println("Invalid coordinates");
            } else {
                oceanMap[x5][y5] = '1';
                break;
            }
        }

        //Print grid with player's ships
        System.out.println("\r");
        System.out.println("  0123456789  ");
        int count2 = 0;
        for(int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    System.out.print(count2 + "|");
                }
                if(oceanMap[i][j] == '1') {
                    System.out.print("@");
                } else {
                    System.out.print(oceanMap[i][j]);
                }
                if (j == 9) {
                    System.out.print("|" + count2);
                }
            }
            System.out.println("\r");
            count2++;
        }
        System.out.println("  0123456789  ");
        System.out.println("\r");

        //Deploying computer's ships
        System.out.println("---Computer is deploying ships---");
        for(int i = 1; i <= 5; i++) {
            while(true) {
                Random r1 = new Random();
                int randInt1 = r1.nextInt(10);
                Random r2 = new Random();
                int randInt2 = r2.nextInt(10);
                if(oceanMap[randInt1][randInt2] == ' ') {
                    oceanMap[randInt1][randInt2] = '2';
                    System.out.println("Ship " + i + " is DEPLOYED");
                    break;
                } else {
                    continue;
                }
            }
        }

        //Battle Sequence
        System.out.println("\r");
        System.out.println("---Time to start the battle---");
        int playCount = 5;
        int compCount = 5;
        char[][] compGuesses = new char[10][10];
        while((playCount != 0) && (compCount != 0)) {
            boolean playerTurn = true;
            boolean computerTurn = true;
            //Player's turn
            while(playerTurn) {
                System.out.println("YOUR TURN");
                System.out.print("Enter x coordinate: ");
                int xPlay = input.nextInt();
                System.out.print("Enter y coordinate: ");
                int yPlay = input.nextInt();
                if((xPlay < 0) || (yPlay < 0) || (xPlay > 9) || (yPlay > 9)) {
                    System.out.println("Invalid coordinates");
                    System.out.println("\r");
                } else if((oceanMap[xPlay][yPlay] == 'x') ||
                        (oceanMap[xPlay][yPlay] == '!') ||
                        (oceanMap[xPlay][yPlay] == '-')) {
                    System.out.println("You've already guessed those coordinates");
                    System.out.println("\r");
                } else {
                    if(oceanMap[xPlay][yPlay] == '1') {
                        System.out.println("Oh no, you sunk your own ship");
                        oceanMap[xPlay][yPlay] = '!';
                        playCount--;
                        playerTurn = false;
                    } else if(oceanMap[xPlay][yPlay] == '2') {
                        System.out.println("Boom! You sunk the ship!");
                        oceanMap[xPlay][yPlay] = 'x';
                        compCount--;
                        playerTurn = false;
                    } else {
                        System.out.println("Sorry, you missed");
                        oceanMap[xPlay][yPlay] = '-';
                        playerTurn = false;
                    }
                }
            }
            //Computer's turn
            System.out.println("\r");
            System.out.println("COMPUTER'S TURN");
            while(computerTurn) {
                Random r3 = new Random();
                int xComp = r3.nextInt(10);
                Random r4 = new Random();
                int yComp = r4.nextInt(10);
                if(compGuesses[xComp][yComp] != 'x') {
                    if(oceanMap[xComp][yComp] == '1') {
                        System.out.println("The Computer sunk one of your ships!");
                        oceanMap[xComp][yComp] = '/';
                        compGuesses[xComp][yComp] = 'x';
                        playCount--;
                        computerTurn = false;
                    } else if(oceanMap[xComp][yComp] == '2') {
                        System.out.println("The Computer sunk one of its own ships");
                        oceanMap[xComp][yComp] = '$';
                        compGuesses[xComp][yComp] = 'x';
                        compCount--;
                        computerTurn = false;
                    } else {
                        System.out.println("Computer missed");
                        compGuesses[xComp][yComp] = 'x';
                        computerTurn = false;
                    }
                } else {
                    continue;
                }
                System.out.println("\r");
            }
            //Print grid
            System.out.println("  0123456789  ");
            int count3 = 0;
            for(int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (j == 0) {
                        System.out.print(count3 + "|");
                    }
                    if(oceanMap[i][j] == '1') {
                        System.out.print("@");
                    } else if(oceanMap[i][j] == '2') {
                        System.out.print(" ");
                    } else {
                        System.out.print(oceanMap[i][j]);
                    }
                    if (j == 9) {
                        System.out.print("|" + count3);
                    }
                }
                System.out.println("\r");
                count3++;
            }
            System.out.println("  0123456789  ");
            System.out.println("\r");
            System.out.println("Your ships: " + playCount + " | "
                    + "Computer's ships: " +compCount);
            System.out.println("\r");
        }

        //End Sequence
        System.out.println("---GAME OVER---");
        if((playCount == 0) && (compCount != 0)) {
            System.out.println("Oof, computer won the battle :(");
        } else if((playCount != 0) && (compCount == 0)){
            System.out.println("Hooray! You won the battle :)");
        } else {
            System.out.println("Tie game :|");
        }
    }
}