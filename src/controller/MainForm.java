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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

public class MainForm implements Initializable {

    /**
     * Stage variable
     */
    Stage stage;

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
    private TableColumn<Part, Integer> partIdCol;

    /**
     * Parts table Name column
     */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /**
     * Parts table Stock column
     */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /**
     * Parts table Price column
     */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /**
     * Search products text field
     */
    @FXML
    private TextField textFieldSearchProducts;

    /**
     * Products table
     */
    @FXML
    private TableView<Product> tableViewProducts;

    /**
     * Products table ID column
     */
    @FXML
    private TableColumn<Product, Integer> productIdCol;

    /**
     * Products table Name column
     */
    @FXML
    private TableColumn<Product, String> productNameCol;

    /**
     * Products table Stock column
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;

    /**
     * Products table Price column
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * When the Add button is clicked in the part pane, this method is called. It displays
     * the add part form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * When the Add button is clicked in the product pane, this method is called. It displays
     * the add product form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Selected part from part table is saved to local variable. The user is asked to confirm the deletion.
     * If confirmed, the part is deleted from inventory.
     *
     * An error will appear if the user does not select an item before clicking the Delete button.
     *
     * @param event
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        try {
            // Get selected part
            Part selected = tableViewParts.getSelectionModel().getSelectedItem();

            // Display confirmation message before deleting
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("Confirm Deletion");
            alertConfirm.setContentText("Are you sure you want to delete part " + selected.getName() + "?");
            alertConfirm.showAndWait();

            // Check for user confirmation response
            if (alertConfirm.getResult().getText().equals("OK")) {
                if (Inventory.deletePart(selected)) {
                    // Confirm deletion
                    Alert alertDeleted = new Alert(Alert.AlertType.WARNING);
                    alertDeleted.setTitle("Item Deleted");
                    alertDeleted.setContentText("Part " + selected.getName() + " has been deleted!");
                    alertDeleted.showAndWait();
                }
                else {
                    // Display error on failed deletion
                    Alert alertProblem = new Alert(Alert.AlertType.ERROR);
                    alertProblem.setTitle("Error!");
                    alertProblem.setContentText("Could not delete part " + selected.getName() + "!");
                    alertProblem.showAndWait();
                }
            }
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from parts table!");
            alertProblem.showAndWait();
        }
    }

    /**
     * Selected product from product table is saved to local variable. If the product has no
     * associated parts, then the user is asked to confirm the deletion. If confirmed, the product
     * is deleted from inventory.
     *
     * An error will appear if the user does not select an item before clicking the Delete button.
     *
     * @param event
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        try {
            // Get selected part
            Product selected = tableViewProducts.getSelectionModel().getSelectedItem();
            if (selected.getAllAssociatedParts().size() == 0) {
                // Display confirmation message before deleting
                Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                alertConfirm.setTitle("Confirm Deletion");
                alertConfirm.setContentText("Are you sure you want to delete product " + selected.getName() + "?");
                alertConfirm.showAndWait();

                // Check for user confirmation response
                if (alertConfirm.getResult().getText().equals("OK")) {
                    if (Inventory.deleteProduct(selected)) {
                        // Confirm deletion
                        Alert alertDeleted = new Alert(Alert.AlertType.WARNING);
                        alertDeleted.setTitle("Item Deleted");
                        alertDeleted.setContentText("Product " + selected.getName() + " has been deleted!");
                        alertDeleted.showAndWait();
                    }
                    else {
                        // Display error on failed deletion
                        Alert alertProblem = new Alert(Alert.AlertType.ERROR);
                        alertProblem.setTitle("Error!");
                        alertProblem.setContentText("Could not delete product " + selected.getName() + "!");
                        alertProblem.showAndWait();
                    }
                }
            } else {
                Alert alertProblem = new Alert(Alert.AlertType.ERROR);
                alertProblem.setTitle("Error!");
                alertProblem.setContentText("Item cannot be deleted with associated parts!");
                alertProblem.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from products table!");
            alertProblem.showAndWait();
        }
    }

    /**
     * Exits the program with status code 0 when the Exit button is clicked
     *
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Modify Part form is loaded. New controller variable is created and used to pass
     * the part selected in the parts table, and its index value, to the Modify Part form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {
            // Set form to be loaded
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/ModifyPart.fxml"));
            loader.load();

            // Set controller reference & pass part to modify
            ModifyPart modPartController = loader.getController();
            int index = Inventory.lookupPartIndex(tableViewParts.getSelectionModel().getSelectedItem().getId());
            modPartController.selectPart(index, tableViewParts.getSelectionModel().getSelectedItem());

            // Navigate to Modify Part window
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from parts table!");
            alertProblem.showAndWait();
        }
    }

    /**
     * Modify Product form is loaded. New controller variable is created and used to pass
     * the product selected in the products table, and its index value, to the Modify Product form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {

        try {
            // Set form to be loaded
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/ModifyProduct.fxml"));
            loader.load();

            // Set controller reference & pass part to modify
            ModifyProduct modProductController = loader.getController();
            int index = Inventory.lookupProductIndex(tableViewProducts.getSelectionModel().getSelectedItem().getId());
            modProductController.selectProduct(index, tableViewProducts.getSelectionModel().getSelectedItem());

            // Navigate to Modify Part window
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alertProblem = new Alert(Alert.AlertType.ERROR);
            alertProblem.setTitle("Error!");
            alertProblem.setContentText("No item was selected from products table!");
            alertProblem.showAndWait();
        }
    }

    /**
     * Creates a filtered list of all parts and all products associated with their search fields.
     * The list is filtered to match search field entries by name or ID.
     *
     * Both tables then have their items set to their appropriate filtered list.
     *
     */
    private void setTableItems() {
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

        FilteredList<Product> searchProductsList = new FilteredList<>(Inventory.getAllProducts(), b -> true);
        textFieldSearchProducts.textProperty().addListener((observable, oldVal, newVal) -> {
            searchProductsList.setPredicate(product -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                if (product.getName().toLowerCase().indexOf(newVal.toLowerCase()) != -1) {
                    return true;
                }
                else if (String.valueOf(product.getId()).toLowerCase().indexOf(newVal.toLowerCase()) != -1) {
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
        tableViewProducts.setItems(searchProductsList);
    }

    /**
     * <code>setTableItems</code> is ran to set the filtered lists to their respective tables.
     *
     * All columns for both tables are then populated with the appropriate fields.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setTableItems();

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
