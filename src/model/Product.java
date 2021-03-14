package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class used for creating new product objects
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     *
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min min to set
     * @param max max to set
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * @param min the min to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * @param max the max to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * @return the id
     */
    public int getId() { return id; }

    /**
     * @return the name
     */
    public String getName() { return name; }

    /**
     * @return the price
     */
    public double getPrice() { return price; }

    /**
     * @return the stock
     */
    public int getStock() { return stock; }

    /**
     * @return the min
     */
    public int getMin() { return min; }

    /**
     * @return the max
     */
    public int getMax() { return max; }

    /**
     * @param part part to add to associatedParts
     */
    public void addAssociatedPart(Part part) { associatedParts.add(part); }

    /**
     * @param selectedAssociatedPart part to be deleted from associatedParts
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        int index = 0;

        for (Part part : associatedParts) {
            if (part.getId() == selectedAssociatedPart.getId()) {
                associatedParts.remove(index, index+1);
                return true;
            }
            index++;
        }
        return false;
    }

    /**
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
