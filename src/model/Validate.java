package model;

import javafx.scene.control.*;
import java.util.Iterator;
import java.util.Stack;

public class Validate {

    /**
     * Stack to hold errors during validation
     */
    private static Stack errors = new Stack();

    /**
     * Default text field style
     */
    private static String resetStyle = "-fx-border-color: none;";

    /**
     * Error text field style
     */
    private static String errorStyle = "-fx-border-color: red; -fx-border-width: 2px";

    /**
     * @param fields fields to reset style
     */
    public static void resetStyles(TextField[] fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].setStyle(resetStyle);
        }
    }

    /**
     * @param fieldInput fieldInput to check if empty
     * @param fieldLabel fieldLabel to use in error message
     * @return true or false if field is empty
     */
    public static Boolean isEmpty(TextField fieldInput, Label fieldLabel) {
        if (fieldInput.getLength() == 0) {
            errors.push(fieldLabel.getText() + " cannot be blank!");
            fieldInput.setStyle(errorStyle);
            return true;
        }
        else { return false; }
    }

    /**
     * @param fieldInput fieldInput to check for wrong data type
     * @param fieldLabel fieldLabel to use in error message
     * @return badInput
     */
    public static Boolean badInputType(TextField fieldInput, Label fieldLabel) {
        // Declare variables
        Boolean badInput = false;
        String fieldIdValue = fieldInput.getId();
        String labelValue = fieldLabel.getText();

        // Check for integers
        if (fieldIdValue.equals("textFieldInv") || fieldIdValue.equals("textFieldMax") || fieldIdValue.equals("textFieldMin") || fieldIdValue.equals("textFieldMachineId")) {
            if (!fieldInput.getText().matches("\\d+")) {
                errors.push(labelValue + " must be an integer!");
                fieldInput.setStyle(errorStyle);
                badInput = true;
            }
        }

        // Check for integers and decimals
        if (fieldIdValue.equals("textFieldPrice")) {
            if (!fieldInput.getText().matches("^\\d*\\.\\d+|\\d+\\.\\d*$|\\d+")) {
                errors.push(labelValue + " must be a number!");
                fieldInput.setStyle(errorStyle);
                badInput = true;
            }
        }
        return badInput;
    }

    /**
     * <p>The validated method is made up of three parts. First it takes all fields passed in to check
     * if any are empty. Any empty fields will be identified by the <code>isEmpty</code> method with the
     * field's style changed with a red outline and an error message added to the error stack. If an empty field
     * is identified, the <code>isValid</code> boolean variable is set to false, skipping the following
     * validation checks. If no fields are empty, the second part runs.</p>
     *
     * <p>The second part of this method checks for bad data types. The <code>badInputType</code> method is
     * called for each field in the stack to ensure its text value matches the appropriate data type. If the
     * text does not match the appropriate data type, an error message is added to the error stack,
     * <code>isValid</code> is set to false and the last validation step is skipped. If all fields have the
     * appropriate data type, the validation continues to the last step. During this second part, a switch
     * statements is used to collect values for min, max and inv to avoid running an additional for loop.</p>
     *
     * <p>The last part of this method checks for logical validation. If max is less than min, an error
     * is added to the stack and <code>isValid</code> is set to false. if inv is not between the min and max
     * values, an error is added to the stack and <code>isValid</code> is set to false.</p>
     *
     * <p>If <code>isValid</code> is false by the end of this method's run, the <code>printErrors()</code> method
     * is called to generate an ERROR dialog box containing the appropriate messages on the error stack.
     * <code>isValid</code> is then returned.</p>
     *
     * @param fields fields to check for validity
     * @param labels labels to use in error messages
     * @return isValid
     */
    public static Boolean validated(TextField[] fields, Label[] labels) {
        Boolean isValid = true;
        errors.clear();
        resetStyles(fields);
        TextField min, max, inv;
        min = max = inv = null;

        // Verify no fields are empty
        for (int i = 0; i < fields.length; i++) {
            if (isEmpty(fields[i], labels[i])) { isValid = false; }
        }

        // Verify provided data types & collect min, max and inv values
        if (isValid) {
            for (int i = 0; i < fields.length; i++) {
                if (badInputType(fields[i], labels[i])) { isValid = false; }
                switch(fields[i].getId()) {
                    case "textFieldMin":
                        if (isValid) { min = fields[i]; }
                        break;
                    case "textFieldMax":
                        if (isValid) { max = fields[i]; }
                        break;
                    case "textFieldInv":
                        if (isValid) { inv = fields[i]; }
                        break;
                    default:
                        break;
                }
            }
        }

        // Check for logical errors
        if (isValid) {
            int minVal = Integer.parseInt(min.getText());
            int maxVal = Integer.parseInt(max.getText());
            int invVal = Integer.parseInt(inv.getText());

            if (maxVal < minVal) {
                errors.push("Max must be larger than min value!");
                max.setStyle(errorStyle);
                isValid = false;
            }
            if (invVal < minVal | invVal > maxVal) {
                errors.push("Inv value must be between min and max!");
                inv.setStyle(errorStyle);
                isValid = false;
            }
        }

        if (!isValid) { printErrors(); }

        return isValid;
    }

    /**
     * Create an error dialog window with all stored error messages printed
     * on separate lines.
     */
    public static void printErrors() {

        String errorContent = "";
        Iterator errIterator = errors.iterator();
        while(errIterator.hasNext()) {
            errorContent += (errIterator.next() + "\n");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setContentText(errorContent);
        alert.showAndWait();

        errors.clear();

    }

}
