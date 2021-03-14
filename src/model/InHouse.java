package model;

/**
 * This class is an extension of the <code>Part</code> class with the addition
 * of <code>machineId</code> and its getter/setter methods.
 */
public class InHouse extends Part {
    private int machineId;

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
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId machineId to set
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }

    /**
     * @return machineId
     */
    public int getMachineId() { return machineId; }

}
