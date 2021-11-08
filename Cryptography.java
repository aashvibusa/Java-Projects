import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cryptography implements ActionListener {
    // Initialize objects and variables
    JFrame frame = new JFrame();
    Container north = new Container();
    Container center = new Container();
    JLabel chooseCipher = new JLabel("Choose cipher: ");
    JTextField input = new JTextField();
    JTextField output = new JTextField();
    JRadioButton scytale = new JRadioButton("Scytale");
    JRadioButton caesar = new JRadioButton("Caesar");
    JRadioButton vigenere = new JRadioButton("Vigenere");
    JButton encrypt = new JButton("Encrypt");
    JButton decrypt = new JButton("Decrypt");
    int height = 0;
    int shift = 0;
    String keyword = "";
    String message = "";
    String s = "";

    // Constructor for Cryptography
    public Cryptography() {
        // Set up main frame
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Set up north container
        north.setLayout(new GridLayout(1,1));
        north.add(chooseCipher);
        north.add(scytale);
        scytale.addActionListener(this);
        north.add(caesar);
        caesar.addActionListener(this);
        north.add(vigenere);
        vigenere.addActionListener(this);
        frame.add(north, BorderLayout.NORTH);

        // Set up center container
        center.setLayout(new GridLayout(2,2));
        center.add(encrypt);
        encrypt.setEnabled(false);
        center.add(decrypt);
        decrypt.setEnabled(false);
        center.add(input);
        input.setEnabled(false);
        center.add(output);
        output.setEnabled(false);
        encrypt.addActionListener(this);
        decrypt.addActionListener(this);
        input.addActionListener(this);
        output.addActionListener(this);
        frame.add(center, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Encrypt and decrypt with the scytale cipher
    String scytale(String input, int height, boolean encrypt) {
        // Find the width of the array
        int width = 0;
        int rowsWithoutExtra = 0;
        if (height != 0) {
            if (input.length() % height > 0) {
                width = (input.length() / height) + 1;
                rowsWithoutExtra = (input.length() % height) - 1;
            } else {
                width = input.length() / height;
                rowsWithoutExtra = -1;
            }
        }
        // Either encrypt or decrypt the input
        String output = "";
        char[][] message = new char[height][width];
        if (encrypt == true) {
            // Loop through and place characters in array accordingly
            int count = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (rowsWithoutExtra == -1) {
                        message[i][j] = input.charAt(count);
                        count++;
                    } else {
                        if (i <= rowsWithoutExtra) {
                            message[i][j] = input.charAt(count);
                            count++;
                        } else {
                            if (j == (width - 1)) {
                                message[i][j] = '@';
                            } else {
                                message[i][j] = input.charAt(count);
                                count++;
                            }
                        }
                    }
                }
            }
            // Concatenate new encrypted message
            for (int j = 0; j < width; j++) {
                for (int i = 0; i < height; i++) {
                    output += Character.toString(message[i][j]);
                }
            }
        } else {
            // Loop through and place characters in array accordingly
            int count = 0;
            for (int j = 0; j < width; j++) {
                for (int i = 0; i < height; i++) {
                    if (count >= input.length()) {
                        message[i][j] = '@';
                    } else {
                        message[i][j] = input.charAt(count);
                        count++;
                    }
                }
            }
            // Concatenate new decrypted message
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (message[i][j] != '@') {
                        output += Character.toString(message[i][j]);
                    }
                }
            }
        }
        return output;
    }

    // Encrypt and decrypt with the caesar cipher
    String caesar(String input, int shift, boolean encrypt) {
        // Either encrypt or decrypt the input
        String output = "";
        char[] message = new char[input.length()];
        if (encrypt == true) {
            // Loop through and create an array with the letters shifted
            for (int i = 0; i < input.length(); i++) {
                int loop = 90 - (input.charAt(i) + shift);
                if (loop >= 0) {
                    message[i] = (char)(input.charAt(i) + shift);
                } else {
                    message[i] = (char)(64 + Math.abs(loop));
                }
            }
            // Concatenate new encrypted message
            for (int i = 0; i < input.length(); i++) {
                output += message[i];
            }
        } else {
            // Loop through and create an array with the letters shifted
            for (int i = 0; i < input.length(); i++) {
                int loop = 65 - (input.charAt(i) - shift);
                if (loop <= 0) {
                    message[i] = (char)(input.charAt(i) - shift);
                } else {
                    message[i] = (char)(91 - Math.abs(loop));
                }
            }
            // Concatenate new decrypted message
            for (int i = 0; i < input.length(); i++) {
                output += message[i];
            }
        }
        return output;
    }

    // Encrypt and decrypt with the vigenere cipher
    String vigenere(String input, String keyword, boolean encrypt) {
        // Clean up the keyword
        keyword = keyword.toUpperCase().replaceAll("\\s", "");
        String output = "";
        char[] message = new char[input.length()];
        if (encrypt == true) {
            int count = 0;
            int index;
            // Loop through and create an array with the letters shifted
            for (int i = 0; i < input.length(); i++) {
                // Find the index of the keyword
                if (count == keyword.length()) {
                    index = 0;
                    count = 1;
                } else {
                    index = count;
                    count++;
                }
                // Add to array accordingly
                int loop = 90 - (input.charAt(i) + (keyword.charAt(index) - 65));
                if (loop >= 0) {
                    message[i] = (char)(input.charAt(i) + (keyword.charAt(index) - 65));
                } else {
                    message[i] = (char)(64 + Math.abs(loop));
                }
            }
            // Concatenate new encrypted message
            for (int i = 0; i < input.length(); i++) {
                output += message[i];
            }
        } else {
            int count = 0;
            int index;
            // Loop through and create an array with the letters shifted
            for (int i = 0; i < input.length(); i++) {
                // Find the index of the keyword
                if (count == keyword.length()) {
                    index = 0;
                    count = 1;
                } else {
                    index = count;
                    count++;
                }
                // Add to array accordingly
                int loop = 65 - (input.charAt(i) - (keyword.charAt(index) - 65));
                if (loop <= 0) {
                    message[i] = (char)(input.charAt(i) - (keyword.charAt(index) - 65));
                } else {
                    message[i] = (char)(91 - (Math.abs(loop)));
                }
            }
            // Concatenate new decrypted message
            for (int i = 0; i < input.length(); i++) {
                output += message[i];
            }
        }
        return output;
    }

    // Allow for user interaction
    @Override
    public void actionPerformed(ActionEvent event) {
        boolean inputEntered = false;
        boolean cancelPressed = false;
        // If user chooses scytale
        if (event.getSource().equals(scytale)) {
            JOptionPane optionPane = new JOptionPane();
            while (!inputEntered) {
                String scytaleInput = JOptionPane.showInputDialog(frame, "Enter the number of rows: ");
                if (scytaleInput == null) {
                    optionPane.setVisible(false);
                    scytale.setSelected(false);
                    cancelPressed = true;
                    break;
                } else if (!scytaleInput.matches("[+-]?[0-9]+") || scytaleInput.equals("0")) {
                    JOptionPane.showMessageDialog(frame, "Your input has to be an integer.");
                } else {
                    height = Integer.parseInt(scytaleInput);
                    s = "scytale";
                    inputEntered = true;
                }
            }
        // If user chooses caesar
        } else if (event.getSource().equals(caesar)) {
            JOptionPane optionPane = new JOptionPane();
            while (!inputEntered) {
                String caesarInput = JOptionPane.showInputDialog(frame, "Enter the shift number: ");
                if (caesarInput == null) {
                    optionPane.setVisible(false);
                    caesar.setSelected(false);
                    cancelPressed = true;
                    break;
                } else if (!caesarInput.matches("[+-]?[0-9]+") || caesarInput.equals("0")) {
                    JOptionPane.showMessageDialog(frame, "Your input has to be an integer.");
                } else {
                    shift = Integer.parseInt(caesarInput);
                    s = "caesar";
                    inputEntered = true;
                }
            }
        // If user chooses vigenere
        } else if (event.getSource().equals(vigenere)) {
            JOptionPane optionPane = new JOptionPane();
            while (!inputEntered) {
                String vigenereInput = JOptionPane.showInputDialog(frame, "Enter the shift keyword: ");
                if (vigenereInput == null) {
                    optionPane.setVisible(false);
                    vigenere.setSelected(false);
                    cancelPressed = true;
                    break;
                } else if (!vigenereInput.matches( "^[ A-Za-z]+$") || vigenereInput.equals("0")) {
                    JOptionPane.showMessageDialog(frame, "Your input has to be a word.");
                } else {
                    keyword = vigenereInput;
                    s = "vigenere";
                    inputEntered = true;
                }
            }
        }

        // Make sure cancel wasn't pressed, otherwise continue
        if (!cancelPressed) {
            // Disable option to choose cipher
            scytale.setEnabled(false);
            caesar.setEnabled(false);
            vigenere.setEnabled(false);
            // Enable option to input message
            input.setEnabled(true);
            encrypt.setEnabled(true);
            decrypt.setEnabled(true);
            output.setEnabled(true);

            // If user chooses to encrypt their message
            if (event.getSource().equals(encrypt)) {
                if (input.getText().equals("")) {
                    input.setText("");
                } else {
                    message = input.getText().replaceAll("[^a-zA-Z]", "").toUpperCase();
                    message = message.replaceAll("\\s", "");
                    // Modify textfield based on cipher selected
                    switch(s) {
                        case "scytale":
                            output.setText(scytale(message, height, true));
                            break;
                        case "caesar":
                            output.setText(caesar(message, shift, true));
                            break;
                        case "vigenere":
                            output.setText(vigenere(message, keyword, true));
                            break;
                    }
                    input.setText("");
                }
            // If user chooses to decrypt their message
            } else if (event.getSource().equals(decrypt)) {
                if (output.getText().equals("")) {
                    output.setText("");
                } else {
                    message = output.getText().replaceAll("[^a-zA-Z]", "").toUpperCase();
                    message = message.replaceAll("\\s", "");
                    // Modify textfield based on cipher selected
                    switch(s) {
                        case "scytale":
                            input.setText(scytale(message, height, false));
                            break;
                        case "caesar":
                            input.setText(caesar(message, shift, false));
                            break;
                        case "vigenere":
                            input.setText(vigenere(message, keyword, false));
                            break;
                    }
                    output.setText("");
                }
            }
        }
    }

    // Main method to start the program
    public static void main(String[] args) { new Cryptography(); }
}