package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to store static lists of parts and products as well as
 * essential inventory management methods and variables.
 */
public class Inventory {

    /**
     * Observable list of all parts in inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Observable list of all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Static part ID reference for creating new parts
     */
    private static int partIdRef = 0;

    /**
     * Static product ID reference for creating new products
     */
    private static int productIdRef = 0;

    /**
     * Adds part from parameter to <code>allParts</code> inventory
     *
     * @param newPart newPart to add to inventory
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product from parameter to <code>allProducts</code> inventory
     *
     * @param newProduct newProduct to add to inventory
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * This method takes an ID passed in and finds the part that matches it.
     * If found, the matching part is returned. If not found, null is returned.
     *
     * @param partId partId to lookup
     * @return part
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * This method takes an ID passed in and finds the part that matches it.
     * If found, the matching index location is returned. If not found, index -1 is returned.
     *
     * @param partId partId to lookup index
     * @return index
     */
    public static int lookupPartIndex(int partId) {
        int index = 0;

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * This method is not implemented.
     *
     * @param productId
     * @return null
     */
    public Product lookupProduct(int productId) {
        return null;
    }

    /**
     * This method takes an ID passed in and finds the product that matches it.
     * If found, the matching index location is returned. If not found, index -1 is returned.
     *
     * @param productId productId to lookup
     * @return index
     */
    public static int lookupProductIndex(int productId) {
        int index = 0;

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * This method is not implemented.
     *
     * @param partName
     * @return nulls
     */
    public ObservableList<Part> lookupPart(String partName) {
        return null;
    }

    /**
     * This method is not implemented.
     *
     * @param productName
     * @return
     */
    public ObservableList<Product> lookupProduct(String productName) {
        return null;
    }

    /**
     * This method uses the set method to find the part at an index location and
     * update it with an updated part passed in.
     *
     * @param index index to locate
     * @param selectedPart selectedPart to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * This method is not implemented
     *
     * @param index index to locate
     * @param newProduct newProduct to update
     */
    public static void updateProduct(int index, Product newProduct) {
    }

    /**
     * This method takes the part passed in, locates it in the parts inventory and
     * removes the item at its index.
     *
     * @param selectedPart selectedPart to delete from allParts
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        int index = 0;

        for (Part part : allParts) {
            if (part.getId() == selectedPart.getId()) {
                allParts.remove(index, index+1);
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * This method takes the product passed in, locates it in the product inventory and
     * removes the item at its index.
     *
     * @param selectedProduct selectedProduct to delete from allProducts
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int index = 0;

        for (Product product : allProducts) {
            if (product.getId() == selectedProduct.getId()) {
                allProducts.remove(index, index+1);
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * @return partIdRef
     */
    public static int getPartIdRef() { return partIdRef; }

    /**
     * Increments static part Id reference
     */
    public static void incPartIdRef() { partIdRef += 1; }

    /**
     * @return productIdRef
     */
    public static int getProductIdRef() { return productIdRef; }

    /**
     * Increments static product Id reference
     */
    public static void incProductIdRef() { productIdRef += 1; }


}
