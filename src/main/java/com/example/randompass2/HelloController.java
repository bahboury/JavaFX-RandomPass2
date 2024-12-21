package com.example.randompass2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.Random;

public class HelloController {

    // Character sets for password generation
    private String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Uppercase letters
    private String lower = "abcdefghijklmnopqrstuvwxyz"; // Lowercase letters
    private String num = "0123456789"; // Numeric characters
    private String special = "~!@#$%^&*+-/.,\\{}[]();:?<>'_"; // Special characters
    private String generatedPassword = ""; // Variable to store the generated password

    // FXML annotations to link UI components with controller variables
    @FXML
    private CheckBox checkBoxLower; // Checkbox for including lowercase letters

    @FXML
    private CheckBox checkBoxNum; // Checkbox for including numbers

    @FXML
    private CheckBox checkBoxSpecial; // Checkbox for including special characters

    @FXML
    private CheckBox checkBoxUpper; // Checkbox for including uppercase letters

    @FXML
    private Label labelStrength; // Label to display password strength (not used in this code)

    @FXML
    private TextField textField; // TextField for user to input desired password length

    @FXML
    private Label labelPass; // Label to display the generated password

    // Method to handle the button click event for generating a password
    @FXML
    void buttonGenerate(ActionEvent event) {
        String passStrength = textField.getText(); // Get the input from the TextField
        try {
            int passlength = Integer.parseInt(passStrength); // Parse the input to an integer
            // Validate the password length
            if (passlength < 4 || passlength > 99) {
                labelPass.setText("Password Length must be between 4 and 99"); // Error message
                labelStrength.setText(""); // Clear strength label
                return; // Exit the method
            }
            // Generate the character set based on selected checkboxes
            String characterSet = generateCharacterSet(
                    checkBoxUpper.isSelected(),
                    checkBoxLower.isSelected(),
                    checkBoxNum.isSelected(),
                    checkBoxSpecial.isSelected()
            );
            // Generate the password using the specified length and character set
            generatePass(passlength, characterSet);
        } catch (NumberFormatException e) {
            // Handle invalid input
            labelPass.setText("Invalid input. Please enter a number between 4 and 99."); // Error message
        }
    }

    // Method to generate the character set based on selected options
    private String generateCharacterSet(boolean isUpper, boolean isLower, boolean isNum, boolean isSpecial) {
        StringBuilder characterSet = new StringBuilder(); // StringBuilder to build the character set
        if (isUpper) {
            characterSet.append(upper); // Add uppercase letters if selected
        }
        if (isLower) {
            characterSet.append(lower); // Add lowercase letters if selected
        }
        if (isNum) {
            characterSet.append(num); // Add numbers if selected
        }
        if (isSpecial) {
            characterSet.append(special); // Add special characters if selected
        }
        return characterSet.toString(); // Return the constructed character set
    }

    // Method to generate the password based on length and character set
    private void generatePass(int passLength, String character) {
        if (character.isEmpty()) {
            labelPass.setText("Select at least one character set"); // Error message if no character set is selected
            labelStrength.setText(""); // Clear strength label
            return; // Exit the method
        }

        // Generate the random password
        generatedPassword = generatedRandomPassword(passLength, character);
        labelPass.setText(generatedPassword); // Display the generated password
        // Update password strength label here if needed (not implemented)
    }

    // Method to generate a random password of specified length from the given character set
    private String generatedRandomPassword(int passLength, String Char) {
        Random random = new Random(); // Random object for generating random numbers
        StringBuilder password = new StringBuilder(); // StringBuilder to build the password
        for (int i = 0; i < passLength; i++) {
            // Append a random character from the character set
            password.append(Char.charAt(random.nextInt(Char.length())));
        }
        return password.toString(); // Return the generated password
    }

    // Method to handle the button click event for copying the password to the clipboard
    @FXML
    void buttonCopy(ActionEvent event) {
        // Get the system clipboard
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent(); // Create a new ClipboardContent object
        content.putString(generatedPassword); // Put the generated password into the clipboard content
        clipboard.setContent(content); // Set the clipboard content
        labelPass.setText("Password copied to clipboard!"); // Display a message indicating the password was copied
    }
}