package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class AddProduct implements Initializable {

    /**
     * Scene variable
     */
    Parent scene;

    /**
     * Search parts text field
     */
    @FXML
    private TextField textFieldSearchParts;

    /**
     * Parts table
     */
    @FXML
    private TableView<Part> tableViewParts;

    /**
     * Parts table ID column
     */
    @FXML
    private TableColumn<Part, Integer> availPartIdCol;

    /**
     * Parts table Name column
     */
    @FXML
    private TableColumn<Part, String> availPartNameCol;

    /**
     * Parts table Stock column
     */
    @FXML
    private TableColumn<Part, Integer> availPartInvCol;

    /**
     * Parts table Price column
     */
    @FXML
    private TableColumn<Part, Double> availPartPriceCol;

    /**
     * Associated parts table
     */
    @FXML
    private TableView<Part> tableViewAssociated;

    /**
     * Associated parts table ID column
     */
    @FXML
    private TableColumn<Part, Integer> selectedPartIdCol;

    /**
     * Associated parts table Name column
     */
    @FXML
    private TableColumn<Part, String> selectedPartNameCol;

    /**
     * Associated parts table Stock column
     */
    @FXML
    private TableColumn<Part, Integer> selectedPartInvCol;

    /**
     * Associated parts table Stock column
     */
    @FXML
    private TableColumn<Part, Double> selectedPartPriceCol;

    /**
     * Product ID text field
     */
    @FXML
    private TextField textFieldId;

    /**
     * Product Name label
     */
    @FXML
    private Label labelName;

    /**
     * Product Name text field
     */
    @FXML
    private TextField textFieldName;

    /**
     * Product Stock label
     */
    @FXML
    private Label labelInv;

    /**
     * Product Stock text field
     */
    @FXML
    private TextField textFieldInv;

    /**
     * Product Price label
     */
    @FXML
    private Label labelPrice;

    /**
     * Product Price text field
     */
    @FXML
    private TextField textFieldPrice;

    /**
     * Product Maximum Stock label
     */
    @FXML
    private Label labelMax;

    /**
     * Product Maximum Stock text field
     */
    @FXML
    private TextField textFieldMax;

    /**
     * Product Minimum Stock label
     */
    @FXML
    private Label labelMin;

    /**
     * Product Minimum Stock text field
     */
    @FXML
    private TextField textFieldMin;

    /**
     * New product to be initialized
     * Initialized product will hold empty <code>associatedParts</code> list
     * to use in <code>tableViewAssociated</code>
     */
    private Product newProduct;

    /**
     * This method is called when a part is selected and the Add button is clicked.
     * It saves the part selected in the <code>tableViewParts</code> to a local variable.
     * If the selected part is not already in the <code>associatedParts</code> list, the part is added.
     *
     * Alert window is created if a NullPointerException is caught due to
     * no part being selected.
     *
     * @param event
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        try {
            Part part = tableViewParts.getSelectionModel().getSelectedItem();
            if (!inList(part.getId())) {
                newProduct.addAssociatedPart(part);
            }
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from available products table!");
            alertProblem.showAndWait();
        }
    }

    /**
     * This method is called when a part is selected from the <code>tableViewAssociated</code> table, and the
     * Remove Associated Part button is clicked. It saves the selected part to a local variable, asks the user to
     * confirm removal, and removes if the user clicks "OK".
     *
     * Alert window is created if a NullPointerException is caught due to
     * no part being selected.
     *
     * @param event
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        try {
            Part part = tableViewAssociated.getSelectionModel().getSelectedItem();

            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confirm Deletion");
            alertConfirm.setContentText("Are you sure you want to remove part " + part.getName() + "?");
            alertConfirm.showAndWait();

            if (alertConfirm.getResult().getText().equals("OK")) {
                if (newProduct.deleteAssociatedPart(part)) {
                    // Confirm deletion
                    Alert alertDeleted = new Alert(Alert.AlertType.WARNING);
                    alertDeleted.setTitle("Item Removed");
                    alertDeleted.setContentText("Part " + part.getName().toLowerCase() + " has been removed!");
                    alertDeleted.showAndWait();
                }
                else {
                    // Display error on failed deletion
                    Alert alertProblem = new Alert(Alert.AlertType.ERROR);
                    alertProblem.setTitle("Error!");
                    alertProblem.setContentText("Could not remove part " + part.getName().toLowerCase() + "!");
                    alertProblem.showAndWait();
                }
            }
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from associated products table!");
            alertProblem.showAndWait();
        }
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
     * text field and sets the respective values in <code>newProduct</code>. <code>newProduct</code>
     * is then added to the product inventory.
     *
     * When complete, the unique product ID reference is incremented and <code>returnToMain</code>
     * runs to return the GUI to the Main Form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

        // Input field validation
        TextField[] fieldArgs = {textFieldName, textFieldInv, textFieldPrice, textFieldMax, textFieldMin};
        Label[] labelArgs = {labelName, labelInv, labelPrice, labelMax, labelMin};
        Boolean isValid = Validate.validated(fieldArgs, labelArgs);

        if (isValid) {
            int idVal = Integer.parseInt(textFieldId.getText());
            String nameVal = textFieldName.getText();
            Double priceVal = Double.parseDouble(String.format("%.2f", Double.parseDouble(textFieldPrice.getText())));
            int invVal = Integer.parseInt(textFieldInv.getText());
            int minVal = Integer.parseInt(textFieldMin.getText());
            int maxVal = Integer.parseInt(textFieldMax.getText());

            newProduct.setId(idVal);
            newProduct.setName(nameVal);
            newProduct.setPrice(priceVal);
            newProduct.setStock(invVal);
            newProduct.setMin(minVal);
            newProduct.setMax(maxVal);

            Inventory.addProduct(newProduct);

            Inventory.incProductIdRef();
            returnToMain(event);
        }
    }

    /**
     * <code>newProduct</code> is initialized so there is an <code>associatedParts</code> list
     * to work with. <code>setTableItems</code> is run to set the filtered parts list and
     * the newly initialized product's associated parts list to their respective tables.
     *
     * All columns for both tables are then populated with the appropriate fields.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        newProduct = new Product(0, null, 0, 0, 0, 0);
        setTableItems();

        // Fill ID field with incremented value
        textFieldId.setText(String.valueOf(Inventory.getProductIdRef() + 1));

        availPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        availPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        selectedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        selectedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        selectedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        selectedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Creates a filtered list of available parts, associated with the search field.
     * The list is filtered to match search field entries with part name, or part ID.
     *
     * Both tables then have their items set to the appropriate observable list.
     */
    public void setTableItems() {
        FilteredList<Part> searchPartsList = new FilteredList<>(Inventory.getAllParts(), b -> true);
        textFieldSearchParts.textProperty().addListener((observable, oldVal, newVal) -> {
            searchPartsList.setPredicate(part -> {

                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                if (part.getName().toLowerCase().indexOf(newVal.toLowerCase()) != -1) {
                    return true;
                }
                else if (String.valueOf(part.getId()).toLowerCase().indexOf(newVal.toLowerCase()) != -1) {
                    return true;
                }
                else {
                    Alert alertProblem = new Alert(Alert.AlertType.WARNING);
                    alertProblem.setTitle("WARNING!");
                    alertProblem.setContentText("No search results found!");
                    alertProblem.showAndWait();
                    return false;
                }
            });
        });
        tableViewParts.setItems(searchPartsList);
        tableViewAssociated.setItems(newProduct.getAllAssociatedParts());

    }

    /**
     * This method checks to see if the part ID passed to it already exists in the
     * products <code>associatedParts</code> list. If it does, it returns true. If it does not exist,
     * the method returns false.
     *
     * @param id
     * @return
     */
    public Boolean inList(int id) {
        for (Part part : newProduct.getAllAssociatedParts()) {
            if (part.getId() == id) {
                return true;
            }
        }
        return false;
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
