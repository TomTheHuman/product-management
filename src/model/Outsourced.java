package model;

/**
 * This class is an extension of the <code>Part</code> class with the addition
 * of <code>companyName</code> and its getter/setter methods.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * The superclass constructor is called to create a part. The machine Id is set
     * by the passed parameter.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @param companyName companyName to set
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * @return companyName
     */
    public String getCompanyName() { return companyName; }

}
