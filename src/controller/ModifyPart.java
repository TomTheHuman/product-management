package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class ModifyPart implements Initializable {

    /**
     * Scene variable
     */
    Parent scene;

    /**
     * Part ID text field
     */
    @FXML
    private TextField textFieldId;

    /**
     * Part Name text field
     */
    @FXML
    private TextField textFieldName;

    /**
     * Part Name label
     */
    @FXML
    private Label labelName;

    /**
     * Part Stock text field
     */
    @FXML
    private TextField textFieldInv;

    /**
     * Part Stock label
     */
    @FXML
    private Label labelInv;

    /**
     * Part Price text field
     */
    @FXML
    private TextField textFieldPrice;

    /**
     * Part Price label
     */
    @FXML
    private Label labelPrice;

    /**
     * Part Maximum Stock text field
     */
    @FXML
    private TextField textFieldMax;

    /**
     * Part Maximum Stock label
     */
    @FXML
    private Label labelMax;

    /**
     * Part Machine ID text field
     */
    @FXML
    private TextField textFieldMachineId;

    /**
     * Part Machine ID label
     */
    @FXML
    private Label labelMachineId;

    /**
     * Part Minimum Stock text field
     */
    @FXML
    private TextField textFieldMin;

    /**
     * Part Minimum Stock label
     */
    @FXML
    private Label labelMin;

    /**
     * Part Company Name text field
     */
    @FXML
    private TextField textFieldCompanyName;

    /**
     * Part Company Name label
     */
    @FXML
    private Label labelCompanyName;

    /**
     * Part In-House radio button
     */
    @FXML
    private RadioButton radioInHouse;

    /**
     * Part Outsourced radio button
     */
    @FXML
    private RadioButton radioOutsourced;

    /**
     * Index of selected part
     */
    private int selectedIndex;

    /**
     * Sets private variable <code>selectedIndex</code> to value passed in
     *
     * @param index
     */
    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }

    /**
     * This method is called when either the <code>radioInHouse</code>
     * or <code>radioOutsourced</code> radio buttons are clicked. This method
     * checks to see which of the two buttons are selected, and changes the
     * visibility property of the respective label and text field.
     *
     * @param event
     */
    @FXML
    void onActionChangeFields(ActionEvent event) {
        // Set Machine ID label and input field visibility
        textFieldMachineId.setVisible(radioInHouse.isSelected());
        labelMachineId.setVisible(radioInHouse.isSelected());

        // Set Company Name label and input field visibility
        textFieldCompanyName.setVisible(radioOutsourced.isSelected());
        labelCompanyName.setVisible(radioOutsourced.isSelected());
    }

    /**
     * This method is called when the cancel button is clicked. It
     * runs the <code>returnToMain</code> method to return the GUI to
     * the Main Form without saving any changes.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        returnToMain(event);
    }

    /**
     * This method is called when the save button is clicked.
     * It first saves text fields and their respective labels to arrays of each data type.
     * <code>Validate.validated</code> is then run with the arrays of fields to check for
     * input validity. If all fields pass the validation check, a true value is returned
     * and saved to <code>isValid</code>.
     *
     * If <code>isValid</code> is true, the method parses the strings entered into each
     * text field. A new part is created and passed to the <code>Inventory.updatePart</code> method,
     * as well as the index location of the part being replaced.
     *
     * <code>returnToMain</code> runs to return the GUI to the Main Form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws IOException {


        // Input field validation
        TextField[] fieldArgs = {textFieldName, textFieldInv, textFieldPrice, textFieldMax, textFieldMin, (radioInHouse.isSelected() ? textFieldMachineId : textFieldCompanyName)};
        Label[] labelArgs = {labelName, labelInv, labelPrice, labelMax, labelMin, (radioInHouse.isSelected() ? labelMachineId : labelCompanyName)};
        Boolean isValid = Validate.validated(fieldArgs, labelArgs);

        if (isValid) {
            int idVal = Integer.parseInt(textFieldId.getText());
            String nameVal = textFieldName.getText();
            Double priceVal = Double.parseDouble(String.format("%.2f", Double.parseDouble(textFieldPrice.getText())));
            int invVal = Integer.parseInt(textFieldInv.getText());
            int minVal = Integer.parseInt(textFieldMin.getText());
            int maxVal = Integer.parseInt(textFieldMax.getText());

            if (radioInHouse.isSelected()) {
                int machineIdVal = Integer.parseInt(textFieldMachineId.getText());
                Inventory.updatePart(selectedIndex, new InHouse(idVal, nameVal, priceVal, invVal, minVal, maxVal, machineIdVal));
            } else if (radioOutsourced.isSelected()) {
                String compNameVal = textFieldCompanyName.getText();
                Inventory.updatePart(selectedIndex, new Outsourced(idVal, nameVal, priceVal, invVal, minVal, maxVal, compNameVal));
            }

            returnToMain(event);
        }
    }

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * This method is called from the Main Form to pass in the selected part
     * to be modified. The Modify Part text fields are populated with the values
     * passed by that part.
     *
     * @param index
     * @param selected
     */
    public void selectPart(int index, Part selected) {
        setSelectedIndex(index);

        textFieldId.setText(String.valueOf(selected.getId()));
        textFieldName.setText(String.valueOf(selected.getName()));
        textFieldInv.setText(String.valueOf(selected.getStock()));
        textFieldPrice.setText(String.valueOf(selected.getPrice()));
        textFieldMax.setText(String.valueOf(selected.getMax()));
        textFieldMin.setText(String.valueOf(selected.getMin()));

        if (selected instanceof InHouse) {
            radioInHouse.setSelected(true);
            onActionChangeFields(null);
            textFieldMachineId.setText(String.valueOf(((InHouse) selected).getMachineId()));
        } else {
            radioOutsourced.setSelected(true);
            onActionChangeFields(null);
            textFieldCompanyName.setText(String.valueOf(((Outsourced) selected).getCompanyName()));
        }
    }

    /**
     * This method returns the GUI back to the Main Form
     *
     * @param event
     * @throws IOException
     */
    public void returnToMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
